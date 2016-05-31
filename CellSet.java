import java.util.Random;

/***
 * 
 * @author Andrew Connell
 *
 * This class will create an array of Cells for the
 * Game of Life, and will be used for checking neighbours
 * by individual cells.
 *
 */

public class CellSet {

    /* Grid for the cells to be placed in */
    private Cell grid_[][];

    /* Ref to width / height */
    int width_;
    int height_;


    /** CONSTRUCTOR **/

    public CellSet(int width, int height) {

        grid_ = new Cell[width][height];

        width_ = width;
        height_ = height;

    }


    /** METHODS **/

    /* Set a Cell to a specific location in the grid */
    public void setCellToPosition(NebulaOfLife thisGame_, int x, int y) {

        grid_[x][y] = new Cell(thisGame_, x, y);

    }


    /* Access a cell on a specific location */
    public Cell accessCell(int x, int y) {

        return grid_[x][y];

    }


    /* Get width */
    public int getWidth() {

        return width_;

    }


    /* Get height */
    public int getHeight() {

        return height_;

    }


    /* Analyse all cells */
    public void analyseCells() {

        for (int y = 0; y < height_; y++) {
            for (int x = 0; x < width_; x++) {

                accessCell(x, y).analyseSurroundings();

            }
        }

    }


    /* Flip the switches for the cells, and revert switchLiveState_ to false */
    public void flipSwitches() {

        for (int y = 0; y < height_; y++) {
            for (int x = 0; x < width_; x++) {

                accessCell(x, y).flipSwitches();

            }
        }

    }

    
    /* Randomly set cells alive or dead */
    public void randomLifeSpark() {
        
        Random rand = new Random();
        
        for (int y = 0; y < height_; y++) {
            for (int x = 0; x < width_; x++) {

                /* 22% chance of sparking life in this cell */
                if (rand.nextInt(100) < 22) accessCell(x, y).setLiveState(true);

            }
        }
        
    }
    
}
