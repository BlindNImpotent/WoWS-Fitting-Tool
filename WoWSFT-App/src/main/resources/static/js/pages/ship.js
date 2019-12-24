$(function () {
    var index = $('.ship').attr('data-ship-index');
    if (index) {
        history.replaceState({
            id: index
        }, '', makeUrl($('[data-ship-index=' + index + ']')));
    }
});