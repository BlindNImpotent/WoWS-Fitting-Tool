package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planes
{
    private float bomber_health_coef;
    private float efficiency_coef;
    private float fighter_health_coef;
}
