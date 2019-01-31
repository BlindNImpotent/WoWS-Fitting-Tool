package WoWSSSC.model.gameparams.commanders;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Skills
{
//    private AIGunsEfficiencyModifier AIGunsEfficiencyModifier;
//    private AIGunsRangeModifier AIGunsRangeModifier;
//    private AdditionalPlanesInSquadModifier AdditionalPlanesInSquadModifier;
//    private AdditionalSmokescreensModifier AdditionalSmokescreensModifier;
//    private AimingFightersPointModifier AimingFightersPointModifier;
//    private AllSkillsCooldownModifier AllSkillsCooldownModifier;
//    private ArtilleryAlertModifier ArtilleryAlertModifier;
//    private AutoRepairModifier AutoRepairModifier;
//    private CentralATBAModifier CentralATBAModifier;
//    private CentralAirDefenceModifier CentralAirDefenceModifier;
//    private EmergencyTeamCooldownModifier EmergencyTeamCooldownModifier;
//    private FighterEfficiencyModifier FighterEfficiencyModifier;
//    private FireProbabilityModifier FireProbabilityModifier;
//    private FireResistanceModifier FireResistanceModifier;
//    private IntuitionModifier IntuitionModifier;
//    private LandmineExploderModifier LandmineExploderModifier;
//    private LastChanceModifier LastChanceModifier;
//    private LastEffortModifier LastEffortModifier;
//    private MainGunsRotationModifier MainGunsRotationModifier;
//    private MeticulousPreventionModifier MeticulousPreventionModifier;
//    private NearEnemyIntuitionModifier NearEnemyIntuitionModifier;
//    private OutOfAttackModifier OutOfAttackModifier;
//    private ParkingDeckModifier ParkingDeckModifier;
//    private PlanePreparingModifier PlanePreparingModifier;
//    private PreparingOnboardShootersModifier PreparingOnboardShootersModifier;
//    private PriorityTargetModifier PriorityTargetModifier;
//    private SuperintendentModifier SuperintendentModifier;
//    private SurvivalModifier SurvivalModifier;
//    private TorpedoAcceleratorModifier TorpedoAcceleratorModifier;
//    private TorpedoAlertnessModifier TorpedoAlertnessModifier;
//    private TorpedoReloadModifier TorpedoReloadModifier;
//    private VisibilityModifier VisibilityModifier;

    private LinkedHashMap<String, SkillModifier> modifiers = new LinkedHashMap<>();

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setTypes(String name, Object value)
    {
        SkillModifier modifier = mapper.convertValue(value, SkillModifier.class);
        String temp = String.valueOf(modifier.getTier()) + "_" + String.valueOf(modifier.getColumn());

        modifiers.put(temp, modifier);
    }
}
