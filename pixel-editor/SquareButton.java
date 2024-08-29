import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class SquareButton extends ToolButton
{
    private Easel easel;
    private SquareTool square;
    ImageIcon squareIcon = new ImageIcon("icons/square.png");
    Image imagesquare = squareIcon.getImage();
    Image resizedsquare = imagesquare.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon squareRIcon = new ImageIcon(resizedsquare);

    /**
     * Creates the square button
     * Holds the square tool in it
     * @param easel the easel to put the button in
     */
    public SquareButton(Easel easel)
    {
        super();
        this.setToolTipText("Square Tool: Draws a square");
        this.easel = easel;
        square = new SquareTool(easel);
        this.setIcon(getIcon(squareRIcon));
    }
    /**
     * When pressed, updates the current tool in the easel to square
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Square");
        easel.setTool(square); }
}
