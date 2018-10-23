package WoWSSSC.model.gameparams.test;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TorpedoShip
{
    private int tier;
    private String name;
    private String nation;
    private String type;
    private String defaultType;
    private List<TorpedoVisibility> torpedoes = new ArrayList<>();

    public TorpedoShip(int tier, String name, String nation, String type, String defaultType)
    {
        this.tier = tier;
        this.name = name;
        this.nation = nation;
        this.type = type;
        this.defaultType = defaultType;
    }
}
