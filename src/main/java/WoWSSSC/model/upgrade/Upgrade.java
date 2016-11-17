package WoWSSSC.model.upgrade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Upgrade
{
    private String name;
    private String description;
    private String image;
    private String tag;
    private String type;
    private long price_credit;
    private long upgrade_id;
    private UpgradeProfile profile;
}
