package WoWSFT.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class BlockIp
{
    private String ip;
    private Date created;
    private int count;
    private int blockCount;
    private Date blockCreated;

    public BlockIp(String ip)
    {
        this.ip = ip;
        this.created = new Date();
        this.count = 1;
    }

    public void doCount()
    {
        this.count++;
    }

    public void reset()
    {
        this.count = 1;
        this.created = new Date();
    }

    public void addBlockCount()
    {
        blockCount++;
        if (blockCreated == null)
        {
            blockCreated = new Date();
        }
    }
    
    public void resetBlock()
    {
        reset();
        blockCount = 0;
        blockCreated = null;
    }
}
