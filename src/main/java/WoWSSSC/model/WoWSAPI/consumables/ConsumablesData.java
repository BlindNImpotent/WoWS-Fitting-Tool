package WoWSSSC.model.WoWSAPI.consumables;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
public class ConsumablesData
{
    private String status;
    private LinkedHashMap<String, Consumables> data = new LinkedHashMap<>();
}
