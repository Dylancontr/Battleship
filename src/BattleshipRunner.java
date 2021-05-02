import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;


public class BattleshipRunner extends Application{
    
    private final int BOARD_SIZE = 10;
    private Square selection;
    
    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String SETUP = "Set up your ships";
        String DIRECTION = "Pick an orientation";
        String COMPSETUP = "Computer setting up";
        String PLAYING = "Choose a space to shoot at";
        GridPane grid = new GridPane();
        Label messages = new Label(SETUP);
        Label errorMessages = new Label("");
        HumanPlayer player1 = new HumanPlayer();
        ComputerPlayer player2 = new ComputerPlayer();
        Label shipSetup = new Label();
        Label currPlayer = new Label("player1");
        Label hitIndicator = new Label();
        Label sunkIndicator = new Label();
        Label opphitIndicator = new Label();
        Label oppsunkIndicator = new Label();

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                Square cell = new Square(0,0,75,i,j);
                cell.setFill(Color.BLUE);
                cell.setStroke(Color.WHITE);
                grid.add(cell,i,j);
                cell.setOnMouseClicked(event ->{
                    if(messages.getText().equals(PLAYING)){
                        if(currPlayer.getText().equals("player1")){
                                String outcome = player1.takeTurn(player2, cell.getRow(), cell.getCol());
                                if(outcome.equals("Hit")){

                                    hitIndicator.setText("Hit");
                                    cell.setFill(Color.BLACK);
                                    currPlayer.setText("player2");

                                }else if(outcome.equals("Miss")){

                                    hitIndicator.setText("Miss");
                                    cell.setFill(Color.WHITE);
                                    currPlayer.setText("player2");
                                        
                                }else if(outcome.equals("Area already shot")){
                                    
                                    hitIndicator.setText("You already shot at that area, go again");
                                    
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
                            if(currPlayer.getText().equals("player2") && player2 instanceof ComputerPlayer){
                                int x = (int)(Math.random()*9);
                                int y = (int)(Math.random()*9);
                                String outcome = player2.takeTurn(player1, x, y);
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
                    else if(messages.getText().equals(DIRECTION)){
                        switch(selection.compareTo(cell)){
                            case 0:
                                messages.setText(SETUP);
                                selection.setFill(Color.BLUE);
                                break;
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
                            case -1:
                                errorMessages.setText("Ship either goes out of bounds, intersects something else,"
                                + "or not a valid direction");
                                messages.setText(SETUP);
                                selection.setFill(Color.BLUE);
                                break;
                        }
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
        shipSetup.setText("Current ship to set " + player1.currShipSize());
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
