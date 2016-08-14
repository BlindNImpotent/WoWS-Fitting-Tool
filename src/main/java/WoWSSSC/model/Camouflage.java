package WoWSSSC.model;

import lombok.Data;

/**
 * Created by Aesis on 2016-08-14.
 */
@Data
public class Camouflage
{
    private String name;
    private String code;

    public Camouflage(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

}
