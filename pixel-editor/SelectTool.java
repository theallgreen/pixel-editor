import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectTool implements ToolInterface{

    private Easel easel;
    private boolean firstClick = true;
    private boolean secondClick = false;
    //these are the two click variables to determine which click the user is on. byu default, the user is on the first click

    private Pixel movePixel;
    private Pixel startPixel; //the starting pixel
    private Pixel endPixel; //the last/target pixel
    private boolean areaSelected = false;
    //by default, the area selected is false.

    private int offsetX;
    private int offsetY;
    //two variables to find which direction to move the selected area in

    private ArrayList<Pixel> pixelsToSelectBorder = new ArrayList<>(); //the square border of the tool
    private ArrayList<Pixel> pixelsToSelect = new ArrayList<>(); //the pixels to select
    private ArrayList<Color> pixelsToMoveColors = new ArrayList<>();//holds the colors that have been selected, to use as a blueprint when drawing

    public SelectTool(Easel easel){
        this.easel = easel; 
    }//setting the easel


    /**Populates list of pixels to change based on location/type of click
     * @param currentPixel most recently clicked pixel
     * @param clickType left or right click
     */
    public void startClickDraw(Pixel currentPixel, Click clickType){

        if ((clickType == Click.LEFT)){

            if(firstClick) 
            //on the first click
            {
            areaSelected = false;
            pixelsToSelectBorder.forEach(pixel1 -> pixel1.drawing(pixel1.getPreviousColour())); 
            pixelsToSelectBorder.clear(); 
            pixelsToSelect.clear(); 
            pixelsToMoveColors.clear();
            //resets area selected variables, removes the border, then clears all 3 arraylists.

            firstClick=false; 
            secondClick=true;
            //sets the click variables accordingly

            startPixel=currentPixel; //sets the starting pixel to the clicked pixel
            // System.out.println("start pixel is " + currentPixel.getPixelX() + "," + currentPixel.getPixelY());
            }

            else if(secondClick) 
            //on the second click
            {   
                firstClick=true; 
                secondClick=false;
                //resets the click variables

                endPixel=currentPixel; 
                //sets the end pixel to the clicked pixel

                updateSelectedA();
                areaSelected = true;
                drawSelectSquare();
                //updates selected area, then draws the select border
            }
        }

        else if((clickType == Click.RIGHT) && (areaSelected == true))
        //if area is selected, and the click is a right click
        {
            movePixel = currentPixel; 
            //sets the current pixel to the 'movePixel', which is used to determine how much to move the selected area

            removeSelectedA();
            removeSelectedA();
            //removes the selected area + clears the list twice so that the selected area is cleared and the pixel's previous color is also reset. 
            pixelsToSelectBorder.clear();   
        }
    }
    /** Populates list of pixels to change based on location/type of drag
     * @param pixel most recently visited pixel
     * @param clickType left or right click*/
    public void startDragDraw(Pixel pixel, Click clickType)
    {
        
        if (clickType == Click.LEFT){
            endPixel = pixel;
            firstClick=true; 
            secondClick=false;
            //resets click variables, sets end pixel to pixel.

            if (pixelsToSelectBorder.size() != 0){
                removeBorder();
            }
            //if a border exists (border arraylist not 0), then remove it, also clears border array

            pixelsToSelect.clear();
            pixelsToMoveColors.clear();
            //clear both select and color arrays

            updateSelectedA();
            areaSelected = true;
            drawSelectSquare();
            //this is the same as clicking, update selected area and then draw the border again for the new area. 

        }//this if statement will handle if the user is dragging the mouse, for the tool

        else if ((clickType == Click.RIGHT) && (areaSelected == true)){
            endPixel = pixel; 
            firstClick=true; 
            secondClick=false;
            //resets click variables and sets end pixel to pixel.

            offsetX = pixel.getPixelX() - movePixel.getPixelX();
            offsetY = pixel.getPixelY() - movePixel.getPixelY();
            moveSelectedA(offsetX, offsetY);
            //calculates how much the user has moved the cursor and calls the move function to move the area
            movePixel = pixel;
          }
    }
    /**Transfers selected portion of image to selected position on canvas*/ 
    public void updateSelectedA(){
    //updates the selected area inside and inclusign the border to pixelsToSelect array

        int startX = startPixel.getPixelX();
        int startY = startPixel.getPixelY();
        int endX = endPixel.getPixelX();
        int endY = endPixel.getPixelY();
        int lengthX = Math.abs(endX - startX);
        int lengthY = Math.abs(endY - startY);
        //initiliases values needed to update the selected area, such as coordinates and lengths.

        for (int i = startY; i <= (lengthY + startY); i++){
            for (int j = startX; j <= (lengthX + startX); j++){
                if(easel.getCurrentCanvas().getPixelAt(j, i)!=null) { pixelsToSelect.add(easel.getCurrentCanvas().getPixelAt(j, i)); }
            }
        }//iterates across the size of the selected area, adding the pixels at said location to the pixelsToSelect array

        pixelsToSelect.forEach(pixel1 -> pixelsToMoveColors.add(pixel1.getCurrentColor()));   
    }
    /**Changes colour of 'moved' pixels to their previous colour */
    public void removeSelectedA(){
    //changes all the pixels in pixelsToSelect into the background color - removes all color in the array
        for (int i = 0; i < pixelsToSelect.size(); i++){
            Pixel pixel1 = pixelsToSelect.get(i);

            try{
                pixel1.drawing(easel.getCurrentCanvas().getBackgroundColour());
            }

            catch(Exception e){
                continue;
            }
        }
        //loops over the arraylist, checking for exceptions if the pixels aren't in the array
    }
    /**Accounts for pixels changed to display selection square */
    public void removeBorder(){
        for (int i = 0; i < pixelsToSelectBorder.size(); i++){
            Pixel pixel = pixelsToSelectBorder.get(i);

            try{
                pixel.drawing(pixel.getPreviousColour());
            }

            catch(Exception e){
                continue;
            }
        }
        //loops over the arraylist, checking for exceptions if pixels aren't in the array

        pixelsToSelectBorder.clear();
        //clears the array when border is removed
    }

    public void moveSelectedA(int offsetX, int offsetY){//updates the pixelsToSelect area based on the pixelsToMoveColors array
        for (int j = 0; j < pixelsToMoveColors.size(); j++){
            try{
                pixelsToSelect.get(j).drawing(pixelsToSelect.get(j).getPreviousColour());

                pixelsToSelect.set(j, easel.getCurrentCanvas().getPixelAt(pixelsToSelect.get(j).getPixelX() + offsetX, pixelsToSelect.get(j).getPixelY() + offsetY));
            }

            catch(Exception e){
                continue;
            }
        }//loops over the pixelstoMovecolors array, resetiing the previous colour in pixelsToSelect and then setting it to the new 'moved' pixel based on the offset

        for (int i = 0; i < pixelsToMoveColors.size(); i++){
            Pixel pixel1 = pixelsToSelect.get(i);

            try{
                pixel1.drawing(pixelsToMoveColors.get(i));
            }

            catch(Exception e){
                continue;
            }
        }//iterates over the pixelsToMoveColors array, drawing the pixelsToSelect pixels with the colors of the blueprint pixelsToMoveColors arraylist.
    }

    /**Displays a square around selected pixels */
    public void drawSelectSquare()
    {
        Pixel newStart = easel.getCurrentCanvas().getPixelAt(Math.min(startPixel.getPixelX(), endPixel.getPixelX()), Math.min(startPixel.getPixelY(), endPixel.getPixelY()));
        Pixel newEnd = easel.getCurrentCanvas().getPixelAt(Math.max(startPixel.getPixelX(), endPixel.getPixelX()), Math.max(startPixel.getPixelY(), endPixel.getPixelY()));
        Pixel newTopRigt = easel.getCurrentCanvas().getPixelAt(newEnd.getPixelX(), newStart.getPixelY());
        Pixel newBotLeft = easel.getCurrentCanvas().getPixelAt(newStart.getPixelX(), newEnd.getPixelY());
        //uses abolute values to calculate the corners of the selected area.

        plotLine(newStart, newTopRigt, 0);
        plotLine(newBotLeft, newEnd, 0);
        //plots horizontal lines using the top 2 and bottom 2 co-ordinates, using plotLine function

        plotLine(newStart, newBotLeft, 1);
        plotLine(newTopRigt, newEnd, 1);
        //plots vertical lines using the left 2 and right 2 co-ordinates, using plotLine function
        
        pixelsToSelectBorder.forEach(pixel1 -> pixel1.drawing(Color.GRAY));
        //once the lines have been added to the pixelsToSelectBorder array, colors them in in the gray color to indicate the border.
    }
    /**Plots a line between specified pixels 
     * @param start pixel with coordinates of beginning of line
     * @param end pixel with coordinates of end of line
     * 
     * @param line line type: horizontal=0, vertical=1
     */
    public void plotLine(Pixel start, Pixel end, int line)
    {
        int startX = start.getPixelX();
        int startY = start.getPixelY();
        int endX = end.getPixelX();
        int endY = end.getPixelY();
        //gets the coordinates of the pixels

        if(line == 0)
        //drawing horizontal lines
        {
            int length = Math.abs(startX - endX);
            //finds the length of the line

            for (int i = startX; i <= length + startX; i++){

                if(!pixelsToSelectBorder.contains(easel.getCurrentCanvas().getPixelAt(i, startY))) {
                    pixelsToSelectBorder.add(easel.getCurrentCanvas().getPixelAt(i, startY));
                }

            }//iterates over the length of the line, adding each pixel to the pixelsToSelectBorder arraylist if it is not already present.
        }

        else if(line == 1)
        //drawing vertical lines
        {
            int length = Math.abs(startY - endY);
            
            for (int i = startY; i <= length + startY; i++){

                if(!pixelsToSelectBorder.contains(easel.getCurrentCanvas().getPixelAt(start.getPixelX(), i))) {
                    pixelsToSelectBorder.add(easel.getCurrentCanvas().getPixelAt(start.getPixelX(), i));
                }

            }//iterates over the length of the line, adding each pixel to the pixelsToSelectBorder arraylist if it is not already present.
        }
        
    }

}
