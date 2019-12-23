package WoWSFT.service;

import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

@Service
public class ParamService
{
    private ObjectMapper mapper = new ObjectMapper();

    public void setBonusParams(String key, LinkedHashMap<String, Object> tempCopy, LinkedHashMap<String, String> bonus)
    {
        tempCopy.forEach((param, cVal) -> {
            if (cVal instanceof Double && ((double) cVal != 0)) {
                if (excludeModernization.stream().anyMatch(param.toLowerCase()::contains)) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((double) cVal));
                } else {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonusCoef((double) cVal)) + " %");
                }
            }
        });
    }
}
