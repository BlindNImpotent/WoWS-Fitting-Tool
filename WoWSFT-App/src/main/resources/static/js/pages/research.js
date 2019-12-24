$(document).on('click', '.button_nation.initiate', function () {
    var $this = $(this),
        $nation = $this.attr('data-nation'),
        $tree = $('.tree'),
        $table = $('.tree.' + $nation),
        $img = $table.find('img');

    $('.button_nation.initiate').removeClass('select');
    $this.addClass('select');

    $('.button_ship').removeClass('select');

    for (var i = 0; i < $img.length; i++) {
        if ($img.eq(i).attr('src') === undefined) {
            $img.eq(i).attr('src', $img.eq(i).attr('data-src'));
        }
    }

    $tree.addClass('hide');
    $table.removeClass('hide');
});

$(document).on('click', '.button_ship', function () {
    var $this = $(this),
        $bShipSelect = $('.button_ship.select'),
        $text = $('#xpText');

    var calc = 0;

    if ($this.hasClass('select')) {
        $this.removeClass('select');
        return;
    }

    if ($bShipSelect.length > 1) {
        $bShipSelect.removeClass('select');
    }
    $this.addClass('select');

    $bShipSelect = $('.button_ship.select');
    if ($bShipSelect.length === 2) {
        var $first = $bShipSelect.eq(0);
        var $temp = $bShipSelect.eq(1);
        var $second;

        if (parseInt($temp.attr('data-tier')) < parseInt($first.attr('data-tier'))) {
            $second = $first;
            $first = $temp;
        } else {
            $second = $temp;
        }

        if ($first.attr('data-tier') === $second.attr('data-tier')) {
            if ($first.attr('data-prev-index') === $second.attr('data-ship-index')) {
                calc = parseInt($first.attr('data-prev-ship-xp')) + parseInt($first.attr('data-prev-ship-comp-xp')) + parseInt($first.attr('data-ship-xp'));
            } else if ($second.attr('data-prev-index') === $first.attr('data-ship-index')) {
                calc = parseInt($second.attr('data-prev-ship-xp')) + parseInt($second.attr('data-prev-ship-comp-xp')) + parseInt($second.attr('data-ship-xp'));
            } else {
                $text.text('Unknown');
                return false;
            }
            $text.text(calc.toLocaleString() + ' XP');
        } else {
            var curTier = parseInt($second.attr('data-tier'));
            var endTier = parseInt($first.attr('data-tier'));
            var endIndex = $first.attr('data-ship-index');
            var $prev = $second;
            var match = false;

            while (curTier >= endTier && curTier > 0) {
                var prevShipIndex = $prev.attr('data-ship-index');
                if (prevShipIndex !== endIndex && prevShipIndex !== undefined) {
                    calc = calc + parseInt($prev.attr('data-ship-xp')) + parseInt($prev.attr('data-prev-ship-comp-xp'));
                    $prev = $('[data-ship-index=' + $prev.attr('data-prev-index') + ']');
                    curTier = $prev.attr('data-tier');
                } else {
                    match = true;
                    break;
                }
            }
            if (match) {
                $text.text(calc.toLocaleString() + ' XP');
            } else {
                $text.text('Unknown');
            }
        }
    }
});