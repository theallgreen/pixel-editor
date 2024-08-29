import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;
/**
 * class for the play button, initiates the animation or rotation of different canvases
 */
public class AnimationButton extends MenuButton 
{
    private MenuPanel homePanel;
    private boolean playing = false;
    //icon settin and resizing 
    ImageIcon playIcon = new ImageIcon("icons/playtool.png");
    Image imageplay = playIcon.getImage();
    Image resizedplaytool = imageplay.getScaledInstance(90, 100, java.awt.Image.SCALE_SMOOTH);
    ImageIcon playNIcon = new ImageIcon(resizedplaytool);

    ImageIcon pauseIcon = new ImageIcon("icons/pause.png");
    Image imagePause = pauseIcon.getImage();
    Image resizedPause = imagePause.getScaledInstance(45, 50, java.awt.Image.SCALE_SMOOTH);
    ImageIcon pauseNIcon = new ImageIcon(resizedPause);
    /**
     * Will make an animation button
     * @param homePanel the panel which this button resides
     */
    public AnimationButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Play Animation: Cycle between existing canvases in order");
        this.homePanel = homePanel;
        this.setIcon(getIcon(playNIcon));
        this.setText("Play");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * When pressed, will update playing boolean and run the animate function
     * @param e action performed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(playing) {
            this.setToolTipText("Play Animation: Cycle between existing canvases in order ");
            this.setIcon(playNIcon);
            this.setText("Play");
        }
        else {
            this.setToolTipText("Pause Animation: Pause cycle");
            this.setText("<html>Pause</html>");
            this.setIcon(pauseNIcon);
        }
        playing= !playing;
        homePanel.animate(playing);
    }
}
