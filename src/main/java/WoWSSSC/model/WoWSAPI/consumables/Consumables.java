package WoWSSSC.model.WoWSAPI.consumables;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
public class Consumables
{
    private ConsumablesProfile profile;
//    private HashMap<String, HashMap> profile;
    private String name;
    private int price_gold;
    private String image;
    private long consumable_id;
    private long price_credit;
    private String type;
    private String description;
}
