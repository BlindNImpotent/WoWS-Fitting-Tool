function drawArtillery(currentIndex, artillery)
{
    if (artillery !== null) {
        var turrets = artillery['turrets'];

        var artilleryCanvas = currentIndex.find('[data-type=artilleryCanvas]');
        var ctx = artilleryCanvas[0].getContext('2d');

        var eccentricity = 3.25;
        ctx.save();
        ctx.scale(1 / eccentricity, 1);
        ctx.beginPath();
        ctx.globalAlpha = 0.5;
        ctx.arc(125 * eccentricity, 125, 125, 0, 2 * Math.PI, false);
        ctx.closePath();
        ctx.fillStyle = '#64C8FF';
        ctx.fill();
        ctx.restore();

        for (var i = 0; i < turrets.length; i++) {
            var current = turrets[i];

            var minAngle = current['horizSector'][0];
            var maxAngle = current['horizSector'][1];
            var vertiPosition = current['position'][0];
            var horizPosition = current['position'][1];
            var horizFactor = Math.PI / -2;

            var deadZone = current['deadZone'];
            var deadZone1 = 0;
            var deadZone2 = 0;
            if (deadZone.length === 1) {
                deadZone1 = deadZone[0][0];
                deadZone2 = deadZone[0][1];
            } else if (deadZone.length === 2) {
                var tempDz1 = deadZone[0][0];
                var tempDz2 = deadZone[0][1];
                var tempDz3 = deadZone[1][0];
                var tempDz4 = deadZone[1][1];

                minAngle = tempDz1 < tempDz2 ? tempDz1 : tempDz2;
                maxAngle = tempDz1 === minAngle ? tempDz2 : tempDz1;
                deadZone1 = tempDz3 < tempDz4 ? tempDz3 : tempDz4;
                deadZone2 = tempDz3 === deadZone1 ? tempDz4 : tempDz3;

                if (minAngle > deadZone1) {
                    var tempMin = minAngle;
                    var tempMax = maxAngle;
                    minAngle = deadZone1;
                    maxAngle = deadZone2;
                    deadZone1 = tempMin;
                    deadZone2 = tempMax;
                }
            }

            var isMerge = false;
            if (deadZone.length === 1 && deadZone2 === maxAngle) {
                isMerge = true;
                maxAngle = deadZone1;
            } else if (deadZone.length === 1 && deadZone1 === minAngle) {
                isMerge = true;
                minAngle = deadZone2;
            } else if (deadZone.length === 1 && minAngle === maxAngle)
            {
                isMerge = true;
                minAngle = deadZone2;
                maxAngle = deadZone1;
            }

            var test = (horizPosition - 1) * Math.PI;

            var isFlip = false;
            if ((i === 2 && (vertiPosition < 3 && vertiPosition >= 1.75 && (turrets[2]['position'][0] - turrets[1]['position'][0] > 1 || deadZone.length === 1)) && horizPosition === 1)
                || (i === 2 && horizPosition === 1 && vertiPosition === 3 && turrets.length === 3)) {
                isFlip = true;
            }

            if ((vertiPosition >= 3 && !isFlip) || (vertiPosition < 3 && isFlip)) {
                minAngle = minAngle + 180;
                maxAngle = maxAngle - 180;
                deadZone1 = deadZone1 + 180;
                deadZone2 = deadZone2 + 180;

                if (horizPosition !== 1 && (horizPosition - 1) % 1 === 0) {
                    var tempValue = minAngle;
                    minAngle = -maxAngle;
                    maxAngle = -tempValue;
                } else {
                    minAngle = minAngle - (test * 180 / Math.PI);
                    maxAngle = maxAngle - (test * 180 / Math.PI);
                }
            } else {
                if (horizPosition !== 1 && (horizPosition - 1) % 1 === 0) {
                    var tv = minAngle;
                    minAngle = -maxAngle;
                    maxAngle = -tv;
                } else {
                    minAngle = minAngle + (test * 180 / Math.PI);
                    maxAngle = maxAngle + (test * 180 / Math.PI);
                }
            }

            if (horizPosition !== 1 && (horizPosition - 1) % 1 === 0) {
                if ((Math.abs(minAngle) < 90 && Math.abs(maxAngle) < 90)) {
                    minAngle = minAngle + (horizPosition - 1) * 90;
                    maxAngle = maxAngle + (horizPosition - 1) * 90;
                } else if ((180 - Math.abs(minAngle) < 90 && 180 - Math.abs(maxAngle) < 90)) {
                    minAngle = minAngle - (horizPosition - 1) * 90;
                    maxAngle = maxAngle - (horizPosition - 1) * 90;
                }
            }

            // -1 .. 7
            var centerX = 125 + (horizPosition - 1) * (250 / 8);
            var centerY = (vertiPosition + 1.5) * (250 / 8);

            if (deadZone.length === 0 || isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 500, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 25, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();
            } else if ((deadZone.length === 1 || deadZone.length === 2) && !isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 500, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (deadZone1 / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 25, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (deadZone1 / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 500, horizFactor + (deadZone2 / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, 25, horizFactor + (deadZone2 / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();
            }
        }
    }

    $('[data-script=artySector]').remove();
}