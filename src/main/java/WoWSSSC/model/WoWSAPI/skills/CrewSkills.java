package WoWSSSC.model.WoWSAPI.skills;

import lombok.Data;

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
}
