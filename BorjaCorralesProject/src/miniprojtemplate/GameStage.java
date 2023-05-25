package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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


	//the class constructor
	public GameStage() {
		this.root = new VBox();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.root.setBackground(new Background(this.bgimg));
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		GameStage.stage = stage;
		
		repairShip.setTranslateX(100);
		repairShip.setTranslateY(800);
		repairShip.setPrefSize(200, 200);
		
		addMaxHealth.setTranslateX(400);
		addMaxHealth.setTranslateY(773);
		addMaxHealth.setPrefSize(200, 200);
		
		addMaxDamage.setTranslateX(700);
		addMaxDamage.setTranslateY(748);
		addMaxDamage.setPrefSize(200, 200);
		
		this.root.getChildren().add(repairShip);
		this.root.getChildren().add(addMaxHealth);
		this.root.getChildren().add(addMaxDamage);
		
		//set stage elements here
		this.root.getChildren().add(canvas);

		GameStage.stage.setTitle("Mini Ship Shooting Game");
		GameStage.stage.setScene(this.scene);

		//invoke the start method of the animation timer
		this.gametimer.start();

		GameStage.stage.show();
	}
	
	public static Stage getStage() {
		return GameStage.stage;
	}
	
}