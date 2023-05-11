package miniprojtemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.event.ActionEvent;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{
	public final static Image skull = new Image("images/skull.png",36,36,false,false);
	public final static Image hp = new Image("images/hp.png",36,36,false,false);
	public final static Image clock = new Image("images/clock.gif",36,36,false,false);

	public final static int MAX_NUM_FISHES = 3;
	public final static int FIRST_FISHES = 7;
	public final static int MAX_BOSS = 1;

	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private ArrayList<Fish> fishes;
	private ArrayList<Boss> boss;
	private ArrayList<Shield> shield;
	private ArrayList<Repair> repair;

	private long seconds;
	private long startTime;
	private long lastShot;
	private long spawnTime;
	private long immunityTime;
	private long shieldStart;
	private long startSec;
	private long powerTime;
	private int winner;
	private boolean boss_kill;
	public final static int playerX = GameStage.WINDOW_WIDTH/2;
	public final static int playerY = GameStage.WINDOW_HEIGHT/2;
	GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;
		this.myShip = new Ship("Going merry",playerX, playerY);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.boss = new ArrayList<Boss>();
		this.repair = new ArrayList<Repair>();
		this.shield = new ArrayList<Shield>();
		this.seconds = 0;
		this.powerTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		this.startTime = System.nanoTime();
		//call the spawnFishes method
		//this.spawnFishes(GameTimer.FIRST_FISHES);
		//call method to handle mouse click event
		this.spawnFishes(5);
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		this.startSec = TimeUnit.NANOSECONDS.toSeconds(this.startTime);
		/*
		 * TODO: Call the moveBullets and moveFishes methods
		 */
		this.moveBullets();
		this.moveFishes();
		//render the ship
		this.myShip.render(this.gc);
		if(seconds - this.spawnTime == 5) {
			this.spawnFishes(5);
			System.out.println("FISH POP: " + this.fishes.size());
		}
		/*
		 * TODO: Call the renderFishes and renderBullets methods
		 */
		this.renderFishes();
		this.renderBullets();

		this.collisionCheck();


		if(this.myShip.isAlive() == false) {
			this.setGameOver(0);
			this.gameOver();
		}
		if((seconds - TimeUnit.NANOSECONDS.toSeconds(this.startTime) == 120)&&
				this.myShip.isAlive()) {
			this.setGameOver(1);
			this.gameOver();
		}
		this.statusBar();
	}

	private void statusBar() {
		this.gc.setFill(Color.WHITE);
		this.gc.setFont(Font.font("Impact", 40));
		this.gc.drawImage(skull, 1200, 5);
		this.gc.fillText(": " + this.myShip.getScore(), 1235, 35);
		this.gc.drawImage(hp, 1610, 5);
		this.gc.fillText(": " + this.myShip.getStrength(), 1650, 35);
		this.gc.drawImage(clock, 1350, 5);
		int totalTime = 120; // in seconds
		long elapsedTime = seconds - startSec;
		long remainingTime = totalTime - elapsedTime;
		String formattedTime = String.format("%02d:%02d", remainingTime / 60, remainingTime % 60);
		this.gc.fillText(": " + formattedTime, 1400, 35);
		if(this.myShip.generalImmunity() == true) {
			this.gc.drawImage(Shield.SHIELD, 0,0);
		}
	}
	//method that will render/draw the fishes to the canvas
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			b.render(gc,b.angle);
		}
	}

	//method that will spawn/instantiate three fishes at a random x,y location
	private void spawnFishes(int fishCount){
		this.spawnTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		for(int i=0;i<fishCount;i++){
			int x = 0;
			int y = 0;
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */
			this.fishes.add(new Fish(x,y));
		}
	}

	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if(b.getVisible() == false) {
				bList.remove(i);
			} else {
				b.move(b.angle);
			}
		}
	}

	//method that will move the fishes
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if(f.isAlive() == true) {
				f.move();
			} else {
				this.fishes.remove(i);
			}
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});
		this.theScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double mouseX = e.getX();
				double mouseY = e.getY();
				double dx = mouseX - GameTimer.playerX;
				double dy = mouseY - GameTimer.playerY;
				double angle = Math.atan2(dy, dx);
				
				shoot(angle);
			}
		});
		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }
	private void shoot(double angle) {
		System.out.println("Angle: "+angle);
		this.myShip.shoot(GameTimer.playerX, GameTimer.playerY, angle);
	}
	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP || ke==KeyCode.W) this.myShip.setDY(-10);

		if(ke==KeyCode.LEFT|| ke==KeyCode.A) this.myShip.setDX(-10);

		if(ke==KeyCode.DOWN|| ke==KeyCode.S) this.myShip.setDY(10);

		if(ke==KeyCode.RIGHT|| ke==KeyCode.D) this.myShip.setDX(10);
  }
	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	private void collisionCheck() {
		for(int i = 0; i < this.fishes.size(); i++) {
			Fish f = this.fishes.get(i);
			if(this.myShip.collidesWith(f) && this.myShip.generalImmunity() == false) {
				//fish dies, myship gets damaged.
				this.myShip.getDamaged(Fish.FISH_STRENGTH);
				//this.immunityTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
				f.die();
			}

			for(int j = 0; j < this.myShip.getBullets().size(); j++) {
				Bullet b = this.myShip.getBullets().get(j);
				if(b.collidesWith(this.fishes.get(i))) {
					b.setVisible(false);
					f.die();
					this.myShip.addScore(Fish.FISH_POINT_VAL);
				}
			}
		}
	}
	public void setGameOver(int num){
		this.winner = num;
	}

	public int getGameOver(){
		return this.winner;
	}
	private void gameOver() {
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();
		System.out.println("gameOver() working");
		int score = this.myShip.getScore();
		boolean boss_kill = this.boss_kill;
		if(this.getGameOver() == 0){
			System.out.println("LOSE");
			transition.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					GameOver gameover = new GameOver(score,boss_kill);
					//gameover.setStageComponents(stage);
					GameStage.getStage().setScene(gameover.getScene(GameStage.getStage()));
				}
			});
		} else {
			System.out.println("WIN");
			transition.setOnFinished(new EventHandler<ActionEvent>(){

				public void handle(ActionEvent arg0){
					GameWin gamewin = new GameWin(score,boss_kill);
					GameStage.getStage().setScene(gamewin.getScene(GameStage.getStage()));
				}
			});
		}
		this.stop();
	}
}