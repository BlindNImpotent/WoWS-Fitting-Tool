package WoWSFT.model.gameparams.ship.component.airdefense;

import WoWSFT.config.WoWSFT;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@WoWSFT
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aura
{
    private double areaDamage;
    private double areaDamageFrequency;
    private double bubbleDamage;
    private double bubbleDuration;
    private double bubbleRadius;
    private boolean enableBarrage;
    private boolean enableCrewSelectedTargetCoeff;
    private double explosionCount;
    private List<String> guns;
    private double halfOuterBubbleZone;
    private double hitChance;
    private double innerBubbleCount;
    private List<Double> innerBubbleSpawnTimeRange;
    private double innerBubbleZone;
    @JsonAlias("isJoint")
    private boolean joint;
    private double maxBubbleActivationDelay;
    private double maxDistance;
    private double maxDistanceStartWorkGap;
    private double minBubbleActivationDelay;
    private double minDistance;
    private double outerBubbleCount;
    private List<Double> outerBubbleSpawnTimeRange;
    private double shotDelay;
    private double shotTravelTime;
    private double timeUniversalsOff;
    private double timeUniversalsOn;
    private String type;
    private double bubbleDamageModifier = 7.0;
}
