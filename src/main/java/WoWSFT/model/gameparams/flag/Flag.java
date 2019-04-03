package WoWSFT.model.gameparams.flag;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class Flag
{
    @JsonProperty("AANearDamage")
    private float aanearDamage = 1f;
    @JsonProperty("AAOuterDamage")
    private float aaouterDamage = 1f;
    private float abilReloadTimeFactor = 1f;
    private float afterBattleRepair = 1f;
    private float burnChanceFactorBig = 1f;
    private float burnChanceFactorSmall = 1f;
    private float burnTime = 1f;
    private boolean canBuy;
    private Object canBuyCustom;
    private boolean canCharge;
    private boolean canSell;
    private float collisionDamageApply = 1f;
    private float collisionDamageNerf =1f;
    private int costCR;
    private int costGold;
    private float creditsFactor = 1f;
    private float crewExpFactor = 1f;
    private float expFactor = 1f;
    private List<String> flags;
    private float floodChanceFactor = 1f;
    private float floodTime = 1f;
    private float freeExpFactor = 1f;
    private int group;
    @JsonProperty("GSIdealRadius")
    private float gsidealRadius = 1f;
    @JsonProperty("GSMaxDist")
    private float gsmaxDist = 1f;
    @JsonProperty("GSShotDelay")
    private float gsshotDelay = 1f;
    private int healthPerLevel;
    private boolean hidden;
    private Object hiddenCustom;
    private long id;
    private String index;
    private String name;
    @JsonProperty("PMDetonationProb")
    private float pmdetonationProb = 1f;
    private float regenerationHPSpeed = 1f;
    private float shootShift = 1f;
    private int sortOrder;
    private float speedCoef = 1f;
    private TypeInfo typeinfo;
    private float visibilityFactor = 1f;
    private float visibilityFactorByPlane = 1f;
    private float visibilityFactorInSmoke = 1f;

    private String identifier;
    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";

    public String getImage()
    {
        return "https://cdn.wowsft.com/signal_flags/" + name + ".png";
    }
}
