package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;

@Data
@WoWSFT
public class Consumable
{
    private HashMap<String, ConsumableSub> subConsumables = new HashMap<>();

    private boolean canBuy;
    private HashMap<String, Boolean> canBuyCustom;
    private float costCR;
    private float costGold;
    private boolean freeOfCharge;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;

    private String description = "";

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    @JsonAnySetter
    public void setSubs(String name, Object value)
    {
        subConsumables.put(name, mapper.convertValue(value, ConsumableSub.class));
    }
}
