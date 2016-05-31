import java.util.Random;

/***
 * 
 * @author Andrew Connell
 *
 * This class creates a cell for a Game of Life
 * that will be placed inside a CellSet object.
 *
 */

public class Cell {

    /* Link to the Game of Life we are playing */
    private NebulaOfLife thisGame_;
    
    /* The CellSet that this Game is attached to */
    private CellSet cellSet_;
    
    /* Each cell has an x and y position within its CellSet */
    private int xPos_;
    private int yPos_;
    
    /* The cell has a living state */
    private Boolean isAlive_;
    
    /* Flip state to dead when calculations for frame completed */
    private Boolean switchLiveState_;
    
    /* Number of generations this cell has been alive for */
    private int numGensAlive_;
    
    /* Produce x/y locations in the main array for this cell's neighbours */
    private int neighbours_[][];
    
    /* Get rid of the magic numbers for ease of reading */
    private int lineAbove = yPos_ - 1;
    private int thisLine = yPos_;
    private int lineBelow = yPos_ + 1;
    private int toLeft = xPos_ - 1;
    private int centred = xPos_;
    private int toRight = xPos_ + 1;

    Random rand = new Random();
    
    
    /** CONSTRUCTOR:: Default constructor sets cell dead **/
    public Cell(NebulaOfLife thisGame, int xPos, int yPos) {
        
        /* Link to the Game */
        thisGame_ = thisGame;
        
        /* CellSet that this cell is tied to */
        cellSet_ = thisGame_.getCellSet();
        
        /* Localisation */
        xPos_ = xPos;
        yPos_ = yPos;

        /* Relative Localisations */
        lineAbove = yPos_ - 1;
        thisLine = yPos_;
        lineBelow = yPos_ + 1;
        toLeft = xPos_ - 1;
        centred = xPos_;
        toRight = xPos_ + 1;
        
        /* Set dead */
        isAlive_ = false;
        
        /* Don't flip state */
        switchLiveState_ = false;
        
        /* Number of generations alive */
        numGensAlive_ = 0;
        
        /* Create neighbours based on this cell's x / y positioning */
        setNeighbours();
        
    }
    
    
    /** METHODS **/
    
    /* Set the neighbours for this cell */
    private void setNeighbours() {

        /* Set offSet positions for lattice boundary */
        setLatticeOffsets();
        
        /* Generate an array and populate with the neighbours */
        neighbours_ = new int[Directions.values().length][2]; 

        /* Assign neighbours to this cell */
        assignNeighbours();
        
    }

    
    /* Set offset positions for the lattice (if cell on edge) for neighbours */
    private void setLatticeOffsets() {
        
        /* If y is 0, then all the NORTHERN cells will be the very bottom line */
        if (yPos_ == 0) {
            lineAbove = thisGame_.cellsHigh_ - 1; // Subtract one, as we're using array notation
        }
        
        /* If y is cellsHigh_ - 1, then SOUTHERN cells will be the very top line */
        if (yPos_ == thisGame_.cellsHigh_ - 1) {
            lineBelow = 0;
        }
        
        /* If x is 0, then all the EASTERN cells will be the very right */
        if (xPos_ == 0) {
            toLeft = thisGame_.cellsWide_ - 1;
        }
        
        /* If x is cellsWide_ - 1, then WESTERN cells will be the very left */
        if (xPos_ == thisGame_.cellsWide_ - 1) {
            toRight = 0;
        }
    
    }
    
    
    /* Assign all neighbours */
    private void assignNeighbours() {
        
        /* Assign neighbours from N to NW, clockwise */
        assignNeighbour(Directions.N, centred, lineAbove);
        assignNeighbour(Directions.NE, toRight, lineAbove);
        assignNeighbour(Directions.E, toRight, thisLine);
        assignNeighbour(Directions.SE, toRight, lineBelow);
        assignNeighbour(Directions.S, centred, lineBelow);
        assignNeighbour(Directions.SW, toLeft, lineBelow);
        assignNeighbour(Directions.W, toLeft, thisLine);
        assignNeighbour(Directions.NW, toLeft, lineAbove);
    
    }
    
    
    /* Assign neighbour for a specific direction */
    private void assignNeighbour(Directions d, int relativeX, int relativeY) {
        
        neighbours_[d.ordinal()][0] = relativeX;
        neighbours_[d.ordinal()][1] = relativeY;
        
    }
    
    
    /* Review the neighbours for a specific cell */
    public void getNeighboursDetails() {
        
        for (Directions d: Directions.values()) {
            System.out.format("Direction: %s, X: %d, Y: %d\n", 
                    d.name(), neighbours_[d.ordinal()][0], neighbours_[d.ordinal()][1]);
        }
        
    }
    
    
    /* Analyse the state of the board for the frame -> decide if this cell is born / stays alive / dies */
    public void analyseSurroundings() {
        
        /* Get the number of live neighbours to calculate what to do */
        int liveNeighbours = countLiveNeighbours();
        
        /* Fewer than two neighbours, if this cell is alive, kill it */
        if (liveNeighbours < 2) {
            if (isAlive_) switchLiveState_ = true; /* Switch the livestate to false on completing calculations for the frame */
        }
        
        /* Two or Three Neighbours */
        if (liveNeighbours == 2 || liveNeighbours == 3) {
            if (isAlive_) {
                ; // Do nothing
            } else {
                if (liveNeighbours == 3) {
                    switchLiveState_ = true; /* Switch the livestate to true on completing calculations for the frame */
                }
            }
        }
        
        /* More than three neighbours */
        if (liveNeighbours > 3 && isAlive_) switchLiveState_ = true; /* Switch the livestate to false on completing calculations for the frame */
        
        /* Random mutation state */
        /* 1 in a million chance of this cell coming to life regardless of conditions surrounding it */
        if (rand.nextInt(1000000) == 1) if (!isAlive_) switchLiveState_ = true;
    
    }
    
    
    /* Check the state of neighbours AT START of frame */
    private int countLiveNeighbours() {
        
        int countLiveNeighbours = 0;
        
        for (int eachNeighbour = 0; eachNeighbour < Directions.values().length; eachNeighbour++) {
            
            //System.out.format("This Cell: %d, %d\nChecking %d, %d\n", xPos_, yPos_, neighbours_[eachNeighbour][0], neighbours_[eachNeighbour][1]);
            if (cellSet_.accessCell(neighbours_[eachNeighbour][0], neighbours_[eachNeighbour][1]).getLiveState()) {
                countLiveNeighbours++;
            };
            
        }
        
        return countLiveNeighbours;
        
    }
    
    
    /* Get the live state of a cell */
    public Boolean getLiveState() {
        
        return isAlive_;
        
    }
    
    
    /* Flip the switches if this cell is to change */
    public void flipSwitches() {
        
        /* Try and save some time */
        if (!switchLiveState_) {
            
            /* Increase number of generations in state */
            numGensAlive_++;
        
            return;
        
        }
        
        /* Switch the live state */
        if (switchLiveState_) {
            
            isAlive_ = !isAlive_;
            
            if (!isAlive_) numGensAlive_ = 0;
            
            switchLiveState_ = false;
        
        }
        
    }
    
    
    /* Set live state of cell */
    public void setLiveState(Boolean state) {
        
        isAlive_ = state;
        
    }
    
    
    /* Get the number of generations this cell has been alive */
    public int getGenerationNumber() {
        
        return numGensAlive_;
        
    }
    
}
