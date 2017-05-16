package WoWSSSC.model.gameparams.ShipComponents.DiveBomber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-05-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiveBomberBomb
{
    private String ammoType;
    private float burnProb;

    public int getBurnProbHundred()
    {
        return (int) (burnProb * 100);
    }
}
