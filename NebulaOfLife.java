import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/***
 * 
 * @author Andrew Connell
 *
 * This class instantiates a NebulaOfLife object,
 * from which the Game of Life can run.
 *
 * This was a little fun project to take Conway's Game of Life and adapt the graphics
 * to fade in / out dependent on the live state of each cell.
 * 
 * This is quite computationally intensive, so might slow on higher resolutions on
 * less powerful machines.  Would be interesting to redo this in C and use OpenGL libraries
 * in order to make use of graphics hardware... (3D GoL!?)
 * 
 */

public class NebulaOfLife {

    GOLDisplay display_;
    Dimension originalDimensions_;

    int cellsWide_;
    int cellsHigh_;

    int cellWidth_;
    int cellHeight_;

    CellSet cells_;
    
    /* Boolean to confirm whether screen should be redrawn or not */
    Boolean calculationsComplete_;


    /** CONSTRUCTOR **/
    public NebulaOfLife(Dimension winDimensions) {

        /* 
         * Default parameters will create a game that is as many 
         * 'cells' wide and high as the display measures 
         */
        
        originalDimensions_ = winDimensions;
        
        cellsWide_ = (int)(winDimensions.width);
        cellsHigh_ = (int)(winDimensions.height);
        
        /* Set the cell dimensions -> useful if using boxes */
        setCellDimensions(winDimensions);

        /* Create a CellSet to hold Cells */
        cells_ = new CellSet(cellsWide_, cellsHigh_);

        /* Generate cells for the CellSet */
        buildCells();

        /* Random Life Spark */
        cells_.randomLifeSpark();
        
        /* Set calculationsComplete_ to true */
        calculationsComplete_ = true;
        
        /* Create a 20ms fire timer to update the screen */
        Timer newTimer = new Timer(20, nextGeneration());
        newTimer.start();


    };


    /** ACTION LISTENER **/
    private ActionListener nextGeneration() {

        ActionListener thisListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                /* If calculationsComplete_ flagged true, move to calculate next set */
                if (calculationsComplete_) {

                    /* Set calculationsComplete_ to false */
                    calculationsComplete_ = false;
                    
                    /* Have every cell analyse whether it should be alive or dead */
                    cells_.analyseCells();

                    /* Switch the cells */
                    cells_.flipSwitches();
                
                    /* Set calculationsComplete_ to true */
                    calculationsComplete_ = true;
                    
                }
                
            }

        };

        return thisListener;

    }




    /** METHODS **/

    /* Set the display for the Game */
    public void setDisplay(GOLDisplay thisDisplay) {

        display_ = thisDisplay;

    }


    /* 
     * Set the dimensions of the rectangle if cellsWide_ 
     * and cellsHigh_ are not screen width/height 
     *  --> else, set cellWidth/Height to be cellsWide/High 
     */
    private void setCellDimensions(Dimension d) {

        if (cellsWide_ != Toolkit.getDefaultToolkit().getScreenSize().width) {
            cellWidth_ = (int) d.width / cellsWide_;
        } else {
            cellWidth_ = cellsWide_;
        }

        if (cellsHigh_ != Toolkit.getDefaultToolkit().getScreenSize().height) {
            cellHeight_ = (int) d.height / cellsHigh_;
        } else {
            cellHeight_ = cellsHigh_;
        }

    }


    /* Build cells and place within the CellSet */
    private void buildCells() {

        /* For each x / y position, place a new cell */
        for (int y = 0; y < cellsHigh_; y++) {
            for (int x = 0; x < cellsWide_; x++) {

                cells_.setCellToPosition(this, x, y);

            }
        }

    }


    /* Get the CellSet */
    public CellSet getCellSet() {

        return cells_;

    }

}
