function turret(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setTurretStats(id);
}

function hull(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setHullStats(id);
}

function engine(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setEngineStats(id);
}

function radar(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setRadarStats(id);
}

function torpedo(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setTorpedoStats(id);
}

function flightControl(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setFlightControlStats(id);
}

function fighter(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setFighterStats(id);
}

function torpedoBomber(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setTorpedoBomberStats(id);
}

function diveBomber(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_module";
        }
        document.getElementById(id).className = "button_module_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
    }

    setDiveBomberStats(id);
}

function setTurretStats(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiArtilleryUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_turretKey;

    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_turretKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_turretKey];
    var components = module['components'];
    var artillery = components['artillery'];

    var GP_ArtilleryJSON = gpShipJSON[artillery[artillery.length - 1]];

    if (shipNation == 'usa')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_AGM_1'];
    }
    else if (shipNation == 'germany')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_GGM_1'];
    }
    else if (shipNation == 'japan')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_JGM_1'];
    }
    else if (shipNation == 'ussr')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_RGM_1'];
    }
    else if (shipNation == 'uk')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_BGM_1'];
    }
    else if (shipNation == 'poland')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_WGM_2'];
    }
    else if (shipNation == 'pan_asia')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_ZGM_1'];
    }
    else if (shipNation == 'france')
    {
        GP_TurretJSON = GP_ArtilleryJSON['HP_FGM_1'];
    }

    maxMainGunRange = GP_ArtilleryJSON['maxDist'];
    sigmaCount = GP_ArtilleryJSON['sigmaCount'];
    numBarrels = GP_TurretJSON['numBarrels'];

    numTurrets = 0;
    for (var i in Object.keys(GP_ArtilleryJSON))
    {
        if (!(Object.keys(GP_ArtilleryJSON)[i]).indexOf('HP_'))
        {
            numTurrets = numTurrets + 1;
        }
    }
    turretBarrelDiameter = GP_TurretJSON['barrelDiameter'];

    var ammoList = GP_TurretJSON['ammoList'];

    ammoList.sort();

    var APShell;
    $.ajax({
        url: "/GameParams/name/" + ammoList[0],
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            APShell = data;
        }
    });

    APShellSpeed = APShell['bulletSpeed'];
    APShellDMG = APShell['alphaDamage'];

    var HEShell;
    $.ajax({
        url: "/GameParams/name/" + ammoList[1],
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            HEShell = data;
        }
    });
    HEShellSpeed = HEShell['bulletSpeed'];
    HEShellDMG = HEShell['alphaDamage'];
    HEShellBurnProb = HEShell['burnProb'];

    mainGunRotation = GP_TurretJSON['rotationSpeed'][0];
    if (mainGunRotation != 0)
    {
        mainGunRotationTime = 180 / mainGunRotation;
    }

    mainGunReload = GP_TurretJSON['shotDelay'];

    mainGunDispersionTangent = GP_TurretJSON['idealRadius'];
    mainGunDispersionTangent = mainGunDispersionTangent * 0.03 * Math.PI / 180;
    mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);

    refresh();
}

function setHullStats(id)
{
    antiAirAuraDistanceFar = 0;
    AAFarDPS = 0;
    antiAirAuraDistanceMedium = 0;
    AAMediumDPS = 0;
    antiAirAuraDistanceNear = 0;
    AANearDPS = 0;
    secondaryMaxDist = 0;

    planesReserveCapacity = 0;


    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiHullUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_hullKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_hullKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_hullKey];
    var components = module['components'];
    var hull = components['hull'];
    var ATBAArray = components['atba'];
    var AirDefenseArray = components['airDefense'];
    var airArmament = components['airArmament'];

    var GP_AirArmamentJSON = gpShipJSON[airArmament[airArmament.length - 1]];

    if (GP_AirArmamentJSON != null)
    {
        planesReserveCapacity = GP_AirArmamentJSON['planesReserveCapacity'];
    }


    var GP_HullJSON = gpShipJSON[hull[hull.length - 1]];

    maxRepairCost = GP_HullJSON['maxRepairCost'];
    maxHP = GP_HullJSON['health'];
    rudderShift = GP_HullJSON['rudderTime'] / 1.3;
    speed = GP_HullJSON['maxSpeed'];
    turningRadius = GP_HullJSON['turningRadius'];
    sConceal = GP_HullJSON['visibilityFactor'];
    aConceal = GP_HullJSON['visibilityFactorByPlane'];
    stealthFireSurfaceDetection = GP_HullJSON['visibilityCoefGK'];
    AAFireAirDetection = GP_HullJSON['visibilityCoefATBAByPlane'];
    burnTime = GP_HullJSON['burnNodes'][0][3];
    floodTime = GP_HullJSON['floodParams'][2];

    if (turretBarrelDiameter < 0.140 && GP_TurretJSON != null)
    {
        antiAirAuraDistanceFar = GP_TurretJSON['antiAirAuraDistance'] * 0.03;

        var AuraFar = GP_TurretJSON['AuraFar'];
        if (AuraFar != null)
        {
            var guns = AuraFar['guns'];

            AAFarDPS = GP_TurretJSON['antiAirAuraStrength'] * guns.length * 100;
        }

        AAFarBarrelDiameter = turretBarrelDiameter;
    }
    else if (turretBarrelDiameter >= 0.140 || GP_TurretJSON == null)
    {
        if (ATBAArray != null)
        {
            var ATBA = gpShipJSON[ATBAArray[ATBAArray.length - 1]];

            if (ATBA != null)
            {
                secondaryMaxDist = ATBA['maxDist'];

                if (ATBA['AuraFar'] != null)
                {
                    var AuraFar = ATBA['AuraFar'];
                    var guns = AuraFar['guns'];
                    var AAFarGunString = guns[0];
                    var AAFarGun = ATBA[AAFarGunString];

                    AAFarBarrelDiameter = AAFarGun['barrelDiameter'];
                    antiAirAuraDistanceFar = AAFarGun['antiAirAuraDistance'] * 0.03;

                    var count = 0;
                    while (count < guns.length)
                    {
                        var tempStr = guns[count];
                        var tempObj = ATBA[tempStr];
                        var dps = tempObj['antiAirAuraStrength'];

                        AAFarDPS = AAFarDPS + dps;

                        count++;
                    }
                    AAFarDPS = AAFarDPS * 100;
                }
            }
        }
    }

    if (AirDefenseArray.length > 0)
    {
        var AirDefense = gpShipJSON[AirDefenseArray[AirDefenseArray.length - 1]];

        if (AirDefense != null)
        {
            if (AirDefense['AuraMedium'] != null)
            {
                var AuraMedium;
                if (AirDefense['AuraMedium1'] != null)
                {
                    AuraMedium = AirDefense['AuraMedium1'];
                }
                else
                {
                    AuraMedium = AirDefense['AuraMedium'];
                }

                var guns = AuraMedium['guns'];
                var AAMediumGunString = guns[0];
                var AAMediumGun = AirDefense[AAMediumGunString];
                antiAirAuraDistanceMedium = AAMediumGun['antiAirAuraDistance'] * 0.03;

                var count = 0;
                while (count < guns.length)
                {
                    var tempStr = guns[count];
                    var tempObj = AirDefense[tempStr];
                    var dps = tempObj['antiAirAuraStrength'];

                    AAMediumDPS = AAMediumDPS + dps;

                    count++;
                }
                AAMediumDPS = AAMediumDPS * 100;
            }

            if (AirDefense['AuraNear'] != null)
            {
                var AuraNear = AirDefense['AuraNear'];
                var guns = AuraNear['guns'];
                var AANearGunString = guns[0];
                var AANearGun = AirDefense[AANearGunString];
                antiAirAuraDistanceNear = AANearGun['antiAirAuraDistance'] * 0.03;

                var count = 0;
                while (count < guns.length)
                {
                    var tempStr = guns[count];
                    var tempObj = AirDefense[tempStr];
                    var dps = tempObj['antiAirAuraStrength'];

                    AANearDPS = AANearDPS + dps;

                    count++;
                }
                AANearDPS = AANearDPS * 100;
            }
            else if (AirDefense['AuraMedium1'] != null)
            {
                var AuraMedium = AirDefense['AuraMedium'];
                if (AuraMedium != null)
                {
                    var guns = AuraMedium['guns'];
                    var AANearGunString = guns[0];
                    var AANearGun = AirDefense[AANearGunString];
                    antiAirAuraDistanceNear = AANearGun['antiAirAuraDistance'] * 0.03;

                    var count = 0;
                    while (count < guns.length)
                    {
                        var tempStr = guns[count];
                        var tempObj = AirDefense[tempStr];
                        var dps = tempObj['antiAirAuraStrength'];

                        AANearDPS = AANearDPS + dps;

                        count++;
                    }
                    AANearDPS = AANearDPS * 100;
                }
            }
        }
    }
    refresh()
}

function setEngineStats(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiEngineUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_engineKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_engineKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_engineKey];
    var components = module['components'];
    var engine = components['engine'];

    engineObj = gpShipJSON[engine[0]];

    speedCoef = 1 + engineObj['speedCoef'];
    horsePower = engineObj['histEnginePower'];

    refresh();
}

function setRadarStats(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiRadarUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];

    var GP_radarKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_radarKey = data['name']
        }
    });

    var module = gpShipUpgradeInfo[GP_radarKey];
    var components = module['components'];
    var radar = components['fireControl'];

    var GP_RadarJSON = gpShipJSON[radar[radar.length - 1]];
    maxDistCoef = GP_RadarJSON['maxDistCoef'];

    refresh()
}

function setTorpedoStats(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiTorpedoUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_torpedoKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_torpedoKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_torpedoKey];
    var components = module['components'];
    var torpedo = components['torpedoes'];

    var tobj = null;
    var tobj2 = null;
    var tobj3 = null;

    if (torpedo != null)
    {
        tobj = gpShipJSON[torpedo[0]];
    }

    if (shipNation == 'germany')
    {
        tobj2 = tobj['HP_GGT_1'];
    }
    else if (shipNation == 'usa')
    {
        tobj2 = tobj['HP_AGT_1'];
    }
    else if (shipNation == 'japan')
    {
        if (ship_id_str == 'PJSC008')
        {
            tobj2 = tobj['HP_JGT_3'];
        }
        else
        {
            tobj2 = tobj['HP_JGT_1'];
        }
    }
    else if (shipNation == 'ussr')
    {
        tobj2 = tobj['HP_RGT_1'];
    }
    else if (shipNation == 'pan_asia')
    {
        tobj2 = tobj['HP_ZGT_1'];
    }
    else if (shipNation == 'poland')
    {
        tobj2 = tobj['HP_WGT_1'];
    }
    else if (shipNation == 'uk')
    {
        tobj2 = tobj['HP_BGT_1'];
    }

    torpDiameter = tobj2['barrelDiameter'];

    numTubes = tobj2['numBarrels'];

    numTorpTurrets = Object.keys(tobj).length;

    tobj3 = tobj2['rotationSpeed'];

    torpedoRotation = tobj3[0];
    torpedoReload = tobj2['shotDelay'];

    var ammoList = tobj2['ammoList'];
    var ammo = ammoList[0];
    var ammoObj;
    
    $.ajax({
        url: "/GameParams/name/" + ammo,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            ammoObj = data;
        }
    });
    maxTorpedoRange = ammoObj['maxDist'] * 0.03;
    torpedoSpeed = ammoObj['speed'];
    torpedoVisibilityFactor = ammoObj['visibilityFactor'];

    refresh();
}

function setFlightControlStats(id)
{
    torpedoBomberSquadronCount = 0;
    diveBomberSquadronCount = 0;
    fighterSquadronCount = 0;
    scoutSquadronCount = 0;
    totalSquadronCount = 0;
    torpedoBomberSquadronSize = 0;
    diveBomberSquadronSize = 0;
    fighterSquadronSize = 0;
    scoutSquadronSize = 0;
    torpedoBomberCount = 0;
    diveBomberCount = 0;
    fighterCount = 0;
    scoutCount = 0;
    
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiFlightControlUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_flightControlKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_flightControlKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_flightControlKey];
    var components = module['components'];
    var flightControl = components['flightControl'];

    var GP_FlightControlJSON = gpShipJSON[flightControl[flightControl.length - 1]];

    var planesReserveAssignment = GP_FlightControlJSON['planesReserveAssignment'];
    torpedoBomberSquadronCount = planesReserveAssignment['Bomber'];
    diveBomberSquadronCount = planesReserveAssignment['Dive'];
    fighterSquadronCount = planesReserveAssignment['Fighter'];
    scoutSquadronCount = planesReserveAssignment['Scout'];
    totalSquadronCount = torpedoBomberSquadronCount + diveBomberSquadronCount + fighterSquadronCount + scoutSquadronCount;

    var squadrons =  GP_FlightControlJSON['squadrons'];

    for (var i in squadrons)
    {
        if (squadrons[i][0] == 'Bomber')
        {
            torpedoBomberSquadronSize = squadrons[i][1];
        }
        else if (squadrons[i][0] == 'Dive')
        {
            diveBomberSquadronSize = squadrons[i][1];
        }
        else if (squadrons[i][0] == 'Fighter')
        {
            fighterSquadronSize = squadrons[i][1];
        }
        else if (squadrons[i][0] == 'Scout')
        {
            scoutSquadronSize = squadrons[i][1];
        }
    }
    
    torpedoBomberCount = planesReserveCapacity / totalSquadronCount * torpedoBomberSquadronCount;
    diveBomberCount = planesReserveCapacity / totalSquadronCount * diveBomberSquadronCount;
    fighterCount = planesReserveCapacity / totalSquadronCount * fighterSquadronCount;
    scoutCount = planesReserveCapacity / totalSquadronCount * scoutSquadronCount;

    refresh();
}

function setFighterStats(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;

    if (typeof id == 'string')
    {
        API_JSON = apiFighterUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_fighterKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_fighterKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_fighterKey];
    var components = module['components'];
    var fighter = components['fighter'];

    var planeType = gpShipJSON[fighter[fighter.length - 1]]['planeType'];
    var GP_FighterJSON;

    $.ajax({
        url: "/GameParams/name/" + planeType,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_FighterJSON = data;
        }
    });


    refresh();
}

function setTorpedoBomberStats(id)
{


    refresh();
}

function setDiveBomberStats(id)
{


    refresh();
}