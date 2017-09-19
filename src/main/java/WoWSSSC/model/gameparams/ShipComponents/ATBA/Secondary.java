package WoWSSSC.model.gameparams.ShipComponents.ATBA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Qualson-Lee on 2017-05-25.
 */
@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class Secondary
{
    private List<String> ammoList;
    private float barrelDiameter;
    private long id;
    private String index;
    private String name;
    private int numBarrels;
    private float shotDelay;
    private boolean smallGun;

    private int count;
    private String realName;
    private Shell shell;

    private float antiAirAuraDistance;
    private float antiAirAuraStrength;

    public int getBarrelDiameterReal()
    {
        return (int) (barrelDiameter * 100);
    }
}
