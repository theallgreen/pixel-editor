public class PenTool implements ToolInterface
{
    private Easel easel;
    public PenTool(Easel easel) { this.easel = easel; }

    /**
     * Implementation of the click of a pixel for the pen tool
     * Will draw with the selected size and colour
     * @param currentPixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startClickDraw(Pixel currentPixel, Click clickType) {
        if(clickType== Click.MIDDLE) { easel.setCurrentColour(currentPixel.getCurrentColor()); }
        else {
            Pixel targetPixel;
            for(int changeX=(easel.getPenSize()-1); changeX>-(easel.getPenSize()); changeX--) {
                for(int changeY=(easel.getPenSize()-1); changeY>-(easel.getPenSize()); changeY--) {
                    targetPixel = easel.getCurrentCanvas().getPixelAt((currentPixel.getPixelX()+changeX),(currentPixel.getPixelY()+changeY));
                    if(targetPixel != null) {
                        if(clickType==Click.LEFT) { targetPixel.drawing(easel.getCurrentColor()); } //on left click, draw
                        else if (clickType==Click.RIGHT) { targetPixel.drawing(easel.getBackgroundColour()); } //on right click, erase
                    }
                }
            }
        }

    }

    /**
     * Implementation of the drag event
     * In this case it is the same as the click event so just calls click
     * @param currentPixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startDragDraw(Pixel currentPixel, Click clickType) { startClickDraw(currentPixel,clickType); }
}
