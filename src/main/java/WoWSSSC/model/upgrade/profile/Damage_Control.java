package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Damage_Control
{
    private float burning_time_coef;
    private float fire_starting_chance_coef;
    private float flood_starting_chance_coef;
    private float flooding_time_coef;
}
