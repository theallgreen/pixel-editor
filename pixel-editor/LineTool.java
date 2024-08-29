import java.util.ArrayList;

public class LineTool implements ToolInterface
{
    private Easel easel;
    private boolean firstClick = true;
    private boolean secondClick = false;
    private Pixel startPixel; //the starting pixel
    private Pixel endPixel; //the last/target pixel
    private ArrayList<Pixel> pixelsToChange = new ArrayList<>(); //the current list of pixels to change
    private ArrayList<Pixel> pixelsToChangeNew = new ArrayList<>(); //a copy of the list of pixels to change that can be edited safely
    private enum Type { //used to differentiate what code is run in addEndPixels method
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public LineTool(Easel easel) { this.easel = easel; }

    /**
     * On the first click of a pair, this will just store the location of the target pixel
     * On second click will calculate the line between the two points and then colour them
     * @param currentPixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startClickDraw(Pixel currentPixel, Click clickType)
    {
        if(clickType==Click.LEFT) { //only runs on left click
            if(firstClick) //on the first click
            {
                pixelsToChange.clear(); //clears the list of pixels to change to remove mistakenly adding to an existing list
                firstClick=false; //updates that the next click will be the second click
                secondClick=true;
                startPixel=currentPixel; //sets the starting pixel to the clicked pixel
            }
            else if(secondClick) //on the second click
            {
                firstClick=true; //resets to be the first click again
                secondClick=false;
                endPixel=currentPixel; //sets the end pixel to the clicked pixel

                calculatePixels(); //will run a method which works out which pixels it needs to change at a basic level, and adds each one to the array list of pixels to change
                if(easel.getPenSize()>1) { //once the main pixels have been determined, then deal with different pen sizes
                    pixelsToChange.forEach(this::calculatePenSizePixels); //this method will work out which pixels to add to the list to change for each pixel in turn
                    pixelsToChange.clear(); //the current list is then cleared to remove duplicate entries
                    pixelsToChange.addAll(pixelsToChangeNew); //the list is then updated with the new list, created in the method above
                    pixelsToChangeNew.clear(); //the backup list is then cleared for next time
                }
                pixelsToChange.forEach(pixel -> pixel.drawing(easel.getCurrentColor())); //the complete list of pixels is then drawn
            }
        }

    }

    /**
     * When dragging, updates the line each time the user enters a new pixel
     * Sets the pixels changed last time back to their previous colour
     * Calculates which pixels to changes this time
     * Changes the current selection pf pixels
     * @param pixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startDragDraw(Pixel pixel, Click clickType)
    {
        if(clickType==Click.LEFT) { //only runs on left click
            endPixel=pixel; //only needs to set the end pixel because the starting pixel is set in the click method above
            firstClick=true; //resets the click variables as this is unimportant for dragging
            secondClick=false;

            pixelsToChange.forEach(pixel1 -> pixel1.drawing(pixel1.getPreviousColour())); //undraws the last list of pixels to change
            pixelsToChange.clear(); //empties the list of pixels to change, so it can be recalculated
            calculatePixels(); //calculates the basic list of pixels to change
            if(easel.getPenSize()>1) { //deals with the varying pen sizes
                pixelsToChange.forEach(this::calculatePenSizePixels); //calculates which extra pixels to change due to pen size
                pixelsToChange.clear(); //clears the list to remove duplicates
                pixelsToChange.addAll(pixelsToChangeNew); //sets the list to the updated list calculated in the last method
                pixelsToChangeNew.clear(); //clears the extra list for next time
                computeEndPixels(); //computes which pixels to change for the ends of the line
            }
            pixelsToChange.forEach(pixel1 -> pixel1.drawing(easel.getCurrentColor())); //draws the pixels that have been selected to be drawn
        }
    }

    /**
     * A method which determines which pixels should be added to the list pixelsToChange at the ends of the line
     */
    public void computeEndPixels() {
        if((Math.abs(endPixel.getPixelY()-startPixel.getPixelY()))<(Math.abs(endPixel.getPixelX()-startPixel.getPixelX()))) { //if change in x is greater than change in y
            if(startPixel.getPixelX()>endPixel.getPixelX()) { //if start pixel is to the right of end pixel
                addEndPixels(startPixel, Type.RIGHT);
                addEndPixels(endPixel,Type.LEFT);
            } else { //if start pixel is to the left of end pixel
                addEndPixels(startPixel,Type.LEFT);
                addEndPixels(endPixel,Type.RIGHT);
            }
        } else { //if change in y is greater than change in x
            if(startPixel.getPixelY()>endPixel.getPixelY()) { //if start pixel is below the end pixel
                addEndPixels(startPixel,Type.DOWN);
                addEndPixels(endPixel,Type.UP);
            } else { //if start pixel is above the end pixel
                addEndPixels(startPixel,Type.UP);
                addEndPixels(endPixel,Type.DOWN);
            }
        }
    }

    /**
     * Once it is determined which way the pixels should be added, selects the correct pixels and adds them to the list
     * @param targetPixel the pixel in question,  either the start or end pixel as that is the end of the line
     * @param location where the extra pixels should be added
     */
    public void addEndPixels(Pixel targetPixel, Type location) {
        switch(location) { //will have a slightly different implementation depending on the direction the pixels need to be added
            case UP:
                for(int i=easel.getPenSize()-2;i>-(easel.getPenSize()-1);i--) {
                    if(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+i,targetPixel.getPixelY()-1) != null) { pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+i,targetPixel.getPixelY()-1)); }
                }
                break;
            case DOWN:
                for(int i=easel.getPenSize()-2;i>-(easel.getPenSize()-1);i--) {
                    if(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+i,targetPixel.getPixelY()+1) != null) { pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+i,targetPixel.getPixelY()+1)); }
                }
                break;
            case LEFT:
                for(int i=easel.getPenSize()-2;i>-(easel.getPenSize()-1);i--) {
                    if(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()-1,targetPixel.getPixelY()+i) != null) { pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()-1,targetPixel.getPixelY()+i)); }
                }
                break;
            case RIGHT:
                for(int i=easel.getPenSize()-2;i>-(easel.getPenSize()-1);i--) {
                    if(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+1,targetPixel.getPixelY()+i) != null) { pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(targetPixel.getPixelX()+1,targetPixel.getPixelY()+i)); }
                }
                break;
        }
    }

    /**
     * Uses Bresenham's line algorithm to work out which pixels to update
     */
    public void calculatePixels()
    {
        // System.out.println("Start: "+startPixel.getPixelX()+"/"+ startPixel.getPixelY());
        // System.out.println("End: "+endPixel.getPixelX()+"/"+endPixel.getPixelY());

        // Uses Bresenham's line algorithm
        if((Math.abs(endPixel.getPixelY()-startPixel.getPixelY()))<(Math.abs(endPixel.getPixelX()-startPixel.getPixelX()))) { //if change in x is greater than change in y
            if(startPixel.getPixelX()>endPixel.getPixelX()) { //if start pixel is to the right of end pixel
                plotPixelsLow(endPixel.getPixelX(),endPixel.getPixelY(),startPixel.getPixelX(),startPixel.getPixelY());
            } else { //if start pixel is to the left of end pixel
                plotPixelsLow(startPixel.getPixelX(),startPixel.getPixelY(),endPixel.getPixelX(),endPixel.getPixelY()); }
        } else { //if change in y is greater than change in x
            if(startPixel.getPixelY()>endPixel.getPixelY()) { //if start pixel is below the end pixel
                plotPixelsHigh(endPixel.getPixelX(),endPixel.getPixelY(),startPixel.getPixelX(),startPixel.getPixelY());
            } else { //if start pixel is above the end pixel
                plotPixelsHigh(startPixel.getPixelX(),startPixel.getPixelY(),endPixel.getPixelX(),endPixel.getPixelY()); }
        }
    }

    /**
     * Is given a target pixel, and will work out which extra pixels around it should be added to the list
     * Varies for each tool
     * @param currentPixel the currently selected pixel
     */
    public void calculatePenSizePixels(Pixel currentPixel)
    {
        int penSize = easel.getPenSize();
        for(int i=penSize-1;i>-penSize;i--) {
            Pixel newPixel;
            if ((Math.abs(endPixel.getPixelY() - startPixel.getPixelY())) < (Math.abs(endPixel.getPixelX() - startPixel.getPixelX()))) {
                if (easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX(), currentPixel.getPixelY() + i) != null) {
                    newPixel = easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX(), currentPixel.getPixelY()+i);
                    pixelsToChangeNew.add(newPixel);
                }
            } else {
                if (easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX() + i, currentPixel.getPixelY()) != null) {
                    newPixel = easel.getCurrentCanvas().getPixelAt(currentPixel.getPixelX() + i, currentPixel.getPixelY());
                    pixelsToChangeNew.add(newPixel);
                }
            }
        }
    }

    /**
     * Calculates the pixels to change when the change in x is greater than the change in y
     * @param x0 the start pixel x
     * @param y0 the start pixel y
     * @param x1 the end pixel x
     * @param y1 the end pixel y
     */
    public void plotPixelsLow(int x0, int y0, int x1, int y1)
    {
        int changeX = x1 - x0;
        int changeY = y1 - y0;
        int yi=1;
        if(changeY<0)
        {
            yi=-1;
            changeY=-changeY;
        }
        int D = (2*changeY)-changeX;
        int currentY = y0;
        for(int currentX=x0;currentX<=x1;currentX++)
        {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(currentX, currentY));
            //System.out.println(currentX+"/!/"+currentY);
            if(D>0)
            {
                currentY+=yi;
                D += (2*(changeY-changeX));
            } else { D += 2*changeY; }
        }
    }

    /**
     * Calculates the pixels to change when the change in y is greater than the change in x
     * @param x0 the start pixel x
     * @param y0 the start pixel y
     * @param x1 the end pixel x
     * @param y1 the end pixel y
     */
    public void plotPixelsHigh(int x0, int y0, int x1, int y1)
    {
        int changeX = x1 - x0;
        int changeY = y1 - y0;
        int xi=1;
        if(changeX<0)
        {
            xi=-1;
            changeX=-changeX;
        }
        int D = (2*changeX)-changeY;
        int currentX = x0;
        for(int currentY=y0;currentY<=y1;currentY++)
        {
            pixelsToChange.add(easel.getCurrentCanvas().getPixelAt(currentX, currentY));
            //System.out.println(currentX+"/?/"+currentY);
            if (D>0) {
                currentX += xi;
                D += (2 * (changeX - changeY));
            } else { D += 2 * changeX; }
        }
    }
    public void setFirstClick(boolean val){firstClick = val;}
    public void setSecondClick(boolean val){secondClick = val;}
    public boolean getFirstClick(){return firstClick;}
    public boolean getSecondClick(){return secondClick;}


}