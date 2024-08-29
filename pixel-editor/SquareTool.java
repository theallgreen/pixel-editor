import java.util.HashSet;
import java.util.Set;

public class SquareTool implements ToolInterface
{
    private Easel easel;
    private boolean firstClick = true;
    private boolean secondClick = false;
    private Pixel startPixel;
    private Pixel endPixel;
    private Set<Pixel> lastPixels = new HashSet<Pixel>();

    public SquareTool(Easel easel) { this.easel = easel; }
    /**If first click,
     * Stores coordinates of clicked pixel to become corners of square 
     * If second click,
     * Stores coordinates and draws square
     */
    public void startClickDraw(Pixel pixel, Click clickType) {
        if(firstClick)
        {
            //System.out.println(lastPixels.size());
            lastPixels.clear();
            //System.out.println(lastPixels.size());
            firstClick = false;
            secondClick = true;
            startPixel = pixel;
            lastPixels.add(startPixel);
            //System.out.println("click1: \n"+ pixel.getPixelX()+ "," +pixel.getPixelY());
        }
        else if(secondClick)
        {
            firstClick = true;
            secondClick = false;
            endPixel = pixel;
            //System.out.println("click2: \n"+ pixel.getPixelX()+ "," +pixel.getPixelY());
            
            drawNewSquare();
            //lastPixels.clear();
        }
    }
    /**Dynamically displays preview of square to be drawn */
    public void startDragDraw(Pixel pixel, Click clickType) {

        firstClick=true;
        secondClick=false;
        endPixel=pixel;
        eraseLastSquare();
    
        drawNewSquare();        
        
    }
    /*Calls the plotsquare method taking into account line thickness  */
    private void drawNewSquare()
    {
        Pixel newstart = easel.getCurrentCanvas().getPixelAt(Math.min(startPixel.getPixelX(), endPixel.getPixelX()),Math.min(startPixel.getPixelY(), endPixel.getPixelY()));
        Pixel newEnd = easel.getCurrentCanvas().getPixelAt(Math.max(startPixel.getPixelX(), endPixel.getPixelX()),Math.max(startPixel.getPixelY(), endPixel.getPixelY()));

        plotSquare(newstart,newEnd);
        if(easel.getPenSize() > 1)
        {
            
            for(int i = 1;i < easel.getPenSize();i++)
            {
                //outer
                plotSquare(easel.getCurrentCanvas().getPixelAt(newstart.getPixelX()-i,newstart.getPixelY()-i), easel.getCurrentCanvas().getPixelAt(newEnd.getPixelX()+i,newEnd.getPixelY()+i));
                // //inner
                plotSquare(easel.getCurrentCanvas().getPixelAt(newstart.getPixelX()+i,newstart.getPixelY()+i), easel.getCurrentCanvas().getPixelAt(newEnd.getPixelX()-i,newEnd.getPixelY()-i));
            }
        }
        lastPixels.forEach(pixel -> pixel.drawing(easel.getCurrentColor()));
    }
    /*Return previously selected pixels to their previous colour */
    private void eraseLastSquare()
    {
        //System.out.println(lastPixels.size());
        lastPixels.forEach(pixel-> pixel.drawing(pixel.getPreviousColour()));
        lastPixels.clear();
        //System.out.println(lastPixels.size());

    }
    /*Plots the square by calculating corners and calling plotLine function */
    private void plotSquare(Pixel startPixel, Pixel endPixel)
    {
        //erase last pixels drawn5)

        if(startPixel!=null && endPixel != null){
        //System.out.println("start: (" + startPixel.getPixelX() + "," + startPixel.getPixelY() + ")");
        //System.out.println("end: (" + endPixel.getPixelX() + "," + endPixel.getPixelY() + ")\n");

        //horizontal lines
        plotLine(startPixel, easel.getCurrentCanvas().getPixelAt(endPixel.getPixelX()-1, startPixel.getPixelY())); 
        plotLine(endPixel, easel.getCurrentCanvas().getPixelAt(startPixel.getPixelX()+1, endPixel.getPixelY()));
        //vertical lines
        plotLine(easel.getCurrentCanvas().getPixelAt(startPixel.getPixelX(), startPixel.getPixelY()+1), easel.getCurrentCanvas().getPixelAt(startPixel.getPixelX(), endPixel.getPixelY()));
        plotLine(easel.getCurrentCanvas().getPixelAt(endPixel.getPixelX(), endPixel.getPixelY()-1), easel.getCurrentCanvas().getPixelAt(endPixel.getPixelX(), startPixel.getPixelY()));}
    }
    /**Plots a horizontal or vertical line between start and end pixels */
    private void plotLine(Pixel start, Pixel end)
    {
        if(start!= null && end != null){
            if(start.getPixelX() == end.getPixelX())//vertical line
            {
                int first = start.getPixelY();
                int last = end.getPixelY();
                if(start.getPixelY() > end.getPixelY())
                {
                    first = end.getPixelY();
                    last = start.getPixelY();
                }
                for(int y = first; y <= last; y++)
                {
                    Pixel pixel = easel.getCurrentCanvas().getPixelAt(start.getPixelX(), y);
                    if(pixel != null){
                        lastPixels.add(pixel);
                    }
                }
            }else if(start.getPixelY() == end.getPixelY())//horizontal line
            {
                int first = start.getPixelX();
                int last = end.getPixelX();
                if(start.getPixelX() > end.getPixelX())
                {
                    first = end.getPixelX();
                    last = start.getPixelX();
                }
                for(int x = first; x <= last; x++)
                {
                    Pixel pixel = easel.getCurrentCanvas().getPixelAt(x,start.getPixelY());
                    if(pixel != null){
                        lastPixels.add(pixel);
                    }
                }
            }
        }
    }
}
