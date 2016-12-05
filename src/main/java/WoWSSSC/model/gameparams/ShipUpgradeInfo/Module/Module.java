package WoWSSSC.model.gameparams.ShipUpgradeInfo.Module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Module
{
    private Components components;
    private List<String> disabledAbilities;
    private List<String> nextShips;
    private HashSet<String> next = new HashSet<>();
    private String prev;
    private String ucType;
}
