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
    private float barrelDiameter;
    private boolean canRotate;
    private List<List<Float>> deadZone;
    private List<Float> horizSector;
    private long id;
    private String index;
    private List<Float> mainSector;
    private String name;
    private int numAmmos;
    private int numBarrels;
    private List<Float> position;
    private List<Float> rotationSpeed;
    private List<Float> shootSector;
    private float shotDelay;
    private boolean smallGun;
    private float timeBetweenShots;
    private float timeToChangeAngle;
    private float timeToChangeSpeed;
    private List<Float> torpedoAngles;
    private TypeInfo typeinfo;
    private List<Float> vertSector;
}
