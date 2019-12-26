package WoWSFT.service;

import WoWSFT.model.gameparams.CommonModifier;
import WoWSFT.model.gameparams.ship.Ship;
import WoWSFT.model.gameparams.ship.component.airdefense.Aura;
import WoWSFT.model.gameparams.ship.component.planes.Plane;
import WoWSFT.utils.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
                addAura(auraFar, val.getAuraFar(), true);
                addAura(auraMedium, val.getAuraMedium(), false);
                addAura(auraNear, val.getAuraNear(), false);
            }
        });

        ship.getComponents().getAtba().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(atba))) {
                addAura(auraFar, val.getAuraFar(), true);
                addAura(auraMedium, val.getAuraMedium(), false);
                addAura(auraNear, val.getAuraNear(), false);
            }
        });

        ship.getComponents().getArtillery().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(artillery))) {
                addAura(auraFar, val.getAuraFar(), true);
                addAura(auraMedium, val.getAuraMedium(), false);
                addAura(auraNear, val.getAuraNear(), false);
            }
        });

        ship.setAuraFar(sortAura(auraFar.stream().filter(a -> a.getAreaDamage() > 0).collect(Collectors.toList())));
        ship.setAuraFarBubble(sortAura(auraFar.stream().filter(a -> a.getBubbleDamage() > 0).collect(Collectors.toList())));
        ship.setAuraMedium(sortAura(auraMedium));
        ship.setAuraNear(sortAura(auraNear));
    }

    private void addAura(List<Aura> aura, List<Aura> temp, boolean hasBubble)
    {
        for (Aura x : temp) {
            if (!hasBubble || (x.getInnerBubbleCount() > 0 || x.getOuterBubbleCount() > 0)) {
                aura.add(x);
            }
        }
    }

    private List<Aura> sortAura(List<Aura> aura)
    {
        if (aura.size() > 1) {
            for (int i = 1; i < aura.size(); i++) {
                aura.get(0).setInnerBubbleCount(aura.get(0).getInnerBubbleCount() + aura.get(i).getInnerBubbleCount());
                aura.get(0).setOuterBubbleCount(aura.get(0).getOuterBubbleCount() + aura.get(i).getOuterBubbleCount());
            }
            aura.subList(1, aura.size()).clear();
        }
        return aura;
    }

    public void setParameters(Ship ship)
    {
        for (int i = 0; i < ship.getSelectUpgrades().size(); i++) {
            if (ship.getSelectUpgrades().get(i) > 0) {
                CommonModifier modifier = mapper.convertValue(ship.getUpgrades().get(i).get(ship.getSelectUpgrades().get(i) - 1), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        for (int i = 0; i < ship.getSelectSkills().size(); i++) {
            if (ship.getSelectSkills().get(i) == 1) {
                CommonModifier modifier = mapper.convertValue(ship.getCommander().getCSkills().get(i / 8).get(i % 8), CommonModifier.class);
                setUpgrades(ship, modifier);
            }
        }

        ship.getConsumables().forEach(slot -> slot.forEach(c -> c.getSubConsumables().forEach((key, sub) ->
                sub.setBonus(CommonUtils.getBonus(mapper.convertValue(sub, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getFighter().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(CommonUtils.getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getDiveBomber().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(CommonUtils.getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
        ship.getComponents().getTorpedoBomber().forEach((k, p) -> p.getConsumables().forEach(c -> c.getSubConsumables().forEach((sKey, sVal) ->
                sVal.setBonus(CommonUtils.getBonus(mapper.convertValue(sVal, new TypeReference<LinkedHashMap<String, Object>>(){}))))));
    }

    private void setUpgrades(Ship ship, CommonModifier modifier)
    {
        ship.getComponents().getArtillery().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(artillery))) {
                val.setGMIdealRadius(val.getGMIdealRadius() * modifier.getGmidealRadius());
                val.setMaxDist(val.getMaxDist() * modifier.getGmmaxDist() * (val.getBarrelDiameter() > smallGun ? oneCoeff : modifier.getSmallGunRangeCoefficient()));
                val.getTurrets().forEach(t -> {
                    t.getRotationSpeed().set(0, t.getRotationSpeed().get(0) * modifier.getGmrotationSpeed() + (t.getBarrelDiameter() > smallGun ? modifier.getBigGunBonus() : modifier.getSmallGunBonus()));
                    t.setShotDelay(t.getShotDelay() * modifier.getGmshotDelay() * (t.getBarrelDiameter() > smallGun ? oneCoeff : modifier.getSmallGunReloadCoefficient())
                            * (oneCoeff - (ship.getAdrenaline() / modifier.getHpStep() * modifier.getTimeStep())));
                });
                val.getShells().forEach((s, ammo) -> {
                    if (HE.equalsIgnoreCase(ammo.getAmmoType())) {
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
                if (HE.equalsIgnoreCase(val.getRocket().getAmmoType())) {
                    val.getRocket().setBurnProb(val.getRocket().getBurnProb() + modifier.getRocketProbabilityBonus());
                }
            }
        });

        ship.getComponents().getDiveBomber().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(diveBomber))) {
                setPlanes(ship, val, modifier);

                val.setMaxHealth((int) ((val.getMaxHealth() + (ship.getLevel() * modifier.getPlaneHealthPerLevel())) * modifier.getAirplanesDiveBombersHealth() * modifier.getAirplanesHealth()));
                if (HE.equalsIgnoreCase(val.getBomb().getAmmoType())) {
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
                    if (HE.equalsIgnoreCase(sec.getAmmoType())) {
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
                
                val.setPrioritySectorPreparation(val.getPrioritySectorPreparation() * modifier.getPrioSectorCooldownCoefficient());
                val.setPrioritySectorDuration(val.getPrioritySectorDuration() * modifier.getPrioSectorCooldownCoefficient());
                val.setPrioritySectorDamageInitial(val.getPrioritySectorDamageInitial() * modifier.getPrioSectorStartPhaseStrengthCoefficient());
            }
        });

        ship.getComponents().getHull().forEach((c, val) -> {
            if (c.equalsIgnoreCase(ship.getModules().get(hull))) {
                val.setBurnProb(val.getBurnProb() * modifier.getBurnProb() * modifier.getProbabilityCoefficient());
                val.setBurnTime(val.getBurnTime() * modifier.getBurnTime() * modifier.getCritTimeCoefficient());
                val.setBurnSizeSkill(modifier.getProbabilityCoefficient() != oneCoeff ? 3 : val.getBurnSizeSkill());
                val.setFloodProb(val.getFloodProb() * modifier.getFloodProb());
                val.setFloodTime(val.getFloodTime() * modifier.getFloodTime() * modifier.getCritTimeCoefficient());
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
        ship.getAuraFarBubble().forEach(aura -> setAura(aura, modifier));
        ship.getAuraMedium().forEach(aura -> setAura(aura, modifier));
        ship.getAuraNear().forEach(aura -> setAura(aura, modifier));

        ship.getConsumables().forEach(c -> c.forEach(s -> s.getSubConsumables().forEach((k, sC) -> {
            if ("scout".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getScoutWorkTime());
            } else if ("crashCrew".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getCrashCrewWorkTime());
                if ("EmergencyTeamCooldownModifier".equalsIgnoreCase(modifier.getModifier())) {
                    sC.setReloadTime(sC.getReloadTime() * modifier.getReloadCoefficient());
                }
            } else if ("speedBoosters".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getSpeedBoosterWorkTime());
            } else if ("airDefenseDisp".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getAirDefenseDispWorkTime());
            } else if ("sonar".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getSonarSearchWorkTime());
                if ("TorpedoAlertnessModifier".equalsIgnoreCase(modifier.getModifier())) {
                    sC.setDistTorpedo(sC.getDistTorpedo() * modifier.getRangeCoefficient());
                }
            } else if ("rls".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getRlsSearchWorkTime());
            } else if ("smokeGenerator".equalsIgnoreCase(sC.getConsumableType())) {
                sC.setWorkTime(sC.getWorkTime() * modifier.getSmokeGeneratorWorkTime());
                sC.setLifeTime(sC.getLifeTime() * modifier.getSmokeGeneratorLifeTime());
                sC.setRadius(sC.getRadius() * modifier.getRadiusCoefficient());
            }

            if ("AllSkillsCooldownModifier".equalsIgnoreCase(modifier.getModifier())) {
                sC.setReloadTime(sC.getReloadTime() * modifier.getReloadCoefficient());
            }

            if (sC.getNumConsumables() > 0) {
                sC.setNumConsumables(sC.getNumConsumables() + modifier.getAdditionalConsumables());
            }
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
