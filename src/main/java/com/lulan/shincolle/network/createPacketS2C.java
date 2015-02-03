package com.lulan.shincolle.network;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

//create server packet by Jabelar
//web: jabelarminecraft.blogspot.tw/p/packet-handling-for-minecraft-forge-172.html
public class createPacketS2C {
 
	public createPacketS2C() {
	}

	/**ENTITY SYNC PACKET
	 * 用於同步server跟client的entity資料
	 * Format: PacketID + EntityID + ShipLevel + Kills + 
	 *         AttrBonus[] + AttrFinal[] + EntityState[] + BonusPoint[]
	 * 
	 */
	public static FMLProxyPacket createEntitySyncPacket(Entity parEntity) throws IOException {
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.ENTITY_SYNC);
		//Entity ID (用於辨識entity是那一隻)
		bbos.writeInt(parEntity.getEntityId());
		//以下寫入要傳送的資料
		if (parEntity instanceof BasicEntityShip) {
			BasicEntityShip entity = (BasicEntityShip)parEntity;
			bbos.writeShort(entity.getShipLevel());
			bbos.writeInt(entity.getKills());
			bbos.writeInt(entity.getExpCurrent());
			bbos.writeInt(entity.getNumAmmoLight());
			bbos.writeInt(entity.getNumAmmoHeavy());
			
			bbos.writeFloat(entity.getFinalHP());
			bbos.writeFloat(entity.getFinalATK());
			bbos.writeFloat(entity.getFinalDEF());
			bbos.writeFloat(entity.getFinalSPD());
			bbos.writeFloat(entity.getFinalMOV());
			bbos.writeFloat(entity.getFinalHIT());
			
			bbos.writeByte(entity.getEntityState());
			bbos.writeByte(entity.getEntityEmotion());
			bbos.writeByte(entity.getEntitySwinType());
			
			bbos.writeByte(entity.getBonusHP());
			bbos.writeByte(entity.getBonusATK());
			bbos.writeByte(entity.getBonusDEF());
			bbos.writeByte(entity.getBonusSPD());
			bbos.writeByte(entity.getBonusMOV());
			bbos.writeByte(entity.getBonusHIT());			
		}

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
	
	//sync entity state only (no attribute or ship inventory)
	public static FMLProxyPacket createEntityStateSyncPacket(Entity parEntity) throws IOException {
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.STATE_SYNC);
		//Entity ID (用於辨識entity是那一隻)
		bbos.writeInt(parEntity.getEntityId());
		//以下寫入要傳送的資料
		if (parEntity instanceof BasicEntityShip) {
			BasicEntityShip entity = (BasicEntityShip)parEntity;	
			bbos.writeByte(entity.getEntityState());
			bbos.writeByte(entity.getEntityEmotion());
			bbos.writeByte(entity.getEntitySwinType());		
		}

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
	
	/**ATTACK PARTICLE(SMALL) PACKET
	 * 發送特效封包, 使被攻擊的entity發出particle, 普通攻擊適用
	 * Format: PacketID + TargetEntityID + ParticleID
	 */
	public static FMLProxyPacket createAttackSmallParticlePacket(Entity target, int type) throws IOException {
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.PARTICLE_ATK);
		//Entity ID (用於辨識entity是那一隻)
		bbos.writeInt(target.getEntityId());
		//以下寫入要傳送的資料
		bbos.writeByte((byte)type);

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
	
	/**ATTACK PARTICLE(SMALL) PACKET
	 * 發送特效封包, 使目標地點+位移方向發出特效
	 * Format: PacketID + posX + posY + posZ + lookX + lookY + lookZ + type
	 */
	public static FMLProxyPacket createCustomPosAttackParticlePacket(int entityID, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, int type) throws IOException {
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.PARTICLE_ATK2);
		//entity ID (為發送方設定, 設為-1則表示不找entity)
		bbos.writeInt(entityID);
		//position and look vector
		bbos.writeFloat((float)posX);
		bbos.writeFloat((float)posY);
		bbos.writeFloat((float)posZ);
		bbos.writeFloat((float)lookX);
		bbos.writeFloat((float)lookY);
		bbos.writeFloat((float)lookZ);
		//以下寫入要傳送的資料
		bbos.writeByte((byte)type);

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
 
	//send to all player on the server
	public static void sendToAll(FMLProxyPacket parPacket) {
      CommonProxy.channel.sendToAll(parPacket);
	}

	//send entity attribute sync packet
	public static void sendS2CEntitySync(Entity parEntity) {
    	try {
    		LogHelper.info("DEBUG : send SYNC packet to client");
    		sendToAll(createEntitySyncPacket(parEntity));
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	//send entity state sync packet
	public static void sendS2CEntityStateSync(Entity parEntity) {
    	try {
    		LogHelper.info("DEBUG : send State SYNC packet to client");
    		sendToAll(createEntityStateSyncPacket(parEntity));
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	//send attack particle packet
	public static void sendS2CAttackParticle(Entity parEntity, int type) {
    	try {
    		sendToAll(createAttackSmallParticlePacket(parEntity, type));
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	//send attack particle at custom position packet
		public static void sendS2CAttackParticle2(int entityID, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, int type) {
	    	try {
	    		sendToAll(createCustomPosAttackParticlePacket(entityID, posX, posY, posZ, lookX, lookY, lookZ, type));
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}
		}
}
