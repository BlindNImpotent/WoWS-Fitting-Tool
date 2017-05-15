package WoWSSSC.model.WoWSAPI;

import lombok.Data;

/**
 * Created by Aesis on 2017. 5. 16..
 */
@Data
public class Meta
{
    private int count;
    private int page_total;
    private int total;
    private int limit;
    private int page;
}
