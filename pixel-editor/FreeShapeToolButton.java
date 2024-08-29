import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class FreeShapeToolButton extends ToolButton
{
    private Easel easel;
    private FreeShapeTool freeShapeTool;
    ImageIcon freeShapeIcon = new ImageIcon("icons/freeshape.png");
    Image imageShape = freeShapeIcon.getImage();
    Image resizedshape = imageShape.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon shapeRIcon = new ImageIcon(resizedshape);

    /**
     * Creates the free shape tool button
     * Holds the free shape tool in it
     * @param easel the easel to put the button in
     */
    public FreeShapeToolButton(Easel easel)
    {
        super();
        this.setToolTipText("Free Shape Tool: Draw a shape with multiple sides");
        this.easel = easel;
        freeShapeTool = new FreeShapeTool(easel);
        this.setIcon(getIcon(shapeRIcon));
        
       
    }

    /**
     * When pressed, updates the current tool in the easel to circle
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        easel.setTool(freeShapeTool); }
}
