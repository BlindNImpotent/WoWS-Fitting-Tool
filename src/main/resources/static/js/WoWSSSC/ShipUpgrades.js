function mod(id, list)
{
    if (document.getElementById(id).className == "button_upgrade_selected")
    {
        document.getElementById(id).className = "button_upgrade"
        reset(id)
        return;
    }

    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_upgrade";
            reset(list[i]);
        }
        document.getElementById(id).className = "button_upgrade_selected";

    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_upgrade";
            reset(list[i]);
        }
        document.getElementById(id).className = "button_upgrade_selected";
    }

    modCalc(id, list)
}

function modCalc(id, list)
{
    for (var i in list)
    {
        reset(list[i]);
    }

    var moduleData;
    if (id != 'None1' && id != 'None2' && id != 'None3' && id != 'None4' && id != 'None5' && id != 'None6')
    {
        $.ajax({
            url: "/GameParams/name/" + id,
            type: "GET",
            async: false,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                moduleData = data;
            }
        });
    }

    if (id == 'PCM030_MainWeapon_Mod_I')
    {
        MAM1_GMCritProb = moduleData['GMCritProb'];
        MAM1_GMMaxHP = moduleData['GMMaxHP'];
        MAM1_GMRepairTime = moduleData['GMRepairTime'];
        MAM1_GTCritProb = moduleData['GTCritProb'];
        MAM1_GTMaxHP = moduleData['GTMaxHP'];
        MAM1_GTRepairTime = moduleData['GTRepairTime'];
    }
    else if (id == 'PCM031_SecondaryWeapon_Mod_I')
    {
        AAM1_ADMaxHP = moduleData['ADMaxHP'];
        AAM1_GSMaxHP = moduleData['GSMaxHP'];
    }
    else if (id == 'PCM032_PowderMagazine_Mod_I')
    {
        MM1_PMDetonationProb = moduleData['PMDetonationProb'];
    }
    else if (id == 'PCM033_Guidance_Mod_I')
    {
        ASM1_GMIdealRadius = moduleData['GMIdealRadius'];
        ASM1_GSIdealRadius = moduleData['GSIdealRadius'];
        ASM1_GSMaxDist = moduleData['GSMaxDist'];
        ASM1_GTRotationSpeed = moduleData['GTRotationSpeed'];
    }
    else if (id == 'PCM028_FireControl_Mod_I_US')
    {
        APRM1US_GMMaxDist = moduleData['GMMaxDist'];
        APRM1US_GSMaxDist = moduleData['GSMaxDist'];
        APRM1US_GSIdealRadius = moduleData['GSIdealRadius'];
    }
    else if (id == 'PCM006_MainGun_Mod_II')
    {
        MBM2_GMRotationSpeed = moduleData['GMRotationSpeed'];
        MBM2_GMShotDelay = moduleData['GMShotDelay'];
    }
    else if (id == 'PCM011_AirDefense_Mod_II')
    {
        AAGM2_AAMaxDist = moduleData['AAMaxDist'];
    }
    else if (id == 'PCM012_SecondaryGun_Mod_II')
    {
        SBM2_GSMaxDist = moduleData['GSMaxDist'];
        SBM2_GSIdealRadius = moduleData['GSIdealRadius'];
    }
    else if (id == 'PCM013_MainGun_Mod_III')
    {
        MBM3_GMRotationSpeed = moduleData['GMRotationSpeed'];
        MBM3_GMShotDelay = moduleData['GMShotDelay'];
    }
    else if (id == 'PCM014_Torpedo_Mod_III')
    {
        TTMM3_GTCritProb = moduleData['GTCritProb'];
        TTMM3_GTShotDelay = moduleData['GTShotDelay'];
    }
    else if (id == 'PCM015_FireControl_Mod_II')
    {
        GFCSM2_GMMaxDist = moduleData['GMMaxDist'];
    }
    else if (id == 'PCM018_AirDefense_Mod_III')
    {
        AAGM3_AAAura = moduleData['AAAura'];
    }
    else if (id == 'PCM019_SecondaryGun_Mod_III')
    {
        SBM3_GSShotDelay = moduleData['GSShotDelay'];
    }
    else if (id == 'PCM029_FireControl_Mod_II_US')
    {
        APRM2US_GMIdealRadius = moduleData['GMIdealRadius'];
    }
    else if (id == 'PCM020_DamageControl_Mod_I')
    {
        DCSM1_burnProb = moduleData['burnProb'];
        DCSM1_floodProb = moduleData['floodProb'];
    }
    else if (id == 'PCM021_Engine_Mod_I')
    {
        PM1_engineCritProb = moduleData['engineCritProb'];
        PM1_engineRepairTime = moduleData['engineRepairTime'];
    }
    else if (id == 'PCM022_SteeringGear_Mod_I')
    {
        SGM1_SGCritProb = moduleData['SGCritProb'];
        SGM1_SGRepairTime = moduleData['SGRepairTime'];
    }
    else if (id == 'PCM023_DamageControl_Mod_II')
    {
        DCSM2_burnTime = moduleData['burnTime'];
        DCSM2_floodTime = moduleData['floodTime'];
    }
    else if (id == 'PCM024_Engine_Mod_II')
    {
        PM2_engineBackwardForsageMaxSpeed = moduleData['engineBackwardForsageMaxSpeed'];
        PM2_engineBackwardUpTime = moduleData['engineBackwardUpTime'];
        PM2_engineForwardForsageMaxSpeed = moduleData['engineForwardForsageMaxSpeed'];
        PM2_engineForwardUpTime = moduleData['engineForwardUpTime'];
    }
    else if (id == 'PCM025_SteeringGear_Mod_II')
    {
        SGM2_SGRudderTime = moduleData['SGRudderTime'];
    }
    else if (id == 'PCM026_LookoutStation_Mod_I')
    {
        TASM1_visionDistCoeff = moduleData['visionDistCoeff'];
        TASM1_visionTorpedoCoeff = moduleData['visionTorpedoCoeff'];
        TASM1_visionXRayShipCoeff = moduleData['visionXRayShipCoeff'];
    }
    else if (id == 'PCM027_ConcealmentMeasures_Mod_I')
    {
        CSM1_visibilityDistCoeff = moduleData['visibilityDistCoeff'];
    }
    else if (id == 'PCM003_Airplanes_Mod_I')
    {
        AGM1_airplanesAntiAirAura = moduleData['airplanesAntiAirAura'];
    }
    else if (id == 'PCM009_FlightControl_Mod_I')
    {
        FCM1_airplanesPrepareTime = moduleData['airplanesPrepareTime'];
    }
    else if (id == 'PCM010_Airplanes_Mod_II')
    {
        AGM2_airplanesFighterVitalityTime = moduleData['airplanesFighterVitalityTime']
    }
    else if (id =='PCM016_FlightControl_Mod_II')
    {
        FCM2_airplanesSpeed = moduleData['airplanesSpeed'];
    }
    else if (id == 'PCM017_Airplanes_Mod_III')
    {
        AGM3_airplanesBomberVitalityTime = moduleData['airplanesBomberVitalityTime'];
    }
    else if (id == 'PCM034_Guidance_Mod_0')
    {
        ASM0_GMIdealRadius = moduleData['GMIdealRadius'];
        ASM0_GMRotationSpeed = moduleData['GMRotationSpeed'];
        ASM0_GSIdealRadius = moduleData['GSIdealRadius'];
        ASM0_GSMaxDist = moduleData['GSMaxDist'];
        ASM0_GTRotationSpeed = moduleData['GTRotationSpeed'];
    }
    // else if (id == 'None1' || id == 'None2' || id == 'None3' || id == 'None4' || id == 'None5' || id == 'None6')
    // {
    //
    // }
    else
    {
        console.log(moduleData);
    }

    refresh();
}

function reset(id)
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