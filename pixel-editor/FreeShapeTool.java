public class FreeShapeTool implements ToolInterface{

    private Easel easel;
    private LineTool lineTool;
    private boolean firstClick = true;

    public FreeShapeTool(Easel easel) 
    { 
        this.easel = easel; 
        lineTool = new LineTool(easel);
    }
    /**
     * On first left click, stores starting point of first line 
     * On subsequent left clicks, finishes the line and starts a new one
     * On right click, completes final line ready to start a new shape 
     * @param pixel start/end of line
     * @param clickType left or right click  
     */
    public void startClickDraw(Pixel pixel, Click clickType) {
        
        try {

            lineTool.startClickDraw(pixel, Click.LEFT);
            if(!firstClick && clickType == Click.LEFT)
            {
                lineTool.startClickDraw(pixel, Click.LEFT);
            }
            //lineTool.setFirstClick(false);
            firstClick = false;
            if(clickType == Click.RIGHT)
            {
                lineTool.setFirstClick(true);
                lineTool.setSecondClick(false);

                firstClick = true;

            }
           
        } catch (Exception e) {
            System.out.println("click draw failed");
        }
       
    }
     
    @Override
    public void startDragDraw(Pixel pixel, Click clickType) {
        
    }
    
}
