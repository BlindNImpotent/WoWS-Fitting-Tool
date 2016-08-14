//    <![CDATA[

function flag(code)
{
    // var idList = list.substring(1, list.length - 1);
    // idList = idList.split(', ');

    if (document.getElementById(code).className == "button_flag_selected")
    {
        document.getElementById(code).className = "button_flag";

        resetFlags(code);
    }
    else if(document.getElementById(code).className == "button_flag")
    {
        document.getElementById(code).className = "button_flag_selected"


        var flagJSON;
        $.ajax({
            url: "/GameParams/name/" + code,
            type: "GET",
            async: false,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                flagJSON = data;
            }
        });

        if (code == 'PCEF001_Zulu_SignalFlag')
        {
            creditsFactorFlag = flagJSON['creditsFactor'];
        }
        else if (code == 'PCEF003_EqualSpeed_SignalFlag')
        {
            expFactorFlag = flagJSON['expFactor'];
        }
        else if (code == 'PCEF005_SM_SignalFlag')
        {
            speedCoefFlag = flagJSON['speedCoef'];
        }
        else if (code == 'PCEF006_HY_SignalFlag')
        {
            collisionDamageApplyFlag = flagJSON['collisionDamageApply'];
            collisionDamageNerfFlag = flagJSON['collisionDamageNerf'];
        }
        else if (code == 'PCEF007_ID_SignalFlag')
        {
            regenerationHPSpeedFlag = flagJSON['regenerationHPSpeed'];
        }
        else if (code == 'PCEF008_IY_SignalFlag')
        {
            burnTimeFlag = flagJSON['burnTime'];
        }
        else if (code == 'PCEF009_JY2_SignalFlag')
        {
            floodTimeFlag = flagJSON['floodTime'];
        }
        else if (code == 'PCEF010_JC_SignalFlag')
        {
            PMDetonationProbFlag_JC = flagJSON['PMDetonationProb'];
        }
        else if (code == 'PCEF011_IB3_SignalFlag')
        {
            afterBattleRepairFlag = flagJSON['afterBattleRepair'];
        }
        else if (code == 'PCEF012_MY6_SignalFlag')
        {
            GSIdealRadiusFlag = flagJSON['GSIdealRadius'];
            GSShotDelayFlag = flagJSON['GSShotDelay'];
        }
        else if (code == 'PCEF013_PP_SignalFlag')
        {
            freeExpFactorFlag = flagJSON['freeExpFactor'];
        }
        else if (code == 'PCEF014_NF_SignalFlag')
        {
            abilReloadTimeFactorFlag = flagJSON['abilReloadTimeFactor'];
        }
        else if (code == 'PCEF015_ZH_SignalFlag')
        {
            crewExpFactorFlag_ZH = flagJSON['crewExpFactor'];
        }
        else if (code == 'PCEF016_NE7_SignalFlag')
        {
            AAAuraFlag = flagJSON['AAAura'];
            AAPassiveAuraFlag = flagJSON['AAPassiveAura'];
        }
        else if (code == 'PCEF017_VL_SignalFlag')
        {
            burnChanceFactorBigFlag_VL = flagJSON['burnChanceFactorBig'] - 1;
            burnChanceFactorSmallFlag_VL = flagJSON['burnChanceFactorSmall'] - 1;
            floodChanceFactorFlag_VL = flagJSON['floodChanceFactor'];
        }
        else if (code == 'PCEF018_IX_SignalFlag')
        {
            PMDetonationProbFlag_IX = flagJSON['PMDetonationProb'];
            burnChanceFactorBigFlag_IX = flagJSON['burnChanceFactorBig'] - 1;
            burnChanceFactorSmallFlag_IX = flagJSON['burnChanceFactorSmall'] - 1;
        }
        else if (code == 'PCEF019_JW1_SignalFlag')
        {
            PMDetonationProbFlag_JW1 = flagJSON['PMDetonationProb'];
            floodChanceFactorFlag_JW1 = flagJSON['floodChanceFactor'];
        }
        else if (code == 'PCEF020_Blue_Dragon_Flag')
        {
            crewExpFactorFlag_BD = flagJSON['crewExpFactor'];
        }
    }

    refresh();
}

function setFlag()
{
    var table = document.getElementById('flagTable');
    var row0 = table.insertRow(0);
    var row1 = table.insertRow(1);
    var row2 = table.insertRow(2);
    var row3 = table.insertRow(3);

    for (var i = 0; i < Math.ceil(flagsList.length / 3); i++)
    {
        row0.insertCell(i);
        row1.insertCell(i);
        row2.insertCell(i);
        row3.insertCell(i);
    }

    for (var i = 0; i < flagsList.length; i++)
    {
        flagCodeList.push(flagsList[i]['code']);
    }

    for (var i = 0; i < flagsList.length; i++)
    {
        var imgSrc = flagImageLocation + flagsList[i]['code'] + '.png';

        if (i < Math.ceil(flagsList.length / 3))
        {
            table.rows[0].cells[i].innerHTML = "<button onclick=flag(id) id=" + flagsList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + flagsList[i]['name'] + "</button>";
        }
        else if (i >= Math.ceil(flagsList.length / 3) && i < Math.ceil(flagsList.length / 3) * 2)
        {
            table.rows[1].cells[i - Math.ceil(flagsList.length / 3)].innerHTML = "<button onclick=flag(id) id=" + flagsList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + flagsList[i]['name'] + "</button>";
        }
        else if (i >= Math.ceil(flagsList.length / 3) * 2 && i < flagsList.length)
        {
            table.rows[2].cells[i - Math.ceil(flagsList.length / 3) * 2].innerHTML = "<button onclick=flag(id) id=" + flagsList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + flagsList[i]['name'] + "</button>";
        }

        document.getElementById(flagsList[i]['code']).className = "button_flag";
    }
}

function resetFlags(code)
{
    if (code == 'PCEF001_Zulu_SignalFlag')
    {
        creditsFactorFlag = 1;
    }
    else if (code == 'PCEF003_EqualSpeed_SignalFlag')
    {
        creditsFactorFlag = 1;
    }
    else if (code == 'PCEF005_SM_SignalFlag')
    {
        speedCoefFlag = 1;
    }
    else if (code == 'PCEF006_HY_SignalFlag')
    {
        collisionDamageApplyFlag = 1;
        collisionDamageNerfFlag = 1;
    }
    else if (code == 'PCEF007_ID_SignalFlag')
    {
        regenerationHPSpeedFlag = 1;
    }
    else if (code == 'PCEF008_IY_SignalFlag')
    {
        burnTimeFlag = 1;
    }
    else if (code == 'PCEF009_JY2_SignalFlag')
    {
        floodTimeFlag = 1;
    }
    else if (code == 'PCEF010_JC_SignalFlag')
    {
        PMDetonationProbFlag_JC = 1;
    }
    else if (code == 'PCEF011_IB3_SignalFlag')
    {
        afterBattleRepairFlag = 1;
    }
    else if (code == 'PCEF012_MY6_SignalFlag')
    {
        GSIdealRadiusFlag = 1;
        GSShotDelayFlag = 1;
    }
    else if (code == 'PCEF013_PP_SignalFlag')
    {
        freeExpFactorFlag = 1;
    }
    else if (code == 'PCEF014_NF_SignalFlag')
    {
        abilReloadTimeFactorFlag = 1;
    }
    else if (code == 'PCEF015_ZH_SignalFlag')
    {
        crewExpFactorFlag_ZH = 1;
    }
    else if (code == 'PCEF016_NE7_SignalFlag')
    {
        AAAuraFlag = 1;
        AAPassiveAuraFlag = 1;
    }
    else if (code == 'PCEF017_VL_SignalFlag')
    {
        burnChanceFactorBigFlag_VL = 0;
        burnChanceFactorSmallFlag_VL = 0;
        floodChanceFactorFlag_VL = 1;
    }
    else if (code == 'PCEF018_IX_SignalFlag')
    {
        PMDetonationProbFlag_IX = 1;
        burnChanceFactorBigFlag_IX = 0;
        burnChanceFactorSmallFlag_IX = 0;
    }
    else if (code == 'PCEF019_JW1_SignalFlag')
    {
        PMDetonationProbFlag_JW1 = 1;
        floodChanceFactorFlag_JW1 = 1;
    }
    else if (code == 'PCEF020_Blue_Dragon_Flag')
    {
        crewExpFactorFlag_BD = 1;
    }

    refresh();
}


//    ]]>
