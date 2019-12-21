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

    public LinkedHashMap<String, String> getBonus(LinkedHashMap<String, Object> copy)
    {
        LinkedHashMap<String, String> bonus = new LinkedHashMap<>();

        copy.forEach((param, cVal) -> {
            if (speed.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((double) cVal) + " kts");
            } else if (param.toLowerCase().contains("boostcoeff")) {
                if ((double) cVal >= 2.0) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((double) cVal));
                } else {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonus((double) cVal)) + " %");
                }
            } else if (rate.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonus((double) cVal)) + " %");
            } else if (multiple.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), "X " + CommonUtils.replaceZero(cVal.toString()));
            } else if (coeff.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonusCoef((double) cVal)) + " %");
            } else if (noUnit.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), (double) cVal > 0 ? CommonUtils.replaceZero(cVal.toString()) : "∞");
            } else if (meter.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getDistCoefWG((double) cVal) + " km");
            } else if (rateNoSym.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " %");
            } else if (time.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " s");
            } else if (extraAngle.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((double) cVal) + " °");
            } else if (angle.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " °");
            } else if (extra.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((double) cVal));
            } else if (param.toLowerCase().equalsIgnoreCase("affectedClasses")) {
                List<String> tempList = mapper.convertValue(cVal, new TypeReference<List<String>>(){});
                if (CollectionUtils.isNotEmpty(tempList)) {
                    String affected = "";
                    for (String tl : tempList) {
                        affected = affected.concat(IDS + tl.toUpperCase() + " ");
                    }
                    bonus.put(MODIFIER + param.toUpperCase(), affected.trim());
                }
            }
        });
        return bonus;
    }
}
