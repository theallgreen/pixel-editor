public interface ToolInterface
{
    /**
     * Blank method to be implemented differently in each tool
     * Called when pixel clicked
     * @param pixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startClickDraw(Pixel pixel, Click clickType);
    /**
     * Blank method to be implemented differently in each tool
     * Called when pixel dragged
     * @param pixel the target pixel
     * @param clickType the type of button that was pressed
     */
    public void startDragDraw(Pixel pixel, Click clickType);

    
}
