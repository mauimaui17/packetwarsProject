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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class About {
	private Background bground;
	private BorderPane bp;
	private Canvas canvas;
	private Font font20px;
	private Font font12px;
	private Scene scene;
	private StackPane root;

	public About(){
//		this.bground = new Background(new BackgroundImage(new Image("images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
		this.bp = new BorderPane();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.font20px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 20);
		this.font12px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 12);
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	public void setStageComponents(Stage stage){

		Button back = new Button("Back");
		this.buttonProp(back, stage);
		bp.setTop(back);

		Text source = new Text("Reference: "
				+ "\nUPLB ICS for the CMSC22 Source Code/Template"
				+ "\nTo fix ship firerate, we referenced \nhttps://stackoverflow.com/questions/21509652/libgdx-best-way-to-adjust-fire-rate-in-a-loop"
				+ "\n\nImage Source: "
				+ "\nhttps://www.pinclipart.com/pindetail/hJmhTR_transparent-sprites-ship-clip-black-and-white-stock/"
				+ "\nhttps://toppng.com/free-image/top-down-spaceship-png-clip-top-down-spaceship-PNG-free-PNG-Images_166864"
				+ "\nhttps://opengameart.org/content/space-background-3"
				+ "\nhttps://depositphotos.com/125343024/stock-illustration-line-art-keyboard-keys-in.html"
				+ "\nhttp://pixelartmaker.com/art/32194e369f51794"
				+ "\nhttps://www.pngkit.com/bigpic/u2e6w7a9w7r5e6t4/"
				+ "\n\nBackground Animated by John Maui Borja" 
				+ "\nStatus Bar Sprites and Power Ups drawn by John Maui Borja"
				+ "\n\nFont Source:"
				+ "\nhttps://www.fontspace.com/starlord-font-f22619");
		this.textProp(source);
		this.bp.setCenter(source);
		BorderPane.setAlignment(source, Pos.CENTER);

		Text create = new Text("Created by:"
				+ "\nJohn Maui Borja | Ralph Jason Corrales"
				+ "\n2021");
		this.textProp(create);
		this.bp.setBottom(create);
		BorderPane.setAlignment(create, Pos.CENTER);

		this.root.getChildren().addAll(this.canvas,this.bp);
//		this.root.setBackground(this.bground);

		stage.setScene(this.scene);
		stage.setTitle("Mini Ship Shooting Game");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.show();

	}

	private void buttonProp(Button btn, Stage stage){
		btn.setMaxSize(100,50);
		btn.setFont(font20px);
		btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");

		btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println(btn.getText());
				WelcomeScreen welcome = new WelcomeScreen();
				welcome.setStageComponents(stage);
			}
		});

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
	}

	private void textProp(Text t){
		t.setFont(font12px);
		t.setStyle("-fx-fill: #dc983a");
		t.setTextAlignment(TextAlignment.CENTER);
	}
}