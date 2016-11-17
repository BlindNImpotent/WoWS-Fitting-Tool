package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artillery
{
    private float ammo_critical_damage_chance_coef;
    private float ammo_detonation_chance_coef;
    private float ammo_repair_time_coef;
    private float critical_damage_chance_coef;
    private float reload_time_coef;
    private float repair_time_coef;
    private float rotation_time_coef;
}
