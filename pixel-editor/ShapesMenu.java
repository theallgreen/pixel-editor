import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ShapesMenu extends MenuFrame
{
    private static final int numberOfShapes = 3;

    /**
     * Creates a menu that holds the different shape tools
     * @param easel
     */
    public ShapesMenu(Easel easel) {
        super();
        this.setSize(100, numberOfShapes*45);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true); //hides the bar at the top to improve style

        buttons.add(new SquareButton(easel));
        buttons.add(new CircleButton(easel));
        buttons.add(new FreeShapeToolButton(easel));
        JPanel panel = new JPanel();

        GridLayout grid = new GridLayout(numberOfShapes,1);
        panel.setLayout(grid);
        for (ToolButton shapeButton : buttons) {
            panel.add(shapeButton);
            shapeButton.addActionListener(this);
        }
        this.setContentPane(panel);
        this.setVisible(false);
    }

    /**
     * A method which sets the background of all menu buttons to green.
     * Called when another button in the tool panel is pressed that isn't the shapes button
     */
    public void resetBackgrounds() { buttons.forEach(shapeButton -> shapeButton.setBackground(Color.WHITE)); }

    /**
     * Listens for a button press in the menu and sets the pressed button to orange
     * Also hides the menu
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        this.setVisible(false);
        buttons.forEach(shapeButton -> shapeButton.setBackground(Color.WHITE));
        for (ToolButton shapeButton : buttons) {
            if (actionEvent.getSource() == shapeButton) { shapeButton.setBackground(Color.GRAY); }
        }
    }

}
