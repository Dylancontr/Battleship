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
        for(int i = 0; i < NUM_SHIPS; i++){
            int size = i;
            if(size == 1) size = 2;
            ships[i] = new Ship(size,i);
        }
        i = 0;
    }

    public int getNUM_SHIPS(){
        return NUM_SHIPS;
    }
    
    public boolean setShipCoor(int r, int c, char d){
        switch(d){
            case 'n':
                for(int j = 0; j < ships[i].getLength(); j++)
                    if(r-j < 0 || r-j > BOARD_SIZE_ROW|| !areaIsFree(r-j,c)) return false;                
                break;
            case 'e':
                for(int j = 0; j < ships[i].getLength(); j++)                  
                    if(c+j < 0 || c + j > BOARD_SIZE_COL || !areaIsFree(r,c+j)) return false;                
                break;
            case 's':
                for(int j = 0; j < ships[i].getLength(); j++)
                    if(r+j < 0 || r + j > BOARD_SIZE_ROW || !areaIsFree(r+j,c)) return false;                
                break;
            case 'w':
                for(int j = 0; j <ships[i].getLength(); j++)
                    if(c-j < 0 || c - j > BOARD_SIZE_COL || !areaIsFree(r,c-j)) return false; 
                break;               
            }
        ships[i].setShip(r, c, d);
        switch(d){
            case 'n':
                for(int j = 0; j < ships[i].getLength(); j++)
                    board[r-j][c] = ships[i].getShipPart(i);
                break;
            case 'e':
                for(int j = 0; j < ships[i].getLength(); j++)                  
                    board[r][c+j] = ships[i].getShipPart(i);            
                break;
            case 's':
                for(int j = 0; j < ships[i].getLength(); j++)
                    board[r+j][c] = ships[i].getShipPart(i);        
                break;
            case 'w':
                for(int j = 0; j <ships[i].getLength(); j++)
                    board[r][c-j] = ships[i].getShipPart(i);
                break;               
            }
            ++i;
        return true;
    }

    public int getIndex(){
        return i;
    }

    private boolean areaIsFree(int r, int c){
        return board[r][c] == null;
    }

    //-1 = miss, id = returns sunk ship id, 0 = hit
    protected int shoot(int r, int c){
        if(areaIsFree(r,c)) return -1;
        if(board[r][c] instanceof ShipPart){
            int id = ((ShipPart)board[r][c]).beHit();
            if(ships[id].isSunk()) return id;
        }
        return 0;

    }

    public abstract boolean placeShips(int r, int c, char d);
    public abstract String takeTurn(Player other, int r, int c);

}
