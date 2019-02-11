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

var uClick = false;
var uClickIndex = -1;
var uClickPosition = -1;
$(document).on('click', '.button_upgrade', function (e) {
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $position = parseInt($this.attr('data-position')),
        $uSlot = $this.parents('.uSlot[data-index=' + $index + ']'),
        $ship = $this.parents('.ship'),
        $upgrades = $ship.find('.button_upgrade[data-index=' + $index + ']');

    var uCCopy = uClick,
        uCICopy = uClickIndex,
        uCPCopy = uClickPosition;

    if ($uSlot.hasClass('closed')) {
        $uSlot.removeClass('closed');
        $uSlot.find('.button_upgrade').removeClass('hide');
        uClick = true;
        uClickIndex = $index;
        uClickPosition = $position;
        $this.focus();
    } else {
        uClick = false;
        for (var i = 0; i < $upgrades.length; i++) {
            $upgrades.eq(i).removeClass('select');
            $upgrades.eq(i).addClass('hide');
        }
        $this.removeClass('hide');
        $this.addClass('select');
        $uSlot.addClass('closed');
        // $('.button_upgrade[data-index=' + uCICopy + ']').find('[data-position=' + uCPCopy + ']')
    }
});

$(document).on('blur', '.button_upgrade', function (e) {
    console.log('test');
    var $this = $(this),
        $index = parseInt($this.attr('data-index')),
        $position = parseInt($this.attr('data-position')),
        $uSlot = $this.parents('.uSlot[data-index=' + $index + ']'),
        $ship = $this.parents('.ship'),
        $upgrades = $ship.find('.button_upgrade[data-index=' + $index + ']');

    if (!(uClick && $index === uClickIndex && $position === uClickPosition)) {
        console.log('123');
        // return false;
    }

    console.log(uClick, uClickIndex, uClickPosition);
    uClick = false;
    uClickIndex = -1;
    uClickPosition = -1;
    // $this.trigger(e.type);
    console.log(uClick, uClickIndex, uClickPosition);
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