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

public class WelcomeScreen {

	private VBox vbox;
	private Canvas canvas;
	private StackPane root;
	private Scene scene;
	private Background bground;
	private Font font20px;
	private Font font40px;

	public WelcomeScreen(){
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.vbox = new VBox();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.bground = new Background(new BackgroundImage(new Image("images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
		this.font20px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 20);
		this.font40px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 40);
	}

	public void setStageComponents(Stage stage){
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.setSpacing(10);
		Text title = new Text("PACKET WARS");
		Button b1 = new Button ("New Game");
		Button b2 = new Button ("Instructions");
		Button b3 = new Button ("About");
		Button b4 = new Button ("Exit");
		Button b5 = new Button ("Chat Lobby");
		this.textProp(title);
		this.buttonProp(b1, stage);
		this.buttonProp(b2, stage);
		this.buttonProp(b3, stage);
		this.buttonProp(b4, stage);
		this.buttonProp(b5, stage);

		this.vbox.getChildren().addAll(title,b1, b2, b3, b4, b5);
		this.canvas.getGraphicsContext2D();

		this.root.setBackground(this.bground);
		this.root.getChildren().addAll(this.canvas,this.vbox);
		stage.setScene(this.scene);
		stage.setTitle("Mini Ship Shooting Game");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.show();
	}
	
	private void textProp(Text t){
		t.setFont(this.font40px);
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
				System.out.println(btn.getText() + " Hovered");
				btn.setStyle("-fx-border-color: #d75231; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #15c4e5");
			}
		});

		btn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println(btn.getText() + " Unhovered");
				btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");
			}
		});

		if(btn.getText()=="New Game"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					System.out.println(btn.getText());
					//TODO insert start game code
					GameStage theGameStage = new GameStage();
					theGameStage.setStage(stage);
				}
			});
		} else if (btn.getText()=="About"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					btn.setDisable(true);
					System.out.println(btn.getText());
					About abt = new About();
					abt.setStageComponents(stage);
				}
			});
		} else if (btn.getText()=="Instructions"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					btn.setDisable(true);
					System.out.println(btn.getText());
					Instructions ins = new Instructions();
					ins.setStageComponents(stage);
				}
			});
		} else if (btn.getText()=="Exit"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					btn.setDisable(true);
					System.out.println(btn.getText());
					System.exit(0);
				}
			});
		} else if (btn.getText()=="Chat Lobby"){
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					btn.setDisable(true);
					Instructions ins = new Instructions();
					ins.setStageComponents(stage);
				}
			});
		}

	}

	Scene getScene(){
		return this.scene;
	}
}