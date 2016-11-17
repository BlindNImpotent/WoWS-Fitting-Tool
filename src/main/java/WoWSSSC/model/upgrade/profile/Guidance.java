package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guidance
{
    private float artillery_rotation_speed;
    private float artillery_shoot_accuracy;
    private float atba_max_dist;
    private float atba_rotation_speed;
    private float atba_shoot_accuracy;
}
