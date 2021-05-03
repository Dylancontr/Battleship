public abstract class Player {
    
    private IEntity[][] board;//creates a 2d array of IEntity
    private Ship[] ships;//array of ships
    private int i;//index of current ship looked at by other classes
    private final int NUM_SHIPS = 5;//number of ships players have
    protected final int BOARD_SIZE_ROW = 10;//size of board rows
    protected final int BOARD_SIZE_COL = 10;//size of board columns
    
    //creates player based on constants and variables
    public Player(){
        board = new IEntity[BOARD_SIZE_ROW][BOARD_SIZE_COL];
        ships = new Ship[NUM_SHIPS];
        for(int j = 0; j < NUM_SHIPS; j++){
            int size = j+1;
            if(size <= 2) size += 1;
            ships[j] = new Ship(size,j+1);
        }
        i = 0;
    }

    //getter for number of ships
    public int getNUM_SHIPS(){
        return NUM_SHIPS;
    }
    
    //determines if possible to set ship in this spot and does so if able
    public boolean setShipCoor(int r, int c, char d){
        //checks which direction to go in then checks if that direction goes out of bounds or intersects with something else
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
        ships[i].setShip();//sets the ship

        //sets the parts of each ship on the board
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

    //gets index of current ship
    public int getIndex(){
        return i;
    }
    
    //returns if area is free/nothing is in the spot
    public boolean areaIsFree(int r, int c){
        return board[r][c] == null;
    }

    //checks if spot has already been shot
    public boolean shot(int r, int c){
        return board[r][c] instanceof ShotMarker;
    }

    //returns -1 = miss, id = returns sunk ship id, 0 = hit, -2 if spot was already fired upon
    protected int shoot(int r, int c){
        //if areas is free places a shot marker on spot then returns miss
        if(areaIsFree(r,c)){
            board[r][c] = new ShotMarker();
            return -1;
        }
        //if shot marker is present then it will return that the spot was already fired upon
        if(shot(r,c)){
            return -2;
        }
        //if there is a ShipPart present it will tell the ShipPart it was hit, and place a shotMarker,
        //then checks if the ship that the ShipPart is a part of (hence the name) has been sunk it returns the id if so
        //by default the ids are 1-5
        if(board[r][c] instanceof ShipPart){
            int id = ((ShipPart)board[r][c]).beHit();
            board[r][c] = new ShotMarker();
            if(ships[id-1].isSunk()) return id;
        }
        //if the ship hasn't been sunk it will go out here and return 0
        return 0;

    }

    //checks if all ships have been set
    public boolean allShipsSet(){
        if(i < NUM_SHIPS) return false;
        return true;
    }

    //get the length of the current ship
    public int currShipSize(){
        return ships[i].getLength();
    }

    //get the size of the previous ship set
    public int prevShipSize(){
        return ships[i-1].getLength();
    }

    //gets the ship at index specified
    public Ship getShipIndex(int j){
        return ships[j];
    }

    //if all ships sunk returns false true otherwise
    public boolean checkStillAlive(){
        //checking if each ship is sunk or not
        for(int i = 0; i < NUM_SHIPS; i++){
            if(!ships[i].isSunk()) return true;
        }
        return false;
    }

    public void clearBoard(){
        for(int j = 0; j < BOARD_SIZE_ROW; j++)
            for(int k = 0; k < BOARD_SIZE_COL; k++)
                board[j][k] = null;
    }

    public void setCurrIndex(int j){
        i = j;
    }

    //abstract classes, see children classes for explanations
    public abstract boolean placeShips(int r, int c, char d);
    public abstract String takeTurn(Player other, int r, int c);

}
