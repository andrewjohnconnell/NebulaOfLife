import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/***
 * 
 * @author Andrew Connell
 * 
 * This class instantiates a display for the Game of Life
 * to be shown on.
 *
 */

public class GOLDisplay extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 110012L;

    /* The Game of Life that this display is built for */
    private NebulaOfLife thisGame_;
    
    /* Get suitable dimensions for the display */
    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    /* Dimensions to be used for the frame */
    private int width_;
    private int height_;
    
    /* The panel on which the GoL is displayed */
    private GOLPanel panel_;
    
    
    /** CONSTRUCTOR **/
    public GOLDisplay(NebulaOfLife theGame){
        
        /* Set the game */
        thisGame_ = theGame;
        
        /* Set the dimensions for the window */
        this.setSize(setWindowDimensions());
        
        /* Set positioning for the window in the centre of the screen */
        this.setLocation(centreWindow());
        
        /* Set the title */
        this.setTitle("The Nebula of Life");
        
        /* Set specific window size */
        this.setMinimumSize(this.getSize());
        this.setMaximumSize(this.getSize());
        this.setPreferredSize(this.getSize());
        
        /* Hide maximise */
        this.setResizable(false);
        
        /* Create a panel and set it as a component within this display */
        panel_ = new GOLPanel(this);
        this.add(panel_);
        
        /* Default Close */
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Show window */
        this.setVisible(true);
        
        /* Set up the actionListener */
        ActionListener refreshPanel = refreshPicture();
        
        /* Create a constant fire timer to update the screen */
        Timer newTimer = new Timer(20, refreshPanel);
        newTimer.start();
        
    };
    
    
    /** ACTION LISTENER **/
    private ActionListener refreshPicture() {
        
        ActionListener thisListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                /* Only if the calculations have been completed should we repaint */
                if (thisGame_.calculationsComplete_) 
                panel_.repaint();

            }
            
        };
        
        return thisListener;
        
    }
    
    
    
    /** METHODS **/
    
    /* Set the width and height of the window */
    private Dimension setWindowDimensions() {
        
        width_ = (int)(thisGame_.originalDimensions_.width);
        height_ = (int)(thisGame_.originalDimensions_.height);
        
        return new Dimension(width_, height_);
        
    }
    
    
    /* Set the position of the window in the centre of the screen */
    private Point centreWindow() {
        
        int xPos = (int) ((screenWidth - width_) / 2);
        int yPos = (int) ((screenHeight - height_) / 2);
        
        return new Point(xPos, yPos);
        
    }
    
    
    /* Access the Game of Life this display is being used for */
    public NebulaOfLife accessCurrentGame() {
        
        return thisGame_;
        
    }
    
    
    
    
}
