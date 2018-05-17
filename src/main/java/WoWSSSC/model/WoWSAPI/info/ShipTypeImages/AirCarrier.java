package WoWSSSC.model.WoWSAPI.info.ShipTypeImages;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Aesis on 2017. 4. 22..
 */
@Data
public class AirCarrier
{
    private String image;
    private String image_elite;
    private String image_premium;

    public String getImage()
    {
        if (StringUtils.isNotEmpty(image)) {
            return image.replace("http", "https");
        }
        return image;
    }

    public String getImage_elite()
    {
        if (StringUtils.isNotEmpty(image_elite)) {
            return image_elite.replace("http", "https");
        }
        return image_elite;
    }

    public String getImage_premium()
    {
        if (StringUtils.isNotEmpty(image_premium)) {
            return image_premium.replace("http", "https");
        }
        return image_premium;
    }
}
