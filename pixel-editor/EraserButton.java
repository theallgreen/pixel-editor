import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class EraserButton extends ToolButton //trying to fix stuff
{
    private Easel easel;
    private EraserTool eraser;
    ImageIcon eraserIcon = new ImageIcon("icons/rubber.png");
    Image imageerase = eraserIcon.getImage();
    Image resizedEraser = imageerase.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon eraserNIcon = new ImageIcon(resizedEraser);
    /**
     * Creates the eraser button
     * @param easel the easel in which to add the button
     */
    public EraserButton(Easel easel)
    {
        super();
        this.setToolTipText("Eraser Tool: Restores to original canvas colour");
        this.easel = easel;
        eraser = new EraserTool(easel);
        this.setIcon(getIcon(eraserNIcon));
    }

    /**
     * When pressed, will set the current tool in the easel to eraser
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) { easel.setTool(eraser); }
}
