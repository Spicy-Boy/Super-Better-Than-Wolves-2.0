package net.minecraft.src;

import java.util.Random;

//GUNS! Based on the source code provided by Balkon's Weapons mod, ranged component, shooter, musket
public class SuperBTWItemGun extends ItemBow {
	private final int m_iWeaponDamage; // melee damage

	public SuperBTWItemGun(int itemID) {
		super(itemID);
		setFull3D();
		this.setMaxStackSize(1);
		m_iWeaponDamage = 2;

	}

	// yes, physically hitting something with your gun does a little damage too
	public int getDamageVsEntity(Entity entity) {
		return m_iWeaponDamage;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// same as bow, not sure what this does...
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack gun, World world, EntityPlayer entityplayer) {
		if (gun.stackSize <= 0 || entityplayer.isUsingItem()) {
			return gun;
		}

		// TESTER VVV
		System.out.println("ON RIGHT CLICK!");

		if (hasAmmo(gun, world, entityplayer)) // Check can reload
		{
			// TESTER VVV
			System.out.println("AMMO DETECTED IN INVENTORY!");

			if (isReadyToFire(gun)) {
				// Start aiming weapon to fire
				soundCharge(gun, world, entityplayer);
				entityplayer.setItemInUse(gun, getMaxItemUseDuration(gun));
			} else {
				// Begin reloading
				entityplayer.setItemInUse(gun, getMaxItemUseDuration(gun));
			}
		} else {
			// Can't reload; no ammo
			if (gun.getTagCompound() == null) {
				setHasClicked(gun, false);
				// TESTER VVV
				System.out.println("Setting has right clicked to " + gun.getTagCompound().getBoolean("clk"));
			}

			if (!(gun.getTagCompound().getBoolean("clk"))) {
				soundEmpty(gun, world, entityplayer);

				setHasClicked(gun, true);
				entityplayer.setItemInUse(gun, getMaxItemUseDuration(gun));
				// TESTER VVV
				System.out.println("Setting has right clicked to " + gun.getTagCompound().getBoolean("clk"));
			}

			entityplayer.setItemInUse(gun, getMaxItemUseDuration(gun));

			setReloadState(gun, none);
		}

		return gun;
	}

	public boolean hasAmmo(ItemStack itemstack, World world, EntityPlayer player) {
		for (int iTempSlot = 0; iTempSlot < 9; iTempSlot++) {
			ItemStack tempStack = player.inventory.getStackInSlot(iTempSlot);

			if (tempStack != null && tempStack.itemID == SuperBTWDefinitions.bullet.itemID) {
				return true;
			}
		}

		return false;
	}

	public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityPlayer player) {
		for (int iTempSlot = 0; iTempSlot < 9; iTempSlot++) {
			ItemStack tempStack = player.inventory.getStackInSlot(iTempSlot);

			if (tempStack != null && tempStack.itemID == SuperBTWDefinitions.bullet.itemID) {
				player.inventory.consumeInventoryItem(SuperBTWDefinitions.bullet.itemID);
				return true;
			}
		}

		return false;
	}

//	TODO this is not being called in 1.5.2! We need to figure out a solution to this
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
		// TESTER
		System.out.println("USING TICK CALLED!");

		if (getReloadState(itemstack) == none
				&& getMaxItemUseDuration(itemstack) - count >= getReloadDuration(itemstack)) {
			effectReloadDone(itemstack, entityplayer.worldObj, entityplayer);
			entityplayer.clearItemInUse();
			setReloadState(itemstack, reloaded);
		}
	}

	public EnumAction getItemUseAction(ItemStack gun) {
		int state = getReloadState(gun);

		// TESTER
//		System.out.println("Choosing EnumAction for right click");
		//VV this ensures that the player doesn't block when no ammo is present
		boolean wasCalledByEmptyClick = getHasClicked(gun); // if hasClicked returns true, dont run reload procedure!
		
		if (state == none && !wasCalledByEmptyClick)  //was
		{	
			return EnumAction.block;
			
		} else if (state == ready) 
		{
			
			return EnumAction.bow;

		}
		return EnumAction.none;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		// TESTER for release right click
		System.out.println("STOPPED USING");
		
		//this if chain simply prevents the click sound from being spammed when right clicking without any ammo
		if (itemstack.getTagCompound() == null) {
			setHasClicked(itemstack, false);
			
			// TESTER VVV
			System.out.println("Setting has right clicked to " + itemstack.getTagCompound().getBoolean("clk"));
			
		} else if ((itemstack.getTagCompound().getBoolean("clk"))) {
			setHasClicked(itemstack, false);
			
			// TESTER VVV
			System.out.println("Setting has right clicked to " + itemstack.getTagCompound().getBoolean("clk"));
			
		}

		if (!isReloaded(itemstack)) {
			return;
		}
		if (isReadyToFire(itemstack)) {
			
			fire(itemstack, world, entityplayer, i);

			setReloadState(itemstack, none);
		} 
		else 
		{
			setReloadState(itemstack, ready);
		}

	}

	public int getReloadDuration(ItemStack gun) // parameter is currently unused
	{
		// was 1 I think?
		return 100;
	}

	public final Random getItemRand() {
		return itemRand;
	}

	// RENDERING and client shenanigans
	
	//!! this is implemented in EntityPlayer instead
	public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.swingItem();
		world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F / (getItemRand().nextFloat() * 0.4F + 0.8F));
	}

	public void fire(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		int j = getMaxItemUseDuration(itemstack) - i;
		float f = j / 20F;
		f = (f * f + f * 2F) / 3F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		f += 0.02F;

		if (!world.isRemote) {
			SuperBTWEntityBullet entitymusketbullet = new SuperBTWEntityBullet(world, entityplayer, 1F / f);
			world.spawnEntityInWorld(entitymusketbullet);
		}

		setReloadState(itemstack, none);

		postShootingEffects(itemstack, entityplayer, world);

	}

	public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
		float f = entityplayer.isSneaking() ? -0.05F : -0.1F;
		double d = -MathHelper.sin((entityplayer.rotationYaw / 180F) * 3.141593F)
				* MathHelper.cos((0 / 180F) * 3.141593F) * f;
		double d1 = MathHelper.cos((entityplayer.rotationYaw / 180F) * 3.141593F)
				* MathHelper.cos((0 / 180F) * 3.141593F) * f;
		entityplayer.rotationPitch -= entityplayer.isSneaking() ? 7.5F : 15F;
		entityplayer.addVelocity(d, 0, d1);
	}

	public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
		world.playSoundEffect(x, y, z, "random.explode", 3F, 1F / (getItemRand().nextFloat() * 0.4F + 0.7F));

		float particleX = -MathHelper.sin(((yaw + 23F) / 180F) * 3.141593F)
				* MathHelper.cos((pitch / 180F) * 3.141593F);
		float particleY = -MathHelper.sin((pitch / 180F) * 3.141593F) - 0.1F;
		float particleZ = MathHelper.cos(((yaw + 23F) / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);

		for (int i = 0; i < 3; i++) {
			world.spawnParticle("smoke", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
		}
		world.spawnParticle("flame", x + particleX, y + particleY, z + particleZ, 0.0D, 0.0D, 0.0D);
	}

	// RELOADING & gun states
	public static int none = 0;
	public static int reloaded = 1;
	public static int ready = 2;

	public static boolean hasRightClicked = false;

	public boolean isReloaded(ItemStack gun) {
		return getReloadState(gun) >= reloaded; // if the gun's state is => 1
	}

	public boolean isReadyToFire(ItemStack gun) {
		return getReloadState(gun) >= ready; // if the gun's state is >= 2
	}

	private void initTagCompound(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
	}

	public int getReloadState(ItemStack itemstack) {
		if (itemstack.hasTagCompound())
			return itemstack.getTagCompound().getByte("rld");
		return 0;
	}

	public void setReloadState(ItemStack itemstack, int state) {
		// TESTER of STATESVVV
		System.out.println("Set gun state to " + state);

		initTagCompound(itemstack);
		itemstack.getTagCompound().setByte("rld", (byte) state);
	}
	
	//the clicked methods serve to prevent spamming the click noise when holding right click without any ammo
	public void setHasClicked(ItemStack gun, boolean clicked) {
		initTagCompound(gun);
		gun.getTagCompound().setBoolean("clk", clicked); // this sets the state "clk" to

	}

	public boolean getHasClicked(ItemStack gun) {
		if (gun.hasTagCompound()) {
			return gun.getTagCompound().getBoolean("clk");
		}

		return false;
	}

	// SOUNDS

	public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// nada here :p can be added in inheritors
	}

	public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F / 0.8F);
	}

	public final void postShootingEffects(ItemStack itemstack, EntityPlayer entityplayer, World world) {
		effectPlayer(itemstack, entityplayer, world);
		effectShoot(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.rotationYaw,
				entityplayer.rotationPitch);
	}

}
