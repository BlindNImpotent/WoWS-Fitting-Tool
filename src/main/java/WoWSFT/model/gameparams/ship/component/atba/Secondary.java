package WoWSFT.model.gameparams.ship.component.atba;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Secondary
{
    private List<String> ammoList;
    private double antiAirAuraDistance;
    private double antiAirAuraStrength;
    private double barrelDiameter;
    private List<List<Double>> deadZone;
    private double delim;
    private List<Double> horizSector;
    private long id;
    private double idealDistance;
    private double idealRadius;
    private String index;
    private double minRadius;
    private String name;
    private int numBarrels;
    private double radiusOnDelim;
    private double radiusOnMax;
    private double radiusOnZero;
    private List<Double> rotationSpeed;
    private double shotDelay;
    private boolean smallGun;
    private double smokePenalty;
    private TypeInfo typeinfo;
    private List<Double> vertSector;
    @JsonInclude
    private double GSIdealRadius = 1.0;

    private int count;

    private double alphaDamage;
    private double alphaPiercingHE;
    private String ammoType;
    private double bulletSpeed;
    private double burnProb;

    public double getAlphaPiercingHEReal()
    {
        return Math.ceil(alphaPiercingHE) - 1.0;
    }
}
