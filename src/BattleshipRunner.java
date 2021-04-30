import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
public class BattleshipRunner extends Application{
    
    private final int BOARD_SIZE = 10;
    private final String SETUP = "Set up your ships";
    private final String DIRECTION = "Pick an orientation";
    private int r0;
    private int c0;
    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        int shipNum = 1;
        GridPane grid = new GridPane();
        Label messages = new Label("Set up your ships");
        Label errorMessages = new Label("");
        HumanPlayer player1 = new HumanPlayer();

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                Square cell = new Square(0,0,75,i,j);
                cell.setFill(Color.BLUE);
                cell.setStroke(Color.WHITE);
                grid.add(cell,i,j);
                cell.setOnMouseClicked(event ->{
                    if(messages.getText().equals(SETUP)){
                        r0 = cell.getRow();
                        c0 = cell.getCol();
                        errorMessages.setText("");
                        messages.setText(DIRECTION);
                    }
                    if(messages.getText().equals(DIRECTION)){
                        if(c0 - cell.getCol() == 0)
                            if(r0 - cell.getRow() > 0)
                                player1.setShipCoor(r0, c0, 'n');
                            else if(r0 - cell.getRow() < 0)
                                player1.setShipCoor(r0, c0, 's');
                            else{
                                errorMessages.setText("Ship either goes out of bounds or intersects something else");
                                messages.setText(SETUP);
                            }
                        
                    }
                });
            }
        }

        grid.add(messages,BOARD_SIZE+1,0);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }
}
