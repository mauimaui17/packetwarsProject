package miniprojtemplate;

import javafx.scene.image.Image;
import java.util.Random;

public class Fish extends Sprite {
	public final static Image FISH_IMAGE = new Image("images/enemy.png",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static int FISH_POINT_VAL = 1;
	public final static int FISH_ECON_VAL = 10;
	public final static int FISH_STRENGTH = 5;
	public final static int FISH_WIDTH=30;
	public final static int MAX_FISH_SPEED = 5;
	protected boolean alive;
	protected boolean moveRight; //attribute that will determine if a fish will initially move to the right
	protected int speed;
	private int health;
	protected double angle;
	protected int reward;

	Fish(int x, int y){
		super(0,0);
		this.loadImage(Fish.FISH_IMAGE);
		this.alive = true;
		this.speed = 2;
		this.health = 20;
		this.reward = 25;
        // randomly generate starting position at one of the four edges of the screen
        int side = (int) (Math.random() * 4);
        switch (side) {
            case 0: // top
                this.x = (int) (Math.random() * GameStage.WINDOW_WIDTH);
                this.y = -100;
                break;
            case 1: // right
                this.x = GameStage.WINDOW_WIDTH + 100;
                this.y = (int) (Math.random() * GameStage.WINDOW_HEIGHT);
                break;
            case 2: // bottom
                this.x = (int) (Math.random() * GameStage.WINDOW_WIDTH);
                this.y = GameStage.WINDOW_HEIGHT + 100;
                break;
            case 3: // left
                this.x = -100;
                this.y = (int) (Math.random() * GameStage.WINDOW_HEIGHT);
                break;
        }
        double dx = GameTimer.playerX - this.x;
        double dy = GameTimer.playerY - this.y;
        this.angle = Math.atan2(dy, dx);
        this.dx = (int) (this.speed * Math.cos(this.angle));
        this.dy = (int) (this.speed * Math.sin(this.angle));
        // calculate velocity vector towards player's position
	}

	void move(){ //method that changes the x position of the fish
        double dx = GameTimer.playerX - this.x;
        double dy = GameTimer.playerY - this.y;
        double angle = Math.atan2(dy, dx);
        this.dx = (int) (this.speed * Math.cos(angle));
        this.dy = (int) (this.speed * Math.sin(angle));
        this.x += this.dx;
        this.y += this.dy;
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
	public int damaged(int damage) {
		this.health -= damage;
		if (this.health <= 0){
			this.die();
			return this.reward;
		}
		return 0;
	}
}