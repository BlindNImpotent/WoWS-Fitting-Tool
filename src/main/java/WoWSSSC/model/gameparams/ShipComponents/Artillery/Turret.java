package WoWSSSC.model.gameparams.ShipComponents.Artillery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2017-05-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Turret
{
    private List<String> ammoList;
    private float barrelDiameter;

    private List<List<Float>> deadZone;
    private List<Float> horizSector;
    private List<Float> vertSector;
    private List<Float> mainSector;
    private List<Float> position;

    private List<Float> rotationSpeed;
    private float shotDelay;

    private String name;
    private String realName;
    private int numBarrels;
    private float antiAirAuraDistance;
    private float antiAirAuraStrength;
}
