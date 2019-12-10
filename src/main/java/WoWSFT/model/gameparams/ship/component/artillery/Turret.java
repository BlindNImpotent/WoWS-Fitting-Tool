package WoWSFT.model.gameparams.ship.component.artillery;

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
public class Turret
{
    private List<String> ammoList;
    private double antiAirAuraDistance;
    private double antiAirAuraStrength;
    private double barrelDiameter;
    private double coeffPerSecondMin;
    private List<List<Double>> deadZone;
    private double delim;
    private double ellipseRangeMax;
    private double ellipseRangeMin;
    private List<Double> horizSector;
    private long id;
    private double idealDistance;
    private double idealRadius;
    private String index;
    private double maxEllipseRanging;
    private double medEllipseRanging;
    private double minEllipseRanging;
    private double minRadius;
    private String name;
    private int numBarrels;
    private double onMoveTarPosCoeffDelim;
    private double onMoveTarPosCoeffMaxDist;
    private double onMoveTarPosCoeffZero;
    private double onMoveTarPosDelim;
    private List<Double> position;
    private double radiusOnDelim;
    private double radiusOnMax;
    private double radiusOnZero;
    private double reduceTime;
    private List<Double> rotationSpeed;
    private double shotDelay;
    private boolean smallGun;
    private double smokePenalty;
    private TypeInfo typeinfo;
    private List<Double> vertSector;
}
