import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The drawable canvas that is used in an easel
 */
public class Canvas extends JPanel
{
    private Easel easel;
    private Color backgroundColor;
    private GridLayout grid;
    private Pixel[][] pixels;
    private boolean dragged=false;
    private Click click;

    /**
     * Constructor creates canvas and populates with pixels
     */
    public Canvas(Easel easel, int height, int width, Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        this.easel = easel;

        grid = new GridLayout(width, height);
        pixels = new Pixel[height][width]; //had height and width the wrong way round

        setBounds(0, 0, (width*Pixel.length), (height*Pixel.length));
        setLayout(grid);

        for(int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                pixels[j][i] = new Pixel(this, i, j);
                add(pixels[j][i]);
            }
        }
    }

    /**
     *@return returns pixel at current coordinates of parameters x and y
     */
    public Pixel getPixelAt(int x, int y)
    {
        try { return pixels[y][x]; }
        catch(ArrayIndexOutOfBoundsException e) { return null; }
    }
    /**
     * @return returns current pen colour
     */
    public Color getCurrentColor() { return easel.getCurrentColor(); }
    /**
     * 
     * @return returns canvas background colour
     */
    public Color getBackgroundColour() { return backgroundColor; }

    /**
     * Sets the state of the dragged variable to the boolean provided
     * @param state the desired state of the dragged variable
     */
    public void setDragged(boolean state) { dragged = state; }

    /**
     * Returns the current state of whether the cursor is being clicked and dragged
     * @return boolean dragged
     */
    public boolean getDragged() { return dragged; }

    /**
     * Will return the current tool selected in the easel
     * @return the current tool
     */
    public ToolInterface getTool() { return easel.getTool(); }

    /**
     * Returns the current state of layerDrawing in the easel
     * @return the current state
     */
    public boolean getLayeredDrawing() { return easel.getLayeredDrawing(); }

    /**
     * Gets an array list of all the canvases from the easel
     * @return all the canvases
     */
    public ArrayList<Canvas> getAllCanvases() { return easel.getAllCanvases(); }

    /**
     * Gets the current state of isPlaying rom the easel
     * @return the current state
     */
    public boolean isPlaying() { return easel.isPlaying(); }

    /**
     * Returns all the pixels in the canvas
     * @return all pixels
     */
    public Pixel[][] getPixels() { return pixels; }

    /**
     * Will take a set of Pixels, and go through each pixel in this canvas, updating the colours to those in the provided pixels
     * @param newPixels the new set of pixels
     */
    public void setPixels(Pixel[][] newPixels) {
        for(Pixel[] p : pixels) {
            for(Pixel p1 : p) {
                Color newColour = newPixels[p1.getPixelY()][p1.getPixelX()].getCurrentColor();
                p1.setBackground(newColour);
            }
        }
    }

    /**
     * Sets the current status of clicked
     * @param click the new click type
     */
    public void setClick(Click click) { this.click=click; }

    /**
     * Returns the current status of clicked
     * @return clicked
     */
    public Click getClick() { return click; }

    /**
     * Marks the start of an action by the user
     */
    public void startAction() { easel.startAction(); }

    /**
     * Marks the end of an action by the user
     */
    public void stopAction() { easel.stopAction(); }
}
