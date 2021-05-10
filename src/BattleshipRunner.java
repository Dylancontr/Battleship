import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class BattleshipRunner extends Application{
    
    private final int BOARD_SIZE = 10;
    private Square selection;//will use this to save selected square when setting up ships

    //Strings used to determing what stage of the game the players are in
    String SETUP = "Set up your ships";
    String DIRECTION = "Pick an orientation";
    String COMPSETUP = "Computer setting up";
    String PLAYING = "Choose a space to shoot at";

    HumanPlayer player1;
    ComputerPlayer player2;

    GridPane grid;
    GridPane oppGrid;

    Label messages;
    Label errorMessages;
    Label Message;

    Label currPlayer;

    Label shipSetup;

    Label hitIndicator;
    Label sunkIndicator;
    Label opphitIndicator;
    Label oppsunkIndicator;

    GridPane container;

    Button confirm;
    Button deny;

     //Status of ships: additional ships need to be added and removed manually

     Label p1Ships;
     Label p1Des;
     Label p1Sub;
     Label p1Cru;
     Label p1Bat;
     Label p1Car;
     
     Label p2Ships;
     Label p2Des;
     Label p2Sub;
     Label p2Cru;
     Label p2Bat;
     Label p2Car;


    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        
       
        //gridPane that holds gridPanes
        container = new GridPane();
        
        //sets up player grids
        grid = new GridPane();
        oppGrid = new GridPane();

        messages = new Label(SETUP);//determines game state
        errorMessages = new Label("");//any errors are displayed in here
        Message = new Label ("Welcome and Enjoy!");

        //players
        player1 = new HumanPlayer();
        player2 = new ComputerPlayer();
        Label P1 = new Label("Player1");
        Label P2 = new Label ("Player2");

        p1Ships = new Label ("Player 1 Ships: ");
        p1Des = new Label ("Destroyer");
        p1Sub = new Label ("Submarine");
        p1Cru = new Label ("Cruiser");
        p1Bat = new Label ("BattleShip");
        p1Car = new Label ("Carrier");

        p2Ships = new Label ("Player 2 Ships: ");
        p2Des = new Label ("Destroyer");
        p2Sub = new Label ("Submarine");
        p2Cru = new Label ("Cruiser");
        p2Bat= new Label ("BattleShip");
        p2Car = new Label ("Carrier");
        
        shipSetup = new Label();//which ship is being set
        
        currPlayer = new Label("player1");//determines who's turn it is
        
        //hit/miss indicators for both players
        hitIndicator = new Label();
        sunkIndicator = new Label();
        opphitIndicator = new Label();
        oppsunkIndicator = new Label();
        
        //Buttons to finalize board placements
        confirm = new Button("yes");
        deny = new Button("no");

        //Button behaviors
        confirm.setOnMouseClicked(event->{
            messages.setText(COMPSETUP);
            shipSetup.setText("");
            player2.placeShips(0, 0, 'a'); //dummy arguments do nothing
            messages.setText(PLAYING);
            oppsunkIndicator.setText("");
            for(int k = 0; k < BOARD_SIZE*BOARD_SIZE; k++){
                Square editing = (Square)grid.getChildren().get(k);
                editing.setFill(Color.BLUE);
            }
            container.getChildren().removeAll(confirm,deny);
        });

        deny.setOnMouseClicked(event->{
            if(currPlayer.getText().equals("player1")){
                player1.clearBoard();
                player1.setCurrIndex(0);
                messages.setText(SETUP);
                for(int k = 0; k < BOARD_SIZE*BOARD_SIZE; k++){
                    Square editing = (Square)grid.getChildren().get(k);
                    editing.setFill(Color.BLUE);
                }
                oppsunkIndicator.setText("");
                container.getChildren().removeAll(confirm,deny);
            }
        });
        

        //Put Title
    	stage.setTitle("Battle Ship");
    	
    	//Create menu bar
    	MenuBar menu = new MenuBar();
    	
    	//File menu
    	Menu Options = new Menu ("_Options");
    	MenuItem load = new MenuItem("_Load previous game");
    	MenuItem save = new MenuItem("_Save game");
    	MenuItem exit = new MenuItem("E_xit");
    	MenuItem restart = new MenuItem("_Restart");
    	
    	Options.getItems().add(load);
    	Options.getItems().add(save);
    	Options.getItems().add(new SeparatorMenuItem());
    	Options.getItems().add(restart);
    	Options.getItems().add(exit);

        exit.setOnAction(event ->{
    		stage.close();
    	});

        restart.setOnAction(event ->{
        	player1.clearBoard();
        	player1.setCurrIndex(0);
        	for(int k = 0; k < BOARD_SIZE*BOARD_SIZE; k++){
        		Square editing = (Square)grid.getChildren().get(k);
        		editing.setFill(Color.BLUE);
        	}
        	player2.clearBoard();
        	player2.setCurrIndex(0);
        	for(int k = 0;k < BOARD_SIZE*BOARD_SIZE; k++) {
        		Square editing = (Square)oppGrid.getChildren().get(k);
        		editing.setFill(Color.BLUE);
        	}
        	messages.setText(SETUP);
        	errorMessages.setText("");
        	
        	p1Des.getStyleClass().removeAll("label-sunk");
        	p1Sub.getStyleClass().removeAll("label-sunk");
        	p1Cru.getStyleClass().removeAll("label-sunk");
        	p1Bat.getStyleClass().removeAll("label-sunk");
        	p1Car.getStyleClass().removeAll("label-sunk");
        	
        	p2Des.getStyleClass().removeAll("label-sunk");
        	p2Sub.getStyleClass().removeAll("label-sunk");
        	p2Cru.getStyleClass().removeAll("label-sunk");
        	p2Bat.getStyleClass().removeAll("label-sunk");
        	p2Car.getStyleClass().removeAll("label-sunk");
        	
        	currPlayer.setText("player1");
        	
        	shipSetup.setText("");
        	hitIndicator.setText("");
        	sunkIndicator.setText("");
        	opphitIndicator.setText("");
        	oppsunkIndicator.setText("");
        	
        	Message.setText("Game restarted");
        });
        
        save.setOnAction(event ->{
    		try {
    		FileOutputStream savefile = new FileOutputStream("PLAYER.dat");
    		ObjectOutputStream A = new ObjectOutputStream(savefile);
    		A.writeObject(player1);
    		A.writeObject(player2);
            
            A.writeObject(selection);

            A.writeObject(messages.getText());
            A.writeObject(errorMessages.getText());

            A.writeObject(p1Des.getStyleClass().contains("label-sunk"));
            A.writeObject(p1Sub.getStyleClass().contains("label-sunk"));
            A.writeObject(p1Cru.getStyleClass().contains("label-sunk"));
            A.writeObject(p1Bat.getStyleClass().contains("label-sunk"));
            A.writeObject(p1Car.getStyleClass().contains("label-sunk"));

            A.writeObject(p2Des.getStyleClass().contains("label-sunk"));
            A.writeObject(p2Sub.getStyleClass().contains("label-sunk"));
            A.writeObject(p2Cru.getStyleClass().contains("label-sunk"));
            A.writeObject(p2Bat.getStyleClass().contains("label-sunk"));
            A.writeObject(p2Car.getStyleClass().contains("label-sunk"));

            A.writeObject(currPlayer.getText());
            A.writeObject(shipSetup.getText());
            A.writeObject(hitIndicator.getText());
            A.writeObject(sunkIndicator.getText());
            A.writeObject(opphitIndicator.getText());
            A.writeObject(oppsunkIndicator.getText());


    		Message.setText("Game Saved");
    		A.close();
            if(messages.getText().equals("")) container.getChildren().removeAll(confirm,deny);

        }catch (Exception e) {Message.setText("An error has occurred when saving to the file!");
    		e.printStackTrace();}
    	});

    	load.setOnAction(event ->{
        	try {
                FileInputStream Loadfile = new FileInputStream("PLAYER.dat");
                ObjectInputStream B = new ObjectInputStream(Loadfile);
                player1 = (HumanPlayer)(B.readObject());
                player2 = (ComputerPlayer)(B.readObject());

                selection = (Square)(B.readObject());

                messages.setText(((String)(B.readObject())));
                errorMessages.setText(((String)(B.readObject())));

                if((Boolean)B.readObject())p1Des.getStyleClass().add("label-sunk");
                else p1Des.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p1Sub.getStyleClass().add("label-sunk");
                else p1Sub.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p1Cru.getStyleClass().add("label-sunk");
                else p1Cru.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p1Bat.getStyleClass().add("label-sunk");
                else p1Bat.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject() && !p1Car.getStyleClass().contains("label-sunk"))p1Car.getStyleClass().add("label-sunk");
                else p1Car.getStyleClass().removeAll("label-sunk");

                if((Boolean)B.readObject())p2Des.getStyleClass().add("label-sunk");
                else p2Des.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p2Sub.getStyleClass().add("label-sunk");
                else p2Sub.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p2Cru.getStyleClass().add("label-sunk");
                else p2Cru.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p2Bat.getStyleClass().add("label-sunk");
                else p2Bat.getStyleClass().removeAll("label-sunk");
                if((Boolean)B.readObject())p2Car.getStyleClass().add("label-sunk");
                else p2Car.getStyleClass().removeAll("label-sunk");

                currPlayer.setText(((String)(B.readObject())));
                shipSetup.setText(((String)(B.readObject())));
                hitIndicator.setText(((String)(B.readObject())));
                sunkIndicator.setText(((String)(B.readObject())));
                opphitIndicator.setText(((String)(B.readObject())));
                oppsunkIndicator.setText(((String)(B.readObject())));

                Message.setText("Previous game loaded");

                if(!messages.getText().equals("")) container.getChildren().removeAll(confirm,deny);
                
                B.close();

                for(int r = 0; r < BOARD_SIZE; r++){
                    for(int c = 0; c < BOARD_SIZE; c++){
                        Square editing = (Square)oppGrid.getChildren().get((r*BOARD_SIZE + c));
                        if(!messages.getText().equals(PLAYING)) editing = (Square)grid.getChildren().get((r*BOARD_SIZE + c));
                        if(player1.areaIsFree(r, c)){
                            editing.setFill(Color.BLUE);
                        }
                        else if(player1.shot(r,c)){
                            if(!player1.shotType(r, c)) editing.setFill(Color.WHITE);
                            else editing.setFill(Color.BLACK);
                        }else{
                            if(!messages.getText().equals(PLAYING)) editing.setFill(Color.GREEN);
                            else editing.setFill(Color.BLUE);
                        }
                        
                        editing = (Square)grid.getChildren().get((r*BOARD_SIZE + c));
                        if(!messages.getText().equals(PLAYING)) editing = (Square)oppGrid.getChildren().get((r*BOARD_SIZE + c));
                        if(player2.areaIsFree(r, c)){
                            editing.setFill(Color.BLUE);
                        }
                        else if(player2.shot(r,c)){
                            if(!player2.shotType(r, c)) editing.setFill(Color.WHITE);
                            else editing.setFill(Color.BLACK);
                        }else{
                            if(!messages.getText().equals(PLAYING)) editing.setFill(Color.GREEN);
                            editing.setFill(Color.BLUE);
                        }
                    }
                }

                if(messages.getText().equals(DIRECTION)){
                    ((Square)grid.getChildren().get((selection.getRow()*BOARD_SIZE + selection.getCol()))).setFill(Color.RED);
                }


            }catch (Exception e) {
                Message.setText("An error has occurred when loading the file!");
        	    e.printStackTrace();
            } 
    		
    	});
        
        //Sets up boards of squares and sets their behaviors when clicked on
        //defaults for base squares is blue fill and white stroke

        //Computer Board
        setCompBoard();

        setBoard();//PlayerBoard


        shipSetup.setText("Current ship to set " + player1.currShipSize());//tells shipSetup the first ship to be placed



        //sets up the places of all labels on the screen
        container.add(messages,1,1);
        container.add(errorMessages,1,2);
        container.add(shipSetup,1,9);
        container.add(currPlayer,1,6);
        container.add(hitIndicator,1,7);
        container.add(sunkIndicator,1,8);
        container.add(opphitIndicator,1,10);
        container.add(oppsunkIndicator,1, 11);
        container.add(new Label("*************************************************"),1,14);

        container.add(grid,0,1);
        container.add(oppGrid,2,1);

        container.add(P1,0,0);
        container.add(P2,2,0);

        container.add(p1Ships,0,2);
        container.add(p1Des,0,3);
        container.add(p1Sub,0,4);
        container.add(p1Cru,0,5);
        container.add(p1Bat,0,6);
        container.add(p1Car,0,7);
        
        container.add(p2Ships,2,2);
        container.add(p2Des,2,3);
        container.add(p2Sub,2,4);
        container.add(p2Cru,2,5);
        container.add(p2Bat,2,6);
        container.add(p2Car,2,7);
        
        container.setPadding(new Insets(10));

        BorderPane BP = new BorderPane();
		BP.setTop(menu);
		BP.setBottom(Message);
		BP.setCenter(container);
		
		P1.getStyleClass().add("label-player");
		P2.getStyleClass().add("label-player");

        menu.getMenus().add(Options);
		
        Scene scene = new Scene(BP,1725,925);
        
        scene.getStylesheets().add("COLOR.css");
        stage.setScene(scene);
        
        stage.setScene(scene);
        stage.show();
    }

    void setBoard(){
        //Player Board
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                Square cell = new Square(0,0,60,i,j);
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
                                    
                                    sunkIndicator.setText("");
                                    hitIndicator.setText("Hit");
                                    cell.setFill(Color.BLACK);
                                    currPlayer.setText("player2");

                                //if miss is returned, sets cell as white and changes turn 
                                }else if(outcome.equals("Miss")){
                                    
                                    sunkIndicator.setText("");
                                    hitIndicator.setText("Miss");
                                    cell.setFill(Color.WHITE);
                                    currPlayer.setText("player2");
                                
                                //if already shot area selected says so and doesn't change turn
                                }else if(outcome.equals("Area already shot")){
                                    
                                    sunkIndicator.setText("");
                                    hitIndicator.setText("You already shot at that area, go again");
                                    
                                //returns a hit phrase of what is sunk, anything other than a ship being sunk should be made into an if statement
                                }else{
                                    
                                    cell.setFill(Color.BLACK);
                                    hitIndicator.setText("Hit");
                                    sunkIndicator.setText("You " + outcome);
                                    updateSunkLabel(outcome);
                                    if(!player2.checkStillAlive()){
                                        messages.setText("Player1 wins");
                                    }
                                    currPlayer.setText("player2");
                                }
                            }
                            
                            //automatically takes player2's turn if they are a computer
                            if(currPlayer.getText().equals("player2") && player2 instanceof ComputerPlayer){
                                String outcome = "";
                                int r = 0;
                                int c = 0;

                                //ComputerPlayer class has an array of predecided on targets this checks if there is one
                                if(player2.getTarget() != null){
                                    do{
                                        
                                        r = player2.getTarget().getRow();
                                        c = player2.getTarget().getCol();
                                        outcome = player2.takeTurn(player1,r,c);
                                        
                                    }while(outcome.equals("Area already shot") && player2.getTarget() != null);
                                    //while loop to make sure it won't fire on something it already has shot and in that case if there is a target still present
                                }
                                
                                //randomly picks board square until it hits a new spot in the case outcome is not set or do while loop in previous if
                                //resulted in "Area already shot"
                                if(outcome.equals("Area already shot") || outcome.equals("")){

                                    do{
            
                                        r = (int)(Math.random()*10);
                                        c = (int)(Math.random()*10);
                                        outcome = player2.takeTurn(player1, r,c);

                                    }while(outcome.equals("Area already shot"));

                                }



                                //if Hit is returned it sets the cell as Black
                                if(outcome.equals("Hit")){

                                    opphitIndicator.setText("Opponent Hit");
                                    oppsunkIndicator.setText("");
                                    Square editing = (Square)oppGrid.getChildren().get((r*BOARD_SIZE + c));
                                    editing.setFill(Color.BLACK);
                                
                                //if Miss is returned it sets the cell as White
                                }else if(outcome.equals("Miss")){
                                    
                                    opphitIndicator.setText("Opponent Miss");
                                    oppsunkIndicator.setText("");
                                    Square editing = (Square)oppGrid.getChildren().get((r*BOARD_SIZE + c));
                                    editing.setFill(Color.WHITE);
                                
                                //else makes the cell Black and tells what ship has been sunk
                                }else{
                                    
                                    opphitIndicator.setText("Opponent Hit");
                                    Square editing = (Square)oppGrid.getChildren().get((r*BOARD_SIZE + c));
                                    editing.setFill(Color.BLACK);
                                    oppsunkIndicator.setText("Opponent has "+ outcome);
                                    updateSunkLabel(outcome);

                                    if(!player1.checkStillAlive()){
                                        messages.setText("Player2 wins");
                                    }

                                }
                                    currPlayer.setText("player1");//once a spot has been shot, changes to player1
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
                            shipSetup.setText("");
                            messages.setText("");
                            container.add(confirm,1,9);
                            container.add(deny,1,10);
                            oppsunkIndicator.setText("Confirm Placement?");
                        }else{
                            shipSetup.setText("Current ship to set " + player1.currShipSize());
                        }
                    }
                });
            }
        }
    }

    private void setCompBoard(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                Square cell = new Square(0,0,60,i,j);
                cell.setFill(Color.BLUE);
                cell.setStroke(Color.WHITE);
                oppGrid.add(cell,i,j);
            }
        }
    }

    private void updateSunkLabel(String s){
        String playing = currPlayer.getText();

        if(s.equals("sunk Destroyer")){
            //x.getStyleClass().contains("label-sunk")
            if(playing.equals("player1")) p2Des.getStyleClass().add("label-sunk");
            else p1Des.getStyleClass().add("label-sunk");

        }else if(s.equals("sunk Submarine")){
            if(playing.equals("player1")) p2Sub.getStyleClass().add("label-sunk");
            else p1Sub.getStyleClass().add("label-sunk");

        }else if(s.equals("sunk Cruiser")){
            if(playing.equals("player1")) p2Cru.getStyleClass().add("label-sunk");
            else p1Cru.getStyleClass().add("label-sunk");

        }else if(s.equals("sunk Battleship")){
            if(playing.equals("player1")) p2Bat.getStyleClass().add("label-sunk");
            else p1Bat.getStyleClass().add("label-sunk");

        }else if(s.equals("sunk Carrier")){
            if(playing.equals("player1")) p2Car.getStyleClass().add("label-sunk");
            else p1Car.getStyleClass().add("label-sunk");
        }

    }
}