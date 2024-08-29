import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
public class ToolButton extends JButton implements ActionListener //trying to fix stuff
{
    private ToolInterface tool;
    /**Constructor creates a tool button*/
    public ToolButton()
    {
        addActionListener(this);
        //setBackground(Color.GREEN);//set icon
        setIcon(getIcon());
        setBackground(Color.WHITE);
    }
    /**
     * setting icon for each button
     * @param iconP
     * @return icon to be displayed
     */
    public Icon getIcon(ImageIcon iconP){
        return iconP;
    }

    /**
     * Button clicked
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        setBackground(Color.WHITE);
    }

    /**Returns this button's associated tool 
     * returns null if the button has no tool (eg. shapes button has a menu instead)
    */
    public ToolInterface getTool(){return tool;}


}
