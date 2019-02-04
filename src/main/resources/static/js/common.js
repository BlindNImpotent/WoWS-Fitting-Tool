$(document).on('click', '.button_tab.left', function (e) {
    var $this = $(this),
        $type = $this.attr('data-type'),
        $ship = $this.parents('.ship'),
        $tabs = $ship.find('.button_tab.left.select'),
        $panels = $ship.find('.panel_left.show'),
        $info = $ship.find('.panel_left[data-type=' + $type + ']');

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
        $index = $this.attr('data-index'),
        $type = $this.attr('data-type'),
        $pos = $this.attr('data-position'),
        $prevType = $this.attr('data-prev-type'),
        $prevPos = $this.attr('data-prev-position'),
        $ship = $this.parents('.ship'),
        $all = $ship.find('.button_module.select'),
        $modules = $ship.find('.button_module.select[data-index=' + $index + ']');

    for (var i = 0; i < $all.length; i++) {
        var $cur = $all.eq(i),
            $curType = $cur.attr('data-type'),
            $curPos = $cur.attr('data-position'),
            $curPrevType = $cur.attr('data-prev-type'),
            $curPrevPos = $cur.attr('data-prev-position');

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
        $index = $this.attr('data-index'),
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