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
    private float alertDist;
    private float alphaDamage;
    private float alphaPiercingHE;
    private String ammoType;
    private float armingTime;
    private float bulletDiametr;
    private float burnProb;
    private float costCR;
    private float damage;
    private float damageUW;
    private float depth;
    private float directDamage;
    private float fallingTimeCoef;
    private float id;
    private List<String> ignoreClasses;
    private String index;
    @JsonAlias("isDeepWater")
    private boolean deepWater;
    private float maxDist;
    private String name;
    private String planeAmmoType;
    private float speed;
    private float splashArmorCoeff;
    private float splashCubeSize;
    private TypeInfo typeinfo;
    private boolean uwAbility;
    private float uwCritical;
    private float visibilityFactor;
    private float volume;

    public float getMaxDist()
    {
        return CommonUtils.getDistCoefWG(maxDist);
    }
}
