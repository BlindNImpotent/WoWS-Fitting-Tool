package WoWSFT.model.gameparams.commander;

import WoWSFT.config.WoWSFT;
import WoWSFT.model.gameparams.CommonModifier;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

@Data
@WoWSFT
public class Skill extends CommonModifier
{
    private int tier;
    @JsonInclude
    private int column;
    @JsonIgnore
    private int skillType;
    @JsonIgnore
    private boolean turnOffOnRetraining;

    @JsonAlias("isEpic")
    private boolean epic;
    private String modifier;

    private LinkedHashMap<String, String> bonus = new LinkedHashMap<>();
    private String description = "";
    private String image;

    public String getImage()
    {
        return "https://cdn.wowsft.com/images/crew_commander/skills/icon_perk_" + modifier + ".png";
    }
}
