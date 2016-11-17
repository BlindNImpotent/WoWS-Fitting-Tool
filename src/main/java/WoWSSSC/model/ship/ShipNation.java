package WoWSSSC.model.ship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipNation
{
    private LinkedHashMap<String, LinkedHashMap> shipNationsHashMap = new LinkedHashMap<>();
}
