package net.minecraft.src;
//AARON lifted this from Balkon's completely!
public class SuperBTWEntityBullet extends EntityArrow
{
	protected int			xTile;
	protected int			yTile;
	protected int			zTile;
	protected Block			inTile;
	protected int			inData;
	protected boolean		inGround;
	public int				pickupMode;
	protected int			ticksInGround;
	protected int			ticksInAir;
	public boolean			beenInGround;
	
	public float			extraDamage;
	public int				knockBack;
	
	public SuperBTWEntityBullet(World world)
	{
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = null;
		inData = 0;
		inGround = false;
		arrowShake = 0;
		ticksInAir = 0;
		yOffset = 0F;
		pickupMode = 0;
		
		extraDamage = 0;
		knockBack = 0;
		
		setSize(0.5F, 0.5F);
	}
	
	public SuperBTWEntityBullet(World world, double d, double d1, double d2)
	{
		this(world);
		setPosition(d, d1, d2);
	}

	public SuperBTWEntityBullet(World world, EntityLiving entityliving, float deviation)
	{
		this(world);
		shootingEntity = entityliving;
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.1D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setThrowableHeading(motionX, motionY, motionZ, 5.0F, deviation);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (inGround)
		{
			if (rand.nextInt(4) == 0)
			{
				worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			}
			return;
		}
		double speed = getTotalVelocity();
		double amount = 16D;
		if (speed > 2.0D)
		{
			for (int i1 = 1; i1 < amount; i1++)
			{
				worldObj.spawnParticle("explode", posX + (motionX * i1) / amount, posY + (motionY * i1) / amount, posZ + (motionZ * i1) / amount, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public void onEntityHit(Entity entity)
	{
		float damage = 20F + extraDamage;
		DamageSource damagesource = null;
		if (shootingEntity == null)
		{
			damagesource = DamageSource.causeGunDamage(this, this);
		} else
		{
			damagesource = DamageSource.causeGunDamage(this, shootingEntity);
		}
		if (entity.attackEntityFrom(damagesource, (int)damage))
		{
			applyEntityHitEffects(entity);
			playHitSound();
			setDead();
		}
	}
	
	public void applyEntityHitEffects(Entity entity)
	{
		if (isBurning() && !(entity instanceof EntityEnderman))
		{
			entity.setFire(5);
		}
		if (entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving) entity;
			if (knockBack > 0)
			{
				float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				if (f > 0.0F)
				{
					entity.addVelocity(motionX * knockBack * 0.6D / f, 0.1D, motionZ * knockBack * 0.6D / f);
				}
			}
			if (shootingEntity instanceof EntityPlayerMP && shootingEntity != entity && entity instanceof EntityPlayer)
			{
				//AARON copied this exact line from the EntityArrow code. Will have to make sure it does what I think it does (and I really don't know)
				((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
			}
		}
	}
	
	public boolean aimRotation()
	{
		return false;
	}
	
	public int getMaxLifetime()
	{
		return 200;
	}
	
	public float getAirResistance()
	{
		return 0.98F;
	}
	
	public float getGravity()
	{
		return getTotalVelocity() < 3F ? 0.07F : 0F;
	}
	
	public final double getTotalVelocity()
	{
		return Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
	}
	
	public int getMaxArrowShake()
	{
		return 0;
	}
	
	public void playHitSound()
	{
		//a whole lot of silence
	}
}
