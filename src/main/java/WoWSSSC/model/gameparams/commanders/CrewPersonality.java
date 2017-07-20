package WoWSSSC.model.gameparams.commanders;

import lombok.Data;

import java.util.List;

@Data
public class CrewPersonality
{
    private boolean isIsPerson;
    private boolean isIsRetrainable;
    private String peculiarity;
    private String personName;
    private List<String> shipsOtherNation;
    private List<String> shipsOwnNation;
}
