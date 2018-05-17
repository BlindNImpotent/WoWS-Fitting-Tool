package WoWSSSC.model.WoWSAPI.skills;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Qualson-Lee on 2016-11-24.
 */
@Data
public class CrewSkills
{
    private String name;
    private int type_id;
    private List<CrewPerks> perks;
    private int tier;
    private String icon;
    private String description;

    public String getIcon()
    {
        if (StringUtils.isNotEmpty(icon)) {
            return icon.replace("http", "https");
        }
        return icon;
    }

    public void setTier(int tier)
    {
        this.tier = tier;
    }

    public void setTier(String tierString)
    {
        if (StringUtils.isNotEmpty(tierString) && Character.isDigit(tierString.charAt(0))) {
            this.tier = Integer.valueOf(tierString);
        }
    }

    public void setType_id(int type_id)
    {
        this.type_id = type_id;
    }

    public void setType_id(String typeIdString)
    {
        if (StringUtils.isNotEmpty(typeIdString) && Character.isDigit(typeIdString.charAt(0))) {
            this.tier = Integer.valueOf(typeIdString);
        }
    }
}
