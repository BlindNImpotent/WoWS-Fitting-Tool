//    <![CDATA[


function setUpgrades()
{
    var table = document.getElementById('upgradeTable');
    var row0 = table.insertRow(0);
    var row1 = table.insertRow(1);
    var row2 = table.insertRow(2);
    var row3 = table.insertRow(3);
    var row4 = table.insertRow(4);

    for (var i = 0; i < 6; i++)
    {
        row0.insertCell(i)
        row1.insertCell(i)
        row2.insertCell(i)
        row3.insertCell(i)
        row4.insertCell(i)
    }

    for (var i = 0; i < upgrades1.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades1[i]['image'];
        table.rows[i].cells[upgrades1[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades1[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades1[i]['name'] + "</button>";
        document.getElementById(upgrades1[i]['code']).className = "button_upgrade";
    }
    for (var i = 0; i < upgrades2.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades2[i]['image'];
        table.rows[i].cells[upgrades2[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades2[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades2[i]['name'] + "</button>";
        document.getElementById(upgrades2[i]['code']).className = "button_upgrade";
    }
    for (var i = 0; i < upgrades3.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades3[i]['image'];
        table.rows[i].cells[upgrades3[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades3[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades3[i]['name'] + "</button>";
        document.getElementById(upgrades3[i]['code']).className = "button_upgrade";
    }
    for (var i = 0; i < upgrades4.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades4[i]['image'];
        table.rows[i].cells[upgrades4[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades4[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades4[i]['name'] + "</button>";
        document.getElementById(upgrades4[i]['code']).className = "button_upgrade";
    }
    for (var i = 0; i < upgrades5.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades5[i]['image'];
        table.rows[i].cells[upgrades5[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades5[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades5[i]['name'] + "</button>";
        document.getElementById(upgrades5[i]['code']).className = "button_upgrade";
    }
    for (var i = 0; i < upgrades6.length; i++)
    {
        var imgSrc = upgradeImageLocation + upgrades6[i]['image'];
        table.rows[i].cells[upgrades6[i]['slot']].innerHTML = "<button onclick=upgrade(id) id=" + upgrades6[i]['code'] + "><img src=" + imgSrc + "><br />" + upgrades6[i]['name'] + "</button>";
        document.getElementById(upgrades6[i]['code']).className = "button_upgrade";
    }
}


function upgrade(id)
{
    if (document.getElementById(id).className == "button_upgrade_selected")
    {
        document.getElementById(id).className = "button_upgrade"
        resetUpgrades(id)
        return;
    }
    
    var list;
    var json;
    for (var i in upgrades1)
    {
        if (upgrades1[i]['code'] == id)
        {
            list = upgrades1;
            json = upgrades1[i]['json'];
        }
    }
    for (var i in upgrades2)
    {
        if (upgrades2[i]['code'] == id)
        {
            list = upgrades2;
            json = upgrades2[i]['json'];
        }
    }
    for (var i in upgrades3)
    {
        if (upgrades3[i]['code'] == id)
        {
            list = upgrades3;
            json = upgrades3[i]['json'];
        }
    }
    for (var i in upgrades4)
    {
        if (upgrades4[i]['code'] == id)
        {
            list = upgrades4;
            json = upgrades4[i]['json'];
        }
    }
    for (var i in upgrades5)
    {
        if (upgrades5[i]['code'] == id)
        {
            list = upgrades5;
            json = upgrades5[i]['json'];
        }
    }
    for (var i in upgrades6)
    {
        if (upgrades6[i]['code'] == id)
        {
            list = upgrades6;
            json = upgrades6[i]['json'];
        }
    }

    for (var i in list)
    {
        document.getElementById(list[i]['code']).className = "button_upgrade";
        resetUpgrades(list[i]['code']);
    }
    document.getElementById(id).className = "button_upgrade_selected";
    modCalc(id, list, json)
}

function modCalc(id, list, json)
{
    for (var i in list)
    {
        resetUpgrades(list[i]['code']);
    }

    if (id == 'PCM030_MainWeapon_Mod_I')
    {
        MAM1_GMCritProb = json['GMCritProb'];
        MAM1_GMMaxHP = json['GMMaxHP'];
        MAM1_GMRepairTime = json['GMRepairTime'];
        MAM1_GTCritProb = json['GTCritProb'];
        MAM1_GTMaxHP = json['GTMaxHP'];
        MAM1_GTRepairTime = json['GTRepairTime'];
    }
    else if (id == 'PCM031_SecondaryWeapon_Mod_I')
    {
        AAM1_ADMaxHP = json['ADMaxHP'];
        AAM1_GSMaxHP = json['GSMaxHP'];
    }
    else if (id == 'PCM032_PowderMagazine_Mod_I')
    {
        MM1_PMDetonationProb = json['PMDetonationProb'];
    }
    else if (id == 'PCM033_Guidance_Mod_I')
    {
        ASM1_GMIdealRadius = json['GMIdealRadius'];
        ASM1_GSIdealRadius = json['GSIdealRadius'];
        ASM1_GSMaxDist = json['GSMaxDist'];
        ASM1_GTRotationSpeed = json['GTRotationSpeed'];
    }
    else if (id == 'PCM028_FireControl_Mod_I_US')
    {
        APRM1US_GMMaxDist = json['GMMaxDist'];
        APRM1US_GSMaxDist = json['GSMaxDist'];
        APRM1US_GSIdealRadius = json['GSIdealRadius'];
    }
    else if (id == 'PCM006_MainGun_Mod_II')
    {
        MBM2_GMRotationSpeed = json['GMRotationSpeed'];
        MBM2_GMShotDelay = json['GMShotDelay'];
    }
    else if (id == 'PCM011_AirDefense_Mod_II')
    {
        AAGM2_AAMaxDist = json['AAMaxDist'];
    }
    else if (id == 'PCM012_SecondaryGun_Mod_II')
    {
        SBM2_GSMaxDist = json['GSMaxDist'];
        SBM2_GSIdealRadius = json['GSIdealRadius'];
    }
    else if (id == 'PCM013_MainGun_Mod_III')
    {
        MBM3_GMRotationSpeed = json['GMRotationSpeed'];
        MBM3_GMShotDelay = json['GMShotDelay'];
    }
    else if (id == 'PCM014_Torpedo_Mod_III')
    {
        TTMM3_GTCritProb = json['GTCritProb'];
        TTMM3_GTShotDelay = json['GTShotDelay'];
    }
    else if (id == 'PCM015_FireControl_Mod_II')
    {
        GFCSM2_GMMaxDist = json['GMMaxDist'];
    }
    else if (id == 'PCM018_AirDefense_Mod_III')
    {
        AAGM3_AAAura = json['AAAura'];
    }
    else if (id == 'PCM019_SecondaryGun_Mod_III')
    {
        SBM3_GSShotDelay = json['GSShotDelay'];
    }
    else if (id == 'PCM029_FireControl_Mod_II_US')
    {
        APRM2US_GMIdealRadius = json['GMIdealRadius'];
    }
    else if (id == 'PCM020_DamageControl_Mod_I')
    {
        DCSM1_burnProb = json['burnProb'];
        DCSM1_floodProb = json['floodProb'];
    }
    else if (id == 'PCM021_Engine_Mod_I')
    {
        PM1_engineCritProb = json['engineCritProb'];
        PM1_engineRepairTime = json['engineRepairTime'];
    }
    else if (id == 'PCM022_SteeringGear_Mod_I')
    {
        SGM1_SGCritProb = json['SGCritProb'];
        SGM1_SGRepairTime = json['SGRepairTime'];
    }
    else if (id == 'PCM023_DamageControl_Mod_II')
    {
        DCSM2_burnTime = json['burnTime'];
        DCSM2_floodTime = json['floodTime'];
    }
    else if (id == 'PCM024_Engine_Mod_II')
    {
        PM2_engineBackwardForsageMaxSpeed = json['engineBackwardForsageMaxSpeed'];
        PM2_engineBackwardUpTime = json['engineBackwardUpTime'];
        PM2_engineForwardForsageMaxSpeed = json['engineForwardForsageMaxSpeed'];
        PM2_engineForwardUpTime = json['engineForwardUpTime'];
    }
    else if (id == 'PCM025_SteeringGear_Mod_II')
    {
        SGM2_SGRudderTime = json['SGRudderTime'];
    }
    else if (id == 'PCM026_LookoutStation_Mod_I')
    {
        TASM1_visionDistCoeff = json['visionDistCoeff'];
        TASM1_visionTorpedoCoeff = json['visionTorpedoCoeff'];
        TASM1_visionXRayShipCoeff = json['visionXRayShipCoeff'];
    }
    else if (id == 'PCM027_ConcealmentMeasures_Mod_I')
    {
        CSM1_visibilityDistCoeff = json['visibilityDistCoeff'];
    }
    else if (id == 'PCM003_Airplanes_Mod_I')
    {
        AGM1_airplanesAntiAirAura = json['airplanesAntiAirAura'];
    }
    else if (id == 'PCM009_FlightControl_Mod_I')
    {
        FCM1_airplanesPrepareTime = json['airplanesPrepareTime'];
    }
    else if (id == 'PCM010_Airplanes_Mod_II')
    {
        AGM2_airplanesFighterVitalityTime = json['airplanesFighterVitalityTime']
    }
    else if (id =='PCM016_FlightControl_Mod_II')
    {
        FCM2_airplanesSpeed = json['airplanesSpeed'];
    }
    else if (id == 'PCM017_Airplanes_Mod_III')
    {
        AGM3_airplanesBomberVitalityTime = json['airplanesBomberVitalityTime'];
    }
    else if (id == 'PCM034_Guidance_Mod_0')
    {
        ASM0_GMIdealRadius = json['GMIdealRadius'];
        ASM0_GMRotationSpeed = json['GMRotationSpeed'];
        ASM0_GSIdealRadius = json['GSIdealRadius'];
        ASM0_GSMaxDist = json['GSMaxDist'];
        ASM0_GTRotationSpeed = json['GTRotationSpeed'];
    }
    // else if (id == 'None1' || id == 'None2' || id == 'None3' || id == 'None4' || id == 'None5' || id == 'None6')
    // {
    //
    // }
    else
    {
        console.log(json);
    }

    refresh();
}

function resetUpgrades(id)
{
    if (id == 'PCM030_MainWeapon_Mod_I')
    {
        MAM1_GMCritProb = 1;
        MAM1_GMMaxHP = 1;
        MAM1_GMRepairTime = 1;
        MAM1_GTCritProb = 1;
        MAM1_GTMaxHP = 1;
        MAM1_GTRepairTime = 1;
    }
    if (id == 'PCM031_SecondaryWeapon_Mod_I')
    {
        AAM1_ADMaxHP = 1;
        AAM1_GSMaxHP = 1;
    }
    if (id == 'PCM032_PowderMagazine_Mod_I')
    {
        MM1_PMDetonationProb = 1;
    }
    if (id == 'PCM033_Guidance_Mod_I')
    {
        ASM1_GMIdealRadius = 1;
        ASM1_GSIdealRadius = 1;
        ASM1_GSMaxDist = 1;
        ASM1_GTRotationSpeed = 1;
    }
    if (id == 'PCM033_Guidance_Mod_I')
    {
        APRM1US_GMMaxDist = 1;
        APRM1US_GSMaxDist = 1;
        APRM1US_GSIdealRadius = 1;
    }
    if (id == 'PCM028_FireControl_Mod_I_US')
    {
        APRM1US_GMMaxDist = 1;
        APRM1US_GSMaxDist = 1;
        APRM1US_GSIdealRadius = 1;
    }
    if (id == 'PCM006_MainGun_Mod_II')
    {
        MBM2_GMRotationSpeed = 1;
        MBM2_GMShotDelay = 1;
    }
    if (id == 'PCM011_AirDefense_Mod_II')
    {
        AAGM2_AAMaxDist = 1;
    }
    if (id == 'PCM012_SecondaryGun_Mod_II')
    {
        SBM2_GSMaxDist = 1;
        SBM2_GSIdealRadius = 1;
    }
    if (id == 'PCM013_MainGun_Mod_III')
    {
        MBM3_GMRotationSpeed = 1;
        MBM3_GMShotDelay = 1;
    }
    if (id == 'PCM014_Torpedo_Mod_III')
    {
        TTMM3_GTCritProb = 1;
            TTMM3_GTShotDelay = 1;
    }
    if (id == 'PCM015_FireControl_Mod_II')
    {
        GFCSM2_GMMaxDist = 1;
    }
    if (id == 'PCM018_AirDefense_Mod_III')
    {
        AAGM3_AAAura = 1;
    }
    if (id == 'PCM019_SecondaryGun_Mod_III')
    {
        SBM3_GSShotDelay = 1;
    }
    if (id == 'PCM029_FireControl_Mod_II_US')
    {
        APRM2US_GMIdealRadius = 1;
    }
    if (id == 'PCM020_DamageControl_Mod_I')
    {
        DCSM1_burnProb = 1;
        DCSM1_floodProb = 1;
    }
    if (id == 'PCM021_Engine_Mod_I')
    {
        PM1_engineCritProb = 1;
        PM1_engineRepairTime = 1;
    }
    if (id == 'PCM022_SteeringGear_Mod_I')
    {
        SGM1_SGCritProb = 1;
        SGM1_SGRepairTime = 1;
    }
    if (id == 'PCM023_DamageControl_Mod_II')
    {
        DCSM2_burnTime = 1;
        DCSM2_floodTime = 1;
    }
    if (id == 'PCM024_Engine_Mod_II')
    {
        PM2_engineBackwardForsageMaxSpeed = 1;
        PM2_engineBackwardUpTime = 1;
        PM2_engineForwardForsageMaxSpeed = 1;
        PM2_engineForwardUpTime = 1;
    }
    if (id == 'PCM025_SteeringGear_Mod_II')
    {
        SGM2_SGRudderTime = 1;
    }
    if (id == 'PCM026_LookoutStation_Mod_I')
    {
        TASM1_visionDistCoeff = 1;
        TASM1_visionTorpedoCoeff = 1;
        TASM1_visionXRayShipCoeff = 1;
    }
    if (id == 'PCM027_ConcealmentMeasures_Mod_I')
    {
        CSM1_visibilityDistCoeff = 1;
    }
    if (id == 'PCM003_Airplanes_Mod_I')
    {
        AGM1_airplanesAntiAirAura =1;
    }
    if (id == 'PCM009_FlightControl_Mod_I')
    {
        FCM1_airplanesPrepareTime = 1;
    }
    if (id == 'PCM010_Airplanes_Mod_II')
    {
        AGM2_airplanesFighterVitalityTime = 1;
    }
    if (id =='PCM016_FlightControl_Mod_II')
    {
        FCM2_airplanesSpeed = 1;
    }
    if (id == 'PCM017_Airplanes_Mod_III')
    {
        AGM3_airplanesBomberVitalityTime = 1;
    }
    if (id == 'PCM034_Guidance_Mod_0')
    {
        ASM0_GMIdealRadius = 1;
        ASM0_GMRotationSpeed = 1;
        ASM0_GSIdealRadius = 1;
        ASM0_GSMaxDist = 1;
        ASM0_GTRotationSpeed = 1;
    }
    
    refresh();
}

//    ]]>
