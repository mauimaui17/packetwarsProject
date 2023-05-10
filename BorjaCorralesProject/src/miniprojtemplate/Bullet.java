package miniprojtemplate;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	public final static Image BULLET_IMAGE = new Image("images/bullet1.png",Bullet.BULLET_WIDTH,Bullet.BULLET_HEIGHT,false,false);
	public final static int BULLET_WIDTH = 100;
	public final static int BULLET_HEIGHT = 30;
	private final int BULLET_SPEED = 20;

	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
	}

	public void move(){ //method that will move/change the x position of the bullet
		this.x += this.BULLET_SPEED; //Change the x position of the bullet depending on the bullet speed.
		if(this.x >= (GameStage.WINDOW_WIDTH-25)) this.setVisible(false);//If the x position has reached the right boundary of the screen, set the bullet's visibility to false.
	}
}