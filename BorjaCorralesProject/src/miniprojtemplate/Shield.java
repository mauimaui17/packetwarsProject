package miniprojtemplate;

import javafx.scene.image.Image;

public class Shield extends Sprite{
	public static final Image SHIELD = new Image("images/bubble.png",36,36,false,false);
	public static final long SHIELD_DURATION = 3;
	public static final long VISIBLE_DURATION = 5;

	public Shield(int x,int y) {
		super(x,y);
		this.loadImage(Shield.SHIELD);
	}
}