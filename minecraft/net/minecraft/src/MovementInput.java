package net.minecraft.src;

public class MovementInput
{
    /**
     * The speed at which the player is strafing. Postive numbers to the left and negative to the right.
     */
    public float moveStrafe = 0.0F;

    /**
     * The speed at which the player is moving forward. Negative numbers will move backwards.
     */
    public float moveForward = 0.0F;
    public boolean jump = false;
    public boolean sneak = false;
    
    //AARON added special key/ctrl click functionality
    public boolean special = false;
    //^^^

    public void updatePlayerMoveState() {}
}
