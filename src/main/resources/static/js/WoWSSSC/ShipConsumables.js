function consume(id, list, slot)
{
    if (typeof list == 'string')
    {
        list = list.substring(3, list.length - 3);
        list = list.split('"],["')

        for (var i in list)
        {
            list[i] = list[i].split('","');
        }

        id = id.substring(2, id.length - 2);
        id = id.split('","');

        for (var i in list)
        {
            document.getElementById(list[i][0]).className = "button_consumable";
        }
        document.getElementById(id[0]).className = "button_consumable_selected";
    }
    else
    {
        for (var i in list)
        {
            document.getElementById(list[i][0]).className = "button_consumable";
        }
        document.getElementById(id[0]).className = "button_consumable_selected";
    }
    setConsume(id, slot)
}

function setConsume(id, slot)
{
    var consumeJSON;
    
    $.ajax({
        url: "/GameParams/name/" + id[0],
        type: "GET",
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            consumeJSON = data;
        }
    });
    
    if (slot == 1)
    {
        numConsumables1 = consumeJSON[id[1]]['numConsumables'];
        reloadTime1 = consumeJSON[id[1]]['reloadTime'];
        workTime1 = consumeJSON[id[1]]['workTime'];

        if (numConsumables1 == -1)
        {
            numConsumables1 = 'Infinite';
        }
    }
    else if (slot == 2)
    {
        numConsumables2 = consumeJSON[id[1]]['numConsumables'];
        reloadTime2 = consumeJSON[id[1]]['reloadTime'];
        workTime2 = consumeJSON[id[1]]['workTime'];

        if (numConsumables2 == -1)
        {
            numConsumables2 = 'Infinite';
        }
    }
    else if (slot == 3)
    {
        numConsumables3 = consumeJSON[id[1]]['numConsumables'];
        reloadTime3 = consumeJSON[id[1]]['reloadTime'];
        workTime3 = consumeJSON[id[1]]['workTime'];

        if (numConsumables3 == -1)
        {
            numConsumables3 = 'Infinite';
        }
    }
    else if (slot == 4)
    {
        numConsumables4 = consumeJSON[id[1]]['numConsumables'];
        reloadTime4 = consumeJSON[id[1]]['reloadTime'];
        workTime4 = consumeJSON[id[1]]['workTime'];

        if (numConsumables4 == -1)
        {
            numConsumables4 = 'Infinite';
        }
    }

    refresh();
}