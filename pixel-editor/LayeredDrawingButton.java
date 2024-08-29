import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LayeredDrawingButton extends MenuButton
{
    private MenuPanel homePanel;
    ImageIcon layeredIcon = new ImageIcon("icons/layered.png");
    Image imagelayered = layeredIcon.getImage();
    Image resizedlayer = imagelayered.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
    ImageIcon layNIcon = new ImageIcon(resizedlayer);

    /**
     * Creates the layered drawing button
     * @param homePanel the panel of buttons this is in
     */
    public LayeredDrawingButton(MenuPanel homePanel) {
        super();
        this.setToolTipText("Layered Drawing Mode: Draw on layers above the current layer");
        this.homePanel=homePanel;
        this.setIcon(getIcon(layNIcon));
        this.setText("<html>Layered<br>Drawing</html>");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }
    /**
     * When pressed, open a confirmation box
     * If yes is pressed, active layered drawing mode
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) {
        if(!(homePanel.getLayeredDrawing())) {
            int result = JOptionPane.showConfirmDialog(null, "This tool will draw on all layers of a higher canvas number. Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION) {
                homePanel.setLayeredDrawing(!(homePanel.getLayeredDrawing()));
                this.setBackground(Color.GRAY);
            }
        } else {
            this.setBackground(Color.WHITE);
            homePanel.setLayeredDrawing(!(homePanel.getLayeredDrawing()));
        }
    }
}
