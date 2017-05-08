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
    private double burnChanceReduction = 1;
    private double burnDuration = 0;

    private List<Double> floodParams;
    private double floodChance;
    private double floodDamage;
    private double floodDuration;

    private double tonnage;

    private double visibilityCoefATBAByPlane;
    private double visibilityCoefFire;
    private double visibilityCoefFireByPlane;

    public void setBurnNodes(List<List> burnNodes)
    {
        this.burnNodes = burnNodes;

        burnNodesSize = burnNodes.size();

        for (List burnNode : burnNodes)
        {
            if (1 - (double) burnNode.get(1) < burnChanceReduction)
            {
                burnChanceReduction = 1 - (double) burnNode.get(1);
            }

            if ((double) burnNode.get(3) > burnDuration)
            {
                burnDuration = (double) burnNode.get(3);
            }
        }
    }

    public void setFloodParams(List<Double> floodParams)
    {
        this.floodParams = floodParams;

        floodChance = floodParams.get(0);
        floodDamage = floodParams.get(1);
        floodDuration = floodParams.get(2);
    }
}
