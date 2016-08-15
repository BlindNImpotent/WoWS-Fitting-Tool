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
        // document.getElementsByClassName("fighterName")[0].setAttribute("id", id);

        fighterName = apiFighterUpgradeJSON[id]['name'];

        refresh();
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
        // document.getElementsByClassName("fighterName")[0].setAttribute("id", id['index']);
        fighterName = id['name'];
        refresh();
    }
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
        // document.getElementsByClassName("torpedoBomberName")[0].setAttribute("id", id);

        torpedoBomberName = apiTorpedoBomberUpgradeJSON[id]['name'];

        refresh();
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
        // document.getElementsByClassName("torpedoBomberName")[0].setAttribute("id", id['index']);

        torpedoBomberName = id['name'];

        refresh();
    }
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
        // document.getElementsByClassName("diveBomberName")[0].setAttribute("id", id);

        diveBomberName = apiDiveBomberUpgradeJSON[id]['name'];

        refresh();
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]['index']).className = "button_module";
        }
        document.getElementById(id['index']).className = "button_module_selected";
        // document.getElementsByClassName("diveBomberName")[0].setAttribute("id", id['index']);

        diveBomberName = id['name'];

        refresh();
    }
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

    turretBarrelDiameter = GP_TurretJSON['barrelDiameter'];

    var ammoList = GP_TurretJSON['ammoList'];

    ammoList.sort();

    var Shell1;
    $.ajax({
        url: "/GameParams/name/" + ammoList[0],
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            Shell1 = data;
        }
    });

    var Shell2;
    $.ajax({
        url: "/GameParams/name/" + ammoList[1],
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            Shell2 = data;
        }
    });
    if (Shell1['ammoType'] == 'AP')
    {
        APShellSpeed = Shell1['bulletSpeed'];
        APShellDMG = Shell1['alphaDamage'];
        HEShellSpeed = Shell2['bulletSpeed'];
        HEShellDMG = Shell2['alphaDamage'];
        HEShellBurnProb = Shell2['burnProb'];
    }
    else if (Shell2['ammoType'] == 'AP')
    {
        APShellSpeed = Shell2['bulletSpeed'];
        APShellDMG = Shell2['alphaDamage'];
        HEShellSpeed = Shell1['bulletSpeed'];
        HEShellDMG = Shell1['alphaDamage'];
        HEShellBurnProb = Shell1['burnProb'];
    }

    mainGunRotation = GP_TurretJSON['rotationSpeed'][0];
    // if (mainGunRotation != 0)
    // {
    //     mainGunRotationTime = 180 / mainGunRotation;
    // }

    mainGunReload = GP_TurretJSON['shotDelay'];

    mainGunDispersionTangent = GP_TurretJSON['idealRadius'];
    mainGunDispersionTangent = mainGunDispersionTangent * scaleDist * Math.PI / 180;
    mainGunDispersionTangent = Math.tan(mainGunDispersionTangent);

    dualPurpose();

    refresh();
}

function dualPurpose()
{
    if (turretBarrelDiameter < 0.140 && GP_TurretJSON != null)
    {
        antiAirAuraDistanceFar = GP_TurretJSON['antiAirAuraDistance'] * scaleDist;

        AAFarDPS = GP_TurretJSON['antiAirAuraStrength'] * numTurrets * 100;

        AAFarBarrelDiameter = turretBarrelDiameter;
    }

    refresh();
}

function setHullStats(id)
{
    if (turretBarrelDiameter >= 0.140)
    {
        antiAirAuraDistanceFar = 0;
        AAFarDPS = 0;
    }
    antiAirAuraDistanceMedium = 0;
    AAMediumDPS = 0;
    antiAirAuraDistanceNear = 0;
    AANearDPS = 0;
    secondaryMaxDist = 0;

    planesReserveCapacity = 0;
    numTurrets = 0;

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
    var API_module_id = API_JSON['module_id'];

    $.ajax({
        url: "/API/module_hull/" + API_module_id,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            numTurrets = data['profile']['hull']['artillery_barrels'];
        }
    });

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


    if (turretBarrelDiameter >= 0.140 || GP_TurretJSON == null)
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
                    antiAirAuraDistanceFar = AAFarGun['antiAirAuraDistance'] * scaleDist;

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
                antiAirAuraDistanceMedium = AAMediumGun['antiAirAuraDistance'] * scaleDist;

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
                antiAirAuraDistanceNear = AANearGun['antiAirAuraDistance'] * scaleDist;

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
                    antiAirAuraDistanceNear = AANearGun['antiAirAuraDistance'] * scaleDist;

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

    dualPurpose();

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
    maxTorpedoRange = ammoObj['maxDist'] * scaleDist;
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

function showPlanes(id)
{
    if (id == null)
    {
        return;
    }
    var API_JSON;
    var test1;
    var test2;
    var test3;

    if (typeof id == 'string')
    {
        test1 = apiFighterUpgradeJSON[id];
        test2 = apiTorpedoBomberUpgradeJSON[id];
        test3 = apiDiveBomberUpgradeJSON[id];
    }
    else
    {
        API_JSON = id['json'];
    }

    if (test1 != null)
    {
        API_JSON = test1;
    }
    else if (test2 != null)
    {
        API_JSON = test2;
    }
    else if (test3 != null)
    {
        API_JSON = test3;
    }

    var API_module_id_str = API_JSON['module_id_str'];
    var GP_planeKey;
    $.ajax({
        url: "/GameParams/index/" + API_module_id_str,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_planeKey = data['name'];
        }
    });

    var module = gpShipUpgradeInfo[GP_planeKey];
    var components = module['components'];
    var plane;

    if (test1 != null)
    {
        plane = components['fighter'];
    }
    else if (test2 != null)
    {
        plane = components['torpedoBomber'];
    }
    else if (test3 != null)
    {
        plane = components['diveBomber'];
    }

    var planeType = gpShipJSON[plane[plane.length - 1]]['planeType'];
    var GP_PlaneJSON;

    $.ajax({
        url: "/GameParams/name/" + planeType,
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            GP_PlaneJSON = data;
        }
    });

    if (GP_PlaneJSON['typeinfo']['species'] == 'Fighter')
    {
        fighterVitalityTime = GP_PlaneJSON['vitalityTime'];
        fighterAaAmmoAmount = GP_PlaneJSON['aaAmmoAmount'];
        fighterPrepareTime = GP_PlaneJSON['prepareTime'];
        fighterFlightHeight = GP_PlaneJSON['flightHeight'] * scaleDist;
        fighterSpeedMove = GP_PlaneJSON['speedMove'];
        fighterPlaneTurnRadius = GP_PlaneJSON['planeTurnRadius'] * scaleDist;
        fighterSquadronTurnRadius = GP_PlaneJSON['squadronTurnRadius'] * scaleDist;
        fighterActiveAntiAirAura = GP_PlaneJSON['activeAntiAirAura'];
        fighterPatrolAttackDist = GP_PlaneJSON['patrolAttackDist'] * scaleDist;
        fighterPatrolChaseDist = GP_PlaneJSON['patrolChaseDist'] * scaleDist;
        fighterEscortShipFollowDistance = GP_PlaneJSON['escortShipFollowDistance'] * scaleDist;
        fighterEscortShipAttackDist = GP_PlaneJSON['escortShipAttackDist'] * scaleDist;
        fighterEscortShipChaseDist = GP_PlaneJSON['escortShipChaseDist'] * scaleDist;
        fighterEscortPlaneFollowDistance = GP_PlaneJSON['escortPlaneFollowDistance'] * scaleDist;
        fighterEscortPlaneAttackDist = GP_PlaneJSON['escortPlaneAttackDist'] * scaleDist;
        fighterEscortPlaneChaseDist = GP_PlaneJSON['escortPlaneChaseDist'] * scaleDist;
        fighterVisionToPlane = GP_PlaneJSON['visionToPlane'];
        fighterVisionToShip = GP_PlaneJSON['visionToShip'];
        fighterVisionToTorpedo = GP_PlaneJSON['visionToTorpedo'];

        // var div = document.createElement('div');
        // div.setAttribute('id', 'fighterStats');
        // div.innerHTML += 'Vitality<span style="padding-left: 6em" class="fighterVitalityTime"/>'





    }



    refresh();
}
