import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ShapesButton extends ToolButton
{
    private ShapesMenu menu;
    private ToolInterface tool = null;
    ImageIcon shapeIcon = new ImageIcon("icons/shapess.png");
    Image imageshapes = shapeIcon.getImage();
    Image resizedshape = imageshapes.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
    ImageIcon shapesNIcon = new ImageIcon(resizedshape);
    /**
     * Makes the shapes button with the shapes menu
     * @param easel
     */
    public ShapesButton(Easel easel)
    {
        super();
        this.setToolTipText("Shapes Menu");
        this.setIcon(getIcon(shapesNIcon));
        menu = new ShapesMenu(easel);
    }

    /**
     * Updates the location of the menu
     * Shows or hides the menu on button press
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        if(!menu.isVisible()) { menu.setLocation(new Point((this.getLocationOnScreen().x)+this.getWidth(), this.getLocationOnScreen().y)); }
        menu.setVisible(!menu.isVisible());
    }

    /**
     * Sets the colour of the buttons in the menu
     */
    public void resetBackgrounds() { menu.resetBackgrounds(); }

    /**
     * Hides the menu
     */
    public void displayMenu() { menu.setVisible(false); }
    /**return this button's menu */
    public ShapesMenu getMenu(){return menu;}
}
