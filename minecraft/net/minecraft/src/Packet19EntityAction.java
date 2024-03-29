package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet19EntityAction extends Packet
{
    /** Player ID. */
    public int entityId;

    /** 1=sneak, 2=normal */
    public int state;

    public Packet19EntityAction() {}

    public Packet19EntityAction(Entity par1Entity, int par2)
    {
        this.entityId = par1Entity.entityId;
        this.state = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.entityId = par1DataInputStream.readInt();
//      AARON changes this to fix special key/ctrl click crash
//      this.state = par1DataInputStream.readByte();
        this.state = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.entityId);
        
        //AARON added special key/ctrl click functionality
//        par1DataOutputStream.writeByte(this.state);
        par1DataOutputStream.writeInt(this.state);
        //^^^
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityAction(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        
        //AARON added special key/ctrl click functionality
//        return 5;
    	return 8;
    	//^^^
    }
}
