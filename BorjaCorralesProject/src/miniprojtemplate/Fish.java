package miniprojtemplate;

import javafx.scene.image.Image;
import java.util.Random;

public class Fish extends Sprite {
	public final static Image FISH_IMAGE = new Image("images/enemy.png",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static int FISH_POINT_VAL = 1;
	public final static int FISH_STRENGTH = 30;
	public final static int FISH_WIDTH=50;
	public final static int MAX_FISH_SPEED = 5;
	protected boolean alive;
	protected boolean moveRight; //attribute that will determine if a fish will initially move to the right
	protected int speed;

	Fish(int x, int y){
		super(x,y);
		this.loadImage(Fish.FISH_IMAGE);
		this.alive = true;
		Random r = new Random();
		this.speed = 2; //Randomize speed of fish
		//why 6? for some reason nextInt(int origin, int bound) isnt working
		//but nextInt(int bound) returns 0 to bound (Exclusive) so it
		//returns 0 to 5 for this section.
		//add+1 so it turns 1 to 6.
		this.moveRight = r.nextBoolean(); //and moveRight's initial value
	}

	void move(){ //method that changes the x position of the fish
		if(this.moveRight == true && this.x < GameStage.WINDOW_WIDTH - this.getSize()) { //If moveRight is true and if the fish hasn't reached the right boundary yet,
			this.x += this.speed; //move the fish to the right by changing the x position of the fish depending on its speed
		} else if (this.moveRight == true && this.x >= (GameStage.WINDOW_WIDTH - (this.getSize()+100))) { //else if it has reached the boundary, change the moveRight value / move to the left
			this.moveRight = false;
			this.x -= this.speed;
		} else if (this.moveRight == false && this.x > 0) { //Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
			this.x -= this.speed; //move the fish to the left by changing the x position of the fish depending on its speed.
		} else if(this.moveRight == false && this.x <= 0) { //else if it has reached the boundary, change the moveRight value / move to the right
			this.moveRight = true;
			this.x += this.speed;
		}
	}

	//GETTER
	public boolean isAlive() {
		return this.alive;
	}
	public int getSize() {
		return Fish.FISH_WIDTH;
	}
	//SETTER
	public boolean die() {
		return this.alive = false;
	}
}