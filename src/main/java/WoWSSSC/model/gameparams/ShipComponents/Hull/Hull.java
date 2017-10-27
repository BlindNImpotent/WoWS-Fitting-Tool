package WoWSSSC.model.gameparams.ShipComponents.Hull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Aesis on 2017. 5. 8..
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hull
{
    private List<List> burnNodes;
    private int burnNodesSize;
    private float burnChanceReduction = 1;
    private float burnDuration = 0;

    private List<Float> floodParams;
    private float floodChance;
    private float floodDamage;
    private float floodDuration;

    private float tonnage;

    private float visibilityCoefATBAByPlane;
    private float visibilityCoefFire;
    private float visibilityCoefFireByPlane;
    private float visibilityCoefGKInSmoke;

    private int weight;

    public void setBurnNodes(List<List> burnNodes)
    {
        this.burnNodes = burnNodes;

        burnNodesSize = burnNodes.size();

        for (List burnNode : burnNodes)
        {
            if (1 - (double) burnNode.get(1) < burnChanceReduction)
            {
                burnChanceReduction = (float) (1 - (double) burnNode.get(1));
            }

            if ((double) burnNode.get(3) > burnDuration)
            {
                burnDuration = (float) ((double) burnNode.get(3));
            }
        }
    }

    public void setFloodParams(List<Float> floodParams)
    {
        this.floodParams = floodParams;

        floodChance = floodParams.get(0);
        floodDamage = floodParams.get(1);
        floodDuration = floodParams.get(2);
    }
}
