import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
public class LineToolButton extends ToolButton
{
    private Easel easel;
    private ToolInterface tool;
    //private LineTool tool;

    ImageIcon lineIcon = new ImageIcon("icons/linetool.png");
    Image imageline = lineIcon.getImage();
    Image resizedline = imageline.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
    ImageIcon lineNIcon = new ImageIcon(resizedline);

    /**
     * Will create the line tool button
     * @param easel the easel to put the button in
     */
    public LineToolButton(Easel easel)
    {
        super();
        this.setToolTipText("Line Tool: Draws a straight line");
        this.easel = easel;
        tool = new LineTool(easel);
        this.setIcon(getIcon(lineNIcon));
    }

    /**
     * When pressed, will set the current tool to the line tool in the easel
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) { easel.setTool(tool); }

    /**returns line tool */
    //public LineTool getLineTool() {return tool;}
    
    /**returns tool */
    public ToolInterface getTool() {return tool;}
}
