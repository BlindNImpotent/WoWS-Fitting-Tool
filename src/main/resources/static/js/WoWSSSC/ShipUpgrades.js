function mod(id, list)
{
    if (typeof list == 'string')
    {
        list = list.substring(1, list.length - 1);
        list = list.split(', ');

        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_upgrade";
        }
        document.getElementById(id).className = "button_upgrade_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i]).className = "button_upgrade";
        }
        document.getElementById(id).className = "button_upgrade_selected";
    }

    modCalc(id, list)
}

function modCalc(id, list)
{







}