import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class LoadButton extends MenuButton{
    private MenuPanel homePanel;

    ImageIcon loadIcon = new ImageIcon("icons/load.png");
    Image imageload = loadIcon.getImage();
    Image resizedload = imageload.getScaledInstance(100, 120, java.awt.Image.SCALE_SMOOTH);
    ImageIcon loadNIcon = new ImageIcon(resizedload);

    /**
     * Will make a load button
     * @param homePanel the panel which this button resides
     */
    public LoadButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Load");
        this.homePanel=homePanel;
        this.setIcon(getIcon(loadNIcon));
        this.setText("Load");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);

    }
    /**
     * Will run load in the home panel
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) { homePanel.load(); }
    
}
