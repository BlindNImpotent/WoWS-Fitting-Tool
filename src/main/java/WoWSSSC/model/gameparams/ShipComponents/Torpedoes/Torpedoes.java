package WoWSSSC.model.gameparams.ShipComponents.Torpedoes;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Aesis on 2017-05-15.
 */
@Data
public class Torpedoes
{
    private LinkedHashMap<String, Launcher> launchers = new LinkedHashMap<>();

}
