import java.io.Serializable;
/**
 * abstract class represents the Player  
 * @author  Dylan, Evgeniya, Ivan
 */
public abstract class Player implements Serializable{
    
    private IMapEntity[][] board;
    private Ship[] ships;
    private int i;
    private final int NUM_SHIPS = 5;
    protected final int BOARD_SIZE_ROW = 10;
    protected final int BOARD_SIZE_COL = 10;
    
/**
 * non-default constructor
*/
    public Player(){
        board = new IMapEntity[BOARD_SIZE_ROW][BOARD_SIZE_COL];
        ships = new Ship[NUM_SHIPS];
        for(int j = 0; j < NUM_SHIPS; j++){
            int size = j+1;
            if(size <= 2) size += 1;
            ships[j] = new Ship(size,j+1);
        }
        i = 0;
    }

/**
 * @param NUM_SHIPS number of ships players have
 * @return number of Ships for the player
 */
    public int getNUM_SHIPS(){
        return NUM_SHIPS;
    }
    
    
    /**
     * determines if possible to set ship in this spot and does so if able
     * switch checks which direction to go in then checks if that direction goes out of bounds or intersects with something else
     * uses switch to set the parts of each ship on the board
     * @param r -row 
     * @param c- column
     * @param d- direction
     * @return true if  coordinate is in the bound and ship could be set
     */
    public boolean setShipCoor(int r, int c, char d){
       
        switch(d){
            case 'w':
                for(int j = 0; j < ships[i].getLength(); j++)
                    if(r-j < 0 || r-j >= BOARD_SIZE_ROW|| !areaIsFree(r-j,c)) return false;                
                break;
            case 's':
                for(int j = 0; j < ships[i].getLength(); j++)                  
                    if(c+j < 0 || c + j >= BOARD_SIZE_COL || !areaIsFree(r,c+j)) return false;                
                break;
            case 'e':
                for(int j = 0; j < ships[i].getLength(); j++)
                    if(r+j < 0 || r + j >= BOARD_SIZE_ROW || !areaIsFree(r+j,c)) return false;                
                break;
            case 'n':
                for(int j = 0; j <ships[i].getLength(); j++)
                    if(c-j < 0 || c - j >= BOARD_SIZE_COL || !areaIsFree(r,c-j)) return false; 
                break;               
            }
        ships[i].setShip();

        
        switch(d){
            case 'w':
                for(int j = 0; j < ships[i].getLength(); j++)
                    board[r-j][c] = ships[i].getShipPart(j);
                break;
            case 's':
                for(int j = 0; j < ships[i].getLength(); j++)                  
                    board[r][c+j] = ships[i].getShipPart(j);            
                break;
            case 'e':
                for(int j = 0; j < ships[i].getLength(); j++)
                    board[r+j][c] = ships[i].getShipPart(j);
                break;
            case 'n':
                for(int j = 0; j <ships[i].getLength(); j++)
                    board[r][c-j] = ships[i].getShipPart(j);
                break;               
            }
            ++i;//gets the next ship to set
        return true;
    }

/**
 *@return index of current ship looked at by other classes
 */
    public int getIndex(){
        return i;
    }
    
    /**
     *@return true if area is free/nothing is in the cell
     */   
    public boolean areaIsFree(int r, int c){
        return r < 0 || r >= BOARD_SIZE_ROW || c < 0 || c >= BOARD_SIZE_COL || board[r][c] == null;
    }

    /**
     * checks if part has already been shot
     * @param r-row
     * @param c -column
     * @return true if its hit 
     */
    public boolean shot(int r, int c){
        return board[r][c] instanceof ShotMarker;
    }

   /** 
    * @param r-row 
    * @param c- column
    * @return type of ShotMarker(hit=true/miss=false)
    * @throws Exception if its out of Bounds
    */
    public boolean shotType(int r, int c) throws Exception{
        if(shot(r,c)) return ((ShotMarker)(board[r][c])).getType();
        else throw new Exception("Not a ShotMarker");
    }
    
	/** checks if square was shot or not 
	 *  if area is free it will places a shot marker on spot and returns miss
	 *  if shot marker is present then it will return that the spot was already fired upon
	 *  if there is a ShipPart present it will tell the ShipPart it was hit, and place a shotMarker,
     * then checks if the ship that the ShipPart is a part of (hence the name) has been sunk it returns the id if so
     * by default the ids are 1-5
     * if the ship hasn't been sunk it will go out here and return 0
	 * @param r-row 
	 * @param c-column
	 * @return -1 = miss, id = returns sunk ship id, 0 = hit, -2 if spot was already fired upon
	 */ 
    protected int shoot(int r, int c){
        
        if(areaIsFree(r,c)){
            board[r][c] = new ShotMarker(false);
            return -1;
        }
        
        if(shot(r,c)){
            return -2;
        }
        
        if(board[r][c] instanceof ShipPart){
            int id = ((ShipPart)board[r][c]).beHit();
            board[r][c] = new ShotMarker(true);
            if(ships[id-1].isSunk()) return id;
        }
  
        return 0;

    }

   /**
    * checks if all ships have been set 
    * @return true if all ships are set 
    */
    public boolean allShipsSet(){
        if(i < NUM_SHIPS) return false;
        return true;
    }

    /** 
     * @return the length of the current ship
     */
    public int currShipSize(){
        return ships[i].getLength();
    }

    /**
     * @return the size of the previous ship set
     */
    public int prevShipSize(){
        return ships[i-1].getLength();
    }

    /** 
     * @param j- ship at index specified
     * @return the ship at index specified
     */
    public Ship getShip(int j){
        return ships[j];
    }

    /**
     * checks if each ship is sunk or not 
     * @return false if all ships sunk, true otherwise
     */
    public boolean checkStillAlive(){

        for(int i = 0; i < NUM_SHIPS; i++){
            if(!ships[i].isSunk()) return true;
        }
        return false;
    }

    /**
     * sets board (2d array) =null, clears it 
     */
    public void clearBoard(){
        for(int j = 0; j < BOARD_SIZE_ROW; j++)
            for(int k = 0; k < BOARD_SIZE_COL; k++)
                board[j][k] = null;
    }

    /**
     * sets
     * @param j- index of Ship 
     */
    public void setCurrIndex(int j){
        i = j;
    }

   /**
    * places ships on the board
    * @param r-row
    * @param c-column
    * @param d-direction of the ship
    * @return true if all Ships are placed
    */
    public abstract boolean placeShips(int r, int c, char d);
    
    /**
     * takes shot during its turn    
     * @param r-row
     * @param c-column
     * @param d-direction of the ship
     * @return result of shot in the String
     */
    public abstract String takeTurn(Player other, int r, int c);

}