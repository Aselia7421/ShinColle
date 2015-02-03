package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**Process client side packet by Jabelar
 * this class is intended to be sent from server to client to keep custom entities synced
 * 
 * SYNC PACKET: for ExtendEntityProps, client should not send sync-packet back to server
 */
public class ProcessPacketClientSide { 
	//for entity sync
	private static int packetTypeID;
	private static int entityID;
	private static Entity foundEntity;
	//for particle position
	private static byte particleType;
	private static float posX;
	private static float posY;
	private static float posZ;
	private static float lookX;
	private static float lookY;
	private static float lookZ;
	
	public ProcessPacketClientSide() {}

	@SideOnly(Side.CLIENT)
	public static void processPacketOnClient(ByteBuf parBB, Side parSide) throws IOException {
			
		if (parSide == Side.CLIENT) {
			LogHelper.info("DEBUG : recv packet (client side)");

			World theWorld = Minecraft.getMinecraft().theWorld;
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
   
			//read packet ID
			packetTypeID = bbis.readByte();
			
			switch (packetTypeID) {
			case Names.Packets.ENTITY_SYNC:  //entity sync packet
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data
					foundEntityShip.setShipLevel(bbis.readShort(), false);
					foundEntityShip.setKills(bbis.readInt());
					foundEntityShip.setExpCurrent(bbis.readInt());
					foundEntityShip.setNumAmmoLight(bbis.readInt());
					foundEntityShip.setNumAmmoHeavy(bbis.readInt());
					
					foundEntityShip.setFinalHP(bbis.readFloat());
					foundEntityShip.setFinalATK(bbis.readFloat());
					foundEntityShip.setFinalDEF(bbis.readFloat());
					foundEntityShip.setFinalSPD(bbis.readFloat());
					foundEntityShip.setFinalMOV(bbis.readFloat());
					foundEntityShip.setFinalHIT(bbis.readFloat());
					
					foundEntityShip.setEntityState(bbis.readByte(), false);
					foundEntityShip.setEntityEmotion(bbis.readByte(), false);
					foundEntityShip.setEntitySwimType(bbis.readByte(), false);					
					
					foundEntityShip.setBonusHP(bbis.readByte());
					foundEntityShip.setBonusATK(bbis.readByte());
					foundEntityShip.setBonusDEF(bbis.readByte());
					foundEntityShip.setBonusSPD(bbis.readByte());
					foundEntityShip.setBonusMOV(bbis.readByte());
					foundEntityShip.setBonusHIT(bbis.readByte());
				}
				break;
				
			case Names.Packets.STATE_SYNC:  //entity sync packet
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data	
					foundEntityShip.setEntityState(bbis.readByte(), false);
					foundEntityShip.setEntityEmotion(bbis.readByte(), false);
					foundEntityShip.setEntitySwimType(bbis.readByte(), false);					

				}
				break;
				
			case Names.Packets.PARTICLE_ATK:  //attack particle
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = getEntityByID(entityID, theWorld);
				//read particle type
				particleType = bbis.readByte();
				//spawn particle
				ParticleHelper.spawnAttackParticle(foundEntity, particleType);			
				break;
				
			case Names.Packets.PARTICLE_ATK2:  //attack particle at custom position
				//read entity id
				entityID = bbis.readInt();
				foundEntity = getEntityByID(entityID, theWorld);
				//read position + look vector
				posX = bbis.readFloat();
				posY = bbis.readFloat();
				posZ = bbis.readFloat();
				lookX = bbis.readFloat();
				lookY = bbis.readFloat();
				lookZ = bbis.readFloat();
				//read particle type
				particleType = bbis.readByte();
				//spawn particle
				ParticleHelper.spawnAttackParticleCustomVector(foundEntity, (double)posX, (double)posY, (double)posZ, (double)lookX, (double)lookY, (double)lookZ, particleType);			
				break;
				
			}//end switch
		bbis.close();   
		}
	}
 
	//get entity by ID
	public static Entity getEntityByID(int entityID, World world) {
		for(Object obj: world.getLoadedEntityList()) {
			if(entityID != -1 && ((Entity)obj).getEntityId() == entityID) {
				return ((Entity)obj);
			}
		}
		return null;
	}

}
