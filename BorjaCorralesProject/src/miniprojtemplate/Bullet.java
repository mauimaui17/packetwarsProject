package miniprojtemplate;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	public final static Image BULLET_IMAGE = new Image("images/bullet1.png",Bullet.BULLET_WIDTH,Bullet.BULLET_HEIGHT,false,false);
	public final static int BULLET_WIDTH = 100;
	public final static int BULLET_HEIGHT = 30;
	private final int BULLET_SPEED = 20;
	public double angle;

	public Bullet(int x, int y, double angle){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
		this.angle = angle;
	}

	public void move(double angle) {
	    double dx = BULLET_SPEED * Math.cos(angle);
	    double dy = BULLET_SPEED * Math.sin(angle);
	    this.x += dx;
	    this.y += dy;
	    if (this.x >= GameStage.WINDOW_WIDTH - 25) {
	        this.setVisible(false);
	    }
	    if (this.y >= GameStage.WINDOW_HEIGHT - 25) {
	        this.setVisible(false);
	    }
	}
}