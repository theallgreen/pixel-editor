import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UndoButton extends MenuButton
{
    private MenuPanel homePanel;
   ImageIcon undoIcon = new ImageIcon("icons/undo.png");
   Image imageUndo = undoIcon.getImage();
   Image resizedUndo = imageUndo.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
   ImageIcon undoNIcon = new ImageIcon(resizedUndo);

    /**
     * Will make an undo button
     * @param homePanel the panel which this button resides
     */
    public UndoButton(MenuPanel homePanel)
    {
        super();
        this.setToolTipText("Undo: Undo last change");
        this.homePanel=homePanel;
        this.setIcon(getIcon(undoNIcon));
        this.setText("Undo");
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * Will run undo in the home panel and the animation is not currently playing
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e) 
    { 
        if(!homePanel.getPlaying()) 
        { homePanel.undo(); } 
    }
}
