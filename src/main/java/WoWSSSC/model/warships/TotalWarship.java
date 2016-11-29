package WoWSSSC.model.warships;

import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-29.
 */
@Data
public class TotalWarship
{
    private String nation;
    private String type;
    private String name;
    private String description;
    private long tier;
    private long ship_id;
    private long price_gold;
    private long price_credit;
    private boolean is_premium;
    private WarshipImages images;
}
