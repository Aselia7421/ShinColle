package com.lulan.shincolle.reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Values {
	
	//numbers
	public static final class N {
		public static final float RAD_DIV = 57.295779513F;
		public static final float RAD_MUL = 0.0174532925F;
	}
	
	/**SHIP ATTRIBUTES MAP
	 * attr: float[]: 0:baseHP 1:baseATK 2:baseDEF 3:baseSPD 4:baseMOV 5:baseHIT
	 *                6:modHP 7:modATK 8:modDEF 9:modSPD 10:modMOV 11:modHIT
	 *                
	 * NOTE: modMOV = mark for exists ship, NO USE
	 */
	public static final Map<Byte, float[]> ShipAttrMap = Collections.unmodifiableMap(new HashMap<Byte, float[]>() {{
	//destroyer                               HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT     
	put(ID.S_DestroyerI,         new float[] {20F,  3F,  5F,  0.4F, 0.5F,  6F,  0.3F,  0.25F, 0.11F, 0.5F,  1F,    0.4F});
	put(ID.S_DestroyerRO,        new float[] {22F,  4F,  6F,  0.4F, 0.5F,  6F,  0.32F, 0.28F, 0.12F, 0.5F,  1F,    0.4F});
	put(ID.S_DestroyerHA,        new float[] {24F,  3F,  7F,  0.4F, 0.5F,  6F,  0.34F, 0.25F, 0.13F, 0.5F,  1F,    0.4F});
	put(ID.S_DestroyerNI,        new float[] {28F,  4F,  9F,  0.4F, 0.5F,  6F,  0.36F, 0.28F, 0.15F, 0.5F,  1F,    0.4F});
	//cruiser
//	put(ID.S_LightCruiserHO,     new float[] {33F,  6F,  11F, 0.4F, 0.45F, 8F,  0.38F, 0.3F,  0.17F, 0.53F, 0.9F,  0.45F});
//	put(ID.S_LightCruiserHE,     new float[] {36F,  9F,  13F, 0.4F, 0.45F, 8F,  0.4F,  0.35F, 0.18F, 0.53F, 0.9F,  0.45F});
//	put(ID.S_LightCruiserTO,     new float[] {39F,  8F,  15F, 0.4F, 0.45F, 8F,  0.42F, 0.32F, 0.19F, 0.53F, 0.9F,  0.45F});
//	put(ID.S_LightCruiserTSU,    new float[] {48F,  12F, 16F, 0.4F, 0.45F, 8F,  0.44F, 0.38F, 0.20F, 0.53F, 0.9F,  0.45F});
//	put(ID.S_TorpedoCruiserCHI,  new float[] {48F,  16F, 18F, 0.4F, 0.42F, 9F,  0.46F, 0.44F, 0.21F, 0.56F, 0.84F, 0.45F});
	put(ID.S_HeavyCruiserRI,     new float[] {58F,  14F, 18F, 0.4F, 0.42F, 9F,  0.48F, 0.4F,  0.21F, 0.56F, 0.84F, 0.45F});
	put(ID.S_HeavyCruiserNE,     new float[] {62F,  15F, 19F, 0.4F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.45F});
	//carrier                                 HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//	put(ID.S_LightCarrierNU,     new float[] {65F,  20F, 20F, 0.4F, 0.32F, 14F, 0.52F, 0.45F, 0.22F, 0.5F,  0.64F, 0.6F});
	put(ID.S_CarrierWO,          new float[] {85F,  25F, 21F, 0.4F, 0.36F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
	//battleship
//	put(ID.S_BattleshipRU,       new float[] {90F,  30F, 25F, 0.4F, 0.32F, 10F, 0.7F,  0.63F, 0.25F, 0.65F, 0.64F, 0.5F});
	put(ID.S_BattleshipTA,       new float[] {84F,  19F, 23F, 0.5F, 0.42F, 10F, 0.65F, 0.55F, 0.24F, 0.7F,  0.84F, 0.5F});
	put(ID.S_BattleshipRE,       new float[] {120F, 25F, 25F, 0.45F,0.36F, 12F, 0.8F,  0.6F,  0.25F, 0.63F, 0.72F, 0.5F});
	//transport
//	put(ID.S_TransportWA,        new float[] {70F,  2F,  10F, 0.4F, 0.3F,  5F,  0.6F,  0.25F, 0.16F, 0.5F,  0.6F,  0.3F});
	//submarine
//	put(ID.S_SubmarineKA,        new float[] {50F,  25F, 7F,  0.3F, 0.3F,  5F,  0.35F, 0.63F, 0.13F, 0.5F,  0.6F,  0.3F});
//	put(ID.S_SubmarineYO,        new float[] {44F,  28F, 8F,  0.3F, 0.3F,  5F,  0.33F, 0.7F,  0.14F, 0.5F,  0.6F,  0.3F});
//	put(ID.S_SubmarineSO,        new float[] {35F,  35F, 10F, 0.3F, 0.28F, 5F,  0.3F,  0.8F,  0.16F, 0.5F,  0.56F, 0.3F});
	//demon                                   HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//	put(ID.S_IsolatedDemon,      new float[] {225F, 18F, 34F, 0.38F,0.22F, 24F, 1.3F,  0.45F, 0.29F, 0.6F,  0.44F, 0.8F});
//	put(ID.S_LightCruiserDemon,  new float[] {130F, 30F, 25F, 0.4F, 0.45F, 14F, 0.8F,  0.65F, 0.25F, 0.6F,  0.9F,  0.55F});
	//hime                                    HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//	put(ID.S_AirdefenseHime,     new float[] {120F, 32F, 35F, 0.4F, 0.5F,  12F, 0.8F,  0.7F,  0.3F,  0.6F,  1F,    0.5F});
	put(ID.S_AirfieldHime,       new float[] {240F, 21F, 32F, 0.4F, 0.3F,  26F, 1.2F,  0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//	put(ID.S_AnchorageHime,      new float[] {150F, 19F, 32F, 0.35F,0.3F,  23F, 0.95F, 0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//	put(ID.S_ArmoredCarrierHime, new float[] {150F, 34F, 35F, 0.4F, 0.42F, 20F, 0.9F,  0.73F, 0.3F,  0.63F, 0.84F, 0.7F});
	put(ID.S_BattleshipHime,     new float[] {200F, 42F, 40F, 0.4F, 0.4F,  16F, 1.0F,  0.8F,  0.32F, 0.73F, 0.8F,  0.6F});
//	put(ID.S_CarrierHime,        new float[] {180F, 40F, 35F, 0.4F, 0.45F, 22F, 0.85F, 0.77F, 0.3F,  0.65F, 0.9F,  0.7F});
//	put(ID.S_DestroyerHime,      new float[] {90F,  22F, 20F, 0.4F, 0.5F,  12F, 0.55F, 0.5F,  0.22F, 0.6F,  1F,    0.5F});
	put(ID.S_HarbourHime,        new float[] {260F, 19F, 36F, 0.35F,0.2F,  24F, 1.35F, 0.45F, 0.3F,  0.6F,  0.4F,  0.8F});
//	put(ID.S_MidwayHime,         new float[] {350F, 20F, 45F, 0.38F,0.2F,  30F, 1.5F,  0.55F, 0.34F, 0.6F,  0.4F,  0.8F});
	put(ID.S_NorthernHime,       new float[] {210F, 17F, 30F, 0.35F,0.32F, 22F, 1.15F, 0.4F,  0.27F, 0.6F,  0.64F, 0.8F});
//	put(ID.S_SeaplaneHime,       new float[] {170F, 24F, 25F, 0.4F, 0.45F, 18F, 1F,    0.6F,  0.25F, 0.63F, 0.9F,  0.65F});
//	put(ID.S_SouthernHime,       new float[] {170F, 35F, 34F, 0.4F, 0.3F,  20F, 1F,    0.73F, 0.29F, 0.63F, 0.6F,  0.7F});
	//water demon                             HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//	put(ID.S_AnchorageWD,        new float[] {230F, 28F, 35F, 0.4F, 0.35F, 26F, 1.35F, 0.6F,  0.3F,  0.7F,  0.7F,  1F});
//	put(ID.S_BattleshipWD,       new float[] {280F, 50F, 45F, 0.4F, 0.42F, 21F, 1.1F,  1F,    0.34F, 0.85F, 0.84F, 0.7F});
	put(ID.S_CarrierWD,          new float[] {190F, 45F, 40F, 0.4F, 0.42F, 25F, 1F,    0.95F, 0.32F, 0.75F, 0.84F, 0.8F});
//	put(ID.S_HarbourWD,          new float[] {300F, 35F, 45F, 0.4F, 0.35F, 29F, 1.5F,  0.63F, 0.34F, 0.7F,  0.7F,  1F});
	//hostile ship                            HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
	put(ID.S_DestroyerShimakaze, new float[] {35F,  9F,  10F, 0.4F, 0.6F,  9F,  0.35F, 0.35F, 0.16F, 0.55F, 1.2F,  0.45F});
	put(ID.S_BattleshipNagato,   new float[] {100F, 40F, 26F, 0.4F, 0.32F, 14F, 0.85F, 0.8F,  0.25F, 0.63F, 0.64F, 0.6F});
	put(ID.S_BattleshipYamato,   new float[] {150F, 55F, 36F, 0.4F, 0.3F,  20F, 1F,    1F,    0.3F,  0.7F,  0.6F,  0.7F});
	put(ID.S_SubmarineU511,      new float[] {28F,  30F, 7F,  0.3F, 0.3F,  7F,  0.3F,  0.7F,  0.13F, 0.7F,  0.6F,  0.4F});
	put(ID.S_SubmarineRo500,     new float[] {32F,  32F, 10F, 0.3F, 0.3F,  8F,  0.33F, 0.75F, 0.16F, 0.7F,  0.6F,  0.4F});
	}});
	
	/**damage modifier array [damage type id][target type id]*/
	public static final float[][] ModDmg = 
		{{},
		 {}
		};
	
	/**EQUIP MAP
	 * equip state:
	 *		 0:equip type 1:hp 2:L.atk 3:H.atk 4:L.plane atk 5:H.plane atk 6:def 
	 *       7:spd 8:mov 9:range 10:critical 11:double hit 12:triple hit 13:miss reduce
	 *       14:rare level 15:rare rate
	 * 
	 * equip type: 0:unused
	 *       1:cannon, torpedo
	 *       2:aircraft-R, engine, armor, radar
	 *       3:aircraft-T/F/B
	 */
	public static final Map<Byte, float[]> EquipMap = Collections.unmodifiableMap(new HashMap<Byte, float[]>() {{
	//single cannon
	put(ID.E_CANNON_SINGLE_5,    new float[]{1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, ID.EquipType.CANNON_SI, 150F});
	put(ID.E_CANNON_SINGLE_6,    new float[]{1F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, ID.EquipType.CANNON_SI, 150F});
	//twin cannon
	put(ID.E_CANNON_TWIN_5,      new float[]{1F, 0F, 4F, 0F, 0F, 0F, 0F, 0.2F, -0.02F, 1.5F, 0F, 0.05F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 250});
	put(ID.E_CANNON_TWIN_6,      new float[]{1F, 0F, 5F, 0F, 0F, 0F, 0F, 0.4F, -0.03F, 2.0F, 0F, 0.05F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 200});
	put(ID.E_CANNON_TWIN_5DP,    new float[]{1F, 0F, 5F, 0F, 0F, 0F, 0F, 0.25F, -0.04F, 2.0F, 0F, 0.06F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 180});
	put(ID.E_CANNON_TWIN_125,    new float[]{1F, 0F, 8F, 0F, 0F, 0F, 0F, 0.2F, -0.06F, 2.5F, 0F, 0.06F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 160});
	put(ID.E_CANNON_TWIN_14,     new float[]{1F, 0F, 11F, 0F, 0F, 0F, 0F, 0.2F, -0.08F, 3.0F, 0F, 0.07F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 120});
	put(ID.E_CANNON_TWIN_16,     new float[]{1F, 0F, 15F, 0F, 0F, 0F, 0F, 0.2F, -0.1F, 3.5F, 0F, 0.08F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 80});
	put(ID.E_CANNON_TWIN_20,     new float[]{1F, 0F, 20F, 0F, 0F, 0F, 0F, 0.2F, -0.15F, 4.0F, 0F, 0.10F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 50});
	//triple cannon
	put(ID.E_CANNON_TRI_8,       new float[]{1F, 0F, 9F, 0F, 0F, 0F, 0F, 0.4F, -0.1F, 3.0F, 0F, 0.06F, 0.06F, 0F, ID.EquipType.CANNON_TR, 80});
	put(ID.E_CANNON_TRI_16,      new float[]{1F, 0F, 17F, 0F, 0F, 0F, 0F, 0.4F, -0.2F, 4.5F, 0F, 0.08F, 0.08F, 0F, ID.EquipType.CANNON_TR, 50});
	put(ID.E_CANNON_FG_15,       new float[]{1F, 100F, 9F, 0F, 0F, 0F, 5F, 0.3F, -0.25F, 3.0F, 0F, 0.06F, 0.06F, 0F, ID.EquipType.CANNON_TR, 120});
	//torpedo
	put(ID.E_TORPEDO_21MK1,      new float[]{1F, 0F, 0F, 12F, 0F, 0F, 0F, 0.2F, -0.04F, 0F, 0.06F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 150});
	put(ID.E_TORPEDO_21MK2,      new float[]{1F, 0F, 0F, 20F, 0F, 0F, 0F, 0.25F, -0.05F, 0.5F, 0.08F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 130});
	put(ID.E_TORPEDO_22MK1,      new float[]{1F, 0F, 0F, 50F, 0F, 0F, 0F, 0.3F, -0.06F, 1.0F, 0.10F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 110});
	put(ID.E_TORPEDO_CUTTLEFISH, new float[]{1F, 0F, 0F, 80F, 0F, 0F, 0F, 0.35F, -0.08F, 1.5F, 0.12F, 0F, 0F, 0F, ID.EquipType.TORPEDO_HI, 80});
	put(ID.E_TORPEDO_HIGHSPEED,  new float[]{1F, 0F, 0F, 80F, 0F, 0F, 0F, 0.4F, -0.1F, 2.5F, 0.14F, 0F, 0F, 0F, ID.EquipType.TORPEDO_HI, 50});
	//Torpedo aircraft
	put(ID.E_AIRCRAFT_TMK1,      new float[]{3F, 0F, 0F, 0F, 2F, 15F, 0F, 0F, -0.08F, 3.5F, 0.06F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 180});
	put(ID.E_AIRCRAFT_TMK2,      new float[]{3F, 0F, 0F, 0F, 6F, 30F, 0F, 0F, -0.1F, 4.0F, 0.08F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 150});
	put(ID.E_AIRCRAFT_TMK3,      new float[]{3F, 0F, 0F, 0F, 11F, 60F, 0F, 0F, -0.14F, 4.5F, 0.10F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 130});
	put(ID.E_AIRCRAFT_TAVENGER,  new float[]{3F, 0F, 0F, 0F, 8F, 100F, 0F, 0.25F, -0.2F, 5.0F, 0.12F, 0F, 0F, 0F, ID.EquipType.AIR_T_HI, 80});
	//Fighter aircraft
	put(ID.E_AIRCRAFT_FMK1,      new float[]{3F, 0F, 0F, 0F, 3F, 0F, 0F, 0.3F, -0.04F, 3.5F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 180});
	put(ID.E_AIRCRAFT_FMK2,      new float[]{3F, 0F, 0F, 0F, 5F, 0F, 0F, 0.35F, -0.06F, 4.0F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 150});
	put(ID.E_AIRCRAFT_FMK3,      new float[]{3F, 0F, 0F, 0F, 8F, 0F, 0F, 0.4F, -0.08F, 4.5F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 130});
	put(ID.E_AIRCRAFT_FFLYFISH,  new float[]{3F, 0F, 0F, 0F, 12F, 0F, 0F, 0.45F, -0.1F, 5.0F, 0F, 0F, 0F, 0.01F, ID.EquipType.AIR_F_HI, 100});
	put(ID.E_AIRCRAFT_FHELLCAT,  new float[]{3F, 0F, 0F, 0F, 10F, 0F, 0F, 0.55F, -0.12F, 6.0F, 0F, 0F, 0F, 0.02F, ID.EquipType.AIR_F_HI, 80});
	//Bomber aircraft
	put(ID.E_AIRCRAFT_BMK1,      new float[]{3F, 0F, 0F, 0F, 12F, 0F, 0F, 0.0F, -0.06F, 3.0F, 0.12F, 0F, 0F, 0F, ID.EquipType.AIR_B_LO, 150});
	put(ID.E_AIRCRAFT_BMK2,      new float[]{3F, 0F, 0F, 0F, 18F, 0F, 0F, 0.0F, -0.08F, 3.0F, 0.16F, 0F, 0F, 0F, ID.EquipType.AIR_B_LO, 130});
	put(ID.E_AIRCRAFT_BFLYFISH,  new float[]{3F, 0F, 0F, 0F, 24F, 0F, 0F, 0.0F, -0.11F, 3.5F, 0.20F, 0F, 0F, 0F, ID.EquipType.AIR_B_HI, 100});
	put(ID.E_AIRCRAFT_BHELL,     new float[]{3F, 0F, 0F, 0F, 21F, 0F, 0F, 0.25F, -0.15F, 4.5F, 0.24F, 0F, 0F, 0F, ID.EquipType.AIR_B_HI, 80});
	//Recon aircraft
	put(ID.E_AIRCRAFT_R,         new float[]{2F, 0F, 2F, 2F, 2F, 2F, 0F, 0.2F, -0.1F, 7.0F, 0.05F, 0F, 0F, 0.04F, ID.EquipType.AIR_R_LO, 120});
	put(ID.E_AIRCRAFT_RFLYFISH,  new float[]{2F, 0F, 4F, 4F, 4F, 4F, 0F, 0.3F, -0.15F, 9.0F, 0.10F, 0F, 0F, 0.08F, ID.EquipType.AIR_R_HI, 90});
	//radar
	put(ID.E_RADAR_AIRMK1,       new float[]{2F, 0F, 2F, 0F, 2F, 0F, 5F, 0F, -0.04F, 4.0F, 0.03F, 0F, 0F, 0.04F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_AIRMK2,       new float[]{2F, 0F, 4F, 0F, 4F, 0F, 8F, 0F, -0.06F, 6.0F, 0.06F, 0F, 0F, 0.08F, ID.EquipType.RADAR_LO, 150});
	put(ID.E_RADAR_SURMK1,       new float[]{2F, 0F, 0F, 8F, 0F, 8F, 5F, 0F, -0.04F, 4.0F, 0.03F, 0F, 0F, 0.04F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_SURMK2,       new float[]{2F, 0F, 0F, 16F, 0F, 16F, 8F, 0F, -0.06F, 6.0F, 0.06F, 0F, 0F, 0.08F, ID.EquipType.RADAR_LO, 150});
	put(ID.E_RADAR_SONAR,        new float[]{2F, 0F, 2F, 4F, 2F, 4F, 3F, 0F, -0.02F, 2.0F, 0.02F, 0F, 0F, 0.03F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_AIRABYSS,     new float[]{2F, 0F, 6F, 0F, 6F, 0F, 15F, 0F, -0.08F, 9.0F, 0.09F, 0F, 0F, 0.12F, ID.EquipType.RADAR_HI, 130});
	put(ID.E_RADAR_SURABYSS,     new float[]{2F, 0F, 0F, 20F, 0F, 20F, 15F, 0F, -0.08F, 9.0F, 0.09F, 0F, 0F, 0.12F, ID.EquipType.RADAR_HI, 130});	
	put(ID.E_RADAR_SONARMK2,     new float[]{2F, 0F, 4F, 10F, 4F, 10F, 6F, 0F, -0.04F, 4.0F, 0.04F, 0F, 0F, 0.06F, ID.EquipType.RADAR_HI, 130});
	//turbine
	put(ID.E_TURBINE,            new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.15F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_LO, 130});
	put(ID.E_TURBINE_IMP,        new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.2F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_LO, 110});
	put(ID.E_TURBINE_ENH,        new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_HI, 80});
	//armor
	put(ID.E_ARMOR,              new float[]{2F, 45F, 0F, 0F, 0F, 0F, 8F, 0F, -0.1F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.ARMOR_LO, 350});
	put(ID.E_ARMOR_ENH,          new float[]{2F, 180F, 0F, 0F, 0F, 0F, 16F, 0F, -0.2F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.ARMOR_HI, 300});
	}});
	
	
}
