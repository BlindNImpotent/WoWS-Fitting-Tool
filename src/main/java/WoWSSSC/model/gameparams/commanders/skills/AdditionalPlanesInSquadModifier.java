package WoWSSSC.model.gameparams.commanders.skills;

import lombok.Data;

@Data
public class AdditionalPlanesInSquadModifier
{
    private int tier;
    private int column;
    private int skillType;
    private boolean turnOffOnRetraining;

    private int diveBomber;
    private int fighter;
    private int torpedoBomber;
}
