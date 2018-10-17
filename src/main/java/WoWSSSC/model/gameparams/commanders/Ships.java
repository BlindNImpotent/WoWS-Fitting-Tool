package WoWSSSC.model.gameparams.commanders;

import lombok.Data;

import java.util.List;

@Data
public class Ships
{
    private List<String> nation;
    private List<String> peculiarity;
    private List<String> ships;
    private List<String> groups;
}
