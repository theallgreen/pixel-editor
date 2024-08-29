import java.util.ArrayList;

/**
 * class that holds circletool functionality
 */
public class CircleTool implements ToolInterface
{
    private Easel easel;
    private boolean firstClick = true;
    private boolean secondClick = false;
    private Pixel startPixel; //the starting pixel
    private Pixel endPixel; //the last/target pixel
    
    private ArrayList<Pixel> pixelsToChange = new ArrayList<>(); //the current list of pixels to change
    
    /**
     * constructor class to set easel
     * @param easel easel, canvas holder
     */
    public CircleTool(Easel easel) { this.easel = easel; }
    

    /**
     * function for drawing  circle with clicks rather than dragging and dropping
     * @param currentPixel currently clikced pixel
     * @param clickType check which mouse type click has been performed by the user
     */
    public void startClickDraw(Pixel currentPixel, Click clickType) {

        if(firstClick) //on the first click
        {
            pixelsToChange.clear(); //clears the list of pixels to change to remove mistakenly adding to an existing list
            firstClick=false; 
            secondClick=true; //updates that the next click will be the second click
            startPixel=currentPixel; //sets the starting pixel to the clicked pixel
        }

        else if(secondClick) //on the second click
        {
            firstClick=true; //resets to be the first click again
            secondClick=false;
            endPixel=currentPixel; //sets the end pixel to the clicked pixel

            if(Math.abs(endPixel.getPixelX()-startPixel.getPixelX())>3 && Math.abs(endPixel.getPixelY()-startPixel.getPixelY())>3) {
                //this condition checks if the size of the circle is less than 3. if not, the circle is not drawn

                int centreX = (startPixel.getPixelX() + endPixel.getPixelX()) / 2;
                int centreY = (startPixel.getPixelY() + endPixel.getPixelY()) / 2;
                //calculates the centre x and y values by finding the midpoint

                int radiusX = Math.abs(endPixel.getPixelX()-startPixel.getPixelX())/2;
                int radiusY = Math.abs(endPixel.getPixelY()-startPixel.getPixelY())/2;
                //calculates the radius x and y values by finding the absolute values and finding the midpoint.

                if (easel.getPenSize() == 1){
                    plotPixels(centreX, centreY, radiusX, radiusY);
                }

                else if (easel.getPenSize() == 2){
                    plotPixels(centreX, centreY, radiusX, radiusY);

                    plotPixels(centreX, centreY, radiusX - 1, radiusY - 1);
                }

                else if(easel.getPenSize() == 3){
                    plotPixels(centreX, centreY, radiusX, radiusY);

                    plotPixels(centreX , centreY , radiusX + 1, radiusY + 1);

                    plotPixels(centreX , centreY , radiusX - 1, radiusY - 1);
                }
                //for every pen size, draws an extra circle
                
            }

            for (int i = 0; i < pixelsToChange.size(); i++){
                Pixel pixel1 = pixelsToChange.get(i);
                try{
                    pixel1.drawing(easel.getCurrentColor());
                }
    
                catch(Exception e){
                    continue;
                }
            }
            pixelsToChange.clear();

        }
    }

    /**
     * the function for drawing a circle with dragging to extend/set size
     * @param pixel, clicked pixel at start
     * @param clickType the type of mouseclick user performed
     */
    public void startDragDraw(Pixel pixel, Click clickType) {

        endPixel=pixel; 
        firstClick=true; 
        secondClick=false;

        for (int i = 0; i < pixelsToChange.size(); i++){
            Pixel pixel1 = pixelsToChange.get(i);
            try{
                pixel1.drawing(pixel1.getPreviousColour());
            }

            catch(Exception e){
                continue;
            }
        }
        pixelsToChange.clear();
        
        if(Math.abs(endPixel.getPixelX()-startPixel.getPixelX())>3 && Math.abs(endPixel.getPixelY()-startPixel.getPixelY())>3) {
            //checks if the size of the circle is less than 3. if not, the circle is not drawn
            int centreX = (startPixel.getPixelX() + endPixel.getPixelX()) / 2;
            int centreY = (startPixel.getPixelY() + endPixel.getPixelY()) / 2;
            int radiusX = Math.abs(endPixel.getPixelX()-startPixel.getPixelX())/2;
            int radiusY = Math.abs(endPixel.getPixelY()-startPixel.getPixelY())/2;
            //same as above, finds centre and radius

            if (easel.getPenSize() == 1){
                plotPixels(centreX, centreY, radiusX, radiusY);
            }
            
            else if (easel.getPenSize() == 2){
                plotPixels(centreX, centreY, radiusX, radiusY);
            
                plotPixels(centreX, centreY, radiusX - 1, radiusY - 1);
            }
            
            else if(easel.getPenSize() == 3){
                plotPixels(centreX, centreY, radiusX, radiusY);
            
                plotPixels(centreX , centreY , radiusX + 1, radiusY + 1);

            
                plotPixels(centreX , centreY , radiusX - 1, radiusY - 1);
            }
            //for every pen size, draws an extra circle
        }
        

        for (int i = 0; i < pixelsToChange.size(); i++){
            Pixel pixel1 = pixelsToChange.get(i);
            try{
                pixel1.drawing(easel.getCurrentColor());
            }

            catch(Exception e){
                continue;
            }
        }
      
    }
    /**
     * function that uses Bresenham's circle algorithm to compute which pixels are part of the circle
     * @param cX circle center X coordinate int
     * @param cY circle center Y coordinate int
     * @param rX radius of circle in X direction int
     * @param rY radius of circle in y direction int
     */
    public void plotPixels(int cX, int cY, int rX, int rY){
        int X, Y, changeX, changeY, error, twoASq, twoBSq, stopX, stopY;

        twoASq = 2 * (rX * rX);
        twoBSq = 2 * (rY * rY);
        X = rX;
        Y = 0;
        changeX = rY * rY * (1-(2*rX));
        changeY = rX * rX;
        error = 0;
        stopX = twoBSq * rX;
        stopY = 0;
        while (stopX >= stopY){
            addPixels(X, Y, cX, cY);
            Y += 1;
            stopY += twoASq;
            error += changeY;
            changeY += twoASq;
            if ((2*error + changeX) > 0){
                X -= 1;
                stopX -= twoBSq;
                error += changeX;
                changeX += twoBSq;
            }
        }

        X = 0;
        Y = rY;
        changeX = rY * rY;
        changeY = rX * rX *(1-(2*rY));
        error = 0;
        stopX = 0;
        stopY = twoASq * rY;
        while (stopX <= stopY){
            addPixels(X, Y, cX, cY);
            X += 1;
            stopX += twoBSq;
            error += changeX;
            changeX += twoBSq;
            if ((2*error + changeY) > 0){
                Y -= 1;
                stopY -= twoASq;
                error += changeY;
                changeY += twoASq;
            }
        }
    }

    /**
     * this function adds the pixels that need to be drawn to the array 
     * @param x 
     * @param y
     * @param cx 
     * @param cy 
     */
    public void addPixels(int x,int y, int cx, int cy){

        if(!pixelsToChange.contains(easel.getCurrentCanvas().getPixelAt(cx + x, cy + y))) {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(cx + x, cy + y));
        }
        if(!pixelsToChange.contains(easel.getCurrentCanvas().getPixelAt(cx - x, cy + y))) {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(cx - x, cy + y)); 
        }
        if(!pixelsToChange.contains(easel.getCurrentCanvas().getPixelAt(cx - x, cy - y))) {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(cx - x, cy - y));
        }
        if(!pixelsToChange.contains(easel.getCurrentCanvas().getPixelAt(cx + x, cy - y))) {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(cx + x, cy - y));
        }
        //checking if the array contains the pixel, if not adds the pixel at location to the list.


    }
}
