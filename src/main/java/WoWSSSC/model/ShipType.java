package WoWSSSC.model;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Qualson-Lee on 2016-11-15.
 */
@Data
public class ShipType
{
    private HashMap<String, Ship> ships = new HashMap<>();
}
