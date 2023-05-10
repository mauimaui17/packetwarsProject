package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Boss extends Fish{
	public final static Image BOSS_SPRITE = new Image("images/boss.png",Boss.BOSS_SIZE,Boss.BOSS_SIZE,false,false);
	public final static int BOSS_SIZE = 200;
	public final static int HEALTH = 3000; //the health it has.
	public final static int STRENGTH = 50; //the damage it deals.
	private int hp;

	public Boss(int x, int y) {
		super(x, y);
		this.alive = true;
		this.loadImage(Boss.BOSS_SPRITE);
		this.hp = Boss.HEALTH;
		Random r = new Random();
		this.speed = r.nextInt(5) + 1; //Randomize speed of boss
		//from 1 to 5.
	}

	public void getDamaged(int damage) {
		if(this.hp > 0) this.hp -= damage;
		if(this.hp <= 0) this.die();
	}

	public void mercy() { ////this is a mercy mechanic
		this.moveRight = true;
	}

	//GETTERS
	public int getHp() {
		return this.hp;
	}
	public int getSize() {
		return Boss.BOSS_SIZE;
	}
}