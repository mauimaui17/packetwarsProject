package miniprojtemplate;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Instructions {
	private Background bground;
	private BorderPane bp;
	private Canvas canvas;
	private FlowPane buttons;
	private Font font20px;
	private Scene scene;
	private StackPane root;
	private VBox vbox;
	private int pageNum;

	public Instructions(){
//		this.bground = new Background(new BackgroundImage(new Image("images/spayce.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
		this.bp = new BorderPane();
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.buttons = new FlowPane();
		this.font20px = Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 20);
		this.root = new StackPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.vbox = new VBox();
		this.pageNum = 1;
	}

	public void setStageComponents(Stage stage){
		Button back = new Button("Back");
		this.buttonProp(back, stage);

		this.vbox.setSpacing(10);

		Text title = new Text("Instructions");
		this.titleTextProp(title);

		FlowPane fp = new FlowPane();
		this.flowpaneProp(fp);

		fp.getChildren().addAll(back, title);

		this.bp.setTop(fp);
		BorderPane.setAlignment(fp, Pos.CENTER);

		showPage1();

		this.bp.setCenter(this.vbox);
		this.root.getChildren().addAll(this.canvas,this.bp);
//		this.root.setBackground(this.bground);
		stage.setScene(this.scene);
		stage.setTitle("Mini Ship Shooting Game");
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.show();

	}

	private void buttonProp(Button btn, Stage stage){
		btn.setMaxSize(100,50);
		btn.setFont(this.font20px);
		btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");

		btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println(btn.getText());
				//TODO insert welcome screen
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

	private void titleTextProp(Text t){
		t.setFont(this.font20px);
		t.setStyle("-fx-fill: #dc983a");
		t.setTextAlignment(TextAlignment.CENTER);
	}

	private void flowpaneProp(FlowPane fp){
		fp.setMaxWidth(GameStage.WINDOW_WIDTH);
		fp.setHgap(GameStage.WINDOW_WIDTH/3.25);
	}

	private void showPage1() {
		// TODO Auto-generated method stub
		clearVbox();
		ImageView wasd = new ImageView(new Image("images/wasd.png"));
		ImageView arrw = new ImageView(new Image("images/arrow.png"));
		ImageView spce = new ImageView(new Image("images/spcbr.png"));
		wasd.setFitHeight(80);
		wasd.setPreserveRatio(true);
		arrw.setFitHeight(80);
		arrw.setPreserveRatio(true);
		spce.setFitHeight(40);
		spce.setPreserveRatio(true);
		Text text = new Text("Controls\n");
		Text move = new Text("WASD/Arrow Keys to move\n");
		Text shot = new Text("Spacebar to shoot\n");
		insTextProp(text);
		insTextProp(move);
		insTextProp(shot);

		this.vbox.getChildren().addAll(text, wasd, arrw, move, spce, shot);
		this.vbox.setAlignment(Pos.CENTER);

		Button p2 = new Button("Next Page");
		viewPage(p2);
		this.buttons.getChildren().addAll(p2);
		this.buttons.setAlignment(Pos.BASELINE_RIGHT);
		this.bp.setBottom(buttons);
	}

	private void showPage2() {
		// TODO Auto-generated method stub
		clearVbox();

		Text text = new Text("POWERUPS\n");
		ImageView regenIcon = new ImageView(new Image("images/hp.png"));
		ImageView bubblIcon = new ImageView(new Image("images/bubble.png"));
		Text regenIns = new Text("Collect HP to regenerate ship's health\n");
		Text bubblIns = new Text("Collect Bubble to create a "
				+ "\nshield that last for 3 seconds\n");

		insTextProp(text);
		insTextProp(regenIns);
		insTextProp(bubblIns);

		this.vbox.getChildren().addAll(text, regenIcon, regenIns, bubblIcon, bubblIns);
		this.vbox.setAlignment(Pos.CENTER);

		Button p1 = new Button("Previous Page");
		viewPage(p1);
		this.buttons.getChildren().addAll(p1);
		this.buttons.setAlignment(Pos.BASELINE_RIGHT);
		this.bp.setBottom(buttons);

	}

	private void insTextProp(Text t){
		t.setFont(Font.loadFont("file:resources/fonts/Starlord-Z8zJ.ttf", 15));
		t.setStyle("-fx-fill: #dc983a");
		t.setTextAlignment(TextAlignment.CENTER);
	}

	private void viewPage(Button btn){
		btn.setFont(this.font20px);
		btn.setStyle("-fx-border-color: #b78f62; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: #dc983a");

		btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				System.out.println(btn.getText());
				//TODO insert welcome screen
				if (getPageNum() == 1){
					showPage2();
					setPageNum(2);
				} else {
					showPage1();
					setPageNum(1);
				}
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

	private void clearVbox(){
		if (this.vbox.getChildren() != null){
			this.vbox.getChildren().clear();
		}
		if (this.buttons.getChildren() != null){
			this.buttons.getChildren().clear();
		}
	}

	private int getPageNum(){
		return this.pageNum;
	}

	private void setPageNum(int x){
		this.pageNum = x;
	}
}
