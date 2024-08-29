import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class PenButton extends ToolButton
{
    private Easel easel;
    private PenTool pen;
    ImageIcon penIcon = new ImageIcon("icons/penfr.png");
    Image imagepen = penIcon.getImage();
    Image resizedpen = imagepen.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon penRIcon = new ImageIcon(resizedpen);
    /**
     * Creates the pen button
     * Holds the pen tool in it
     * @param easel the easel to put the button in
     */
    public PenButton(Easel easel)
    {
        super();
        this.setToolTipText("Pen Tool: Draws based on colour selected and pen size");
        this.easel = easel;
        pen = new PenTool(easel);
        //this.setText("Pen");
        this.setIcon(getIcon(penRIcon));
        this.setBackground(Color.GRAY); //gray not white because it is the initial selected tool
        easel.setTool(pen); //do not put in all tool button classes, only this one to set default open tool
    }

    /**
     * When pressed, updates the current tool in the easel to pen
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) { easel.setTool(pen); }
}
