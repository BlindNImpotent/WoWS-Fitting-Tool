function generateAll()
{
    var html = '',
        name = getName(),
        left = getLeft();

    html = html +
        '<div class="ship" data-ship-index="' + warship.index + '" data-ship-position="0">' +
            name +
        '   <div class="info_box">' +
                left +
        '   </div>' +
        '   <div class="info_box">' +

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
        '       <span>' + global[IDS + warship.typeinfo.species.toUpperCase()] +'</span><br />' +
        '       <strong style="font-size: 2em;">' + global[IDS + warship.index.toUpperCase() + FULL] + '</strong><br />' +
        // '       <img title="' + global[IDS + warship.index + DESC] + '" src="' + warship.imageSmall + '" /><br />' +
        '       <br />' +
        '   </div>';

    return name;
}

function getLeft()
{
    return '<div class="info_box_inner">' + getModules() + getSkills() + getUpgrades() + '</div>';
    // return '<div class="info_box_inner">' + getModules() + getUpgrades() + '</div>' +
    //         '<div class="info_box_inner">' + getSkills() + '</div>';
}

function getModules()
{
    var mods = '',
        positions = warship.positions,
        rows = new Array(warship.shipUpgradeInfo.maxRows),
        maxRows = warship.shipUpgradeInfo.maxRows;

    for (var i = 0; i < maxRows; i++) {
        rows[i] = '';
    }

    var cols = warship.shipUpgradeInfo.cols;

    var ind = 0;
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
                            '   <button class="button_module ' + (positions[col] === modList[next].position ? 'select' : '') + '" data-index="' + ind + '"' +
                            '           data-type="' + modList[next].ucTypeShort + '" data-position="' + modList[next].position + '"' +
                            '           data-prev-type="' + modList[next].prevType + '" data-prev-position="' + modList[next].prevPosition + '">' +
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
        ind++;
    }

    for (var i = 0; i < rows.length; i++) {
        mods += '<tr>' + rows[i] + '</tr>'
    }
    
    mods =
        '<div class="panel_left" data-type="modules">' +
        '   <table>' +
                mods +
        '   </table>' +
        '</div>';

    return mods;
}

function getUpgrades()
{
    var upgs = '';

    for (var i = 0; i < warship.upgrades.length; i++) {
        upgs +=
            '<ul>' +
            '   <li><button class="button_upgrade select" data-index="' + i + '" data-position="0">+</button></li>';

        for (var j = 0; j < warship.upgrades[i].length; j++) {
            var current = warship.upgrades[i][j];
            var bonus = global[IDS + 'TITLE_' + current.name.toUpperCase()] + '\n\n';
            for (var b in current.bonus) {
                bonus += global[IDS + b] + ': ' + current.bonus[b] + '\n';
            }
            bonus = bonus.trim();

            upgs +=
                '   <li>' +
                '       <button class="button_upgrade hide"' +
                '           title="' + bonus + '" data-index="' + i + '" data-position="' + (j + 1) +'">' +
                '           <img src="' + current.image + '" /><br />' +
                '       </button>' +
                '   </li>'
        }
        upgs +=
            '</ul>'
    }

    upgs =
        '<div class="panel_left" data-type="upgrades">' +
            upgs +
        '</div>';

    return upgs;
}

function getSkills()
{
    var sks = '';

    for (var i = 0; i < skills.cSkills.length; i++) {
        var skillRow = skills.cSkills[i];
        sks += '<tr>';
        for (var j = 0; j < skillRow.length; j++) {
            var current = skillRow[j];
            var bonus = global[IDS + 'SKILL_' + current.modifier.toUpperCase()] + '\n\n' +
                        global[IDS + 'SKILL_DESC_' + current.modifier.toUpperCase()] + '\n\n';
            for (var b in current.bonus) {
                var modifier = global[IDS + b];
                if (modifier !== undefined) {
                    bonus += modifier + ': ' + current.bonus[b] + '\n';
                }
            }
            bonus = bonus.trim();
            sks +=
                '<td>' +
                '   <button class="button_skill" title="' + bonus + '"' +
                '           data-index="' + i + '" data-position="' + j + '">' +
                '       <img src="' + current.image + '" /><br />' +
                '   </button>' +
                '</td>'
        }
        sks += '</tr>';
    }

    sks =
        '<div class="panel_left" data-type="crewSkills">' +
        '   <div data-type="skills" style="padding: 8px;">' +
        '       <strong>Skill pts: </strong><strong class="points">0</strong><strong> / 19</strong>' +
        '   </div>' +
        '   <table class="skills">' +
        '       <tbody>' +
                    sks +
        '       </tbody>' +
        '   </table>' +
        '</div>';

    return sks;
}

function getRight()
{
    return '<div class="info_box_inner">' + '</div>';
}

function getArtillery()
{
    var arty = '';

    if (warship.components.artillery !== undefined && warship.modules.artillery !== undefined) {
        arty +=
            '<div class="switch" data-type="arty">' +
            '   <strong>' + global['IDS_SHIP_PARAM_ARTILLERY'] + '</strong>' +
            '</div>';

        arty +=
            '<table class="toggle arty">' +
            '   <tbody>' +
            '   </tbody>' +
            '</table>';


        arty =
            '<div class="panel_right" data-type="arty">' +
                arty +
            '</div>';
    }

    return arty;
}