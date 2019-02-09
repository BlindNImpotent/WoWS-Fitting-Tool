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
});

$(document).on('click', '.button_upgrade', function () {
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $ship = $this.parents('.ship'),
        $upgrades = $ship.find('.button_upgrade.select[data-index=' + $index + ']');

    if ($this.hasClass('select')) {
        $this.removeClass('select');
    } else {
        for (var i = 0; i < $upgrades.length; i++) {
            $upgrades.eq(i).removeClass('select');
        }
        $this.addClass('select');
    }
});

$(document).on('click', '.button_consumable', function () {
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $ship = $this.parents('.ship'),
        $consumables = $ship.find('.button_consumable.select[data-index=' + $index + ']');

    for (var i = 0; i < $consumables.length; i++) {
        $consumables.eq(i).removeClass('select');
    }
    $this.addClass('select');
});

$(document).on('click', '.button_skill', function () {
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $ship = $this.parents('.ship'),
        $skills = $ship.find('.button_skill.select'),
        $maxSpts = 19,
        $totalSpts = 0;

    if ($this.hasClass('select')) {
        $this.removeClass('select')
    } else {
        var canUse = false;

        if ($index === 0) {
            canUse = true;
        }

        for (var i = 0; i < $skills.length; i++) {
            var $sIndex = parseInt($skills.eq(i).attr('data-index'));
            $totalSpts += $sIndex + 1;

            if ($sIndex + 1 === $index || $index === 0) {
                canUse = true;
            }
        }

        if (!canUse || $totalSpts + $index + 1 > $maxSpts) {
            return false;
        } else {
            $this.addClass('select');
        }
    }
});

$(document).on('click', '.switch', function (e) {
    var $this = $(this),
        $type = $this.attr('data-type'),
        $toggle = $this.parent().find($type);

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