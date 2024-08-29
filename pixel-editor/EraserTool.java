public class EraserTool implements ToolInterface
{
    private Easel easel;

    /**
     * Sets the easel in which it will act
     * @param easel
     */
    public EraserTool(Easel easel) { this.easel = easel; }

    /**
     * Will draw the pixel to the background colour
     * Will take into account pen size
     * @param currentPixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startClickDraw(Pixel currentPixel, Click clickType) {
        if(clickType==Click.LEFT || clickType==Click.RIGHT) { //only erases on left and right click, not middle-click
            Pixel targetPixel;
            for(int changeX=(easel.getPenSize()-1); changeX>-(easel.getPenSize()); changeX--) {
                for(int changeY=(easel.getPenSize()-1); changeY>-(easel.getPenSize()); changeY--) {
                    targetPixel = easel.getCurrentCanvas().getPixelAt((currentPixel.getPixelX()+changeX),(currentPixel.getPixelY()+changeY));
                    if(targetPixel != null) { targetPixel.drawing(easel.getBackgroundColour()); }
                }
            }
        }
    }

    /**
     * When dragged, will just call the click method as the functionality is the same in this case
     * @param currentPixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startDragDraw(Pixel currentPixel, Click clickType) { startClickDraw(currentPixel, clickType); }
}
