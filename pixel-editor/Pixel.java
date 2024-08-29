import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class Pixel extends JButton implements MouseListener //trying to fix stuff
{
    private final Canvas canvas;
    private Color previousColour;
    public static int length = 25;
    private int x;
    private int y;
    private boolean checked;

    /**
     * Constructor for a pixel
     * Gives each pixel an x and y coordinate
     * */
    public Pixel(Canvas canvas, int x, int y) {
        addMouseListener(this);
        previousColour=canvas.getBackgroundColour(); //sets the previous colour to the starting colour
        this.setBackground(canvas.getBackgroundColour());
        this.setBorderPainted(false);
        this.canvas = canvas;
        this.x=x;
        this.y=y;
    }

    /**
     * Will colour the pixel the current colour
     * Will also store the colour it was before updating
     */
    public void drawing(Color colour)
    {
        if (colour.getRGB() != -1118482)
        {
            if(!(canvas.isPlaying())) {
                previousColour=this.getBackground();
                this.setBackground(colour);
                if(canvas.getLayeredDrawing()) {
                    ArrayList<Canvas> canvases = canvas.getAllCanvases();
                    for(int i=canvases.indexOf(canvas)+1;i<canvases.size();i++) {
                        canvases.get(i).getPixelAt(this.x,this.y).drawing(colour);
                    }
                }
            } //draw if animation is not playing
        } //draw if colour selected is not the null colour
    }

    /**
     * Gets the x coordinate of the pixel
     * @return x
     */
    public int getPixelX() { return x; }

    /**
     * Gets the y coordinate of the pixel
     * @return y
     */
    public int getPixelY() { return y; }

    /**
     * Gets the current status of checked
     * @return whether the pixel has been checked
     */
    public boolean getChecked() { return checked; }

    /**
     * Sets the value of checked to the provided value
     * @param newChecked the new value of checked
     */
    public void setChecked(boolean newChecked) { checked = newChecked; }
    /**
     * returns current colour of pixel
     * @return background colour of pixel
     */
    public Color getCurrentColor(){ return this.getBackground();}
    /**
     * Returns the previous colour of the pixel
     * @return previous colour
     */
    public Color getPreviousColour() { return previousColour; }

    /**
     * Detects if the mouse has been pressed
     * Sets a shared variable in canvas to true, to signify the start of a drag
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        canvas.setDragged(true); //starts a drag motion
        canvas.startAction(); //starts an action on click
        //determines which button was pressed and sets a shared variable in canvas
        if(e.getButton()==MouseEvent.BUTTON1) { canvas.setClick(Click.LEFT); } //if left click, set variable in canvas to LEFT
        else if(e.getButton()==MouseEvent.BUTTON3) { canvas.setClick(Click.RIGHT); } //if right click, set variable in canvas to RIGHT
        else { canvas.setClick(Click.MIDDLE); } //else set variable in canvas to MIDDLE
        canvas.getTool().startClickDraw(this, canvas.getClick()); //draws on the currently clicked pixel
    }

    /**
     * Detects if the mouse has been pressed
     * Sets a shared variable in canvas to false, to signify the end of a drag
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {
        canvas.setDragged(false);
        canvas.stopAction(); //ends an action on release
    } //finishes a drag motion

    /**
     * Upon entering each pixel, checks is the user has clicked and is dragging
     * If so, colour the pixel
     * @param e the event to be processed
     */
    public void mouseEntered(MouseEvent e) { if(canvas.getDragged()) { canvas.getTool().startDragDraw(this, canvas.getClick()); } }

    /**
     * Currently unused MouseListener function
     * @param e the event to be processed
     */
    public void mouseExited(MouseEvent e) {}
    /**
     * Currently unused MouseListener function
     * @param e the event to be processed
     */
    public void mouseClicked(MouseEvent e) {}
}
