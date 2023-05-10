package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameOver {
	private Canvas canvas;
	private Background bground;
	private Font font20px;
	private Scene scene;
	private StackPane root;
	private VBox vbox;
	private int score;
	private boolean boss_kill;
	public GameOver(int score, boolean boss_kill){
		this.bground = new Background(new BackgroundImage(new Image("images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.font20px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 20);
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.vbox = new VBox();
		this.score = score;
		this.boss_kill = boss_kill;
		//System.out.println("Initializing");
	}

	public void setStageComponents(Stage stage){
		//System.out.println("Displaying");
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.setSpacing(10);

		Text text = new Text("Mission Failed, We'll Get Them Next Time.");
		Text text2 = new Text("You brought " + this.score + " xenos with you.");
		Text text3 = new Text("");
		if(this.boss_kill) {
			text3 = new Text("You managed to kill their commander,\n but at what cost?");
		}
		Button b1 = new Button ("Main Menu");
		Button b2 = new Button ("Exit");

		this.textProp(text);
		this.textProp(text2);
		this.textProp(text3);
		this.buttonProp(b1, stage);
		this.buttonProp(b2, stage);

		this.vbox.getChildren().addAll(text, text2,text3, b1, b2);
		this.canvas.getGraphicsContext2D();
		this.root.getChildren().addAll(this.canvas,this.vbox);
		this.root.setBackground(this.bground);
		stage.setScene(this.scene);
		stage.setTitle("Mini Ship Shooting Game");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.show();
	}

	private void textProp(Text t){
		t.setFont(this.font20px);
		t.setStyle("-fx-fill: #dc983a");
		t.setTextAlignment(TextAlignment.CENTER);
	}

	private void buttonProp(Button btn, Stage stage){
		btn.setFont(this.font20px);
		btn.setMaxSize(250,50);
		btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");

		btn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println("Hovered");
				btn.setStyle("-fx-border-color: #d75231; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #15c4e5");
			}
		});

		btn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println("Unhovered");
				btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");
			}
		});

		if (btn.getText()== "Exit"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					System.out.println(btn.getText());
					System.exit(0);
				}
			});
		} else if (btn.getText()== "Main Menu"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					System.out.println(btn.getText());
					//TODO insert start game code
					WelcomeScreen welcome = new WelcomeScreen();
					welcome.setStageComponents(stage);
				}
			});
		}
	}

	Scene getScene(Stage stage){
		this.setStageComponents(stage);
		return this.scene;
	}
}