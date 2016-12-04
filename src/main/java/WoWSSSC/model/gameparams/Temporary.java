package WoWSSSC.model.gameparams;

import WoWSSSC.model.gameparams.Values.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temporary
{
    private long id;
    private TypeInfo typeinfo;
}
