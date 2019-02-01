package WoWSFT.model.gameparams.ship.component.airdefense;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aura
{
    private float areaDamage;
    private float areaDamageFrequency;
    private float bubbleDamage;
    private float bubbleDuration;
    private float bubbleRadius;
    private boolean enableBarrage;
    private boolean enableCrewSelectedTargetCoeff;
    private float explosionCount;
    private List<String> guns;
    private float halfOuterBubbleZone;
    private float hitChance;
    private float innerBubbleCount;
    private List<Float> innerBubbleSpawnTimeRange;
    private float innerBubbleZone;
    private boolean isJoint;
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
    private String type;
}
