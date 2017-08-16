package WoWSSSC.model.gameparams.test;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TorpedoVisibility
{
    private int tier;
    private String name;
    private double visibility;

    public TorpedoVisibility(int tier, String name, double visibility)
    {
        this.tier = tier;
        this.name = name;
        this.visibility = visibility;
    }
}
