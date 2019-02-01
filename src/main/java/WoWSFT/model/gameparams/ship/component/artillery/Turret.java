package WoWSFT.model.gameparams.ship.component.artillery;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Turret
{
    private List<String> ammoList;
    private float antiAirAuraDistance;
    private float antiAirAuraStrength;
    private float barrelDiameter;
    private float coeffPerSecondMin;
    private List<List<Float>> deadZone;
    private float delim;
    private float ellipseRangeMax;
    private float ellipseRangeMin;
    private List<Float> horizSector;
    private long id;
    private float idealDistance;
    private float idealRadius;
    private String index;
    private float maxEllipseRanging;
    private float medEllipseRanging;
    private float minEllipseRanging;
    private float minRadius;
    private String name;
    private int numBarrels;
    private float onMoveTarPosCoeffDelim;
    private float onMoveTarPosCoeffMaxDist;
    private float onMoveTarPosCoeffZero;
    private float onMoveTarPosDelim;
    private List<Float> position;
    private float radiusOnDelim;
    private float radiusOnMax;
    private float radiusOnZero;
    private float reduceTime;
    private List<Float> rotationSpeed;
    private float shotDelay;
    private boolean smallGun;
    private float smokePenalty;
    private TypeInfo typeinfo;
    private List<Float> vertSector;
}
