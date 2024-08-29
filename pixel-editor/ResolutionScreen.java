import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ResolutionScreen extends JFrame implements ActionListener //trying to fix stuff
{
    private JPanel mainPanel = new JPanel();
    private JPanel subPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JLabel topLabel = new JLabel("Enter the display height and width in pixels:", SwingConstants.CENTER);
    private int defaultHeight = 30;
    private int defaultWidth = 30;
    private JLabel heightText = new JLabel("Height:", SwingConstants.CENTER);
    private JTextField inputHeight = new JTextField();
    private JLabel widthText = new JLabel("Width:", SwingConstants.CENTER);
    private JTextField inputWidth = new JTextField();
    private JLabel colourLabel = new JLabel("Please select a background colour. Leaving it blank will default to white.", SwingConstants.CENTER);
    private JColorChooser backgroundColourChooser = new JColorChooser();
    private JButton submitButton = new JButton("Submit");
    private ImageIcon icon = new ImageIcon("icons/logo.png");

    private int resolutionHeight = 0;
    private int resolutionWidth = 0;
    private Color backgroundColour;

    private GridLayout labelLayout = new GridLayout(2, 2);
    private GridLayout mainLayout = new GridLayout(3,1);
    private GridLayout subLayout = new GridLayout(3,1);

    private String enteredHeight;
    private String enteredWidth;

    /**
     * Creates the screen allowing the user to customise the screen resolution and background colour
     */
    public ResolutionScreen()
    {
        this.setTitle("Set Resolution");
        this.setSize(550, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        mainPanel.setLayout(mainLayout);
        labelPanel.setLayout(labelLayout);
        subPanel.setLayout(subLayout);

        subPanel.add(topLabel);

        labelPanel.add(heightText);
        inputHeight.setText("" + defaultHeight);
        labelPanel.add(inputHeight);

        labelPanel.add(widthText);
        inputWidth.setText("" + defaultWidth);
        labelPanel.add(inputWidth);

        subPanel.add(labelPanel);
        mainPanel.add(subPanel);

        subPanel.add(colourLabel);
        mainPanel.add(backgroundColourChooser);
        //Updates the colour chooser, so it only has the swatches tab
        AbstractColorChooserPanel[] panels = backgroundColourChooser.getChooserPanels();
        for(AbstractColorChooserPanel a : panels) { if(!a.getDisplayName().equals("Swatches")) { backgroundColourChooser.removeChooserPanel(a); }}
        backgroundColourChooser.setPreviewPanel(new JPanel()); //hides preview panel

        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 30));

        mainPanel.add(submitButton);
        submitButton.addActionListener(this);

        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    /**
     * Will listen for a button press and then create an easel, with a canvas of the desired size
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            enteredHeight = inputHeight.getText();
            char[] enteredHeightChar = enteredHeight.toCharArray();
            enteredWidth = inputWidth.getText();
            char[] enteredWidthChar = enteredWidth.toCharArray();

            //code to get the text and add to variables, also gets background colour
        
            if(backgroundColourChooser.getColor().getRGB()!=-1118482) //checks to make sure a blank colour is not selected.
            {
                try
                {   
                    this.resolutionHeight = Integer.valueOf(inputHeight.getText());
                    this.resolutionWidth = Integer.valueOf(inputWidth.getText());
                    backgroundColour = backgroundColourChooser.getColor();

                    int height = Integer.parseInt(enteredHeight);
                    int width = Integer.parseInt(enteredWidth);

                    if (height > 100 || height < 0){
                        JOptionPane.showMessageDialog(mainPanel, "Please only enter a height between 0 and 100","Invalid Height",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else if (width > 100 || width < 0){
                        JOptionPane.showMessageDialog(mainPanel, "Please only enter a width between 0 and 100","Invalid Width",JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    if(resolutionHeight != 0 && resolutionWidth != 0){
                        this.setVisible(false);
                        Easel easel = new Easel(resolutionHeight, resolutionWidth, backgroundColour);
                    }
                }
                catch(NumberFormatException nfe)
                {
                    if (inputHeight.getText().isEmpty() || inputWidth.getText().isEmpty()){
                        JOptionPane.showMessageDialog(mainPanel, "Please fill in all fields","BLANK FIELD",JOptionPane.ERROR_MESSAGE);
                    }
                    else if (!inputHeight.getText().isEmpty() || inputWidth.getText().isEmpty()){
                        //height nums only 
                        for(int y = 0; y<enteredHeight.length(); y++){
                            if (!Character.isDigit(enteredHeightChar[y])){
                                JOptionPane.showMessageDialog(mainPanel, "Please only enter numbers","INCORRECT TYPE",JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        //width nums only
                        for(int x = 0; x<enteredWidth.length(); x++){
                            if (!Character.isDigit(enteredWidthChar[x])){
                                JOptionPane.showMessageDialog(mainPanel, "Please only enter numbers","INCORRECT TYPE",JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                    } 
                }
            }
        }
    }
}
