package miniprojtemplate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

	private Scene scene;
	private static Stage stage;
	private VBox root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private BackgroundImage bgimg = new BackgroundImage(new Image("images/bg.jpg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
    private Text chatText;
    private TextField chatInput;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
	private GridPane overlay;
	private ArrayList<String> chatHistory;
	private int chatYOffsetBase = -50;
	private int chatYOffset = 16;
	private int chatYOffsetFac = 0;
	private int chatHistoryCount = 0;
	private int chatHistoryLimit = 15;
	private String playerName;
	//the class constructor
	public GameStage(String ipAddress, String port, String playerName) {
		this.root = new VBox();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT-100);
		this.root.setBackground(new Background(this.bgimg));
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
        this.overlay = new GridPane();		
		this.gametimer = new GameTimer(this.gc,this.scene,this.root);
		this.chatHistory = new ArrayList<String>();
		this.playerName = playerName;
		System.out.println(ipAddress + " " + port);
        try {
            socket = new Socket(ipAddress, Integer.parseInt(port));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
		Thread receiveThread = new Thread(new ReceiveHandler());
        receiveThread.start();
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
        chatText.setTranslateX(50);
        chatText.setTranslateY(-50);
        root.getChildren().add(chatText);
	}
    private void processChatMessage(String message) {
		if(message.equals("/attack")) {
		   if (this.gametimer.sendWave()) {
			   out.println("Attack wave sent!");
		   } else {
			   out.println("Someone just tried to send out an attack! No money though.");
		   }
		}
		//read this, these functions called return strings. I want these to appear on the user's chat.
		else if(message.equals("/repair")) {
		   this.gametimer.repairUpgrade();
		} else if (message.equals("/hpup")) {
		   this.gametimer.upgradeHealth();
		} else if (message.equals("/dmup")) {
		   this.gametimer.upgradeDamage();
		} else {
		   out.println(playerName + ": " + message);
		}
    }
    private class ReceiveHandler implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    // Handle the received message
                	displayMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void displayMessage(String message) {
    	chatYOffsetFac = 0;
    	this.chatHistory.add(message);
    	if (this.chatHistory.size() > 15) this.chatHistory.remove(0);
    	StringBuffer messages = new StringBuffer();
    	chatHistoryCount = 0;
    	this.chatHistory.forEach((msg) -> {
    		if (chatHistoryCount <= chatHistoryLimit) {
				messages.append(msg + "\n");
				chatYOffsetFac += 1;
    		}
    		chatHistoryCount += 1;
    	});
    	chatText.setTranslateY(chatYOffsetBase - chatYOffset * chatYOffsetFac);
		this.chatText.setText(messages.toString());
		this.chatText.setFill(Color.WHITE);

        //System.out.println("Received message from server: " + message);
        if(message.equals("/attack")) {
        	this.gametimer.spawnFishes(15);
        }
    }
	public static Stage getStage() {
		return GameStage.stage;
	}
	
}