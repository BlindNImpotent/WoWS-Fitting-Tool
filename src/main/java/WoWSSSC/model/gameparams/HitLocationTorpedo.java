package WoWSSSC.model.gameparams;

import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class HitLocationTorpedo
{
    private float armorCoeff;
    private float autoRepairTime;
    private float autoRepairTimeMin;
    private float buoyancyShipPercent;
    private String burnNode;
    private boolean canBeDestroyed;
    private List<Float> critProb;
    private List<Float> critProbHP;
    private List damageEffects;
    private List<String> deathEffects;
    private String detonateEffect;
    private boolean detonateOnDead;
    private List<Float> hitDetonationMaxProbAtDamage;
    private List<Float> hitDetonationMinProbAtDamage;
    private String hlType;
    private float maxBuoyancy;
    private float maxHP;
    private float moduleCritCoeffCR;
    private float moduleCritCoeffXP;
    private String parentHL;
    private float regeneratedHPPart;
    private float rndPartHP;
    private List<String> splashBoxes;
    private float tbSkipProbMax;
    private float tbSkipProbMin;
    private float transmitBPDamageToParentFactor;
    private float transmitHPDamageToParentFactor;
    private float volume;
    private float volumeCoeff;
}
