import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;

/**
 * The main display class
 */
public class Easel extends JFrame implements MouseWheelListener //trying to fix stuff
{
    private JPanel panel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel(this);
    private MenuPanel menuPanel = new MenuPanel(this);
    private JColorChooser colorChooser = new JColorChooser(); //currently stores current pen colour
    private ArrayList<Canvas> canvases = new ArrayList<>();
    private GridLayout smallGrid = new GridLayout(2,1);
    private JPanel canvasNoPanel = new JPanel();
    private JLabel canvasNoLabel1 = new JLabel("   Current Canvas:");
    private ImageIcon icon = new ImageIcon("icons/logo.png");

    private JLabel canvasNoLabel2 = new JLabel("1");
    private int currentCanvas=0;

    private int canvasHeight;
    private int canvasWidth;
    private Color backgroundColour;
    private ToolInterface currentTool;
    private static int MAX_WIDTH = 650;
    private static int MAX_HEIGHT = 775;
    private boolean layeredDrawing = false;
    private boolean playing = false;
    private String fileName;

    private String filePath;
    private ArrayList<ArrayList<Canvas>> storedCanvases = new ArrayList<>();
    private boolean action=false;
    private Timer timer = new Timer();
    private HelpPage helpPage;


    /**
     * Creates the main frame that holds the canvas and all the buttons, also the colour selector
     */
    public Easel(int canvasHeight, int canvasWidth, Color backgroundColour)
    {
        setTitle("The Easel");
        // setSize(600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.backgroundColour = backgroundColour;
        this.setIconImage(icon.getImage());
        colorChooser.setColor(backgroundColour);
        

        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new BorderLayout());
        panel.add(toolPanel, BorderLayout.WEST);
        panel.add(menuPanel, BorderLayout.NORTH);
        canvases.add(new Canvas(this, canvasHeight, canvasWidth, backgroundColour));
        storedCanvases.add(new ArrayList<>()); //adds the initial backup list for the first canvas
        storedCanvases.get(0).add(canvases.get(0)); //adds the first canvas to the list of backups
        panel.add(canvases.get(canvases.size()-1), BorderLayout.CENTER);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(colorChooser, BorderLayout.CENTER);
        canvasNoPanel.setLayout(smallGrid);
        canvasNoPanel.add(canvasNoLabel1);
        canvasNoPanel.add(canvasNoLabel2);
        canvasNoLabel2.setFont(new Font("Dialog", Font.PLAIN, 36));
        bottomPanel.add(canvasNoPanel, BorderLayout.WEST);
        panel.add(bottomPanel,BorderLayout.SOUTH);
        canvasNoLabel1.setHorizontalAlignment(JLabel.CENTER);
        canvasNoLabel1.setVerticalAlignment(JLabel.CENTER);
        canvasNoLabel2.setHorizontalAlignment(JLabel.CENTER);
        canvasNoLabel2.setVerticalAlignment(JLabel.TOP);

        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
        for(AbstractColorChooserPanel a : panels) { if(!a.getDisplayName().equals("Swatches")) { colorChooser.removeChooserPanel(a); }}
        colorChooser.setPreviewPanel(new JPanel()); //hides preview panel
        colorChooser.setColor(Color.BLACK); //sets starting colour to black

        this.addMouseWheelListener(this);

        add(panel);
        setBounds(200, 50, MAX_WIDTH, MAX_HEIGHT);

        setResizable(false);
        setVisible(true);
        this.helpPage = new HelpPage(this.getX(), this.getY(), MAX_WIDTH);
        menuPanel.setHelpPage(helpPage);

        helpPage.setVisible(true);
    }

    /**
     * @return returns the current colour selected in the colour chooser
     */
    public Color getCurrentColor() { return colorChooser.getColor(); }

    /**
     * Sets the currently selected colour in the colour chooser
     * @param newColour the new colour
     */
    public void setCurrentColour(Color newColour) { colorChooser.setColor(newColour); }
    public Color getBackgroundColour() { return backgroundColour; }


    /**
     * @return returns the current penSize selected by the spinner in the tool menu
     */
    public int getPenSize() { return toolPanel.getPenSize(); }

    /**
     * Will increment the number of canvases and create a new canvas then add it to the display
     */
    public void createNewCanvas()
    {
        canvases.add(new Canvas(this, canvasHeight, canvasWidth, backgroundColour));
        storedCanvases.add(new ArrayList<>()); //creates the new backup list for the new canvas
        panel.add(canvases.get(canvases.size()-1));
        canvases.get(currentCanvas).setVisible(false);
        canvases.get(canvases.size()-1).setVisible(true);
    }

    /**
     * Will change canvases when the user changes the value in the spinner
     * Hides the current canvas, shows the target canvas and updates the current canvas value
     * @param newCanvas the new canvas index to show
     */
    public void changeCanvas(int newCanvas)
    {
        //check there is a canvas
        canvases.get(currentCanvas).setVisible(false);
        canvases.get(newCanvas).setVisible(true);
        currentCanvas=newCanvas;
        canvasNoLabel2.setText(String.valueOf(currentCanvas+1));
    }
    /**
     * Creates a new canvas, retrieves the pixels of the current canvas
     * Will then set the pixels to the new canvas, 
     * add it to the canvases list, in the position after the current canvas
     * hides the current canvas to show the duplicate canvas
     */
    public void duplicateCurrentCanvas()
    {
        Canvas copyCanvas = new Canvas(this, canvasHeight, canvasWidth, backgroundColour);
        Pixel[][] pixels = canvases.get(currentCanvas).getPixels();
        copyCanvas.setPixels(pixels);
        canvases.add((currentCanvas+1),copyCanvas);
        storedCanvases.add((currentCanvas+1),new ArrayList<>());
        panel.add(canvases.get(currentCanvas+1));
        canvases.get(currentCanvas).setVisible(false);
        canvases.get(currentCanvas+1).setVisible(true);
        currentCanvas=currentCanvas+1;

    }

    /**
     * Hides the current canvas, then removes it from the array list
     * Will then show the next correct canvas
     * If there are no canvases creates a new canvas
     */
    public void deleteCurrentCanvas() {
        canvases.get(currentCanvas).setVisible(false);
        canvases.remove(currentCanvas);
        storedCanvases.remove(currentCanvas); //removes all stored backup canvases as they are no longer needed
        if(canvases.isEmpty()) {
            storedCanvases.add(new ArrayList<>()); //adds an empty array
            currentCanvas=0;
            createNewCanvas();
            storedCanvases.get(0).add(canvases.get(0)); //adds the new blank canvas to the backup
        } else {
            currentCanvas--;
            if(currentCanvas<0) { currentCanvas=0; }
            canvases.get(currentCanvas).setVisible(true);
        }
    }

    /**
     * Is called when the save button is pressed
     * If the path and name have not been set before, will open a window allowing the user to choose where to save the project
     * If the path and name are set, will save the files
     * Also creates a metadata file that stores basic information that relates to all canvases
     */
    public void save(Boolean first) {

        if(fileName==null || filePath==null) {
            JFileChooser files = new JFileChooser();
            files.setDialogTitle("Select a folder");
            files.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = files.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (files.getSelectedFile().mkdir()) {
                    filePath = files.getSelectedFile().getPath();
                    fileName = files.getSelectedFile().getName();
                }
            }
        }
        if(fileName!=null && filePath!=null) {
            //System.out.println("test");
            File file = new File(filePath + "/" + fileName + "Metadata.txt");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("Width:" + canvasWidth + "\n");
                writer.write("Height:" + canvasHeight + "\n");
                writer.write("Background Colour:" + backgroundColour.getRGB() + "\n");
                writer.write("Number of canvases:" + canvases.size()+ "\n");
                writer.close();
                canvases.forEach(this::saveCanvas);
            } catch (IOException e) {
                System.out.println("Failed to write to file.");
            }
            if (first) { //if the user has clicked the save button, restart auto save
                timer.cancel();
                timer = new Timer();
                autoSave();
            }
        }
    }

    /**
     * After the user has clicked save and the system has saved the canvas, start auto-saving every 15 seconds
     */
    public void autoSave() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                save(false);
            }
        }, 15000, 15000);
    }

    /**
     * Takes a canvas a writes the pixel data into an appropriately named file in the target folder
     * If the file already exists it will override it, if not it will create a file
     * Writes a text file with all the pixel RGBs in a comma separated 2D array
     * @param canvas the target canvas
     */
    private void saveCanvas(Canvas canvas) {
        int number = canvases.indexOf(canvas);
        String filename = filePath+"/"+fileName+number+".txt";
        File file = new File(filename);
        Pixel[][] pixels = canvas.getPixels();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int y = 0 ; y<canvasHeight;y++) {
                for (int x = 0; x<canvasWidth;x++) {
                    writer.write(pixels[x][y].getBackground().getRGB()+",");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("error 2");
        }
    }
    /**User selects an image folder of canvases to be loaded to the easel*/
    public void load()
    {
        timer.cancel();
        //warning are you sure you want to load a new image, this will delete current canvases - see layered drawing toolbutton
        int result = JOptionPane.showConfirmDialog(null, "Do you want to save the current project?", "Warning", JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION) {
            if(fileName==null && filePath==null) {
                save(true);
            } else {
                save(false);
            }
        }
        canvases.forEach(canvas -> canvas.setVisible(false));
        System.out.println(canvases.size());
        //remove stored canvases (undo)
        JFileChooser files = new JFileChooser();
        files.setDialogTitle("Select a folder");
        files.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnValue = files.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            filePath = files.getSelectedFile().getPath();
            fileName = files.getSelectedFile().getName();
            canvases.removeAll(canvases);
        }

        //validate file type
        int noCanvases = 0;
        if(fileName!=null && filePath!=null) {noCanvases = readMetadata();}

        for(int i = 0; i<1; i++)
        {
            try {
                File file = new File(filePath + "/" + fileName + i + ".txt");
                System.out.println("Loading page " + i + " ...");
                Scanner read = new Scanner(file);
                Canvas canvas = new Canvas(this, canvasHeight, canvasWidth, backgroundColour);
                Pixel[][] pixels = canvas.getPixels();

                canvas.setVisible(false);
                canvases.add(canvas);
                for(int y = 0; y< canvasHeight; y++)
                {
                    if(read.hasNextLine())
                    {
                        String[] line = read.nextLine().split(",");
                        for(int x = 0; x < canvasWidth; x++) {
                            Color colour = Color.decode(line[x]);
                            if(x<canvasWidth) pixels[x][y].setBackground(colour);
                        }
                    }
                }
                canvas.setPixels(pixels);                
                panel.add(canvas, BorderLayout.CENTER);

                read.close();
            } catch (Exception e) {
                System.out.println("End of files.");
            }
        }
        System.out.println(canvases.size());
        canvases.get(0).setVisible(true);
        currentCanvas=0;
    }
    /**Reads metadata (canvas size, background colour, no. canvases) from selected image folder's metadata file*/
    public int readMetadata()
    {
        int noCanvases = 0;
        File file = new File(filePath + "/" + fileName + "Metadata.txt");
        try{
            Scanner read = new Scanner(file);
            canvasWidth = Integer.parseInt(read.nextLine().replaceFirst("Width:", ""));
            canvasHeight = Integer.parseInt(read.nextLine().replaceFirst("Height:", ""));
            backgroundColour = Color.decode(read.nextLine().replaceFirst("Background Colour:", ""));
            noCanvases = Integer.parseInt(read.nextLine().replaceFirst("Number of canvases:", ""));
            
            read.close();
        } catch (Exception e) {
            System.out.println("File read error");
        }
        return noCanvases;
    }

    /**
     * Will get the currently selected tool
     * @return the current tool
     */
    public ToolInterface getTool() { return currentTool; }

    /**
     * Will set the current tool to a new tool
     * @param tool the new tool
     */
    public void setTool(ToolInterface tool) { this.currentTool = tool; }

    /**
     * Will get the current canvas
     * @return the canvas
     */
    public Canvas getCurrentCanvas() { return canvases.get(currentCanvas); }

    /**
     * Sets the variable layeredDrawing to the requested state
     * @param state the target state of the variable
     */
    public void setLayeredDrawing(boolean state) { layeredDrawing = state; }

    /**
     * Gets the current value of the layeredDrawing variable
     * @return the current value of the layered drawing variable
     */
    public boolean getLayeredDrawing() { return layeredDrawing; }

    /**
     * Will return a list of all current canvases on the easel
     * @return all the canvases as an array list
     */
    public ArrayList<Canvas> getAllCanvases() { return canvases; }

    /**
     * Will set the current value of the playing variable
     * @param state the target state
     */
    public void setPlaying(boolean state) { playing = state; }

    /**
     * Gets the current value of the playing variable
     * @return the current state
     */
    public boolean isPlaying() { return playing; }
    
    /**
     * Returns canvas width
     * @return canvasWidth canvas width in pixels
     */
    public int getCanvasWidth(){return canvasWidth;}

    /**
     * Returns canvas height
     * @return canvasHeight canvas height in pixels
     */
    public int getCanvasHeight(){return canvasHeight;}

    /*Get tool in position i of tool panel */
    //public ToolInterface getToolAt(int i) {return toolPanel.getToolButtonAt(i).getTool();}

    /**Get tool button in position i of tool panel */
    public ToolButton getToolButtonAt(int i) {return toolPanel.getToolButtonAt(i);}

    /**
     * Will mark the start of an action by the user
     * Will take a copy of the current canvas and store it in an array of arrays, so it can be recalled in undo
     */
    public void startAction() {
        if(!action) {
            //System.out.println(storedCanvases.get(currentCanvas).get(storedCanvases.get(currentCanvas).size()-1).getPixelAt(0,0).getCurrentColor());
            Pixel[][]oldPixels = canvases.get(currentCanvas).getPixels();
            Canvas oldCanvas = new Canvas(this, canvasHeight, canvasWidth, backgroundColour);
            oldCanvas.setPixels(oldPixels);
            storedCanvases.get(currentCanvas).add(oldCanvas);
            //System.out.println(storedCanvases.get(currentCanvas).get(storedCanvases.get(currentCanvas).size()-1).getPixelAt(0,0).getCurrentColor());
            action=true;
        }
    }

    /**
     * Marks the end of an action, ready for the next one
     */
    public void stopAction() { action = false; }

    /**
     * Will get the most recent update to the canvas and restore it
     * Modifies the list, so it can be done again
     */
    public void undo() {
        ArrayList<Canvas> tempArray = storedCanvases.get(currentCanvas);
        if(tempArray.size()>0) {
            Canvas latestCanvas = tempArray.get(tempArray.size()-1);
            canvases.get(currentCanvas).setPixels(latestCanvas.getPixels());
            tempArray.remove(latestCanvas);
        }
    }
    /**Returns index of current canvas
     * @return current canvas number
     */
    public int getCanvasNumber() { return currentCanvas; }
    /**Allows user to change current canvas using mouse scroll
     * @param mouseWheelEvent registered mouse wheel movement 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        if(mouseWheelEvent.getWheelRotation()<0) {
            menuPanel.setScrollValue(currentCanvas+2);
        } else if(mouseWheelEvent.getWheelRotation()>0) {
            menuPanel.setScrollValue(currentCanvas);
        }
        mouseWheelEvent.consume();
    }

    public HelpPage getHelpPage() { return helpPage; }
}

