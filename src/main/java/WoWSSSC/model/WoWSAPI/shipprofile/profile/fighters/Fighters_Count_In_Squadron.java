package WoWSSSC.model.WoWSAPI.shipprofile.profile.fighters;

import lombok.Data;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class Fighters_Count_In_Squadron
{
    private long max;
    private long min;

    public int getMax()
    {
        return (int) max;
    }

    public int getMin()
    {
        return (int) min;
    }
}
