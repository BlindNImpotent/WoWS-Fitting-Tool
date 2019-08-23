package WoWSFT.model.gameparams.flag;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@WoWSFT
public class Flag extends CommonModifier
{
    private boolean canBuy;
    private Object canBuyCustom;
    private List<String> flags;
    private int group;
    private boolean hidden;
    private Object hiddenCustom;
    private long id;
    private String index;
    private String name;
    private TypeInfo typeinfo;
    private int sortOrder;
    private String identifier;
    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";

    public String getImage()
    {
        return "https://cdn.wowsft.com/images/signal_flags/" + name + ".png";
    }
}
