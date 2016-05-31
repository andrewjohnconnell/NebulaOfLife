import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JPanel;

/***
 * 
 * @author Andrew Connell
 *
 * This class creates a panel for the Game of Life
 * to be displayed on, within the GOLDisplay.
 *
 * All graphical methods are held in here.
 *
 */

public class GOLPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 110011L;

    /* Owner reference variable */
    GOLDisplay holder_;
    
    /* Width of panel / Height of panel */
    int width;
    int height;
    

    /** CONSTRUCTOR **/
    public GOLPanel(GOLDisplay heldBy) {

        /* Set the holder_ for this panel to heldBy */
        holder_ = heldBy;

        /* Set width / height */
        width = holder_.accessCurrentGame().cellWidth_;
        height = holder_.accessCurrentGame().cellHeight_;
        
        /* The GOLPanel should share the same dimensions as the GOLDisplay */
        this.setSize(new Dimension(holder_.getWidth(), holder_.getHeight()));

        /* Set the background to black, just to make sure it is visible on testing */
        this.setBackground(Color.BLACK);

    }


    /** METHODS **/

    /* Override the paintComponent() */
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        /* Set anti-aliasing, if available */
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        /* Repaint */
        super.paintComponent(g2d);

        /* Draw a grid for each of the 'cells' in the game */
        drawCells(g2d);

    }


    /* Direct access to the current Game of Life */
    public NebulaOfLife getCurrentGame() {
        
        return holder_.accessCurrentGame();
        
    }
    
    
    /* Draw the grid for the game */
    public void drawCells(Graphics2D g) {
        
        CellSet cells_ = getCurrentGame().getCellSet();
        
        /* For each cell, draw a line (for pixels) */
        for (int y = 0; y < cells_.getHeight(); y++) {
            for (int x = 0; x < cells_.getWidth(); x++) {

                filteredColours(g, cells_, x, y);
                g.fillRect((x * width), (y * height), width, height);
                
            }
        }
        
    }

    
    /* COLOUR VARIABLES */
    int baseR = 160, baseG = 185, baseB = 185;
    int liveR, liveG, liveB;
    int deadR, deadG, deadB;

    /* Calculate the next colour to be displayed for a cell */
    private void filteredColours(Graphics2D g, CellSet cells_, int x, int y) {
        
        int genNum = cells_.accessCell(x, y).getGenerationNumber();
        
        setLiveColours(genNum);
        setDeadColours(genNum);

        setColourToGraphics(g, cells_, x, y);
        
    }
    
    
    /* Set live R, G, B values */
    private void setLiveColours(int genNum) {
        
        liveR = baseR + (genNum * 1);
        liveG = baseG + (genNum * 1);
        liveB = baseB + (genNum * 2);

        /* Handle exceptions */
        if (liveR > 255) liveR = 255;
        if (liveG > 255) liveG = 255;
        if (liveB > 255) liveB = 255;
        
    }
    
    
    /* Set dead R, G, B values */
    private void setDeadColours(int genNum) {
        
        deadR = baseR - (genNum * 1);
        deadG = baseG - (genNum * 1);
        deadB = baseB - (genNum * 1);
        
        /* Handle exceptions */
        if (deadR < 40) deadR = 40;
        if (deadG < 40) deadG = 40;
        if (deadB < 55) deadB = 55;
        
    }
    
    
    /* Set colour to graphics */
    private void setColourToGraphics(Graphics2D g, CellSet cells_, int x, int y) {
        
        if (cells_.accessCell(x, y).getLiveState()) {
            g.setColor(new Color(liveR, liveG, liveB));
        } else {
            g.setColor(new Color(deadR, deadG, deadB));
        }
        
    }
    
}
