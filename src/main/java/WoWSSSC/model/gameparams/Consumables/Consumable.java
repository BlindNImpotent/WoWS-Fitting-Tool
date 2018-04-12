package WoWSSSC.model.gameparams.Consumables;

import WoWSSSC.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2017-04-19.
 */
@Data
public class Consumable
{
    private HashMap<String, ConsumableType> types = new HashMap<>();
    private boolean canBuy;
    private int costCR;
    private int costGold;
    private boolean freeOfCharge;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private String globalName;

    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setTypes(String name, Object value)
    {
        types.put(name, mapper.convertValue(value, ConsumableType.class));
    }
}
