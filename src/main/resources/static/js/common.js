$(document).on('click', '.button_tab', function (e) {
    var $this = $(this),
        $type = $this.attr('data-type'),
        $direction = $this.attr('data-direction'),
        $ship = $this.parents('.ship'),
        $tabs = $ship.find('.button_tab.select[data-direction=' + $direction + ']'),
        $panels = $ship.find('.panel_' + $direction + '.show'),
        $info = $ship.find('.panel_' + $direction + '[data-type=' + $type + ']');

    for (var i = 0; i < $tabs.length; i++) {
        $tabs.eq(i).removeClass('select');
    }
    $this.addClass('select');

    for (var i = 0; i < $panels.length; i++) {
        $panels.eq(i).removeClass('show').addClass('hide');
    }
    $info.removeClass('hide').addClass('show');
});

$(document).on('click', '.button_module', function(){
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $type = $this.attr('data-type'),
        $pos = parseInt($this.attr('data-position')),
        $prevType = $this.attr('data-prev-type'),
        $prevPos = parseInt($this.attr('data-prev-position')),
        $ship = $this.parents('.ship'),
        $all = $ship.find('.button_module.select'),
        $modules = $ship.find('.button_module.select[data-index=' + $index + ']');

    for (var i = 0; i < $all.length; i++) {
        var $cur = $all.eq(i),
            $curType = $cur.attr('data-type'),
            $curPos = parseInt($cur.attr('data-position')),
            $curPrevType = $cur.attr('data-prev-type'),
            $curPrevPos = parseInt($cur.attr('data-prev-position'));

        if ((($curPrevType === $type && $curPrevPos > $pos) || ($prevType === $curType && $prevPos > $curPos)) && $curType !== $type) {
            return false;
        }
    }

    for (var i = 0; i < $modules.length; i++) {
        $modules.eq(i).removeClass('select');
    }
    $this.addClass('select');

    warship.positions[$type] = $pos;

    // setNewStats($ship);
});

$(document).on('click', '.button_upgrade', function (e) {
    e.stopPropagation();
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $ship = $this.parents('.ship'),
        $upgrades = $ship.find('.button_upgrade[data-index=' + $index + ']');

    if (!$this.hasClass('select')) {
        for (var i = 0; i < $upgrades.length; i++) {
            $upgrades.eq(i).removeClass('select');
            $upgrades.eq(i).addClass('hide');
        }
        $this.addClass('select');
        $this.removeClass('hide');
    } else {
        $upgrades.removeClass('hide');
    }
});

$(document).on('click', '.button_consumable', function (e) {
    e.stopPropagation();
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $ship = $this.parents('.ship'),
        $consumables = $ship.find('.button_consumable[data-index=' + $index + ']');

    if (!$this.hasClass('select')) {
        for (var i = 0; i < $consumables.length; i++) {
            $consumables.eq(i).removeClass('select');
            $consumables.eq(i).addClass('hide');
        }
        $this.addClass('select');
        $this.removeClass('hide');
    } else {
        $consumables.removeClass('hide');
    }
});

$(document).on('click', function () {
    var $upgrades = $('.button_upgrade'),
        $consumables = $('.button_consumable');

    for (var i = 0; i < $upgrades.length; i++) {
        if (!$upgrades.eq(i).hasClass('select')) {
            $upgrades.eq(i).addClass('hide');
        }
    }

    for (var i = 0; i < $consumables.length; i++) {
        if (!$consumables.eq(i).hasClass('select')) {
            $consumables.eq(i).addClass('hide');
        }
    }
});

var $maxSpts = 19;

$(document).on('click', '.button_skill', function () {
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $pos = parseInt($this.attr('data-position')),
        $ship = $this.parents('.ship'),
        $pts = $ship.find('.points'),
        $skills = $ship.find('.button_skill.select'),
        $totalSpts = 0;

    var canUse = true;
    var hasIndex = [false, false, false, false];

    if (!$this.hasClass('select')) {
        hasIndex[$index] = true;
        $totalSpts = $totalSpts + $index + 1;
    }

    for (var i = 0; i < $skills.length; i++) {
        var $sIndex = parseInt($skills.eq(i).attr('data-index'));
        var $sPos = parseInt($skills.eq(i).attr('data-position'));

        if ($sIndex !== $index || $sPos !== $pos) {
            $totalSpts = $totalSpts + $sIndex + 1;
            hasIndex[$sIndex] = true;
        }
    }

    for (var i = 3; i > 0; i--) {
        if (hasIndex[i] && !hasIndex[i - 1]) {
            canUse = false;
            break;
        }
    }

    if (!canUse || $totalSpts > $maxSpts) {
        return false;
    }

    if ($this.hasClass('select')) {
        $this.removeClass('select');
    } else {
        $this.addClass('select');
    }

    $pts.text($totalSpts);
});

$(document).on('click', '.switch', function (e) {
    var $this = $(this),
        $type = $this.attr('data-type'),
        $toggle = $this.parent().find('.' + $type);

    for (var i = 0; i < $toggle.length; i++) {
        if ($toggle.eq(i).hasClass('toggle')) {
            if ($toggle.eq(i).hasClass('hide')) {
                $toggle.eq(i).removeClass('hide');
            } else {
                $toggle.eq(i).addClass('hide');
            }
        }
    }
});