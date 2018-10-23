package WoWSSSC.model.gameparams.test;

import lombok.Data;

@Data
public class TorpedoVisibility
{
    private String name;
    private float maxDist;
    private double visibility;

    public TorpedoVisibility(String name, float maxDist, double visibility)
    {
        this.name = name;
        this.maxDist = Math.round(maxDist * 10) / 10f;
        this.visibility = Math.round(visibility * 10) / 10d;
    }
}
