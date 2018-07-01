package com.lulan.shincolle.entity.cruiser;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipSkillAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:cannon, 1:head, 2:sword, 3:eye, 4:shoes
 */
public class EntityCLTenryuu extends BasicEntityShipSmall
{
	
	private Predicate targetSelector;
	private int remainAttack;
	private Vec3d skillMotion;
	private ArrayList<Entity> damagedTarget;
	
	
	public EntityCLTenryuu(World world)
	{
		super(world);
		this.setSize(0.75F, 1.65F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.LIGHT_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CLTenryuu);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setStateMinor(ID.M.NumState, 5);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.CL]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CL]);
		this.modelPosInGUI = new float[] {0F, 22F, 0F, 42F};
		this.targetSelector = new TargetHelper.Selector(this);
		this.remainAttack = 0;
		this.skillMotion = Vec3d.ZERO;
		this.damagedTarget = new ArrayList<Entity>();
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(12);
		
		this.initPre();
	}
	
	@Override
	public int getEquipType()
	{
		return 1;
	}
	
	@Override
    public boolean canBePushed()
    {
		if (this.getStateEmotion(ID.S.Phase) > 0) return false;
        return super.canBePushed();
    }
	
	@Override
	public boolean canFly()
	{
		if (this.getStateEmotion(ID.S.Phase) > 0) return false;
		return super.canFly();
	}

	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//skill attack
		this.tasks.addTask(0, new EntityAIShipSkillAttack(this));
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	//晚上時額外增加屬性
	@Override
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		if (!this.world.isDaytime())
		{
			this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.15F);
			this.getAttrs().setAttrsRaw(ID.Attrs.DODGE, this.getAttrs().getAttrsRaw(ID.Attrs.DODGE) + 0.15F);
		}
	}
	
	@Override
	public void onLivingUpdate()
	{
		//client side
		if (this.world.isRemote)
		{
			//final attack phase
			if (this.StateEmotion[ID.S.Phase] == 3)
			{
				ParticleHelper.spawnAttackParticleAtEntity(this, 1D, 1D, 0.6D, (byte)14);
			}
		}
		//server side
		else
		{
			//every 128 ticks
        	if ((this.ticksExisted & 127) == 0 && !this.isMorph)
        	{
        		//married effect
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					//apply buff to owner
  					EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				if (player != null && getDistanceSqToEntity(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION , 100+getStateMinor(ID.M.ShipLevel), 0, false, false));
					}
  				}//end married buff
        	}
        	
			//apply skill effect
			this.updateSkillEffect();
		}
		
		super.onLivingUpdate();
	}
	
	private void updateSkillEffect()
	{
		if (this.StateEmotion[ID.S.Phase] > 1)
		{
			//clear attacked target list
			if (this.StateTimer[ID.T.AttackTime3] == 6)
			{
				this.damagedTarget.clear();
				
				//apply sound
				this.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, ConfigHandler.volumeFire, this.getSoundPitch());
				this.playSound(ModSounds.SHIP_JET, ConfigHandler.volumeFire, this.getSoundPitch());
			
				//apply attack time
				this.applyParticleAtAttacker(5, null, Dist4d.ONE);
			}
			//draw movement blur
			else if (this.StateTimer[ID.T.AttackTime3] == 3)
			{
				this.applyParticleAtTarget(5, null, new Dist4d(this.skillMotion.xCoord, this.skillMotion.yCoord, this.skillMotion.zCoord, 1D));
			
				//apply final attack sound
				if (this.StateEmotion[ID.S.Phase] == 3)
				{
					this.playSound(ModSounds.SHIP_AP_ATTACK, ConfigHandler.volumeFire * 1.1F, this.getSoundPitch() * 0.6F);
				}
			}
			
			//apply damage
			if (this.StateTimer[ID.T.AttackTime3] <= 7 && this.StateTimer[ID.T.AttackTime3] >= 0)
			{
				//apply motion
				this.motionX = this.skillMotion.xCoord;
				this.motionY = this.skillMotion.yCoord;
				this.motionZ = this.skillMotion.zCoord;
				
				//attack on colliding
				this.damageNearbyEntity();
			}
			else
			{
				//apply motion
				this.motionX = 0D;
				this.motionY = 0D;
				this.motionZ = 0D;
			}
			
			//sync motion
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
		}
	}
	
	private void damageNearbyEntity()
	{
		float rawatk = this.getAttackBaseDamage(this.StateEmotion[ID.S.Phase] == 2 ? 2 : 3, null);
		
		ArrayList<Entity> list = EntityHelper.getEntitiesWithinAABB(this.world, Entity.class,
				this.getEntityBoundingBox().expand(2D, 1.5D, 2D), this.targetSelector);

		for (Entity target : list)
		{
			boolean attacked = false;
			
			//check target was not attacked before
			for (Entity ent : this.damagedTarget)
			{
				if (ent.equals(target))
				{
					attacked = true;
					break;
				}
			}
			
			if (attacked)
			{
				continue;							//attacked, skip to next
			}
			else
			{
				this.damagedTarget.add(target);		//not attacked, add to attacked list
			}
			
			float atk = CombatHelper.modDamageByAdditionAttrs(this, target, rawatk, 0);
			
        	//目標不能是自己 or 主人, 且可以被碰撞
        	if (target.canBeCollidedWith() && EntityHelper.isNotHost(this, target))
        	{
        		//若owner相同, 則傷害設為0 (但是依然觸發擊飛特效)
        		if (TeamHelper.checkSameOwner(this, target))
        		{
        			atk = 0F;
            	}
        		else
        		{
        		    //roll miss, cri, dhit, thit
        		    atk = CombatHelper.applyCombatRateToDamage(this, target, true, 1F, atk);
        	  		
        	  		//damage limit on player target
        		    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
        	  		
        	  		//check friendly fire
        			if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
        			
        	  		//確認攻擊是否成功
        		    if (target.attackEntityFrom(DamageSource.causeMobDamage(this), atk))
        		    {
        		    	applyParticleAtTarget(1, target, Dist4d.ONE);
        		    	if (this.rand.nextInt(2) == 0) this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
        		    	
        		        if (ConfigHandler.canFlare) flareTarget(target);
        		        
        		        //push target
        		        if (target.canBePushed())
        		        {
        		        	if (target instanceof IShipAttackBase)
        		        	{
                    			target.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.02F, 
                   	                   0.2D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.02F);
        		        	}
        		        	else
        		        	{
                    			target.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.05F, 
                   	                   0.4D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.05F);
        		        	}
                 			
                 			//for other player, send ship state for display
        		        	this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
        		        }
        	        }
        		}//end not same owner
        	}//end can collide
		}//end for all target
	}
	
	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		if (this.isMorph) return super.attackEntityWithHeavyAmmo(target);
		
		//ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
	
		if (this.StateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_AP_P1, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, target, Dist4d.ONE);
  			
  			//charging
  			this.StateEmotion[ID.S.Phase] = -1;
		}
		else if (this.StateEmotion[ID.S.Phase] == -1)
		{
  			//start skill attack
  			this.StateEmotion[ID.S.Phase] = 1;
			this.remainAttack = 3 + (int)(this.getLevel() * 0.03F);
		}
		
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        return true;
	}
	
	private Entity checkSkillTarget(Entity target)
	{
		//target null
		if (target == null)
		{
			return null;
		}
		//target exist
		else
		{
			//if target dead or too far away, find new target
			if (!target.isEntityAlive() || target.getDistanceSqToEntity(this) > (this.getAttrs().getAttackRange() * this.getAttrs().getAttackRange()))
			{
				if (this.remainAttack > 0)
				{
					ArrayList<Entity> list = EntityHelper.getEntitiesWithinAABB(this.world, Entity.class,
							this.getEntityBoundingBox().expand(10D, 10D, 10D), this.targetSelector);
			
					if (list.size() > 0)
					{
						target = list.get(this.rand.nextInt(list.size()));
						this.setEntityTarget(target);
						return target;
					}
				}
				
				return null;
			}
		}
		
		return target;
	}

	private void updateSkillHoriAttack(Entity target)
	{
		//get random pos
		BlockPos pos = BlockHelper.findRandomSafePos(target);
		Vec3d vecpos = new Vec3d(pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D);
		double dist = this.getDistanceSqToCenter(pos);
		
		//calc motion
		this.skillMotion = CalcHelper.getUnitVectorFromA2B(new Vec3d(target.posX, target.posY, target.posZ), vecpos);
		this.skillMotion = this.skillMotion.scale(-1.25D);
		
		//calc rotation
		float[] degree = CalcHelper.getLookDegree(this.skillMotion.xCoord, this.skillMotion.yCoord, this.skillMotion.zCoord, true);
		this.rotationYaw = degree[0];
		this.rotationYawHead = degree[0];
		
		//apply teleport
		EntityHelper.applyTeleport(this, dist, vecpos);
		
		//update flag and sync
		this.remainAttack--;
		this.StateTimer[ID.T.AttackTime3] = 7;
		this.setStateEmotion(ID.S.Phase, 2, true);
		this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
	}
	
	private void updateSkillFinalAttack(Entity target)
	{
		//get random pos
		BlockPos pos = BlockHelper.findTopSafePos(target);
		Vec3d vecpos = new Vec3d(pos.getX()+0.5D, pos.getY(), pos.getZ()+0.5D);
		double dist = this.getDistanceSqToCenter(pos);
		
		//calc motion
		this.skillMotion = new Vec3d(0D, Math.abs(vecpos.yCoord - target.posY) * -0.25D, 0D);
		
		//apply teleport
		EntityHelper.applyTeleport(this, dist, vecpos);
		
		//update flag and sync
		this.remainAttack--;
		this.StateTimer[ID.T.AttackTime3] = 9;
		this.setStateEmotion(ID.S.Phase, 3, true);
		this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
	}
	
	/**
	 * Skill Phase:
	 * 
	 * -1: skill ready to enter phase 1
	 * 0: none
	 * 1: skill ready, find new position and teleport
	 * 2: horizontal attack
	 * 3: final attack
	 * 
	 * Process:
	 * 
	 * 0 -> -1 -> 1 -> 2 -> 1
	 *              -> 3 -> 0
	 */
	@Override
	public boolean updateSkillAttack(Entity target)
	{
		//check target
		target = this.checkSkillTarget(target);
		
		//no target, reset phase
		if (target == null)
		{
			this.setStateEmotion(ID.S.Phase, 0, true);
			this.remainAttack = 0;
			this.StateTimer[ID.T.AttackTime3] = 0;
			this.skillMotion = Vec3d.ZERO;
			return false;
		}
		
		//find next teleport pos
		if (this.StateEmotion[ID.S.Phase] == 1)
		{
			//horizontal attack
			if (this.remainAttack > 1)
			{
				this.updateSkillHoriAttack(target);
			}
			//final attack
			else
			{
				this.updateSkillFinalAttack(target);
			}
		}
		
		//ticking
		if (this.StateTimer[ID.T.AttackTime3] <= 0)
		{
			//in horizontal attack state
			if (this.StateEmotion[ID.S.Phase] == 2)
			{
				this.setStateEmotion(ID.S.Phase, 1, true);
			}
			//in final attack state
			else if (this.StateEmotion[ID.S.Phase] == 3)
			{
				this.setStateEmotion(ID.S.Phase, 0, true);
			}
		}
		
		//skill tick--
		if (this.StateTimer[ID.T.AttackTime3] > 0) this.StateTimer[ID.T.AttackTime3]--;
		
		return false;
	}
	
    @Override
	public double getMountedYOffset()
    {
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.2F;
  			}
  			else
  			{
  				return this.height * 0.3F;
  			}
  		}
  		else
  		{
  			return this.height * 0.7F;
  		}
	}
	
	@Override
  	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 15, 1D, 1D, 0.9D), point);
  		break;
  		case 2:  //heavy cannon
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, 1D, 1D, 0.7D), point);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, 1D, 1D, 0.7D), point);
		break;
  		case 5:  //for attack time setting
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 15, 0.9D, 1D, 1D), point);
		break;
  		}
  	}
	
	@Override
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, ConfigHandler.volumeFire * 1.2F, this.getSoundPitch() * 0.85F);
  	        
  			//entity sound
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
	        }
		break;
  		}//end switch
  	}
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light attack
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  	  	case 2:  //heavy attack: horizontal
  			return this.shipAttrs.getAttackDamageHeavy() * 0.3F;
  		case 3:  //heavy attack: final
  			return this.shipAttrs.getAttackDamageHeavy() * 1.2F;
		default: //melee
			return this.shipAttrs.getAttackDamage() * 2F;
  		}
  	}
	
	@Override
  	public void applyParticleAtTarget(int type, Entity target, Dist4d distVec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch (type)
  		{
  		case 1:  //light cannon
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  		break;
  		case 5:  //high speed movement
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 44, posX+skillMotion.xCoord*2D, posY+height*0.4D+skillMotion.yCoord*2.5D, posZ+skillMotion.zCoord*2D, distVec.x, distVec.y, distVec.z, false), point);
		break;
		default: //melee
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
		break;
  		}
  	}
	
	@Override
  	public void applySoundAtTarget(int type, Entity target)
  	{
  		switch (type)
  		{
  		
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  		break;
  		case 1:  //light cannon
		default: //melee
			if (target instanceof IShipEmotion)
			{
				this.playSound(ModSounds.SHIP_HITMETAL, ConfigHandler.volumeFire, this.getSoundPitch());
			}
			else
			{
				this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
			}
		break;
  		}
  	}
	
	
}