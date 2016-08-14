//    <![CDATA[


function camouflage(code)
{
    // var idList = list.substring(1, list.length - 1);
    // idList = idList.split(', ');

    shootShiftCamo = 1;
    visibilityFactorCamo = 1;
    visibilityFactorByPlaneCamo = 1;
    afterBattleRepairCamo = 1;
    expFactorCamo = 1;

    if (document.getElementById(code).className == "button_camouflage_selected")
    {
        document.getElementById(code).className = "button_camouflage";
    }
    else
    {
        for (var i in camouCodeList)
        {
            document.getElementById(camouCodeList[i]).className = "button_camouflage";
        }
        document.getElementById(code).className = "button_camouflage_selected";

        var camouJSON;
        $.ajax({
            url: "/GameParams/name/" + code,
            type: "GET",
            async: false,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                camouJSON = data;
            }
        });

        shootShiftCamo = camouJSON['shootShift'];
        visibilityFactorCamo = camouJSON['visibilityFactor'];
        visibilityFactorByPlaneCamo = camouJSON['visibilityFactorByPlane'];
        if (camouJSON['afterBattleRepair'] != null)
        {
            afterBattleRepairCamo = camouJSON['afterBattleRepair'];
        }
        if (camouJSON['expFactor'] != null)
        {
            expFactorCamo = camouJSON['expFactor'];
        }
    }

    refresh();
}

function setCamouflage()
{
    var table = document.getElementById('camouTable');
    var row0 = table.insertRow(0);
    var row1 = table.insertRow(1);
    var row2 = table.insertRow(2);

    for (var i = 0; i < Math.ceil(camouflageList.length / 3); i++)
    {
        row0.insertCell(i);
        row1.insertCell(i);
        row2.insertCell(i);
    }

    for (var i = 0; i < camouflageList.length; i++)
    {
        camouCodeList.push(camouflageList[i]['code']);
    }

    for (var i = 0; i < camouflageList.length; i++)
    {
        var imgSrc = camouImageLocation + camouflageList[i]['code'] + '.png';

        if (i < Math.ceil(camouflageList.length / 3))
        {
            table.rows[0].cells[i].innerHTML = "<button onclick=camouflage(id) id=" + camouflageList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + camouflageList[i]['name'] + "</button>";
        }
        else if (i >= Math.ceil(camouflageList.length / 3) && i < Math.ceil(camouflageList.length / 3) * 2)
        {
            table.rows[1].cells[i - Math.ceil(camouflageList.length / 3)].innerHTML = "<button onclick=camouflage(id) id=" + camouflageList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + camouflageList[i]['name'] + "</button>";
        }
        else if (i >= Math.ceil(camouflageList.length / 3) * 2 && i < camouflageList.length)
        {
            table.rows[2].cells[i - Math.ceil(camouflageList.length / 3) * 2].innerHTML = "<button onclick=camouflage(id) id=" + camouflageList[i]['code'] + "><img src=" + imgSrc + "\/><br />" + camouflageList[i]['name'] + "</button>";
        }

        document.getElementById(camouflageList[i]['code']).className = "button_camouflage";
    }
}

//    ]]>
