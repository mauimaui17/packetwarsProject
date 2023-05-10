package miniprojtemplate;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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