package resources;

import sound.Sound;

public class Sounds {
	
	public static Sound BOSS_DEFEATED;
	public static Sound BOUNCE;
	public static Sound ERROR;
	public static Sound GUN_SHOT;
	public static Sound JUMP;
	public static Sound MELEE_ATTACK;
	public static Sound COIN;
	
	
	public static void init() {
		BOSS_DEFEATED 	= new Sound("BOSS_DEFEATED.wav");
		BOUNCE 			= new Sound("BOUNCE.wav");
		ERROR 			= new Sound("ERROR.wav");
		GUN_SHOT 		= new Sound("GUN_SHOT.wav");
		JUMP	 		= new Sound("JUMP.wav");
		MELEE_ATTACK	= new Sound("MELEE_ATTACK.wav");
		COIN			= new Sound("COIN.wav");
	}
}
