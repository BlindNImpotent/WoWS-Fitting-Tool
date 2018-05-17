package WoWSSSC.model.WoWSAPI.consumables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by Aesis on 2017. 4. 20..
 */
@Data
public class Consumables
{
    private ConsumablesProfile profile;
    private String name;
    private int price_gold;
    private String image;
    private long consumable_id;
    private long price_credit;
    private String type;
    private String description;
    private String bonusDescription;

    private String upgradeSlot;

    public String getImage()
    {
        if (StringUtils.isNotEmpty(image)) {
            return image.replace("http", "https");
        }
        return image;
    }

    @JsonIgnore
    private int tempCR;

    public String getBonusDescriptionHtml()
    {
        return StringUtils.isNotEmpty(bonusDescription) ? bonusDescription.replace("\n", "<br />") : "";
    }
}
