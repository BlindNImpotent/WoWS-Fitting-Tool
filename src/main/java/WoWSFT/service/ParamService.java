package WoWSFT.service;

import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import WoWSFT.model.gameparams.ship.component.planes.Plane;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static WoWSFT.model.Constant.*;

@Service
public class ParamService
{
    private ObjectMapper mapper = new ObjectMapper();

    public void setAA(Ship ship)
    {
        List<Aura> auraFar = new ArrayList<>();
        List<Aura> auraMedium = new ArrayList<>();
        List<Aura> auraNear = new ArrayList<>();

        ship.getComponents().getAirDefense().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(airDefense))) {
                auraFar.addAll(val.getAuraFar());
                auraMedium.addAll(val.getAuraMedium());
                auraNear.addAll(val.getAuraNear());
            }
        });

        ship.getComponents().getAtba().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(atba))) {
                auraFar.addAll(val.getAuraFar());
                auraMedium.addAll(val.getAuraMedium());
                auraNear.addAll(val.getAuraNear());
            }
        });

        ship.getComponents().getArtillery().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(artillery))) {
                auraFar.addAll(val.getAuraFar());
                auraMedium.addAll(val.getAuraMedium());
                auraNear.addAll(val.getAuraNear());
            }
        });

        ship.setAuraFar(auraFar);
        ship.setAuraMedium(auraMedium);
        ship.setAuraNear(auraNear);
    }

    public void setParameters(Ship ship)
    {
        for (int i = 0; i < ship.getSelectSkills().size(); i++) {
            if (ship.getSelectSkills().get(i) == 1) {
                CommonModifier modifier = mapper.convertValue(ship.getCommander().getCSkills().get(i / 8).get(i % 8), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        for (int i = 0; i < ship.getSelectUpgrades().size(); i++) {
            if (ship.getSelectUpgrades().get(i) > 0) {
                CommonModifier modifier = mapper.convertValue(ship.getUpgrades().get(i).get(ship.getSelectUpgrades().get(i) - 1), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        ship.getConsumables().forEach(slot -> slot.forEach(c -> c.getSubConsumables().forEach((key, sub) -> sub.setBonus(getBonus(mapper.convertValue(sub, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getFighter().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getDiveBomber().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getTorpedoBomber().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
    }

    private void setUpgrades(Ship ship, CommonModifier modifier)
    {
        ship.getComponents().getArtillery().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(artillery))) {
                val.setGMIdealRadius(val.getGMIdealRadius() * modifier.getGmidealRadius());
                val.setMaxDist(val.getMaxDist() * modifier.getGmmaxDist() * (val.getBarrelDiameter() > smallGun ? oneCoeff : modifier.getSmallGunRangeCoefficient()));
                val.getTurrets().forEach(t -> {
                    t.getRotationSpeed().set(0, (t.getRotationSpeed().get(0) + (t.getBarrelDiameter() > smallGun ? modifier.getBigGunBonus() : modifier.getSmallGunBonus())) * modifier.getGmrotationSpeed());
                    t.setShotDelay(t.getShotDelay() * modifier.getGmshotDelay() * (t.getBarrelDiameter() > smallGun ? oneCoeff : modifier.getSmallGunReloadCoefficient())
                            * (oneCoeff - (ship.getAdrenaline() / modifier.getHpStep() * modifier.getTimeStep())));
                });
                val.getShells().forEach((s, ammo) -> {
                    if ("HE".equalsIgnoreCase(ammo.getAmmoType())) {
                        ammo.setBurnProb(ammo.getBurnProb() + modifier.getProbabilityBonus() + (ammo.getBulletDiametr() > smallGun ? modifier.getChanceToSetOnFireBonusBig() : modifier.getChanceToSetOnFireBonusSmall()));
                        ammo.setAlphaPiercingHE(ammo.getAlphaPiercingHE() * (ammo.getBulletDiametr() > smallGun ? modifier.getThresholdPenetrationCoefficientBig() : modifier.getThresholdPenetrationCoefficientSmall()));
                    }
                });
            }
        });

        ship.getComponents().getTorpedoes().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(torpedoes))) {
                val.getLaunchers().forEach(l -> {
                    l.getRotationSpeed().set(0, l.getRotationSpeed().get(0) * modifier.getGtrotationSpeed());
                    l.setShotDelay(l.getShotDelay() * modifier.getGtshotDelay() * modifier.getLauncherCoefficient() * (oneCoeff - (ship.getAdrenaline() / modifier.getHpStep() * modifier.getTimeStep())));
                });
                val.getAmmo().setMaxDist(val.getAmmo().getMaxDist() * modifier.getTorpedoRangeCoefficient());
                val.getAmmo().setSpeed(val.getAmmo().getSpeed() + modifier.getTorpedoSpeedBonus());
            }
        });

        ship.getComponents().getFighter().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(fighter))) {
                setPlanes(ship, val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesFightersHealth() * modifier.getAirplanesHealth()));
                if ("HE".equalsIgnoreCase(val.getRocket().getAmmoType())) {
                    val.getRocket().setBurnProb(val.getRocket().getBurnProb() + modifier.getRocketProbabilityBonus());
                }
            }
        });

        ship.getComponents().getDiveBomber().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(diveBomber))) {
                setPlanes(ship, val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesDiveBombersHealth() * modifier.getAirplanesHealth()));
                if ("HE".equalsIgnoreCase(val.getBomb().getAmmoType())) {
                    val.getBomb().setBurnProb(val.getBomb().getBurnProb() + modifier.getBombProbabilityBonus());
                }
            }
        });

        ship.getComponents().getTorpedoBomber().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(torpedoBomber))) {
                setPlanes(ship, val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesTorpedoBombersHealth() * modifier.getAirplanesHealth()));
                val.getTorpedo().setMaxDist(val.getTorpedo().getMaxDist() * modifier.getPlaneTorpedoRangeCoefficient());
                val.getTorpedo().setSpeed(val.getTorpedo().getSpeed() + modifier.getPlaneTorpedoSpeedBonus());
            }
        });

        ship.getComponents().getAirArmament().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(airArmament))) {
                val.setDeckPlaceCount(val.getDeckPlaceCount() + modifier.getAirplanesExtraHangarSize());
            }
        });

        ship.getComponents().getAtba().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(atba))) {
                val.setGSIdealRadius(val.getGSIdealRadius() * modifier.getGsidealRadius());
                val.setMaxDist(val.getMaxDist() * modifier.getGsmaxDist() * modifier.getSmallGunRangeCoefficient());

                val.getSecondaries().forEach((k, sec) -> {
                    sec.setShotDelay(sec.getShotDelay() * modifier.getGsshotDelay() * modifier.getSmallGunReloadCoefficient() * (oneCoeff - (ship.getAdrenaline() / modifier.getHpStep() * modifier.getTimeStep())));
                    sec.setGSIdealRadius(sec.getGSIdealRadius() * modifier.getGsidealRadius() * (ship.getLevel() >= 7 ? modifier.getAtbaIdealRadiusHi() : modifier.getAtbaIdealRadiusLo()));
                    if ("HE".equalsIgnoreCase(sec.getAmmoType())) {
                        sec.setBurnProb(sec.getBurnProb() + modifier.getProbabilityBonus() + modifier.getChanceToSetOnFireBonusSmall());
                        sec.setAlphaPiercingHE(sec.getAlphaPiercingHE() * modifier.getThresholdPenetrationCoefficientSmall());
                    }
                });
            }
        });

        ship.getComponents().getAirDefense().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(airDefense))) {
                val.setPrioritySectorStrength(val.getPrioritySectorStrength() * modifier.getPrioritySectorStrengthCoefficient());
                val.setPrioritySectorChangeDelay(val.getPrioritySectorChangeDelay() * modifier.getSectorSwitchDelayCoefficient());
                val.setPrioritySectorEnableDelay(val.getPrioritySectorEnableDelay() * modifier.getSectorSwitchDelayCoefficient());
            }
        });

        ship.getComponents().getHull().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(hull))) {
                val.getBurnNodes().forEach(n -> {
                    n.set(1, ((double) n.get(1)) * modifier.getBurnProb() * modifier.getProbabilityCoefficient());
                    n.set(3, ((double) n.get(3)) * modifier.getBurnTime() * modifier.getCritTimeCoefficient());
                });
                val.setBurnSizeSkill(modifier.getProbabilityCoefficient() != oneCoeff ? 3 : val.getBurnSizeSkill());
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
            }
        });

        ship.getComponents().getEngine().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(engine))) {
                val.setBackwardEngineForsagMaxSpeed(val.getBackwardEngineForsagMaxSpeed() * modifier.getEngineBackwardForsageMaxSpeed());
                val.setBackwardEngineForsag(val.getBackwardEngineForsag() * modifier.getEngineBackwardForsagePower());
                val.setBackwardEngineUpTime(val.getBackwardEngineUpTime() * modifier.getEngineBackwardUpTime());
                val.setForwardEngineForsagMaxSpeed(val.getForwardEngineForsagMaxSpeed() * modifier.getEngineForwardForsageMaxSpeed());
                val.setForwardEngineUpTime(val.getForwardEngineUpTime() * modifier.getEngineForwardUpTime());
                val.setForwardEngineForsag(val.getForwardEngineForsag() * modifier.getEngineForwardForsagePower());
            }
        });

        ship.getAuraFar().forEach(aura -> setAura(aura, modifier));
        ship.getAuraMedium().forEach(aura -> setAura(aura, modifier));
        ship.getAuraNear().forEach(aura -> setAura(aura, modifier));

        ship.getConsumables().forEach(c -> c.forEach(s -> s.getSubConsumables().forEach((k, sC) -> {
            sC.setWorkTime(sC.getWorkTime() * ("scout".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getScoutWorkTime() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("crashCrew".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getCrashCrewWorkTime() : oneCoeff));
            sC.setReloadTime(sC.getReloadTime() * ("crashCrew".equalsIgnoreCase(sC.getConsumableType())
                    && "EmergencyTeamCooldownModifier".equalsIgnoreCase(modifier.getModifier()) ? modifier.getReloadCoefficient() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("speedBoosters".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSpeedBoosterWorkTime() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("airDefenseDisp".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getAirDefenseDispWorkTime() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("sonar".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSonarSearchWorkTime() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("rls".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getRlsSearchWorkTime() : oneCoeff));

            sC.setWorkTime(sC.getWorkTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSmokeGeneratorWorkTime() : oneCoeff));
            sC.setLifeTime(sC.getLifeTime() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getSmokeGeneratorLifeTime() : oneCoeff));
            sC.setRadius(sC.getRadius() * ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType()) ? modifier.getRadiusCoefficient() : oneCoeff));

            sC.setReloadTime(sC.getReloadTime() * ("AllSkillsCooldownModifier".equalsIgnoreCase(modifier.getModifier()) ? modifier.getReloadCoefficient() : oneCoeff));

            sC.setNumConsumables(sC.getNumConsumables() + (sC.getNumConsumables() > 0 ? modifier.getAdditionalConsumables() : 0));
        })));
    }
    
    private void setAura(Aura aura, CommonModifier modifier)
    {
        if (aura != null) {
            if (aura.getInnerBubbleCount() > 0) {
                aura.setInnerBubbleCount(aura.getInnerBubbleCount() + modifier.getAaextraBubbles());
                aura.setBubbleDamage(aura.getBubbleDamage() * modifier.getAaouterDamage() * modifier.getAdvancedOuterAuraDamageCoefficient());
            }
            aura.setAreaDamage(aura.getAreaDamage() * modifier.getAanearDamage() * modifier.getNearAuraDamageCoefficient());
        }
    }

    protected LinkedHashMap<String, String> getBonus(LinkedHashMap<String, Object> copy)
    {
        LinkedHashMap<String, String> bonus = new LinkedHashMap<>();

        copy.forEach((param, cVal) -> {
            if (speed.stream().anyMatch(param.toLowerCase()::contains)) {
                bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal) + " kts");
            } else if (param.toLowerCase().contains("boostcoeff")) {
                if ((float) cVal >= 2.0) {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym((float) cVal));
                } else {
                    bonus.put(MODIFIER + param.toUpperCase(), CommonUtils.getNumSym(CommonUtils.getBonus((float) cVal)) + " %");
                }
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

    private void setPlanes(Ship ship, Plane plane, CommonModifier modifier)
    {
        if (plane.getHangarSettings() != null) {
            plane.getHangarSettings().setTimeToRestore(plane.getHangarSettings().getTimeToRestore() * modifier.getPlaneSpawnTimeCoefficient() * modifier.getAirplanesSpawnTime());
        }
        plane.setMaxForsageAmount(plane.getMaxForsageAmount() * modifier.getForsageDurationCoefficient() * modifier.getAirplanesForsageDuration());
        plane.getConsumables().forEach(consumable -> consumable.getSubConsumables().values().forEach(sub -> sub.setReloadTime(sub.getReloadTime() * modifier.getReloadCoefficient())));
        plane.setSpeedMoveWithBomb(plane.getSpeedMoveWithBomb() * modifier.getFlightSpeedCoefficient());
        plane.setSpeedMove(plane.getSpeedMove() * modifier.getFlightSpeedCoefficient());
        plane.setMaxVisibilityFactor(plane.getMaxVisibilityFactor() * modifier.getSquadronCoefficient() * modifier.getSquadronVisibilityDistCoeff());
        plane.setMaxVisibilityFactorByPlane(plane.getMaxVisibilityFactorByPlane() * modifier.getSquadronCoefficient() * modifier.getSquadronVisibilityDistCoeff());
        plane.setSpeedMoveWithBomb(plane.getSpeedMoveWithBomb() * modifier.getAirplanesSpeed() * (oneCoeff + (ship.getAdrenaline() / modifier.getSquadronHealthStep() * modifier.getSquadronSpeedStep())));
        plane.getConsumables().forEach(c -> c.getSubConsumables().forEach((key, val) -> {
            val.setReloadTime(val.getReloadTime() * ("AllSkillsCooldownModifier".equalsIgnoreCase(modifier.getModifier()) ? modifier.getReloadCoefficient() : oneCoeff));
            val.setFightersNum(val.getFightersNum() + (val.getFightersNum() > 0 ? modifier.getExtraFighterCount() : 0));
        }));
    }
}
