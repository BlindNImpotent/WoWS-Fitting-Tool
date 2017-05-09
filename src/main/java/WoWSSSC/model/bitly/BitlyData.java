package WoWSSSC.model.bitly;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aesis on 2017-05-09.
 */
@Data
public class BitlyData
{
    private int status_code;
    private String status_txt;
    private Object data;
}
