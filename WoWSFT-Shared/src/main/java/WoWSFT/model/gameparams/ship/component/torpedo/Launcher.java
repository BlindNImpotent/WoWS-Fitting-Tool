package WoWSFT.model.gameparams.ship.component.torpedo;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@WoWSFT
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Launcher
{
    private List<String> ammoList;
    private double barrelDiameter;
    private boolean canRotate;
    private List<List<Double>> deadZone;
    private List<Double> horizSector;
    private long id;
    private String index;
    private List<Double> mainSector;
    private String name;
    private int numAmmos;
    private int numBarrels;
    private List<Double> position;
    private List<Double> rotationSpeed;
    private List<Double> shootSector;
    private double shotDelay;
    private boolean smallGun;
    private double timeBetweenShots;
    private double timeToChangeAngle;
    private double timeToChangeSpeed;
    private List<Double> torpedoAngles;
    private TypeInfo typeinfo;
    private List<Double> vertSector;
}
