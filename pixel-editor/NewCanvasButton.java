import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class NewCanvasButton extends MenuButton //trying to fix stuff
{
    private MenuPanel homePanel;
    ImageIcon newIcon = new ImageIcon("icons/newpage.png");
    Image imagenew = newIcon.getImage();
    Image resizednew = imagenew.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
    ImageIcon NewNIcon = new ImageIcon(resizednew);


    /**
     * Will make a new canvas button
     * @param homePanel the panel which this button resides
     */
    public NewCanvasButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("New Canvas: Creates a new canvas of the same background colour");
        this.homePanel=homePanel;
        this.setIcon(getIcon(NewNIcon));
        this.setText("<html>New<br>Canvas</html>");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * Will run newCanvas in the home panel and the animation is not currently playing
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) { if(!homePanel.getPlaying()) { homePanel.newCanvas(); } }
}
