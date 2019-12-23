package WoWSFT.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static WoWSFT.model.Constant.*;

public class CommonUtils
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Double getDistCoefWG(Number number)
    {
        return Math.round(number.doubleValue() / distCoefWG.doubleValue() * 1000.0) / 1000.0;
    }

    public static double getBonusCoef(Number number)
    {
        return (Math.round(number.doubleValue() * 1000.0) - 1000.0) / 10.0;
    }

    public static double getBonus(Number number)
    {
        return Math.round(number.doubleValue() * 1000.0) / 10.0;
    }

    public static String replaceZero(String number)
    {
        return number.endsWith(".0") ? number.substring(0, number.length() - 2) : number;
    }

    public static String getNumSym(Number number)
    {
        return (number.doubleValue() >= 0 ? "+" : "") + replaceZero(number.toString());
    }

    public static double getDecimalRounded(double num, int digits)
    {
        double rounder = Math.pow(10.0, digits);

        return Math.round(num * rounder) / rounder;
    }

    public static String getGameParamsDir() throws IOException
    {
        String directory = new ClassPathResource("/json/live/GameParams.zip").getURL().getPath().replaceFirst(SLASH, "");
        if (directory.startsWith("var") || directory.startsWith("Users")) {
            directory = SLASH + directory;
        }
        return directory;
    }

    public static Object zFetch(ZipFile zf, String index, Class<?> object) throws IOException
    {
        ZipEntry zipEntry = zf.getEntry(index + FILE_JSON);
        if (zipEntry != null) {
            return mapper.readValue(zf.getInputStream(zipEntry), object);
        }
        return null;
    }

    public static LinkedHashMap<String, String> getBonus(LinkedHashMap<String, Object> copy)
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
                        affected = affected.concat(IDS_ + tl.toUpperCase() + " ");
                    }
                    bonus.put(MODIFIER + param.toUpperCase(), affected.trim());
                }
            }
        });
        return bonus;
    }
}
