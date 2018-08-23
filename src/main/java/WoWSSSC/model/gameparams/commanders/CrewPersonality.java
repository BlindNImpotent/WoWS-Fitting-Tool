package WoWSSSC.model.gameparams.commanders;

import lombok.Data;

import java.util.List;

@Data
public class CrewPersonality
{
    private long photoId;
    private long photoPose;
    private boolean dismissable;
    private boolean hasManyNations;
    private boolean hasOverlay;
    private boolean hasRank;
    private boolean isIsPerson;
    private boolean isIsRetrainable;
    private boolean isIsUnique;
    private String peculiarity;
    private String personName;
    private Ships ships;
    private List<String> shipsOtherNation;
    private List<String> shipsOwnNation;
    private int costCR;
    private int costELXP;
    private int costGold;
    private int costXP;
}
