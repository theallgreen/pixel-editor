import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class LightenToolButton extends ToolButton {
    private Easel easel;
    private LightenTool lighten;

   ImageIcon lightenIcon = new ImageIcon("icons/lighten.png");
   Image imageLighten = lightenIcon.getImage();
   Image resizedLighten = imageLighten.getScaledInstance(70, 80, java.awt.Image.SCALE_SMOOTH);
   ImageIcon lightenNIcon = new ImageIcon(resizedLighten);

    /**
     * Will create the lighten tool button
     * @param easel the easel to put the button in
     */
    public LightenToolButton(Easel easel) {
        super();
        this.setToolTipText("Lighten Tool: Lightens the pixel colours that are dragged or clicked");
        this.easel = easel;
        lighten = new LightenTool(easel);
        this.setIcon(getIcon(lightenNIcon));
    }

    /**
     * When pressed, will set the current tool to the lighten tool in the easel
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) { easel.setTool(lighten); }
}
