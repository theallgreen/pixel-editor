import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MenuFrame extends JFrame implements ActionListener{

    protected ArrayList<ToolButton> buttons = new ArrayList<>();
    
    @Override
    public abstract void actionPerformed(ActionEvent arg0);
    
    /**Returns button in position i of menu panel 
     * @param i position within menu panel of button to be returned
    */
    public ToolButton getButtonAt(int i) {return buttons.get(i);}

}
