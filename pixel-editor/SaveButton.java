import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class SaveButton extends MenuButton
{
    private MenuPanel homePanel;
    ImageIcon saveIcon = new ImageIcon("icons/save.png");
    Image imagesave = saveIcon.getImage();
    Image resizedsave = imagesave.getScaledInstance(70, 80, java.awt.Image.SCALE_SMOOTH);
    ImageIcon saveRIcon = new ImageIcon(resizedsave);


    /**
     * Will make a save button
     * @param homePanel the panel which this button resides
     */
    public SaveButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Save: Saves the current canvas to a file");
        this.homePanel=homePanel;
        this.setIcon(getIcon(saveRIcon));
        this.setText("Save");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * Will run save in the home panel
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) { homePanel.save(); }
}
