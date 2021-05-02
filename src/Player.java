public abstract class Player {
    
    private IEntity[][] board;
    private Ship[] ships;
    private int i;
    private final int NUM_SHIPS = 5;
    private final int BOARD_SIZE_ROW = 10;
    private final int BOARD_SIZE_COL = 10;

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

    public int getNUM_SHIPS(){
        return NUM_SHIPS;
    }
    
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
        ships[i].setShip(r, c, d);
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
            ++i;
        return true;
    }

    public int getIndex(){
        return i;
    }

    public boolean areaIsFree(int r, int c){
        return board[r][c] == null;
    }

    //-1 = miss, id = returns sunk ship id, 0 = hit
    protected int shoot(int r, int c){
        if(areaIsFree(r,c)){
            board[r][c] = new ShotMarker();
            return -1;
        }
        if(board[r][c] instanceof ShotMarker){
            return -2;
        }
        if(board[r][c] instanceof ShipPart){
            int id = ((ShipPart)board[r][c]).beHit();
            if(ships[id-1].isSunk()) return id;
            board[r][c] = new ShotMarker();
        }
        return 0;

    }

    public boolean allShipsSet(){
        if(i < NUM_SHIPS) return false;
        return true;
    }

    public int currShipSize(){
        return ships[i].getLength();
    }

    public int prevShipSize(){
        return ships[i-1].getLength();
    }

    public Ship getShipIndex(int j){
        return ships[j];
    }

    public boolean checkStillAlive(){
        for(int i = 0; i < NUM_SHIPS; i++){
            if(!ships[i].isSunk()) return true;
        }
        return false;
    }

    public abstract boolean placeShips(int r, int c, char d);
    public abstract String takeTurn(Player other, int r, int c);

}
