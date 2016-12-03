package WoWSSSC.model.gameparams;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
public class GameParams
{
    public HashMap<String, GameParamsValues> Ship = new HashMap<>();

}
