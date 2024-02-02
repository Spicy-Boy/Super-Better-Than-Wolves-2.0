package net.minecraft.src;

public class SuperBTWWorldBorderManager 
{

	//NOTE currently does not account for the potential for world border to be rectangular rather than square
	//so, if the Z border is larger than the X border,
	public void shrinkWorldBorder (int shrinkByThisMuch)
	{
		int newWorldBorderX;
		
		newWorldBorderX = SuperBTW.instance.getRectangularWorldBorderX() - shrinkByThisMuch;
		
		SuperBTW.instance.setRectangularWorldBorderX(newWorldBorderX);
		
		int newWorldBorderZ;
		
		newWorldBorderZ = SuperBTW.instance.getRectangularWorldBorderZ() - shrinkByThisMuch;
		
		SuperBTW.instance.setRectangularWorldBorderX(newWorldBorderZ);
	}
	
	
}
