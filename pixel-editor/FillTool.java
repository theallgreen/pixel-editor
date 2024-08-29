import java.awt.*;

public class FillTool implements ToolInterface{
    private Easel easel;

    public FillTool(Easel easel){
        this.easel = easel; 
    }
    /**Determines action based on click type
     * left click colours surrounding pixels of the same colour with currently selected colour
     * right click colours all pixels of the same colour with currently selected colour
     * middle click sets currently selected colour to the colour of the clicked pixel
     */
    public void startClickDraw(Pixel startingClickedP, Click clickType){
        if (clickType == Click.LEFT){
            resetChecked();
            checkPixels(startingClickedP, startingClickedP.getCurrentColor());
        } 
        else if(clickType == Click.RIGHT){
            changeAllSame(startingClickedP.getCurrentColor());
        }
        else if(clickType== Click.MIDDLE) {
            easel.setCurrentColour(startingClickedP.getCurrentColor());
        }
    }
    /**Recursive method to check colour of 4 surrounding pixels
     * if pixel at current coordinates is within the canvas, has the same colour as the pixel originally clicked and has not been checked before
     * marks current pixel as checked
     * colours current pixel with currently selected colour
     * calls checkPixels method on surrounding pixels (above,below,left and right)
     * @param currentPixel pixel at centre of check
     * @param originColour colour of pixel originally clicked
     */
    private void checkPixels(Pixel currentPixel, Color originColour){
        if (currentPixel != null && currentPixel.getCurrentColor() == originColour && currentPixel.getChecked()==false){
            currentPixel.drawing(easel.getCurrentColor());
            currentPixel.setChecked(true);
            this.checkPixels(easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX()-1, currentPixel.getPixelY()), originColour);
            this.checkPixels(easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX(), currentPixel.getPixelY()-1), originColour);
            this.checkPixels(easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX()+1, currentPixel.getPixelY()), originColour);
            this.checkPixels(easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX(), currentPixel.getPixelY()+1), originColour);
        }
    }
    /**Sets boolean value checked of each pixel to false*/
    private void resetChecked(){
        for(int x = 0; x<easel.getCanvasWidth(); x++){
            for (int y =0;  y< easel.getCanvasHeight(); y++){
                easel.getCurrentCanvas().getPixelAt(x,y).setChecked(false);  
            }
        } 
    }
    /**Finds all pixels in the canvas with the same colour as the originally clicked pixel
     * @param clickedColour colour ofpixel clicked originally  
     */
    private void changeAllSame(Color clickedColour){
        for(int x = 0; x<easel.getCanvasWidth(); x++){
            for (int y =0;  y< easel.getCanvasHeight(); y++){
                Pixel currentPixel  = easel.getCurrentCanvas().getPixelAt(x,y);
                if (currentPixel != null && currentPixel.getCurrentColor() ==clickedColour){
                    currentPixel.drawing(easel.getCurrentColor());
                }
            }
        } 
    }
    /**Calls clickDraw method */
    public void startDragDraw(Pixel startingClickedP, Click clickType){startClickDraw(startingClickedP, clickType);}

}