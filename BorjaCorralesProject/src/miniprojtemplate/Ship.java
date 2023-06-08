package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;



public class Ship extends Sprite{
	private String name;
	private int strength;
	private int maxStrength;
	private boolean alive;
	private int economy;
	public final static int REPAIR_COST = 10;
	public final static int UPGRADE_HP_COST = 50;
	public final static int UPGRADE_DMG_COST = 30;
	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE = new Image("images/thing.png",70,Ship.SHIP_WIDTH,false,false);
	private final static int SHIP_WIDTH = 50;
	private int score;
	public final static long FIRE_RATE = 100000000L;
	public final static long MERCY_TIME = 1;
	public final static long POWER_IMMUNE_TIME = 1;
	protected int damage;
	private int money;
	private int hpLevel;
	private int dmgLevel;
	public Ship(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = 100;//random int from 0 to 50, 
    //when 100 is added, the strength of the ship becomes 100 to 150.
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE);
		this.score = 0;
		this.damage = 10;
		this.money = 500;
		this.maxStrength = this.strength;
		this.hpLevel = 1;
		this.dmgLevel = 1;
	}
	public int getMaxHealth() {
		return this.maxStrength;
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
		this.bullets.add(new Bullet(x,y,angle,this.damage));
    }

	//method called if up/down/left/right arrow key is pressed.
	public int getMoney() {
		return this.money;
	}
	public void addMoney(int reward) {
		this.money += reward;
	}
	public void getDamaged(int damage) {
		if(this.strength > 0) {
			this.strength -= damage;
		}
		if(this.strength <= 0) {
			this.die();
		}
		System.out.println("HP: " + this.strength);
	}
	public int getScore() {
		return this.score;
	}
	public int getAtkDmg() {
		return this.damage;
	}
	public void addScore(int value) {
		this.score += value;
	}

	//upgrade1: repair - adds 100hp back
	public int repair() {
		if(this.money-150>=0) {
			if(this.strength>this.maxStrength) {
				this.strength = this.maxStrength;
			} else if(this.strength == this.maxStrength) {
				return 2;
			} else {
				int repDam = this.maxStrength - this.strength;
				this.strength += repDam;
				this.money -= Ship.REPAIR_COST;
				return 1;
			}
		}
		return 0;
	}
	
	//upgrade2: increases max hp by 100
	public Boolean addMaxHealth() {
		if(this.money-(Ship.UPGRADE_HP_COST*this.hpLevel)>=0) {
			this.maxStrength += 200;
			this.money -= Ship.UPGRADE_HP_COST*this.hpLevel;
			return true;
		} return false;
	}
	public Boolean upgradeDamage() {
		if(this.money-(Ship.UPGRADE_DMG_COST*this.dmgLevel)>=0) {
			this.damage += 10;
			this.money -= Ship.UPGRADE_HP_COST*this.hpLevel;
			return true;
		} return false;
	}
	
	//add 10 to the economy every time 	the ship kills a fish
	public void earnMoney(int money) {
		this.money += money;
	}
	
	public int getStrength() {
		if(this.strength<0) {
			return 0;
		}
		return this.strength;
	}
}