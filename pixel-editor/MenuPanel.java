import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MenuPanel extends JPanel implements AdjustmentListener
{
    private int buttonNum = 8;//current number of menu icons
    private int value = 1, min = 1, max = 1, extent =0; //sets the required values for the size JSpinner
    private JScrollBar scroll = new JScrollBar(JScrollBar.HORIZONTAL,value,extent,min,max); //creates a scroll bar
    private ArrayList<MenuButton> menuButtons = new ArrayList<>();
    private Easel easel;
    private int currentCanvas;
    private Timer timer = new Timer();
    private BorderLayout border = new BorderLayout();
    private GridLayout buttonGrid = new GridLayout(1, buttonNum);
    private JPanel buttonPanel = new JPanel();
    private boolean playing=false;
    private HelpButton helpButton;

    /**Constructor creates an array of menu buttons */
    public MenuPanel(Easel easel) {
        this.easel=easel;
        buttonPanel.setLayout(buttonGrid);
        
        menuButtons.add(new UndoButton(this));
        menuButtons.add(new LoadButton(this));
        menuButtons.add(new SaveButton(this));
        menuButtons.add(new NewCanvasButton(this));
        menuButtons.add(new DuplicateButton(this));
        menuButtons.add(new DeleteCanvasButton(this));
        menuButtons.add(new LayeredDrawingButton(this));
        menuButtons.add(new AnimationButton(this));
        helpButton = new HelpButton(this);
        menuButtons.add(helpButton);

        menuButtons.forEach(menuButton -> buttonPanel.add(menuButton));
        this.setLayout(border);
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.SOUTH);

        scroll.addAdjustmentListener(this);
    }

    /**
     * Will get the maximum number from the spinner, increment it by 1 and then set this as the new max
     * Then creates a new canvas of this index in the easel
     */
    public void newCanvas() {
        int currentMax = scroll.getMaximum();
        scroll.setMaximum(currentMax+1);
        easel.createNewCanvas();
        scroll.setValue(currentMax+1);
    }
    /**Duplicates current canvas */
    public void dupeCanvas() {
        int currentMax = scroll.getMaximum();
        scroll.setMaximum(currentMax+1);
        easel.duplicateCurrentCanvas();
        int currentValue = scroll.getValue();
        scroll.setValue(currentValue+1);
    }

    /**
     * Will get the current value of the spinner when it is changed and set this as the visible canvas in thr easel
     * @param adjustmentEvent the event from changing the value in th spinner
     */
    public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        int canvas = scroll.getValue();
        if(canvas==0) { easel.changeCanvas(0); }
        else { easel.changeCanvas(canvas-1); }
    }

    /**
     * Will either start or stop the animation of the layers
     * @param newPlaying whether the system is playing or not
     */
    public void animate(boolean newPlaying)
    {
        easel.setPlaying(newPlaying);
        playing=newPlaying;
        int max = scroll.getMaximum(); //gets the max number of layers
        if(playing) {
            currentCanvas = easel.getCanvasNumber();
            //if the display is now playing, schedule a task on the timer to cycle through the layers every 500ms
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(currentCanvas>max) { currentCanvas=0; }
                    scroll.setValue(currentCanvas+1);
                    currentCanvas++;
                }
            }, 0, 750);
        }
        //if the display is now stopped, cancel the timer and reset it
        if(!playing) {
            timer.cancel();
            timer = new Timer();
        }
    }

    /**
     * Calls the save method in the easel
     */
    public void save() { easel.save(true); }
    /**
     * Calls the load method in the easel
     */
    public void load() { easel.load(); }

    /**
     * @return returns the current state of playing
     */
    public boolean getPlaying() { return playing; }

    /**
     * Will delete the currently selected canvas from the easel
     * Then updates the JSpinner to the correct value and maximum
     * Validates the value and max to make sure the spinner does not decrease below 1
     */
    public void deleteCanvas() {
        easel.deleteCurrentCanvas();
        int currentValue = scroll.getValue();
        if(currentValue==1) { scroll.setValue(1); }
        else { scroll.setValue(currentValue-1); }
        int currentMax = scroll.getMaximum();
        if(currentMax==1) { scroll.setMaximum(1); }
        else { scroll.setMaximum(currentMax-1); }
    }

    /**
     * Will set the status of the layeredDrawing variable in the easel to state
     * @param state the desired state of the variable
     */
    public void setLayeredDrawing(boolean state) { easel.setLayeredDrawing(state); }

    /**
     * Will return the current state of the layeredDrawing variable in the easel
     * @return the current state of the layeredDrawing variable
     */
    public boolean getLayeredDrawing() { return easel.getLayeredDrawing(); }

    /**
     * Calls undo in the easel
     */
    public void undo() { easel.undo(); }

    /**Updates canvas scroller value (if within valid range)
     * @param newValue new value of canvas scroller
    */
    public void setScrollValue(int newValue) {
        if(newValue<scroll.getMinimum()) {
            newValue=scroll.getMaximum();
        } else if (newValue> scroll.getMaximum()) {
            newValue=scroll.getMinimum();
        }
        scroll.setValue(newValue);
    }

    public void setHelpPage(HelpPage helpPage) { helpButton.setHelpPage(helpPage); }
}
