package WoWSFT.model.gameparams.modernization;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.TypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@WoWSFT
public class Modernization extends CommonModifier
{
    private int costCR;
    private int costGold;
    private List<String> excludes = new ArrayList<>();
    private List<String> group = new ArrayList<>();
    private long id;
    private String index;
    private String name;
    private List<String> nation = new ArrayList<>();
    private List<Integer> shiplevel = new ArrayList<>();
    private List<String> ships = new ArrayList<>();
    private List<String> shiptype = new ArrayList<>();
    @JsonInclude
    private int slot;
    private int type;
    private TypeInfo typeinfo;

    private String fullName;
    private String image;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";

    public String getImage()
    {
        return "https://cdn.wowsft.com/images/modernization_icons/icon_modernization_" + name + ".png";
    }
}
