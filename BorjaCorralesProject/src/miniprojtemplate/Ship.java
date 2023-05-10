package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;



public class Ship extends Sprite{
	private String name;
	private int strength;
	private boolean alive;

	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE = new Image("images/ship.png",70,Ship.SHIP_WIDTH,false,false);
	private final static int SHIP_WIDTH = 50;
	private int score;
	public final static long FIRE_RATE = 100000000L;
	public final static long MERCY_TIME = 1;
	public final static long POWER_IMMUNE_TIME = 1;
	private boolean mercyImmunity;
	private boolean powerImmunity;

	public Ship(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(51)+100;//random int from 0 to 50, 
    //when 100 is added, the strength of the ship becomes 100 to 150.
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE);
		this.score = 0;
		this.mercyImmunity = false;
		this.powerImmunity = false;
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(int x, int y, double angle){
		//compute for the x and y initial position of the bullet
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		this.bullets.add(new Bullet(x,y,angle));
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		this.x += this.dx;
		this.y += this.dy;
		//check bounds. left most boundary is x = 0, right most is window width
		//upper boundary is y = 0, lower boundary is window height.
		//the width and height are also reduced because the title bar is included
		//in window size but is not part of the actual game.
		if(this.x < 0) {
			this.x = 0; //once its x reaches a position less than 0, reset it to 0.
		}
		if(this.y < 36) {
			this.y = 36; //if it goes beyond the upper boundary, reset its y to 0.
		}
		if(this.x >= (GameStage.WINDOW_WIDTH - 50)) {
			this.x = GameStage.WINDOW_WIDTH - 50;
		}
		if(this.y >= (GameStage.WINDOW_HEIGHT - 50)) {
			this.y = (GameStage.WINDOW_HEIGHT - 50);
		}
	}

	public void getDamaged(int damage) {
		if(this.strength > 0) {
			this.strength -= damage;
			this.mercyImmunity = true;
		}
		if(this.strength <= 0) {
			this.die();
		}
		System.out.println("HP: " + this.strength);
	}

	public int getScore() {
		return this.score;
	}

	public void addScore(int value) {
		this.score += value;
	}

	public void heal() {
		this.strength += 50;
	}
	public void resetMercy() {
		this.mercyImmunity = false;
	}	
	public void resetImmunity() {
		this.powerImmunity = false;
	}
	public boolean getMercyImmunity() {
		return this.mercyImmunity;
	}
	public boolean getPowerImmunity() {
		return this.powerImmunity;
	}
	public void setPowerImmunity() {
		this.powerImmunity = true;
	}
	public boolean generalImmunity() {
		return (this.getPowerImmunity() || this.getMercyImmunity());
	}
	public int getStrength() {
		return this.strength;
	}
}