package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Engine
{
    private float critical_damage_chance_coef;
    private float max_backward_power_coef;
    private float max_forward_power_coef;
    private float repair_time_coef;
}
