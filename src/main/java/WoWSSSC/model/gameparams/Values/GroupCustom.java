package WoWSSSC.model.gameparams.Values;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Aesis on 2016-12-03.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupCustom
{
    @JsonProperty(value = "ASIA")
    private String ASIA;
    @JsonProperty(value = "EU")
    private String EU;
    @JsonProperty(value = "NA")
    private String NA;
    @JsonProperty(value = "RU")
    private String RU;
    @JsonProperty(value = "CN")
    private String CN;
    @JsonProperty(value = "PT")
    private String PT;
}
