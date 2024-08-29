import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
public class SelectToolButton extends ToolButton
{
    private Easel easel;
    private SelectTool select;
    ImageIcon selectIcon = new ImageIcon("icons/select.png");
    Image imageselect = selectIcon.getImage();
    Image resizedselecttool = imageselect.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
    ImageIcon selectNIcon = new ImageIcon(resizedselecttool);

    /**
     * Will create the select tool button
     * @param easel the easel to put the button in
     */
    public SelectToolButton(Easel easel)
    {
        super();
        this.setToolTipText("Select Tool: Selects an area to move");
        this.easel = easel;
        select = new SelectTool(easel);
        this.setIcon(getIcon(selectNIcon));
    }

    /**
     * When pressed, will set the current tool to the select tool in the easel
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        easel.setTool(select); 
    }
}
