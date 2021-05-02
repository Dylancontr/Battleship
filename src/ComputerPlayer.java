public class ComputerPlayer extends Player{

    //calls player constructor
    public ComputerPlayer(){
        super();
    }

    //Parameters are nothing, only exist because are defined in parent class
    @Override
    public boolean placeShips(int r, int c, char d) {
        //keeps going until all ships set
        while(!allShipsSet()){
            //randomly determines row and column
            r = (int)(Math.random() * 9);
            c = (int)(Math.random() * 9);

            //randomly picks a number between 1 and 4 then assigns a direction based on that number
            int direction = (int)(Math.random() * 4 + 1);
            switch(direction){
                case 1:
                    d = 'n';
                    break;
                case 2:
                    d = 'e';
                    break;
                case 3:
                    d = 's';
                    break;
                case 4:
                    d = 'w';
                    break;
            }
            //calls parent to place ships base on randomly generated values
            setShipCoor(r, c, d);
        }
        //returns true when done
        return true;
    }

    //takes shots during its turn, parameters needed by parent, don't play a role
    @Override
    public String takeTurn(Player other, int r, int c) {
        int hitItem;

        //while loop continues until finds a spot to hit that hasn't already been shot
        do{
            
            r = (int)(Math.random()*9);
            c = (int)(Math.random()*9);
            hitItem = other.shoot(r,c);

        }while(hitItem == -2);

        //chooses reaction based on hitItem that was determined by hit function
        //cases 1-5 are the name of ship IDs they have lenghts: 2,3,3,4,5
        //any additional ships will just return their id unless added later
        //any non ships should be specified by a number
        switch(hitItem){
            case -1:
                return "Miss";
            case 0:
                return "Hit";
            case 1:
                return " sunk Destroyer";
            case 2:
                return " sunk Submarine";
            case 3:
                return " sunk Cruiser";
            case 4:
                return " sunk Battleship";
            case 5:
                return " sunk Carrier";
            default:
                return "You sunk ship number " + hitItem;
        }
    }
}
