import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LightenTool implements ToolInterface
{
    private Easel easel;
    private ArrayList<Pixel> pixelsToChange = new ArrayList<>();
    private Click click;
    public LightenTool(Easel easel) { this.easel=easel; }

    /**
     * Will lighten the pixels around and including the target pixel depending on the pen size
     * @param pixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startClickDraw(Pixel pixel, Click clickType) {
        click = clickType;
        pixelsToChange.clear();
        Pixel targetPixel;
        for(int changeX=(easel.getPenSize()-1); changeX>-(easel.getPenSize()); changeX--) {
            for(int changeY=(easel.getPenSize()-1); changeY>-(easel.getPenSize()); changeY--) {
                targetPixel = easel.getCurrentCanvas().getPixelAt((pixel.getPixelX()+changeX),(pixel.getPixelY()+changeY));
                if(targetPixel != null && !pixelsToChange.contains(targetPixel)) { pixelsToChange.add(targetPixel); }
            }
        }
        pixelsToChange.forEach(this::updatePixel);
    }

    /**
     * Called when the user clicks and drags, but has the same implementation as clickDraw
     * @param pixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startDragDraw(Pixel pixel, Click clickType) { startClickDraw(pixel, clickType); }

    /**
     * Is given a pixel and redraws the pixel to a lighter colour
     * @param pixel the selected pixel
     */
    public void updatePixel(Pixel pixel) {
        Color startColour = pixel.getBackground();
        int r = startColour.getRed();
        int g = startColour.getGreen();
        int b = startColour.getBlue();
        float[] HSBValues = new float[3];
        Color.RGBtoHSB(r,g,b,HSBValues);

        double increment = 0.04;
        if(click==Click.LEFT) { //if left click, lighten
            if(HSBValues[2]+increment < 1) { HSBValues[2] = HSBValues[2] + (float) increment; }
            else {
                HSBValues[2] = 1;
                if(HSBValues[1] - (float) increment > 0) { HSBValues[1] = HSBValues[1] - (float) increment; }
                else { HSBValues[1] = 0; }
            }
        }
        if(click==Click.RIGHT) { //if right click, darken
            if(HSBValues[2]-increment > 0) { HSBValues[2] = HSBValues[2] - (float) increment; }
            else { HSBValues[1] = 0; }
        }
        Color newColour = Color.getHSBColor(HSBValues[0],HSBValues[1], HSBValues[2]);
        pixel.drawing(newColour);
    }
}
