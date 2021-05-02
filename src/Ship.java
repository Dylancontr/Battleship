public class Ship {
    
    private boolean sunk;
    private ShipPart[] size;
    private int id;
    int length;
    
    public Ship(int l, int i){
        size = new ShipPart[l];
        for(int j = 0; j < l; j++){
            size[j] = new ShipPart(i);
        }
        length = l;
        sunk = false;
        id = i;
    }

    public void setShip(int r, int c, char d){
        switch(d){
            case 'n':
                for(int i = 0; i < size.length; i++){
                    size[i].setShipID(id);
                    size[i].setRow(r-i);
                    size[i].setCol(c);
                }
                break;
            case 'w':
                for(int i = 0; i < size.length; i++){
                    size[i].setShipID(id);
                    size[i].setRow(r);
                    size[i].setCol(c+i);
                }
                break;
            case 's':
                for(int i = 0; i < size.length; i++){
                    size[i].setShipID(id);
                    size[i].setRow(r+i);
                    size[i].setCol(c);
                }
                break;
            case 'e':
                for(int i = 0; i < size.length; i++){
                    size[i].setShipID(id);
                    size[i].setRow(r);
                    size[i].setCol(c-i);
                }
                break;
        }
        for(int i = 0; i < size.length; i++){
            size[i].setShipID(id);
            size[i].setRow(r);
            size[i].setCol(c);
        }
    }

    public boolean isSunk(){
        for(int i = 0; i < size.length; i++){
            if(!size[i].getHit()) return false;
        }
        sunk = true;
        return true;
    }

    public ShipPart getShipPart(int i){
        return size[i];
    }

    public boolean getSunk(){
        return sunk;
    }

    public int getLength(){
        return length;
    }

}
