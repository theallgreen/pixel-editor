import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HelpButton extends MenuButton
{
    private MenuPanel homePanel;
    private HelpPage helpPage;

    ImageIcon helpIcon = new ImageIcon("icons/help.png");
    Image imageHelp = helpIcon.getImage();
    Image resizedHelp = imageHelp.getScaledInstance(45, 50, java.awt.Image.SCALE_SMOOTH);
    ImageIcon helpNIcon = new ImageIcon(resizedHelp);

    /**
     * Will make a load button
     * @param homePanel the panel which this button resides
     */
    public HelpButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Help");
        this.homePanel=homePanel;
        this.setIcon(getIcon(helpNIcon));
        this.setText("Help");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }
    /**
     * Will run load in the home panel
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) { helpPage.setVisible(!helpPage.isVisible()); }

    public void setHelpPage(HelpPage helpPage) { this.helpPage = helpPage; }
}
