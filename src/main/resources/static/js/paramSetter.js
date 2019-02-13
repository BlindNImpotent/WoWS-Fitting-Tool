function getShipData($shipIndex) {
    $.ajax({
        url: '/ship?index=' + $shipIndex + (modules === null ? '' : '&modules=' + modules),
        type: 'post',
        contentType: "application/json",
        success: function (data) {
            if (data.status === '200') {
                warship = data.result;
                modules = warship.modules;
                positions = warship.positions;
                // generateAll();
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function setNewStats($ship)
{
    var $all = $ship.find('.button_module.select'),
        $components = warship.shipUpgradeInfo.components,
        $hull = $components.hull[warship.positions.hull - 1];

    for (var i = 0; i < $all.length; i++) {
        var current = $all.eq(i),
            cType = current.attr('data-type'),
            comp = $components[cType][warship.positions[cType] - 1];

        if (cType !== 'hull') {
            if ($hull.components[cType] === undefined) {
                if ($components[comp.prevType][comp.prevPosition - 1] === undefined || $components[comp.prevType][comp.prevPosition - 1].name === comp.prev) {
                    warship.modules[cType] = comp.components[cType][comp.components[cType].length - 1];
                }
            } else {
                var thisType = $hull.components[cType];

                for (var j = 0; j < thisType.length; i++) {
                    if (comp.components[cType].includes(thisType[j])) {
                        warship.modules[cType] = thisType[j];
                        break;
                    }
                }
            }
        } else {
            warship.modules[cType] = comp.components[cType][comp.components[cType].length - 1];
        }
    }

    setHull($ship);
}


function setHull($ship)
{
    if (warship.components.hull !== undefined) {
        var tBody = $ship.find('.panel_right[data-type=hull] tbody');

        tBody.find('tr').eq(0).find('td').eq(1).text(warship.components.hull[warship.modules.hull].health)
    }
}