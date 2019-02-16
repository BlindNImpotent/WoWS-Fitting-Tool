package WoWSFT.service;

import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
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
        for (int i = 0; i < ship.getSSkills().size(); i++) {
            if (ship.getSSkills().get(i) == 1) {
                CommonModifier modifier = mapper.convertValue(crew.getCSkills().get(i / 8).get(i % 8), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        for (int i = 0; i < ship.getSUpgrades().size(); i++) {
            if (ship.getSUpgrades().get(i) > 0) {
                CommonModifier modifier = mapper.convertValue(ship.getUpgrades().get(i).get(ship.getSUpgrades().get(i) - 1), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        ship.getConsumables().forEach(slot -> slot.forEach(c -> c.getSubConsumables().forEach((key, sub) -> sub.setBonus(getBonus(mapper.convertValue(sub, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
    }

    private void setUpgrades(Ship ship, CommonModifier modifier)
    {
        if (ship.getComponents().getArtillery().size() > 0) {
            ship.getComponents().getArtillery().forEach((c, val) -> {
                val.setGMIdealRadius(val.getGMIdealRadius() * modifier.getGmidealRadius() * (val.getBarrelDiameter() > 0.139 ? modifier.getSmallGunRangeCoefficient() : 1f));
                val.setMaxDist(val.getMaxDist() * modifier.getGmmaxDist());
                val.getTurrets().forEach(t -> {
                    t.getRotationSpeed().set(0, (t.getRotationSpeed().get(0) + (t.getBarrelDiameter() > 0.139 ? modifier.getBigGunBonus() : modifier.getSmallGunBonus())) * modifier.getGmrotationSpeed());
                    t.setShotDelay(t.getShotDelay() * modifier.getGmshotDelay() * (t.getBarrelDiameter() > 0.139 ? 1f : modifier.getSmallGunReloadCoefficient()));
                });
                val.getShells().forEach((s, ammo) -> {
                    if ("HE".equalsIgnoreCase(ammo.getAmmoType())) {
                        ammo.setBurnProb(ammo.getBurnProb() + modifier.getProbabilityBonus() + (ammo.getBulletDiametr() > 0.139 ? modifier.getChanceToSetOnFireBonusBig() : modifier.getChanceToSetOnFireBonusSmall()));
                        ammo.setAlphaPiercingHE(ammo.getAlphaPiercingHE() * (ammo.getBulletDiametr() > 0.139 ? modifier.getThresholdPenetrationCoefficientBig() : modifier.getThresholdPenetrationCoefficientSmall()));
                    }
                });

                setAuraWithBubble(val.getAuraFar(), modifier);
                setAuraWithBubble(val.getAuraMedium(), modifier);
                setAuraWithoutBubble(val.getAuraNear(), modifier);
            });
        }

        if (ship.getComponents().getTorpedoes().size() > 0) {
            ship.getComponents().getTorpedoes().forEach((c, val) -> {
                val.getLaunchers().forEach(l -> {
                    l.getRotationSpeed().set(0, l.getRotationSpeed().get(0) * modifier.getGtrotationSpeed());
                    l.setShotDelay(l.getShotDelay() * modifier.getGtshotDelay() * modifier.getLauncherCoefficient());
                });
                val.getAmmo().setMaxDist(val.getAmmo().getMaxDist() * modifier.getTorpedoRangeCoefficient());
                val.getAmmo().setSpeed(val.getAmmo().getSpeed() + modifier.getTorpedoSpeedBonus());
            });
        }

        if (ship.getComponents().getAtba().size() > 0) {
            ship.getComponents().getAtba().forEach((c, val) -> {
                val.setGSIdealRadius(val.getGSIdealRadius() * modifier.getGsidealRadius());
                val.setMaxDist(val.getMaxDist() * modifier.getGsmaxDist() * modifier.getSmallGunRangeCoefficient());

                val.getSecondaries().forEach((k, sec) -> {
                    sec.setShotDelay(sec.getShotDelay() * modifier.getGsshotDelay() * modifier.getSmallGunReloadCoefficient());
                    sec.setGSIdealRadius(sec.getGSIdealRadius() * modifier.getGsidealRadius() * (ship.getLevel() >= 7 ? modifier.getAtbaIdealRadiusHi() : modifier.getAtbaIdealRadiusLo()));
                });

                setAuraWithBubble(val.getAuraFar(), modifier);
                setAuraWithBubble(val.getAuraMedium(), modifier);
                setAuraWithoutBubble(val.getAuraNear(), modifier);
            });
        }

        if (ship.getComponents().getAirDefense().size() > 0) {
            ship.getComponents().getAirDefense().forEach((c, val) -> {
                setAuraWithBubble(val.getAuraFar(), modifier);
                setAuraWithBubble(val.getAuraMedium(), modifier);
                setAuraWithoutBubble(val.getAuraNear(), modifier);
                val.setPrioritySectorStrength(val.getPrioritySectorStrength() * modifier.getPrioritySectorStrengthCoefficient());
                val.setPrioritySectorChangeDelay(val.getPrioritySectorChangeDelay() * modifier.getSectorSwitchDelayCoefficient());
            });
        }

        if (ship.getComponents().getHull().size() > 0) {
            ship.getComponents().getHull().forEach((c, val) -> {
                val.getBurnNodes().forEach(n -> {
                    n.set(1, ((double) n.get(1)) * modifier.getBurnProb() * modifier.getProbabilityCoefficient());
                    n.set(3, ((double) n.get(3)) * modifier.getBurnTime() * modifier.getCritTimeCoefficient());
                });
                val.getFloodParams().set(0, val.getFloodParams().get(0) * modifier.getFloodProb());
                val.getFloodParams().set(2, val.getFloodParams().get(2) * modifier.getFloodTime() * modifier.getCritTimeCoefficient());
                val.setRudderTime(val.getRudderTime() * modifier.getSgrudderTime());
                val.setVisibilityFactor(val.getVisibilityFactor() * modifier.getVisibilityDistCoeff());
                val.setVisibilityFactorByPlane(val.getVisibilityFactorByPlane() * modifier.getVisibilityDistCoeff());

                if (!excludeShipSpecies.contains(ship.getTypeinfo().getSpecies())) {
                    val.setVisibilityFactor(val.getVisibilityFactor() * modifier.getCruiserCoefficient());
                    val.setVisibilityFactorByPlane(val.getVisibilityFactorByPlane() * modifier.getCruiserCoefficient());
                }

                val.setHealth(val.getHealth() + ship.getLevel() * modifier.getHealthPerLevel());
            });
        }

        if (ship.getComponents().getEngine().size() > 0) {
            ship.getComponents().getEngine().forEach((c, val) -> {
                val.setBackwardEngineForsagMaxSpeed(val.getBackwardEngineForsagMaxSpeed() * modifier.getEngineBackwardForsageMaxSpeed());
                val.setBackwardEngineForsag(val.getBackwardEngineForsag() * modifier.getEngineBackwardForsagePower());
                val.setBackwardEngineUpTime(val.getBackwardEngineUpTime() * modifier.getEngineBackwardUpTime());
                val.setForwardEngineForsagMaxSpeed(val.getForwardEngineForsagMaxSpeed() * modifier.getEngineForwardForsageMaxSpeed());
                val.setForwardEngineUpTime(val.getForwardEngineUpTime() * modifier.getEngineForwardUpTime());
                val.setForwardEngineForsag(val.getForwardEngineForsag() * modifier.getEngineForwardForsagePower());
            });
        }

        if (ship.getConsumables().size() > 0) {
            ship.getConsumables().forEach(c -> c.forEach(s -> s.getSubConsumables().forEach((k, sC) -> {
                sC.setWorkTime(sC.getWorkTime() * ("scout".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getScoutWorkTime() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("crashCrew".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getCrashCrewWorkTime() : 1f));
                sC.setReloadTime(sC.getReloadTime() * ("crashCrew".equalsIgnoreCase(sC.getConsumableType())
                        && "EmergencyTeamCooldownModifier".equalsIgnoreCase(modifier.getModifier()) ? modifier.getReloadCoefficient() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("speedBoosters".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSpeedBoosterWorkTime() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("airDefenseDisp".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getAirDefenseDispWorkTime() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("sonar".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSonarSearchWorkTime() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("rls".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getRlsSearchWorkTime() : 1f));

                sC.setWorkTime(sC.getWorkTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSmokeGeneratorWorkTime() : 1f));
                sC.setLifeTime(sC.getWorkTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSmokeGeneratorLifeTime() : 1f));
                sC.setRadius(sC.getRadius() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getRadiusCoefficient() : 1f));

                sC.setReloadTime(sC.getReloadTime() * ("AllSkillsCooldownModifier".equalsIgnoreCase(modifier.getModifier()) ? modifier.getReloadCoefficient() : 1f));

                sC.setNumConsumables(sC.getNumConsumables() + (sC.getNumConsumables() > 0 ? modifier.getAdditionalConsumables() : 0));
            })));
        }
    }
    
    private void setAuraWithBubble(Aura auraWithBubble, CommonModifier modifier)
    {
        if (auraWithBubble != null) {
            auraWithBubble.setInnerBubbleCount(auraWithBubble.getInnerBubbleCount() + modifier.getAaextraBubbles());
            auraWithBubble.setAreaDamage(auraWithBubble.getAreaDamage() * modifier.getAanearDamage() * modifier.getNearAuraDamageCoefficient());
            auraWithBubble.setBubbleDamage(auraWithBubble.getBubbleDamage() * modifier.getAaouterDamage() * modifier.getAdvancedOuterAuraDamageCoefficient());
        }
    }
    
    private void setAuraWithoutBubble(Aura auraWithoutBubble, CommonModifier modifier)
    {
        if (auraWithoutBubble != null) {
            auraWithoutBubble.setAreaDamage(auraWithoutBubble.getAreaDamage() * modifier.getAanearDamage() * modifier.getNearAuraDamageCoefficient());
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
