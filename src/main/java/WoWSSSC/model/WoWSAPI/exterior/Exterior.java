package WoWSSSC.model.WoWSAPI.exterior;

import lombok.Data;

/**
 * Created by Qualson-Lee on 2016-11-25.
 */
@Data
public class Exterior
{
    private String description;
    private long exterior_id;
    private String name;
    private long price_credit;
    private long price_gold;
    private String type;
    private Image image;
    private TTC_Coef ttc_coef;
}
