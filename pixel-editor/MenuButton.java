import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
public class MenuButton extends JButton implements ActionListener //trying to fix stuff
{
    /**Constructor creates a menu button */
    public MenuButton()
    {
        addActionListener(this);
        setBackground(Color.WHITE); //set imageIcon
    }
    /**
     * icon setting meathod
     * @param iconP the image in each class
     * @return image to display
     */
    public Icon getIcon(ImageIcon iconP){
        return iconP;
    }
    /**
     * Button clicked
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        setBackground(Color.GRAY); //should be overridden by children (load, save etc.)
    }
}
