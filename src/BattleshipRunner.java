import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;


public class BattleshipRunner extends Application{
    
    private final int BOARD_SIZE = 10;
    private Square selection;//will use this to save selected square when setting up ships
    
    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Strings used to determing what stage of the game the players are in
        String SETUP = "Set up your ships";
        String DIRECTION = "Pick an orientation";
        String COMPSETUP = "Computer setting up";
        String PLAYING = "Choose a space to shoot at";
        
        //sets up grid
        GridPane grid = new GridPane();

        Label messages = new Label(SETUP);//determines game state
        Label errorMessages = new Label("");//any errors are displayed in here

        //players
        HumanPlayer player1 = new HumanPlayer();
        ComputerPlayer player2 = new ComputerPlayer();

        Label shipSetup = new Label();//which ship is being set
        
        Label currPlayer = new Label("player1");//determines who's turn it is

        //hit/miss indicators for both players
        Label hitIndicator = new Label();
        Label sunkIndicator = new Label();
        Label opphitIndicator = new Label();
        Label oppsunkIndicator = new Label();

        //Sets up board of squares and sets their behaviors when clicked on
        //defaults for base squares is blue fill and white stroke

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                Square cell = new Square(0,0,75,i,j);
                cell.setFill(Color.BLUE);
                cell.setStroke(Color.WHITE);
                grid.add(cell,i,j);
                cell.setOnMouseClicked(event ->{
                    //when messages text is playing the human player will shoot at a square
                    if(messages.getText().equals(PLAYING)){
                        //makes sure it's player1 going
                        if(currPlayer.getText().equals("player1")){
                            //takes row and col from square and sents it to the take turn method
                                String outcome = player1.takeTurn(player2, cell.getRow(), cell.getCol());

                                //if Hit is returned it sets the cell as black and changes turn to player 2
                                if(outcome.equals("Hit")){

                                    hitIndicator.setText("Hit");
                                    cell.setFill(Color.BLACK);
                                    currPlayer.setText("player2");

                                //if miss is returned, sets cell as white and changes turn 
                                }else if(outcome.equals("Miss")){

                                    hitIndicator.setText("Miss");
                                    cell.setFill(Color.WHITE);
                                    currPlayer.setText("player2");
                                
                                //if already shot area selected says so and doesn't change turn
                                }else if(outcome.equals("Area already shot")){
                                    
                                    hitIndicator.setText("You already shot at that area, go again");
                                    
                                //returns a hit phrase of what is sunk, anything other than a ship being sunk should be made into an if statement
                                }else{
                                    
                                    cell.setFill(Color.BLACK);
                                    hitIndicator.setText("Hit");
                                    sunkIndicator.setText("You" + outcome);
                                    if(!player2.checkStillAlive()){
                                        messages.setText("Player1 wins");
                                    }
                                    currPlayer.setText("player2");
                                }
                            }
                            //automatically takes player2's turn if they are a computer operates much the same as HumanPlayer
                            //Except that there is no check for a spot already hit since take turn does that already
                            if(currPlayer.getText().equals("player2") && player2 instanceof ComputerPlayer){

                                String outcome = player2.takeTurn(player1, 0, 0);//0's are dummy arguements needed to call method
                                if(outcome.equals("Hit")){

                                    opphitIndicator.setText("Opponent Hit");

                                }else if(outcome.equals("Miss")){
                                    
                                    opphitIndicator.setText("Opponent Miss");

                                }else if(outcome.equals("Area already shot")){
                                    opphitIndicator.setText("You already shot at that area, go again");
                                }else{
                                    
                                    opphitIndicator.setText("Opponent Hit");
                                    oppsunkIndicator.setText("Opponent has "+ outcome);
                                    if(!player1.checkStillAlive()){
                                        messages.setText("Player2 wins");
                                    };
                                    }
                                    currPlayer.setText("player1");
                                }
                            }

                    //if messages text is SETUP it saves the clicked on cell to selection to be used later if the area is not occupied
                    //otherwise it will say the area is occupied and return back
                    if(messages.getText().equals(SETUP)){
                        if(player1.areaIsFree(cell.getRow(),cell.getCol())){
                            selection = cell;
                            messages.setText(DIRECTION);
                            errorMessages.setText("");
                            selection.setFill(Color.RED);
                        }else{
                            errorMessages.setText("Area is occupied");
                        }
                    }
                    
                    //if messages is equal to DIRECTION it will take selection which was selected in SETUP
                    //and determine the orientation of the ship with the square chosen in this step
                    else if(messages.getText().equals(DIRECTION)){
                        switch(selection.compareTo(cell)){
                            //If the selected cell is the same as the first one it will change the cell back to blue and go back to SETUP
                            case 0:
                                messages.setText(SETUP);
                                selection.setFill(Color.BLUE);
                                break;
                            //cases 1-4 are determined by the compareTo function and coorespond to a direction
                            //n:north, s:south, w:west, e:east
                            //they call the setShipCoor functions to check if it is a valid row if not goes back to setup mode
                            //otherwise the selected spots will turn green on the board
                            case 1:
                                if(!player1.setShipCoor(selection.getRow(), selection.getCol(), 'n')){
                                    errorMessages.setText("Not a valid position");
                                    selection.setFill(Color.BLUE);
                                }else{
                                    for(int k = 0; k < player1.prevShipSize(); k++){
                                        Square editing = (Square)grid.getChildren().get((selection.getRow())*BOARD_SIZE + selection.getCol() - k);
                                        editing.setFill(Color.GREEN);
                                    }
                                }
                                messages.setText(SETUP);
                                break;
                            case 2:
                                if(!player1.setShipCoor(selection.getRow(), selection.getCol(), 's')){
                                    errorMessages.setText("Not a valid position");
                                    selection.setFill(Color.BLUE);
                                }else{
                                    for(int k = 0; k < player1.prevShipSize(); k++){
                                        Square editing = (Square)grid.getChildren().get((selection.getRow())*BOARD_SIZE + selection.getCol() + k);
                                        editing.setFill(Color.GREEN);
                                    }
                                }
                                messages.setText(SETUP);
                                break;
                            case 3:
                               if(!player1.setShipCoor(selection.getRow(), selection.getCol(), 'w')){
                                    errorMessages.setText("Not a valid position");
                                    selection.setFill(Color.BLUE);
                                }else{
                                    for(int k = 0; k < player1.prevShipSize(); k++){
                                        Square editing = (Square)grid.getChildren().get((selection.getRow()-k)*BOARD_SIZE + selection.getCol());
                                        editing.setFill(Color.GREEN);
                                    }
                                }
                                messages.setText(SETUP);
                                break;
                            case 4:
                                if(!player1.setShipCoor(selection.getRow(), selection.getCol(), 'e')){
                                    errorMessages.setText("Not a valid position");
                                    selection.setFill(Color.BLUE);
                                }else{
                                    for(int k = 0; k < player1.prevShipSize(); k++){
                                        Square editing = (Square)grid.getChildren().get((selection.getRow()+k)*BOARD_SIZE + selection.getCol());
                                        editing.setFill(Color.GREEN);
                                    }
                                }
                                messages.setText(SETUP);
                                break;
                            //for cells that are not directly north, south, east, or west of cell in selection
                            case -1:
                                errorMessages.setText("Ship either goes out of bounds, intersects something else,"
                                + "or not a valid direction");
                                messages.setText(SETUP);
                                selection.setFill(Color.BLUE);
                                break;
                        }
                        //checks if all ships are set for a player1 then sets ship for player2 (assuming they are a computer)
                        //otherwise it will display the next ship size to be placed
                        if(player1.allShipsSet()){
                            messages.setText(COMPSETUP);
                            shipSetup.setText("");
                            player2.placeShips(0, 0, 'a'); //dummy arguments do nothing
                            messages.setText(PLAYING);
                            for(int k = 0; k < BOARD_SIZE*BOARD_SIZE; k++){
                                Square editing = (Square)grid.getChildren().get(k);
                                editing.setFill(Color.BLUE);
                            }
                        }else{
                            shipSetup.setText("Current ship to set " + player1.currShipSize());
                        }
                    }
                });
            }
        }

        shipSetup.setText("Current ship to set " + player1.currShipSize());//tells shipSetup the first ship to be placed
        //sets up the places of all labels on the screen
        grid.add(messages,BOARD_SIZE+1,0);
        grid.add(errorMessages, BOARD_SIZE+1, 1);
        grid.add(shipSetup, BOARD_SIZE+1,6);
        grid.add(currPlayer, BOARD_SIZE+1,3);
        grid.add(hitIndicator, BOARD_SIZE+1,4);
        grid.add(sunkIndicator, BOARD_SIZE, 5);
        grid.add(opphitIndicator, BOARD_SIZE+1,7);
        grid.add(oppsunkIndicator, BOARD_SIZE+1,8);
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
}
