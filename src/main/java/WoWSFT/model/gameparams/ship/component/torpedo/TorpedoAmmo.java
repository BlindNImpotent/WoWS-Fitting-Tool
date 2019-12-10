package WoWSFT.model.gameparams.ship.component.torpedo;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class TorpedoAmmo
{
    private List<Object> additionalShips;
    private double alertDist;
    private double alphaDamage;
    private double alphaPiercingHE;
    private String ammoType;
    private double armingTime;
    private double bulletDiametr;
    private double burnProb;
    private double costCR;
    private double damage;
    private double damageUW;
    private double depth;
    private double directDamage;
    private double fallingTimeCoef;
    private double id;
    private List<String> ignoreClasses;
    private String index;
    @JsonAlias("isDeepWater")
    private boolean deepWater;
    private double maxDist;
    private String name;
    private String planeAmmoType;
    private double speed;
    private double splashArmorCoeff;
    private double splashCubeSize;
    private TypeInfo typeinfo;
    private boolean uwAbility;
    private double uwCritical;
    private double visibilityFactor;
    private double volume;
}
