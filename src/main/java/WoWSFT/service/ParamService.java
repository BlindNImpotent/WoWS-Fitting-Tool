package WoWSFT.service;

import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.commander.Commander;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.component.ShipComponent;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import WoWSFT.model.gameparams.ship.component.planes.Plane;
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
        for (int i = 0; i < ship.getSelectSkills().size(); i++) {
            if (ship.getSelectSkills().get(i) == 1) {
                CommonModifier modifier = mapper.convertValue(crew.getCSkills().get(i / 8).get(i % 8), CommonModifier.class);
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
    }

    private void setUpgrades(Ship ship, CommonModifier modifier)
    {
        ship.getComponents().getArtillery().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(artillery))) {
                val.setGMIdealRadius(val.getGMIdealRadius() * modifier.getGmidealRadius());
                val.setMaxDist(val.getMaxDist() * modifier.getGmmaxDist() * (val.getBarrelDiameter() > 0.139 ? 1f : modifier.getSmallGunRangeCoefficient()));
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

                setAura(val.getAuraFar(), modifier);
                setAura(val.getAuraMedium(), modifier);
                setAura(val.getAuraNear(), modifier);
            }
        });

        ship.getComponents().getTorpedoes().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(torpedoes))) {
                val.getLaunchers().forEach(l -> {
                    l.getRotationSpeed().set(0, l.getRotationSpeed().get(0) * modifier.getGtrotationSpeed());
                    l.setShotDelay(l.getShotDelay() * modifier.getGtshotDelay() * modifier.getLauncherCoefficient());
                });
                val.getAmmo().setMaxDist(val.getAmmo().getMaxDist() * modifier.getTorpedoRangeCoefficient());
                val.getAmmo().setSpeed(val.getAmmo().getSpeed() + modifier.getTorpedoSpeedBonus());
            }
        });

        ship.getComponents().getFighter().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(fighter))) {
                setPlanes(val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesFightersHealth() * modifier.getAirplanesHealth()));
                if ("HE".equalsIgnoreCase(val.getRocket().getAmmoType())) {
                    val.getRocket().setBurnProb(val.getRocket().getBurnProb() + modifier.getRocketProbabilityBonus());
                }
            }
        });

        ship.getComponents().getDiveBomber().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(diveBomber))) {
                setPlanes(val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesDiveBombersHealth() * modifier.getAirplanesHealth()));
                if ("HE".equalsIgnoreCase(val.getBomb().getAmmoType())) {
                    val.getBomb().setBurnProb(val.getBomb().getBurnProb() + modifier.getBombProbabilityBonus());
                }
            }
        });

        ship.getComponents().getTorpedoBomber().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(torpedoBomber))) {
                setPlanes(val, modifier);

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
                    sec.setShotDelay(sec.getShotDelay() * modifier.getGsshotDelay() * modifier.getSmallGunReloadCoefficient());
                    sec.setGSIdealRadius(sec.getGSIdealRadius() * modifier.getGsidealRadius() * (ship.getLevel() >= 7 ? modifier.getAtbaIdealRadiusHi() : modifier.getAtbaIdealRadiusLo()));
                    if ("HE".equalsIgnoreCase(sec.getAmmoType())) {
                        sec.setBurnProb(sec.getBurnProb() + modifier.getProbabilityBonus() + modifier.getChanceToSetOnFireBonusSmall());
                        sec.setAlphaPiercingHE(sec.getAlphaPiercingHE() * modifier.getThresholdPenetrationCoefficientSmall());
                    }
                });

                setAura(val.getAuraFar(), modifier);
                setAura(val.getAuraMedium(), modifier);
                setAura(val.getAuraNear(), modifier);
            }
        });

        ship.getComponents().getAirDefense().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(airDefense))) {
                setAura(val.getAuraFar(), modifier);
                setAura(val.getAuraMedium(), modifier);
                setAura(val.getAuraNear(), modifier);
                val.setPrioritySectorStrength((val.getPrioritySectorStrength() - 1.0f) * modifier.getPrioritySectorStrengthCoefficient() + 1.0f);
                val.setPrioritySectorChangeDelay(val.getPrioritySectorChangeDelay() * modifier.getSectorSwitchDelayCoefficient());
            }
        });

        ship.getComponents().getHull().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(hull))) {
                val.getBurnNodes().forEach(n -> {
                    n.set(1, ((double) n.get(1)) * modifier.getBurnProb() * modifier.getProbabilityCoefficient());
                    n.set(3, ((double) n.get(3)) * modifier.getBurnTime() * modifier.getCritTimeCoefficient());
                });
                val.setBurnSizeSkill(modifier.getProbabilityCoefficient() != 1f ? 3 : val.getBurnSizeSkill());
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

    private void setPlanes(Plane plane, CommonModifier modifier)
    {
        if (plane.getHangarSettings() != null) {
            plane.getHangarSettings().setTimeToRestore(plane.getHangarSettings().getTimeToRestore() * modifier.getPlaneSpawnTimeCoefficient() * modifier.getAirplanesSpawnTime());
        }
        plane.setMaxForsageAmount(plane.getMaxForsageAmount() * modifier.getForsageDurationCoefficient() * modifier.getAirplanesForsageDuration());
        plane.getConsumables().forEach(consumable -> consumable.getSubConsumables().values().forEach(sub -> sub.setReloadTime(sub.getReloadTime() * modifier.getReloadCoefficient())));
        plane.setSpeedMoveWithBomb(plane.getSpeedMoveWithBomb() * modifier.getFlightSpeedCoefficient());
        plane.setSpeedMove(plane.getSpeedMove() * modifier.getFlightSpeedCoefficient());
        plane.setMaxVisibilityFactor(plane.getMaxVisibilityFactor() * modifier.getSquadronVisibilityDistCoeff());
        plane.setMaxVisibilityFactorByPlane(plane.getMaxVisibilityFactorByPlane() * modifier.getSquadronVisibilityDistCoeff());
        plane.setSpeedMoveWithBomb(plane.getSpeedMoveWithBomb() * modifier.getAirplanesSpeed());
    }
}
