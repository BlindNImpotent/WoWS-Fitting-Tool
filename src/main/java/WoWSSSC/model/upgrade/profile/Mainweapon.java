package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mainweapon
{
    private float artillery_damage_prob;
    private float artillery_max_hp;
    private float artillery_repair_time;
    private float tpd_damage_prob;
    private float tpd_max_hp;
    private float tpd_repair_time;
}
