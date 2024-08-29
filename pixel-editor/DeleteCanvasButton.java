import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

public class DeleteCanvasButton extends MenuButton
{
    private MenuPanel homePanel;
    ImageIcon delIcon = new ImageIcon("icons/delete.png");
    Image imagedel = delIcon.getImage();
    Image resizeddel = imagedel.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
    ImageIcon delNIcon = new ImageIcon(resizeddel);
    /**
     * Creates the delete button
     * @param homePanel the panel of buttons this is in
     */
    public DeleteCanvasButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Delete Canvas: Deletes the current canvas");
        this.homePanel=homePanel;
        this.setIcon(getIcon(delNIcon));
        this.setText("Delete");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * When pressed, open a confirmation box
     * If yes is pressed, delete the current canvas
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        //display warning
        if(!homePanel.getPlaying()) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION) { homePanel.deleteCanvas(); }
        }
    }
}
