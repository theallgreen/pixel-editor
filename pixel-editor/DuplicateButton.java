import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class DuplicateButton extends MenuButton
{
    private MenuPanel homePanel;
    ImageIcon duplicateIcon = new ImageIcon("icons/duplicate.png");
    Image imageDup = duplicateIcon.getImage();
    Image resizeddup = imageDup.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
    ImageIcon dupRIcon = new ImageIcon(resizeddup);
    
    /**
     * Will create a duplicate button
     * @param homePanel top panel
     */
    public DuplicateButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Duplicate Canvas: Duplicate the current canvas");
        this.homePanel=homePanel;
        this.setIcon(getIcon(dupRIcon));
        this.setText("<html>Dupl<br>icate</html>");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * Running DuplicateButton in the home panel
     * only if animation isn't playing.
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) { if(!homePanel.getPlaying()) { homePanel.dupeCanvas(); } }
}
