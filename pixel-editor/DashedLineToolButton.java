
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class DashedLineToolButton extends ToolButton
{
    private Easel easel;
    private DashedLineTool dashedLine;
    ImageIcon dashedLineIcon = new ImageIcon("icons/dashedline.png");
    Image imageDashedLine = dashedLineIcon.getImage();
    Image resizedDashedLine = imageDashedLine.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon dashedLineNIcon = new ImageIcon(resizedDashedLine);
    /**
     * Creates the dashed line button
     * Holds the dashed line tool in it
     * @param easel the easel to put the button in
     */
    public DashedLineToolButton(Easel easel) {
        super();
        this.setToolTipText("Dashed Line Tool");
        this.easel = easel;
        dashedLine = new DashedLineTool(easel);
        this.setIcon(getIcon(dashedLineNIcon));


    }

    /**
     * When pressed, updates the current tool in the easel to dashed line
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        easel.setTool(dashedLine); }
}
