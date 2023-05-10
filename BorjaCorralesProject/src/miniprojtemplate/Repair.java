package miniprojtemplate;

import javafx.scene.image.Image;

public class Repair extends Sprite{
	public final static Image REPAIR = new Image("images/repair.gif",36,36,false,false);
	public Repair(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.loadImage(Repair.REPAIR);
	}
}