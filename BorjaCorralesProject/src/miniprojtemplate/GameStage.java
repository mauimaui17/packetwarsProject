package miniprojtemplate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameStage {
	public static final int WINDOW_HEIGHT = 900;
	public static final int WINDOW_WIDTH = 1740;
	
	public static final Button repairShip = new Button("Repair");
	public static final Button addMaxHealth =  new Button("Upgrade Max Health");
	public static final Button addMaxDamage = new Button("Upgrade Max Damage");
	private Scene scene;
	private static Stage stage;
	private VBox root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private BackgroundImage bgimg = new BackgroundImage(new Image("images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
    private Text chatText;
    private TextField chatInput;
    private Socket socket;
    private PrintWriter out;
	private GridPane overlay;
	//the class constructor
	public GameStage() {
		this.root = new VBox();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT-100);
		this.root.setBackground(new Background(this.bgimg));
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
        this.overlay = new GridPane();		
        repairShip.setTranslateX(100);
		repairShip.setTranslateY(800);
		repairShip.setPrefSize(200, 200);
		
		addMaxHealth.setTranslateX(400);
		addMaxHealth.setTranslateY(773);
		addMaxHealth.setPrefSize(200, 200);
		
		addMaxDamage.setTranslateX(700);
		addMaxDamage.setTranslateY(748);
		addMaxDamage.setPrefSize(200, 200);
		
		root.getChildren().add(repairShip);
		root.getChildren().add(addMaxHealth);
		root.getChildren().add(addMaxDamage);
		this.gametimer = new GameTimer(this.gc,this.scene,this.root);
        try {
            socket = new Socket("0.0.0.0", 1234);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repairShip.setOnAction(e -> {
            // Action to perform when repairShip button is clicked
            System.out.println("Repair button clicked");
            // Perform repair action or any other logic
        });

        addMaxHealth.setOnAction(e -> {
            // Action to perform when addMaxHealth button is clicked
            System.out.println("Add Max Health button clicked");
            // Perform addMaxHealth action or any other logic
        });

        addMaxDamage.setOnAction(e -> {
            // Action to perform when addMaxDamage button is clicked
            System.out.println("Add Max Damage button clicked");
            // Perform addMaxDamage action or any other logic
        });
	}
	//method to add the stage elements
	public void setStage(Stage stage) {
		GameStage.stage = stage;
        this.overlay.setAlignment(Pos.BOTTOM_LEFT);
        this.overlay.setPadding(new Insets(10));
        
		GameStage.stage.setTitle("Packet Wars");
		

		
		//set stage elements here
		//this.root.getChildren().add(canvas);
		this.root.getChildren().add(this.canvas);
		this.chat(root);
		GameStage.stage.setTitle("Mini Ship Shooting Game");
		GameStage.stage.setScene(this.scene);
		//invoke the start method of the animation timer
		this.gametimer.start();

		GameStage.stage.show();
	}
	private void chat(VBox root) {
        chatInput = new TextField();
        chatInput.setPromptText("Enter your message...");
        chatInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String message = chatInput.getText();
                processChatMessage(message);
                chatInput.clear();
            }
        });
        GridPane.setConstraints(chatInput, 0, 0);
        root.getChildren().add(chatInput);
        this.chatText = new Text();
        chatText.setTranslateX(800);
        chatText.setTranslateY(200);
        root.getChildren().add(chatText);
	}
    private void processChatMessage(String message) {
    	   System.out.println(message);
           this.chatText.setText("Chat: " + message);
           out.println(message); // Send the message to the server
           
    }

	public static Stage getStage() {
		
		return GameStage.stage;
	}
	
}