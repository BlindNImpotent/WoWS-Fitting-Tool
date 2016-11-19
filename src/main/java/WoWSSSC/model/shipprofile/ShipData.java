package WoWSSSC.model.shipprofile;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-11-18.
 */
@Data
public class ShipData
{
    private String status;
    private HashMap<String, Ship> data = new HashMap<>();
}
