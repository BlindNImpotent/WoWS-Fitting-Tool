package WoWSSSC.model.gameparams.ShipComponents.AA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aura
{
    private List<String> guns;
    private float selectedTargetCoeff;
    private String auraType;

    private float areaDamage;
    private float areaDamageFrequency;
    private float bubbleDamage;
    private float bubbleDuration;
    private float bubbleRadius;
    private float explosionCount;
    private float halfOuterBubbleZone;
    private float hitChance;
    private float innerBubbleCount;
    private List<Float> innerBubbleSpawnTimeRange;
    private float innerBubbleZone;
    private float maxBubbleActivationDelay;
    private float maxDistance;
    private float maxDistanceStartWorkGap;
    private float minBubbleActivationDelay;
    private float minDistance;
    private float outerBubbleCount;
    private List<Float> outerBubbleSpawnTimeRange;
    private float shotDelay;
    private float shotTravelTime;
    private float timeUniversalsOff;
    private float timeUniversalsOn;
    private float extraBubbleCount;

    public String getAuraType()
    {
        if (StringUtils.isNotEmpty(auraType)) {
            return auraType.replace("Aura", "");
        }
        return "Far";
    }

    public float getBubbleDps()
    {
        return bubbleDamage * 7f;
    }
}
