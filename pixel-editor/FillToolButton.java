import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
public class FillToolButton extends ToolButton
{
    private Easel easel;
    private FillTool fill;
    ImageIcon fillIcon = new ImageIcon("icons/fill.png");
    Image imagefill = fillIcon.getImage();
    Image resizedfilltool = imagefill.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon fillNIcon = new ImageIcon(resizedfilltool);

    /**
     * Will create the line tool button
     * @param easel the easel to put the button in
     */
    public FillToolButton(Easel easel)
    {
        super();
        this.setToolTipText("Fill Tool: Fills within the borders for the selected colour");
        this.easel = easel;
        fill = new FillTool(easel);
        this.setIcon(getIcon(fillNIcon));
    }

    /**
     * When pressed, will set the current tool to the line tool in the easel
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        easel.setTool(fill); 
    }
}
