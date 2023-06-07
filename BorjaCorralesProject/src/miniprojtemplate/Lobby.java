package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

public class Lobby {
	private Background bground;
	private Scene scene;
	private Canvas canvas;
	private StackPane root;
	private FlowPane buttons;
	private BorderPane bp;
	private VBox vbox;
	private VBox vbox1;
	private FlowPane fp1;
	private Font font20px;
	private Insets inset;
	
	private StackPane lobbysp;
	TextField textinput = new TextField();
	
	private Color bgcolor;
	private Color barcolor;
	
	public Lobby() {
		this.bgcolor = Color.rgb(230, 230, 230);
		this.barcolor = Color.rgb(200, 200, 200);
		this.inset = new Insets(20.0f);
		this.lobbysp = new StackPane();
		this.textinput = new TextField();
		this.font20px = Font.loadFont("file:resources/fonts/Poppins-Bold.ttf", 20);
		this.bground = new Background(new BackgroundFill(bgcolor, null, null));
		this.vbox = new VBox();
		this.vbox1 = new VBox();
		this.fp1 = new FlowPane();
		this.buttons = new FlowPane();
		this.bp = new BorderPane();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}
	
	public void setStageComponents(Stage stage) {
		//top
		Button back = new Button("Back");
		this.buttonProp(back, stage);

		Text title = new Text("Chat Lobby");
		this.titleTextProp(title);
		Text dummy = new Text("");
	
		FlowPane fp = new FlowPane();
		FlowPane.setMargin(back, inset);
		FlowPane.setMargin(title, inset);
		fp.setMaxWidth(GameStage.WINDOW_WIDTH);
		fp.setHgap(10.0f);

		fp.getChildren().addAll(dummy, back, title);
		
		Rectangle rectbg = new Rectangle(20, 20, GameStage.WINDOW_WIDTH-40, 60);
		rectbg.setFill(barcolor);
		
		this.lobbysp.getChildren().addAll(rectbg, fp);
	
		//mid
		Rectangle chats = new Rectangle(20, 0, GameStage.WINDOW_WIDTH-40, 700);
		chats.setFill(Color.WHITE);
		
		textinput.setPromptText("Enter your chat here");
		textinput.setMaxWidth(GameStage.WINDOW_WIDTH-40);
		textinput.setPrefHeight(50.0f);
		textinput.setStyle("-fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: #ffffff; -fx-text-fill: #000000");
		
		textinput.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
                String message = textinput.getText();
                processChatMessage(message);
                textinput.clear();
            }
		});
		
		this.vbox1.getChildren().addAll(chats, textinput);
		this.vbox1.setSpacing(20.0f);
		this.vbox1.setAlignment(Pos.TOP_CENTER);
		
		this.vbox.getChildren().addAll(lobbysp, vbox1);
		this.vbox.setAlignment(Pos.TOP_CENTER);
		
		this.root.getChildren().addAll(this.vbox);
		this.root.setBackground(this.bground);
		
		stage.setScene(this.scene);
		stage.setTitle("Chat Lobby");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.show();
	}
	
	private void processChatMessage(String message) {
//        this.chatText.setText("Chat: " + message);
 }
	
	private void titleTextProp(Text t) {
		t.setFont(font20px);
		t.setStyle("-fx-fill: #000000");
		t.setTextAlignment(TextAlignment.CENTER);
	}

	private void buttonProp(Button btn, Stage stage) {
		btn.setMaxSize(100, 50);
		btn.setFont(this.font20px);
		btn.setStyle("-fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #000000");
		
		if(btn.getText()=="Back") {
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					System.out.println(btn.getText());
					//TODO insert welcome screen
					WelcomeScreen welcome = new WelcomeScreen();
					welcome.setStageComponents(stage);
				}
			});
		}else if(btn.getText()=="Send") {
			btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					System.out.println(btn.getText());
					//TODO insert welcome screen
					//send message
				}
			});
		}
	}
}
