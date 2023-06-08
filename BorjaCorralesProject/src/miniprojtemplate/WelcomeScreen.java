package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
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
	private Image image;
	private String name;
	private String ipAddress;
	private String port;
	private TextField tfName;
	private TextField tfIpAddress;
	private TextField tfPort;

	public WelcomeScreen(){
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.vbox = new VBox();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
//		this.bground = new Background(new BackgroundImage(new Image("/images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
		this.font20px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 20);
		this.font40px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 40);
	}

	public void setStageComponents(Stage stage){
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.setSpacing(10);
		Text title = new Text("PACKET WARS");
		Text tName = new Text("Username");
		tfName = new TextField();
		HBox hName = new HBox(tfName);
		Text tIpAddress = new Text("IP ADDRESS");
		Text tPort = new Text("PORT");
		HBox hTextIpPort = new HBox(tIpAddress, tPort);
		tfIpAddress = new TextField();
		tfIpAddress.maxWidth(5);
		tfPort = new TextField();
		tfPort.maxWidth(5);
		HBox hIpAddress = new HBox(tfIpAddress,tfPort);
		hIpAddress.setTranslateX(GameStage.WINDOW_WIDTH/2 - 150);
		hTextIpPort.setTranslateX(GameStage.WINDOW_WIDTH/2 -100);
		hTextIpPort.setSpacing(100);
		hName.setTranslateX(GameStage.WINDOW_WIDTH/2 - 150);
		Button b1 = new Button ("New Game");
		Button b2 = new Button ("Instructions");
		Button b3 = new Button ("About");
		Button b4 = new Button ("Exit");
		this.textProp(title);
		this.buttonProp(b1, stage);
		this.buttonProp(b2, stage);
		this.buttonProp(b3, stage);
		this.buttonProp(b4, stage);

		this.vbox.getChildren().addAll(title,tName, hName, hTextIpPort,hIpAddress,b1, b2, b3, b4);
		this.canvas.getGraphicsContext2D();

//		this.root.setBackground(this.bground);
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
					ipAddress = tfIpAddress.getText();
					port = tfPort.getText();
					name = tfName.getText();
					GameStage theGameStage = new GameStage(ipAddress, port, name);
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
		}

	}

	Scene getScene(){
		return this.scene;
	}
}