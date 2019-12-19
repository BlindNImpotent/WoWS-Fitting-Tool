function drawAirDefense(currentIndex, auraFar, auraMedium, auraNear)
{
    if (auraFar.length > 0 || auraMedium.length > 0 || auraNear.length > 0) {
        var aa = [auraFar, auraMedium, auraNear];

        var airDefenseCanvas = currentIndex.find('[data-type=airDefenseCanvas]');
        var ctx = airDefenseCanvas[0].getContext('2d');

        var mid = 100;
        var size = 80;
        ctx.save();
        ctx.scale(1, 1);
        ctx.beginPath();
        ctx.globalAlpha = 0.5;
        ctx.arc(mid, mid * 1.1, size, 0, 2 * Math.PI, false);
        ctx.closePath();
        ctx.fillStyle = '#64C8FF';
        ctx.fill();
        ctx.restore();

        var furthest = 5800;
        var closest = -1;
        var angles = [[ 0, 180], [ 180, 0]];

        for (var i = 0; i < aa.length; i++) {
            if (aa[i] !== undefined) {
                for (var j = 0; j < aa[i].length; j++) {
                    var current = aa[i][j];
                    var maxDist = current.maxDistance;
                    var minDist = current.minDistance;

                    if (maxDist > furthest) {
                        furthest = maxDist;
                    }
                    if (closest === -1 || closest > minDist) {
                        closest = minDist;
                    }
                }
            }
        }

        ctx.font = '14px Arial';
        ctx.fillText('Coloured Circle: ' + (furthest / 1000).toLocaleString() + ' km',20, 14);

        for (var i = 0; i < aa.length; i++) {
            if (aa[i] !== undefined) {
                for (var j = 0; j < aa[i].length; j++) {
                    for (var k = 0; k < 2; k++) {
                        var angle1 = angles[k][0] + 90;
                        var angle2 = angle1 + angles[k][1] + (-k * 180) + 90;

                        ctx.beginPath();
                        ctx.moveTo(mid, mid * 1.1);
                        ctx.globalAlpha = 1;
                        ctx.arc(mid, mid * 1.1, size * (aa[i][j].maxDistance / furthest), (angle1 / 180 * Math.PI), (angle2 / 180 * Math.PI));
                        // ctx.closePath();
                        ctx.stroke();

                        ctx.beginPath();
                        ctx.moveTo(mid, mid * 1.1);
                        ctx.globalAlpha = 1;
                        ctx.arc(mid, mid * 1.1, size * (aa[i][j].minDistance / furthest), (angle1 / 180 * Math.PI), (angle2 / 180 * Math.PI));
                        // ctx.closePath();
                        ctx.stroke();
                    }
                }
            }
        }
    }

    $('[data-script=airDefenseSector]').remove();
}