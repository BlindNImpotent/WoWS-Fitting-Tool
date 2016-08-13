function setCrewSkills()
{
    crewNation = '';
    switch (shipNation)
    {
        case "usa":
            crewNation = "PAW001_DefaultCrew";
            break;
        case "japan":
            crewNation = "PJW001_DefaultCrew";
            break;
        case "ussr":
            crewNation = "PRW001_DefaultCrew";
            break;
        case "germany":
            crewNation = "PGW001_DefaultCrew";
            break;
        case "pan_asia":
            crewNation = "PZW001_DefaultCrew";
            break;
        case "poland":
            crewNation = "PWW001_DefaultCrew";
            break;
        case "uk":
            crewNation = "PBW001_DefaultCrew";
            break;
        case "france":
            crewNation = "PFW001_DefaultCrew";
        default:
            break;
    }

    var crewNationJSON;
    $.ajax({
        url: "/GameParams/name/" + crewNation,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            crewNationJSON = data;
        }
    });

    var skills = crewNationJSON['Skills'];
    var index = [];

    var table = document.getElementById('skillsTable');
    var row1 = table.insertRow(0)
    var row2 = table.insertRow(1)
    var row3 = table.insertRow(2)
    var row4 = table.insertRow(3)
    var row5 = table.insertRow(4)

    for (var i = 0; i < 6; i++)
    {
        row1.insertCell(i);
        row2.insertCell(i);
        row3.insertCell(i);
        row4.insertCell(i);
        row5.insertCell(i);
    }

    var skill;

    for (var i in skills)
    {
        index.push(i);
    }

    for (var i in index)
    {
        skill = skills[index[i]];

        if (skill['tier'] == 1)
        {
            var imgSrc = skillsImageLocation + index[i] + '.png';

            table.rows[skill['tier']-1].cells[skill['column']].innerHTML = "<button onclick=setSkill(id) id=" + index[i] + "><img src=" + imgSrc + "\/></button>"
            document.getElementById(index[i]).className = "button_skill";
        }
        else if (skill['tier'] == 2)
        {
            var imgSrc = skillsImageLocation + index[i] + '.png';

            table.rows[skill['tier']-1].cells[skill['column']].innerHTML = "<button onclick=setSkill(id) id=" + index[i] + "><img src=" + imgSrc + "\/></button>"
            document.getElementById(index[i]).className = "button_skill";
        }
        else if (skill['tier'] == 3)
        {
            var imgSrc = skillsImageLocation + index[i] + '.png';

            table.rows[skill['tier']-1].cells[skill['column']].innerHTML = "<button onclick=setSkill(id) id=" + index[i] + "><img src=" + imgSrc + "\/></button>"
            document.getElementById(index[i]).className = "button_skill";
        }
        else if (skill['tier'] == 4)
        {
            var imgSrc = skillsImageLocation + index[i] + '.png';

            table.rows[skill['tier']-1].cells[skill['column']].innerHTML = "<button onclick=setSkill(id) id=" + index[i] + "><img src=" + imgSrc + "\/></button>"
            document.getElementById(index[i]).className = "button_skill";
        }
        else if (skill['tier'] == 5)
        {
            var imgSrc = skillsImageLocation + index[i] + '.png';

            table.rows[skill['tier']-1].cells[skill['column']].innerHTML = "<button onclick=setSkill(id) id=" + index[i] + "><img src=" + imgSrc + "\/></button>"
            document.getElementById(index[i]).className = "button_skill";
        }

    }
}

function setSkill(id)
{
    if (document.getElementById(id).className == "button_skill")
    {
        document.getElementById(id).className = "button_skill_selected"
        setSkillStats(id);
    }
    else if (document.getElementById(id).className == "button_skill_selected")
    {
        document.getElementById(id).className = "button_skill"
        refreshSkill(id);
    }
}

function setSkillStats(id)
{
    var crewNationJSON;
    $.ajax({
        url: "/GameParams/name/" + crewNation,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            crewNationJSON = data;
        }
    });

    var skills = crewNationJSON['Skills'];
    var skill = skills[id];

    if (id == 'AIGunsEfficiencyModifier')
    {
        airDefenceEfficiencyCoefficient = skill['airDefenceEfficiencyCoefficient'];
        smallGunReloadCoefficient = skill['smallGunReloadCoefficient'];
    }
    if (id == 'AIGunsRangeModifier')
    {
        airDefenceRangeCoefficient = skill['airDefenceRangeCoefficient'];
        smallGunRangeCoefficient = skill['smallGunRangeCoefficient'];
    }
    if (id == 'AdditionalPlanesInSquadModifier')
    {
        diveBomberCountSkill = skill['diveBomber'];
        fighterCountSkill = skill['fighter'];
        torpedoBomberCountSkill = skill['torpedoBomber'];
    }
    if (id == 'AllSkillsCooldownModifier')
    {
        reloadCoefficientAllSkills = skill['reloadCoefficient'];
    }
    if (id == 'ArtilleryAlertModifier')
    {
        ArtilleryAlertModifier = skill['ArtilleryAlertModifier'];
    }
    if (id == 'AutoRepairModifier')
    {
        critTimeCoefficient = skill['critTimeCoefficient'];
    }
    if (id == 'CentralATBAModifier')
    {
        atbaIdealRadiusHi = skill['atbaIdealRadiusHi'];
        atbaIdealRadiusLo = skill['atbaIdealRadiusLo'];
    }
    if (id == 'CentralAirDefenceModifier')
    {
        airDefenceSelectedTargetCoefficient = skill['airDefenceSelectedTargetCoefficient'];
    }
    if (id == 'EmergencyTeamCooldownModifier')
    {
        reloadCoefficientDamageControl = skill['reloadCoefficient'];
    }
    if (id == 'FighterEfficiencyModifier')
    {
        fightersEfficiencyCoefficient = skill['fightersEfficiencyCoefficient'];
    }
    if (id == 'FireProbabilityModifier')
    {
        probabilityBonusFire = skill['probabilityBonus'];
    }
    if (id == 'FireResistanceModifier')
    {
        probabilityCoefficientFire = skill['probabilityCoefficient'];
    }
    if (id == 'IntuitionModifier')
    {
        switchAmmoReloadCoef = skill['switchAmmoReloadCoef'];
    }
    if (id == 'LastChanceModifier')
    {
        lastChanceHp = skill['lastChanceHp'];
        lastChanceReloadCoefficient = skill['lastChanceReloadCoefficient'];
    }
    if (id == 'LastEffortModifier')
    {
        critRudderTimeCoefficient = skill['critRudderTimeCoefficient'];
    }
    if (id == 'MainGunsRotationModifier')
    {
        bigGunBonus = skill['bigGunBonus'];
        smallGunBonus = skill['smallGunBonus'];
    }
    if (id == 'MeticulousPreventionModifier')
    {
        critProbCoefficient = skill['critProbCoefficient'];
    }
    if (id == 'PlanePreparingModifier')
    {
        diveBombersPrepareCoefficient = skill['diveBombersPrepareCoefficient'];
        fightersPrepareCoefficient = skill['fightersPrepareCoefficient'];
        torpedoBombersPrepareCoefficient = skill['torpedoBombersPrepareCoefficient'];
        vitalityCoefficientPlane = skill['vitalityCoefficient'];
    }
    if (id == 'PreparingOnboardShootersModifier')
    {
        fightersPassiveEfficiencyCoefficient = skill['fightersPassiveEfficiencyCoefficient'];
    }
    if (id == 'SuperintendentModifier')
    {
        additionalConsumables = skill['additionalConsumables'];
    }
    if (id == 'SurvivalModifier')
    {
        healthPerLevel = skill['healthPerLevel'];
    }
    if (id == 'TorpedoAcceleratorModifier')
    {
        torpedoRangeCoefficient = skill['torpedoRangeCoefficient'];
        torpedoSpeedBonus = skill['torpedoSpeedBonus'];
    }
    if (id == 'TorpedoAlertnessModifier')
    {
        rangeCoefficientTorpedoAlert = skill['rangeCoefficient'];
    }
    if (id == 'TorpedoReloadModifier')
    {
        bomberCoefficientTorpedoReload = skill['bomberCoefficient'];
        launcherCoefficientTorpedoReload = skill['launcherCoefficient'];
    }
    if (id == 'VisibilityModifier')
    {
        aircraftCarrierCoefficientConceal = skill['aircraftCarrierCoefficient'];
        battleshipCoefficientConceal = skill['battleshipCoefficient'];
        cruiserCoefficientConceal = skill['cruiserCoefficient'];
        destroyerCoefficientConceal = skill['destroyerCoefficient'];

        if (shipType == 'AirCarrier')
        {
            VisibilityModifier = aircraftCarrierCoefficientConceal;
        }
        else if (shipType == 'Battleship')
        {
            VisibilityModifier = battleshipCoefficientConceal;
        }
        else if (shipType == 'Cruiser')
        {
            VisibilityModifier = cruiserCoefficientConceal;
        }
        else if (shipType == 'Destroyer')
        {
            VisibilityModifier = destroyerCoefficientConceal;
        }
    }
    refresh();
}

function refreshSkill(id)
{
    if (id == 'AIGunsEfficiencyModifier')
    {
        airDefenceEfficiencyCoefficient = 1;
        smallGunReloadCoefficient = 1;
    }
    if (id == 'AIGunsRangeModifier')
    {
        airDefenceRangeCoefficient = 1;
        smallGunRangeCoefficient = 1;
    }
    if (id == 'AdditionalPlanesInSquadModifier')
    {
        diveBomberCountSkill = 0;
        fighterCountSkill = 0;
        torpedoBomberCountSkill = 0;
    }
    if (id == 'AllSkillsCooldownModifier')
    {
        reloadCoefficientAllSkills = 1;
    }
    if (id == 'ArtilleryAlertModifier')
    {
        ArtilleryAlertModifier = 0;
    }
    if (id == 'AutoRepairModifier')
    {
        critTimeCoefficient = 1;
    }
    if (id == 'CentralATBAModifier')
    {
        atbaIdealRadiusHi = 1;
        atbaIdealRadiusLo = 1;
    }
    if (id == 'CentralAirDefenceModifier')
    {
        airDefenceSelectedTargetCoefficient = 1;
    }
    if (id == 'EmergencyTeamCooldownModifier')
    {
        reloadCoefficientDamageControl = 1;
    }
    if (id == 'FighterEfficiencyModifier')
    {
        fightersEfficiencyCoefficient = 0;
    }
    if (id == 'FireProbabilityModifier')
    {
        probabilityBonusFire = 0;
    }
    if (id == 'FireResistanceModifier')
    {
        probabilityCoefficientFire = 1;
    }
    if (id == 'IntuitionModifier')
    {
        switchAmmoReloadCoef = 1;
    }
    if (id == 'LastChanceModifier')
    {
        lastChanceHp = 1;
        lastChanceReloadCoefficient = 1;
    }
    if (id == 'LastEffortModifier')
    {
        critRudderTimeCoefficient = 1;
    }
    if (id == 'MainGunsRotationModifier')
    {
        bigGunBonus = 0;
        smallGunBonus = 0;
    }
    if (id == 'MeticulousPreventionModifier')
    {
        critProbCoefficient = 0;
    }
    if (id == 'PlanePreparingModifier')
    {
        diveBombersPrepareCoefficient = 1;
        fightersPrepareCoefficient = 1;
        torpedoBombersPrepareCoefficient = 1;
        vitalityCoefficientPlane = 1;
    }
    if (id == 'PreparingOnboardShootersModifier')
    {
        fightersPassiveEfficiencyCoefficient = 1;
    }
    if (id == 'SuperintendentModifier')
    {
        additionalConsumables = 0;
    }
    if (id == 'SurvivalModifier')
    {
        healthPerLevel = 0;
    }
    if (id == 'TorpedoAcceleratorModifier')
    {
        torpedoRangeCoefficient = 1;
        torpedoSpeedBonus = 1;
    }
    if (id == 'TorpedoAlertnessModifier')
    {
        rangeCoefficientTorpedoAlert = 0;
    }
    if (id == 'TorpedoReloadModifier')
    {
        bomberCoefficientTorpedoReload = 1;
        launcherCoefficientTorpedoReload = 1;
    }
    if (id == 'VisibilityModifier')
    {
        aircraftCarrierCoefficientConceal = 1;
        battleshipCoefficientConceal = 1;
        cruiserCoefficientConceal = 1;
        destroyerCoefficientConceal = 1;
        VisibilityModifier = 1;
    }
    refresh();
}