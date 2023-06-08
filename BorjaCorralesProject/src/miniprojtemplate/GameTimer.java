package miniprojtemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.*;
import java.net.*;
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
	
	private Rectangle rect1;
	private Rectangle rect2;
	private Rectangle rect3;

	
	private long seconds;
	private long startTime;
	private long lastShot;
	private long spawnTime;
	private long startSec;
	private int winner;
	private boolean boss_kill;
	private int modifier;
	private int respite;
	private VBox root;
	
	public final static int playerX = GameStage.WINDOW_WIDTH/2;
	public final static int playerY = (GameStage.WINDOW_HEIGHT-100)/2;
	GameTimer(GraphicsContext gc, Scene theScene, VBox root){
		this.gc = gc;
		this.root = root;
		this.theScene = theScene;
		this.myShip = new Ship("Going merry",playerX, playerY);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.seconds = 0;
		this.respite = 5;
		this.startTime = System.nanoTime();
		//call the spawnFishes method
		this.spawnFishes(GameTimer.FIRST_FISHES);
		//call method to handle mouse click event
		this.spawnFishes(5);
		this.handleKeyPressEvent();

		this.rect1 = new Rectangle(200, 150, 100, 800);
	}
	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT-100);
		this.seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
		this.startSec = TimeUnit.NANOSECONDS.toSeconds(this.startTime);
		this.moveBullets();
		this.moveFishes();
		//render the ship
		this.myShip.render(this.gc);
		if(seconds - this.spawnTime == (5+this.respite)) {
			this.spawnFishes(5+this.modifier);
			this.modifier+=1;
			System.out.println("FISH POP: " + this.fishes.size());
		}
		this.renderFishes();
		this.renderBullets();
		
		this.collisionCheck();


		if(this.myShip.isAlive() == false) {
			this.setGameOver(0);
			this.gameOver();
		}
		if((seconds - TimeUnit.NANOSECONDS.toSeconds(this.startTime) == 300)&&
				this.myShip.isAlive()) {
			this.setGameOver(1);
			this.gameOver();
		}
		this.statusBar();
		this.upgradeButton();
	}

	private void statusBar() {
		this.gc.setFill(Color.BLACK);
		this.gc.setFont(Font.font("Impact", 40));
		this.gc.fillText("Money: " + this.myShip.getMoney(), 950, 35);
		this.gc.drawImage(skull, 1200, 5);
		this.gc.fillText(": " + this.myShip.getScore(), 1235, 35);
		this.gc.drawImage(hp, 1550, 5);
		this.gc.fillText(": " + this.myShip.getStrength() +"/" + this.myShip.getMaxHealth(), 1600, 35);
		this.gc.drawImage(clock, 1350, 5);
		int totalTime = 300; // in seconds
		long elapsedTime = seconds - startSec;
		long remainingTime = totalTime - elapsedTime;
		String formattedTime = String.format("%02d:%02d", remainingTime / 60, remainingTime % 60);
		this.gc.fillText(": " + formattedTime, 1400, 35);
	}
	
	private void upgradeButton() {
		this.rect1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				repairUpgrade();
			}
		});
	}
	
	public String repairUpgrade() {
		int retVal = this.myShip.repair();
		if(retVal == 2) {
			return("Already at full health!");
		} else if(retVal == 1) {
			return("Repair success!");
		} else {
			return("Not enough cash!");
		}
	}
	
	public String upgradeHealth() {
		Boolean retVal = this.myShip.addMaxHealth();
		if(retVal) {
			return("Upgraded your health!");
		} else {
			return("Not enough cash!");
		}
	}
	
	public String upgradeDamage() {
		Boolean retVal = this.myShip.upgradeDamage();
		if(retVal) {
			return("Your damage has been upgraded!");
		} else {
			return("Not enough cash!");
		}
	}
	
	//method that will render/draw the fishes to the canvas
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc, f.angle);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			b.render(gc,b.angle);
		}
	}

	//method that will spawn/instantiate three fishes at a random x,y location
	public void spawnFishes(int fishCount){
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
	public Boolean sendWave() {
		if(this.myShip.getMoney() >= 500) {
			this.myShip.earnMoney(-500);
			return true;
		}
		return false;
	}
	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
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
			if(f.isAlive() == true) {
				f.move();
			} else {
				this.myShip.addScore(Fish.FISH_POINT_VAL);
				this.myShip.earnMoney(Fish.FISH_ECON_VAL);
				this.fishes.remove(i);
			}
		}
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
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
    }
	private void shoot(double angle) {
		this.myShip.shoot(GameTimer.playerX, GameTimer.playerY, angle);
	}

	private void collisionCheck() {
		for(int i = 0; i < this.fishes.size(); i++) {
			Fish f = this.fishes.get(i);
			if(this.myShip.collidesWith(f)) {
				//fish dies, myship gets damaged.
				this.myShip.getDamaged(Fish.FISH_STRENGTH);
				//this.immunityTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
				f.die();
			}

			for(int j = 0; j < this.myShip.getBullets().size(); j++) {
				Bullet b = this.myShip.getBullets().get(j);
				if(b.collidesWith(this.fishes.get(i))) {
					b.setVisible(false);
					f.damaged(this.myShip.getAtkDmg());
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