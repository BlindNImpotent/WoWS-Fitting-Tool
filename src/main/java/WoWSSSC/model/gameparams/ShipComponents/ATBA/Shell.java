package WoWSSSC.model.gameparams.ShipComponents.ATBA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2017-05-25.
 */
@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class Shell
{
    private float alphaDamage;
    private String ammoType;
    private float bulletSpeed;
    private float burnProb;

    public float getBurnProbHundred()
    {
        return "HE".equalsIgnoreCase(ammoType) ? burnProb * 100 : 0;
    }
}
