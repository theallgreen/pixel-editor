import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

/**
 * class to initialise circle tool when button is clicked, extends toolbutton a superclass of all buttons for tools
 */
public class CircleButton extends ToolButton
{
    private Easel easel;
    private CircleTool circle;
    //image icon creation, resizing and setting
    ImageIcon circleIcon = new ImageIcon("icons/circle.png");
    Image imagecircle = circleIcon.getImage();
    Image resizedcircle = imagecircle.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon circleRIcon = new ImageIcon(resizedcircle);
    
    /**
     * Creates the circle button
     * Holds the circle tool in it
     * @param easel the easel to put the button in
     */
    public CircleButton(Easel easel)
    {
        super();
        this.setToolTipText("Circle Tool: Draw a circle or elipses, either by clicking or dragging");
        this.easel = easel;
        circle = new CircleTool(easel);
        this.setIcon(getIcon(circleRIcon));
        
       
    }

    /**
     * When pressed, updates the current tool in the easel to circle
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        easel.setTool(circle); }
}
