package WoWSSSC.model.WoWSAPI.upgrade;

import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
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
//    private HashMap<String, HashMap> profile;
}
