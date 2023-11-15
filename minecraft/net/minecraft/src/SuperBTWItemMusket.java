package net.minecraft.src;

public class SuperBTWItemMusket extends SuperBTWItemGun 
{

	public SuperBTWItemMusket(int itemID) 
	{
		super(itemID);
		setUnlocalizedName("SuperBTWItemMusket");
		
		this.setMaxDamage(175);
	}
	

//	@Override
//	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
//	{
//		if (!isReloaded(itemstack)) return;
//		if (isReadyToFire(itemstack))
//		{
//			if (hasAmmoAndConsume(itemstack, world, entityplayer))
//			{
//				fire(itemstack, world, entityplayer, i);
//				itemstack.damageItem(1, entityplayer);
//			}
//			setReloadState(itemstack, none);
//		} else
//		{
//			setReloadState(itemstack, ready);
//		}
//	}

}
