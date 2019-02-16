package WoWSFT.service;

import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.commander.Skill;
import WoWSFT.model.gameparams.modernization.Modernization;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

import static WoWSFT.model.Constant.*;

@Service
public class ParamService
{
    private ObjectMapper mapper = new ObjectMapper();

    public void setParameters(Ship ship, Commander crew)
    {
        for (int i = 0; i < ship.getSUpgrades().size(); i++) {
            if (ship.getSUpgrades().get(i) > 0) {
                setUpgrades(ship, ship.getUpgrades().get(i).get(ship.getSUpgrades().get(i) - 1), TYPE_UPGRADE);
            }
        }

        for (int i = 0; i < ship.getSSkills().size(); i++) {
            if (ship.getSSkills().get(i) == 1) {
                setUpgrades(ship, crew.getCSkills().get(i / 8).get(i % 8), TYPE_SKILL);
            }
        }

        ship.getConsumables().forEach(slot -> slot.forEach(c -> c.getSubConsumables().forEach((key, sub) -> sub.setBonus(getBonus(mapper.convertValue(sub, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
    }

    private void setUpgrades(Ship ship, Object modifier, String type)
    {
        Modernization upgrade;
        if (TYPE_UPGRADE.equalsIgnoreCase(type)) {
            upgrade = (Modernization) modifier;
        } else {
            upgrade = new Modernization();
        }

        Skill skill;
        if (TYPE_SKILL.equalsIgnoreCase(type)) {
            skill = (Skill) modifier;
        } else {
            skill = new Skill();
        }

        if (ship.getComponents().getArtillery().size() > 0) {
            ship.getComponents().getArtillery().forEach((c, val) -> {
                val.setGMIdealRadius(val.getGMIdealRadius() * upgrade.getGmidealRadius());
                val.setMaxDist(val.getMaxDist() * upgrade.getGmmaxDist());
                val.getTurrets().forEach(t -> {
                    t.getRotationSpeed().set(0, t.getRotationSpeed().get(0) * upgrade.getGmrotationSpeed());
                    t.setShotDelay(t.getShotDelay() * upgrade.getGmshotDelay());
                });

                if (val.getAuraFar() != null) {
                    val.getAuraFar().setInnerBubbleCount(val.getAuraFar().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraFar().setAreaDamage(val.getAuraFar().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraFar().setBubbleDamage(val.getAuraFar().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraMedium() != null) {
                    val.getAuraMedium().setInnerBubbleCount(val.getAuraMedium().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraMedium().setAreaDamage(val.getAuraMedium().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraMedium().setBubbleDamage(val.getAuraMedium().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraNear() != null) {
                    val.getAuraNear().setAreaDamage(val.getAuraNear().getAreaDamage() * upgrade.getAanearDamage());
                }
            });
        }

        if (ship.getComponents().getTorpedoes().size() > 0) {
            ship.getComponents().getTorpedoes().forEach((c, val) -> {
                val.getLaunchers().forEach(l -> {
                    l.getRotationSpeed().set(0, l.getRotationSpeed().get(0) * upgrade.getGtrotationSpeed());
                    l.setShotDelay(l.getShotDelay() * upgrade.getGtshotDelay());
                });
            });
        }

        if (ship.getComponents().getAtba().size() > 0) {
            ship.getComponents().getAtba().forEach((c, val) -> {
                val.setGSIdealRadius(val.getGSIdealRadius() * upgrade.getGsidealRadius());
                val.setMaxDist(val.getMaxDist() * upgrade.getGsmaxDist());

                val.getSecondaries().forEach((k, sec) -> {
                    sec.setShotDelay(sec.getShotDelay() * upgrade.getGsshotDelay());
                    sec.setGSIdealRadius(sec.getGSIdealRadius() * upgrade.getGsidealRadius());
                });

                if (val.getAuraFar() != null) {
                    val.getAuraFar().setInnerBubbleCount(val.getAuraFar().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraFar().setAreaDamage(val.getAuraFar().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraFar().setBubbleDamage(val.getAuraFar().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraMedium() != null) {
                    val.getAuraMedium().setInnerBubbleCount(val.getAuraMedium().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraMedium().setAreaDamage(val.getAuraMedium().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraMedium().setBubbleDamage(val.getAuraMedium().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraNear() != null) {
                    val.getAuraNear().setAreaDamage(val.getAuraNear().getAreaDamage() * upgrade.getAanearDamage());
                }
            });
        }

        if (ship.getComponents().getAirDefense().size() > 0) {
            ship.getComponents().getAirDefense().forEach((c, val) -> {
                if (val.getAuraFar() != null) {
                    val.getAuraFar().setInnerBubbleCount(val.getAuraFar().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraFar().setAreaDamage(val.getAuraFar().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraFar().setBubbleDamage(val.getAuraFar().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraMedium() != null) {
                    val.getAuraMedium().setInnerBubbleCount(val.getAuraMedium().getInnerBubbleCount() + upgrade.getAaextraBubbles());
                    val.getAuraMedium().setAreaDamage(val.getAuraMedium().getAreaDamage() * upgrade.getAanearDamage());
                    val.getAuraMedium().setBubbleDamage(val.getAuraMedium().getBubbleDamage() * upgrade.getAaouterDamage());
                }
                if (val.getAuraNear() != null) {
                    val.getAuraNear().setAreaDamage(val.getAuraNear().getAreaDamage() * upgrade.getAanearDamage());
                }
            });
        }

        if (ship.getComponents().getHull().size() > 0) {
            ship.getComponents().getHull().forEach((c, val) -> {
                val.getBurnNodes().forEach(n -> {
                    n.set(1, ((double) n.get(1)) * upgrade.getBurnProb());
                    n.set(3, ((double) n.get(3)) * upgrade.getBurnTime());
                });
                val.getFloodParams().set(0, val.getFloodParams().get(0) * upgrade.getFloodProb());
                val.getFloodParams().set(2, val.getFloodParams().get(2) * upgrade.getFloodTime());
                val.setRudderTime(val.getRudderTime() * upgrade.getSgrudderTime());
                val.setVisibilityFactor(val.getVisibilityFactor() * upgrade.getVisibilityDistCoeff());
                val.setVisibilityFactorByPlane(val.getVisibilityFactorByPlane() * upgrade.getVisibilityDistCoeff());
            });
        }

        if (ship.getComponents().getEngine().size() > 0) {
            ship.getComponents().getEngine().forEach((c, val) -> {
                val.setBackwardEngineForsagMaxSpeed(val.getBackwardEngineForsagMaxSpeed() * upgrade.getEngineBackwardForsageMaxSpeed());
                val.setBackwardEngineForsag(val.getBackwardEngineForsag() * upgrade.getEngineBackwardForsagePower());
                val.setBackwardEngineUpTime(val.getBackwardEngineUpTime() * upgrade.getEngineBackwardUpTime());
                val.setForwardEngineForsagMaxSpeed(val.getForwardEngineForsagMaxSpeed() * upgrade.getEngineForwardForsageMaxSpeed());
                val.setForwardEngineUpTime(val.getForwardEngineUpTime() * upgrade.getEngineForwardUpTime());
                val.setForwardEngineForsag(val.getForwardEngineForsag() * upgrade.getEngineForwardForsagePower());
            });
        }

        if (ship.getConsumables().size() > 0) {
            ship.getConsumables().forEach(c -> c.forEach(s -> s.getSubConsumables().forEach((k, sC) -> {
                sC.setWorkTime(sC.getWorkTime() * ("scout".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getScoutWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("crashCrew".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getCrashCrewWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("speedBoosters".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getSpeedBoosterWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("airDefenseDisp".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getAirDefenseDispWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("sonar".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getSonarSearchWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("rls".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getRlsSearchWorkTime() : 1f));
                sC.setWorkTime(sC.getWorkTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getSmokeGeneratorWorkTime() : 1f));
                sC.setLifeTime(sC.getWorkTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? upgrade.getSmokeGeneratorLifeTime() : 1f));
            })));
        }
    }

    protected LinkedHashMap<String, String> getBonus(LinkedHashMap<String, Object> copy)
    {
        LinkedHashMap<String, String> bonus = new LinkedHashMap<>();

        copy.forEach((param, cVal) -> {
            if (speed.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal) + " kts");
            } else if (rate.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonus((float) cVal)) + " %");
            } else if (multiple.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), "X " + CommonUtils.replaceZero(cVal.toString()));
            } else if (coeff.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonusCoef((float) cVal)) + " %");
            } else if (noUnit.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), (float) cVal > 0 ? CommonUtils.replaceZero(cVal.toString()) : "∞");
            } else if (meter.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getDistCoefWG((float) cVal) + " km");
            } else if (rateNoSym.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " %");
            } else if (time.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " s");
            } else if (extraAngle.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal) + " °");
            } else if (angle.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.replaceZero(cVal.toString()) + " °");
            } else if (extra.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal));
            }
        });
        return bonus;
    }
}
