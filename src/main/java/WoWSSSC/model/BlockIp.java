package WoWSSSC.model;

import lombok.Data;

import java.util.Date;

@Data
public class BlockIp
{
    private String ip;
    private Date created;
    private int count;

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
}
