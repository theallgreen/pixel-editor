import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class ToolPanel extends JPanel implements ChangeListener, ActionListener
{
    private int buttonNum = 8; //current number of tool icons
    private int value = 1, min = 1, max = 3, step =1, penSize=value; //sets the required values for the size JSpinner
    private SpinnerNumberModel sizeSpinner = new SpinnerNumberModel(value, min, max ,step); //creates the SpinnerModel that has min, max values
    private JSpinner spinner = new JSpinner(sizeSpinner); //creates the size spinner with the model just created
    private ArrayList<ToolButton> toolButtons = new ArrayList<>();
    private ShapesButton shapeButton;

    /**
     * Constructor creates a tool panel and populates with tool buttons and the pen size spinner
     * */
    public ToolPanel(Easel easel) //add new tool buttons here
    {
        spinner.addChangeListener(this);
        spinner.setToolTipText("Pen Size: Increase/Decrease drawing size");
        setLayout(new GridLayout(buttonNum+1,1));
        this.add(spinner); //adds the spinner to the panel at the top
        toolButtons.add(new PenButton(easel));
        toolButtons.add(new EraserButton(easel)); //adds the eraser button to the panel
        toolButtons.add(new LineToolButton(easel));
        shapeButton = new ShapesButton(easel);
        toolButtons.add(shapeButton);
        toolButtons.add(new FillToolButton(easel));
        toolButtons.add(new LightenToolButton(easel));
        toolButtons.add(new SelectToolButton(easel));
        toolButtons.add(new DashedLineToolButton(easel));

        toolButtons.forEach(toolButton -> toolButton.addActionListener(this));
        toolButtons.forEach(this::add);
    }

    /**
     * @return returns the current penSize selected by the spinner
     */
    public int getPenSize() { return penSize; }

    /**
     * Will set the penSize to the current value selected by the spinner
     * @param changeEvent listens for a change in the value of spinner
     */
    public void stateChanged(ChangeEvent changeEvent) { penSize = (int) sizeSpinner.getValue(); }

    /**
     * Listens for a button press in the tool panel
     * When pressed, set the background of all buttons to green
     * Then set the correct button to orange
     * @param actionEvent the event
     */
    public void actionPerformed(ActionEvent actionEvent)
    {
        toolButtons.forEach(toolButton -> toolButton.setBackground(Color.WHITE));
        for (ToolButton toolButton : toolButtons) {
            if (actionEvent.getSource() == toolButton) {
                if(actionEvent.getSource() != toolButtons.get(3)) { // does something different for the shapes button
                    shapeButton.resetBackgrounds();
                    shapeButton.displayMenu();
                }
                toolButton.setBackground(Color.GRAY);
            }
        }
    }
    /**Returns tool button at position i in the tool panel
     * @param i position of the returned tool button
     */
    public ToolButton getToolButtonAt(int i) {return toolButtons.get(i);}
}
