import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPage extends JFrame implements ActionListener {
    private JPanel mainPanel = new JPanel();
    private JButton okButton = new JButton("OK");
    private BorderLayout border = new BorderLayout();
    private JPanel basicTips = new JPanel();
    private JPanel intermediateTips = new JPanel();
    private JPanel advancedTips = new JPanel();
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private JLabel basicText = new JLabel();
    private JLabel intermediateText = new JLabel();
    private JLabel advancedText = new JLabel();
    private ImageIcon icon = new ImageIcon("icons/logo.png");

    /**
     * Will construct a html display to give the user some helpful tips with the program
     * @param x the x location of the main display
     * @param y the y location of the main display
     * @param width the width f the main display
     */
    public HelpPage(int x, int y, int width) {
        this.setTitle("Help");

        mainPanel.setLayout(border);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(okButton, BorderLayout.PAGE_END);
        basicText.setFont(new Font("Arial", Font.PLAIN, 16));
        intermediateText.setFont(new Font("Arial", Font.PLAIN, 16));
        advancedText.setFont(new Font("Arial", Font.PLAIN, 16));

        tabbedPane.addTab("Basic Tips", basicTips);
        tabbedPane.addTab("Intermediate Tips", intermediateTips);
        tabbedPane.addTab("Advanced Tips", advancedTips);
        this.setBounds(x+width+50,y, 500,700);
        this.setContentPane(mainPanel);
        basicText.setText("<html>Here are some basic tips about the program:<br><br>" +
                "- The pen tool can be used to draw on the canvas by left clicking.<br>" +
                "- The eraser tool will return the pixel to the background colour<br>" +
                "selected.<br>" +
                "- The line and shape tools can be used by clicking twice, or clicking<br>" +
                "and dragging.<br>" +
                "- Previous actions can be reversed using the undo button.<br>" +
                "- The animation can be played and paused with the play button.<br>" +
                "- The fill tool will fill an enclosed space with the selected colour by<br>" +
                "left clicking.<br>" +
                "- New layers can be created and edited individually using the create<br>" +
                "new canvas button.<br>" +
                "- Canvases can be rotated through using the arrows on the bar under<br>" +
                "the menu.<br>" +
                "- The pen size for tools can be changed using the value in the top<br>" +
                "left corner, from 1-3.<br>" +
                "- The help pages can be found again in the help button.<br>" +
                "</html>");
        intermediateText.setText("<html>Here are some intermediate tips about the program:<br><br>" +
                "- The colour of a pixel can be easily selected while using the pen and fill tools<br>" +
                "by middle clicking.<br>" +
                "- The current canvas can be duplicated and put in the next slot with the<br>" +
                "duplicate button.<br>" +
                "- The fill tool will colour all pixels on the canvas that are the same<br>" +
                "colour to the new colour by right clicking.<br>" +
                "- The lighten tool can be used to provide greater detail to the art by <br>" +
                "slightly lightening a pixel with left click, and darkening a pixel with<br>" +
                "right click.<br>" +
                "- A group of pixels can be selected and moved around using the select<br>" +
                "tool and either clicking twice, or clicking and dragging. Be very<br>" +
                "careful using this as if you move it off the the edge on the screen<br>" +
                "the pixels will be deleted.<br>" +
                "- Shapes such as a square or circle can be drawn from the shapes menu.<br>" +
                "- A dotted line can be drawn using the dashed line tool.<br>" +
                "</html>");
        advancedText.setText("<html>Here are some advanced tips about the program:<br><br>" +
                "- When using the pen tool, you can easily erase by right clicking<br>" +
                "- Layered drawing is a useful tool from drawing repeating images/frames.<br>" +
                "It will draw on all layers of a higher canvas number at the same time,<br>" +
                "so be careful.<br>" +
                "- The project can be saved to storage for later editing using the save<br>" +
                "button.<br>" +
                "- The project can then be loaded back up using the load tool and selecting<br>" +
                "the FOLDER that was created when saved.<br>" +
                "- The free shape tool can be using the draw a polygon of any number of<br>" +
                "sides by left clicking in sequence, and right clicking to complete the action.<br>" +
                "</html>");
        basicTips.add(basicText);
        intermediateTips.add(intermediateText);
        advancedTips.add(advancedText);
        this.setIconImage(icon.getImage());
        okButton.addActionListener(this);
        okButton.setBackground(Color.LIGHT_GRAY);
        okButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.pack();
    }

    /**
     * Will listen for the button to be pressed
     * Will hide the help display
     * @param actionEvent the action of pressing the button
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) { this.setVisible(false); }
}
