package WoWSSSC.model.gameparams.Consumables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2017-04-19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumableType
{
    private String consumableType;
    private int numConsumables;
    private float reloadTime;
    private float workTime;
}
