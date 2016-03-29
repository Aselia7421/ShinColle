package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.other.EntityAirplaneTHostile;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroHostile;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.BlockHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicEntityShipHostileCV extends BasicEntityShipHostile implements IShipAircraftAttack {

	protected double launchHeight;		//airplane launch height
	
	
	public BasicEntityShipHostileCV(World world) {
		super(world);
	}

	@Override
	abstract public int getDamageType();

	@Override
	public int getNumAircraftLight() {
		return 10;
	}

	@Override
	public int getNumAircraftHeavy() {
		return 10;
	}

	@Override
	public boolean hasAirLight() {
		return true;
	}

	@Override
	public boolean hasAirHeavy() {
		return true;
	}

	@Override
	public void setNumAircraftLight(int par1) {}

	@Override
	public void setNumAircraftHeavy(int par1) {}

	@Override
	public boolean attackEntityWithAircraft(Entity target) {
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //發射者煙霧特效 (發射飛機不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
        
    	double summonHeight = this.posY + launchHeight;
    	
    	//check the summon block
    	if(!BlockHelper.checkBlockSafe(worldObj, (int)posX, (int)(posY+launchHeight), (int)(posZ))) {
    		summonHeight = posY + this.height * 0.75D;
    	}
    	
    	BasicEntityAirplane plane = new EntityAirplaneZeroHostile(this.worldObj);
        plane.setAttrs(this.worldObj, this, target, summonHeight);
    	this.worldObj.spawnEntityInWorld(plane);
        return true;
	}

	@Override
	public boolean attackEntityWithHeavyAircraft(Entity target) {
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //發射者煙霧特效 (發射飛機不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
        
    	double summonHeight = this.posY + launchHeight;
    	
    	//check the summon block
    	if(!BlockHelper.checkBlockSafe(worldObj, (int)posX, (int)(posY+launchHeight), (int)(posZ))) {
    		summonHeight = posY + this.height * 0.75D;
    	}
    	
    	BasicEntityAirplane plane = new EntityAirplaneTHostile(this.worldObj);
        plane.setAttrs(this.worldObj, this, target, summonHeight);
    	this.worldObj.spawnEntityInWorld(plane);
        return true;
	}


}


