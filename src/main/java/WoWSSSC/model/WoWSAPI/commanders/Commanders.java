package WoWSSSC.model.WoWSAPI.commanders;

import lombok.Data;

import java.util.List;

@Data
public class Commanders
{
    private List<String> first_names;
    private List<String> last_names;
    private String nation;
    private boolean isIs_retrainable;
}
