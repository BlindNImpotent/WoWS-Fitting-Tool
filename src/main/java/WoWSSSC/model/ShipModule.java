package WoWSSSC.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipModule
{
    private HashMap<String, Long> engine = new HashMap<>();

}
