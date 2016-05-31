import java.awt.Dimension;
import java.awt.Toolkit;

/***
 * 
 * @author Andrew Connell
 *
 * This class holds an instance of the Nebula of Life program.
 *
 * The main() argument will also be held inside this class for testing / running purposes.
 * 
 */


public class ProgramHolder {

    NebulaOfLife currentGoL_;
    
    GOLDisplay display_;
    
    /** CONSTRUCTOR **/
    public ProgramHolder() {
        
        System.setProperty("sun.java2d.opengl","True");
        System.setProperty("sun.java2d.d3d", "True");
        
        createNewGame();
        
    }
    
    
    /** METHODS **/
    
    public static void main(String[] args) {
        
        ProgramHolder p = new ProgramHolder();
        
    }
    
    
    /* Create a new game */
    public void createNewGame() {
        /* Dimensions for the window: For test purposes, use 480, 240 */
        Dimension d = new Dimension(480, 240);
        
        /* Create a new game to be played */
        currentGoL_ = new NebulaOfLife(d);
        
        /* Create a display for the Game of Life to be shown on */
        display_ = new GOLDisplay(currentGoL_);

        /* Set the display for currentGoL to display_ */
        currentGoL_.setDisplay(display_);
    }
    
}
