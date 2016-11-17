package WoWSSSC.model.upgrade.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ATBA
{
    private float accuracy_coef;
    private float distance_coef;
    private float health_coef;
    private float reload_time_coef;
}
