package WoWSSSC.model.WoWSAPI.warships;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Qualson-Lee on 2016-11-17.
 */
@Data
public class WarshipImages
{
    private String small;
    private String medium;
    private String large;
    private String contour;
    
    public String getSmall()
    {
        if (StringUtils.isNotEmpty(small)) {
            return small.replace("http", "https");
        }
        return small;
    }
    
    public String getMedium()
    {
        if (StringUtils.isNotEmpty(medium)) {
            return medium.replace("http", "https");
        }
        return medium;
    }

    public String getLarge()
    {
        if (StringUtils.isNotEmpty(large)) {
            return large.replace("http", "https");
        }
        return large;
    }

    public String getContour()
    {
        if (StringUtils.isNotEmpty(contour)) {
            return contour.replace("http", "https");
        }
        return contour;
    }
}
