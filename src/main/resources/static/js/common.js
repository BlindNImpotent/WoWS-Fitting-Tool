$(document).on('click', '.button_tab.left', function (e) {
    var index = $(this).parents('.ship').attr('data-ship-index');
    var currentIndex = $('[data-ship-index=' + index + ']');
    var tabs = currentIndex.find('.button_tab.left');
    var type = $(this).attr('class').replace('button_tab left ', '');

    for (var i = 0; i < tabs.length; i++)
    {
        var current = tabs.eq(i);
        current.removeClass('select');
    }

    var leftFind = currentIndex.find('.button_tab.left.' + type);
    for (var i = 0; i < leftFind.length; i++) {
        leftFind.eq(i).addClass('select');
    }

    var panels = currentIndex.find('.panel_left');
    for (var i = 0; i < panels.length; i++)
    {
        var current = panels.eq(i);
        current.addClass('hide');
    }
    var typeFind = currentIndex.find('.panel_left.' + type);
    for (var i = 0; i < typeFind.length; i++) {
        typeFind.eq(i).removeClass('hide');
    }
});