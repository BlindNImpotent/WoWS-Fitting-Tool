package WoWSSSC.model.WoWSAPI.consumables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
public class Consumables
{
    private ConsumablesProfile profile;
    @JsonIgnore
    private HashMap<String, HashMap> profileHashMap;
    private String name;
    private int price_gold;
    private String image;
    private long consumable_id;
    private long price_credit;
    private String type;
    private String description;

    private String upgradeSlot;

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public void setProfile(ConsumablesProfile profile)
    {
        this.profile = profile;

        profileHashMap = mapper.convertValue(profile, HashMap.class);
    }
}
