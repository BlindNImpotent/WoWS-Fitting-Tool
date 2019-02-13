function generateAll()
{
    var html = '',
        name = getName(),
        left = getLeft();

    html = html +
        '<div class="ship" name="' + warship.index + '" data-ship-index="0">' +
            name +
        '   <div class="info_box">' +
                left +
        '   </div>' +
        '</div>';

    $('.main').html(html);
}

function getName()
{
    var name = '';
    name +=
        '   <div class="name">' +
        '       <img src="' + warship.typeImage + '"/><br />' +
        '       <span>Tier ' + warship.level +'</span><br />' +
        '       <span>' + global[(IDS + warship.typeinfo.species.toUpperCase())] +'</span><br />' +
        '       <strong style="font-size: 2em;">' + global[IDS + warship.index.toUpperCase() + FULL] + '</strong><br />' +
        '       <img title="' + global[IDS + warship.index + DESC] + '" src="' + warship.imageSmall + '" /><br />' +
        '       <br />' +
        '   </div>';

    return name;
}

function getLeft()
{
    var left = '',
        mods = getModules();

    left = left + '<div class="info_box_inner">' + mods + '</div>';

    return left;
}

function getModules()
{
    var mods = '',
        rows = [],
        maxRows = warship.shipUpgradeInfo.maxRows;

    for (var i = 0; i < maxRows; i++) {
        rows[i] = '';
    }

    var cols = warship.shipUpgradeInfo.cols;

    for (var col in cols) {
        if (cols[col] > 0) {
            var modList = warship.shipUpgradeInfo.components[col];

            var next = 0;
            for (var i = 0; i < maxRows; i++) {
                var size = 0;
                while (size < cols[col]) {
                    if (modList[next] === undefined || modList[next].position !== i + 1) {
                        rows[i] += '<td></td>';
                    } else if (modList[next].position === i + 1) {
                        rows[i] +=
                            '<td>' +
                            '   <button class="button_module" data-index="' + size + '" data-type="' + modList[next].ucTypeShort + '" data-position="' + modList[next].position + '" data-prev-type="' + modList[next].prevType + '" data-prev-position="' + modList[next].prevPosition + '">' +
                            '       <img src="' + modList[next].image + '" /><br />' +
                            '       <span>' + global[IDS + modList[next].name.toUpperCase()] + '</span>' +
                            '   </button>' +
                            '</td>';
                        next++;
                    }                    
                    size++;
                }
            }
        }
    }

    for (var i = 0; i < rows.length; i++) {
        mods +=
            '<tr>' +
                rows[i] +
            '</tr>'
    }
    
    mods =
        '<div class="panel_left" data-type="modules">' +
        '   <table>' +
                mods +
        '   </table>' +
        '</div>';

    return mods;
}