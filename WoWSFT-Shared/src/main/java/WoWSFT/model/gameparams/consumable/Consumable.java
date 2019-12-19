package WoWSFT.model.gameparams.consumable;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@WoWSFT
public class Consumable
{
    private LinkedHashMap<String, ConsumableSub> subConsumables = new LinkedHashMap<>();

    private boolean canBuy;
    private LinkedHashMap<String, Boolean> canBuyCustom;
    private double costCR;
    private double costGold;
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
