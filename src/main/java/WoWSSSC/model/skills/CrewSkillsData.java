package WoWSSSC.model.skills;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Qualson-Lee on 2016-11-24.
 */
@Data
public class CrewSkillsData
{
    private String status;
    private LinkedHashMap<String, CrewSkills> data = new LinkedHashMap<>();
}
