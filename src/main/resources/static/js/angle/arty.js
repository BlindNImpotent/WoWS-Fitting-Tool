function drawArtillery(currentIndex, turrets)
{
    if (turrets !== undefined && turrets != null) {
        var artilleryCanvas = currentIndex.find('[data-type=artilleryCanvas]');
        var ctx = artilleryCanvas[0].getContext('2d');

        var eccentricity = 3.25;
        var size = 100;
        var cY = 25;
        ctx.save();
        ctx.scale(1 / eccentricity, 1);
        ctx.beginPath();
        ctx.globalAlpha = 0.5;
        ctx.arc(size * eccentricity, size + cY, size, 0, 2 * Math.PI, false);
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
            var horizFactor = 0;

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

            var tMin = minAngle;
            var tDeadZone1 = deadZone1;
            if (horizPosition === 1) {
                if (3 >= vertiPosition) {
                    horizFactor = -90;
                } else {
                    horizFactor = 90;
                }
                minAngle = minAngle + horizFactor;
                maxAngle = maxAngle + horizFactor;
                deadZone1 = deadZone1 + horizFactor;
                deadZone2 = deadZone2 + horizFactor;
            } else if (horizPosition > 1) {
                tMin = minAngle;
                minAngle = -maxAngle;
                maxAngle = -tMin;
                deadZone1 = -deadZone2;
                deadZone2 = -tDeadZone1;

                if (Math.abs(maxAngle - minAngle) >= 180) {
                    if (3 >= vertiPosition) {
                        horizFactor = -90;
                    } else {
                        horizFactor = 90;
                    }
                } else {
                    if (maxAngle >= 90 && 0 >= minAngle) {
                        horizFactor = -90;
                    } else if ((maxAngle >= 0 && -90 >= minAngle) || (0 >= maxAngle && -90 >= minAngle)) {
                        horizFactor = 90;
                    }
                }
                minAngle = minAngle + horizFactor;
                maxAngle = maxAngle + horizFactor;
                deadZone1 = deadZone1 + horizFactor;
                deadZone2 = deadZone2 + horizFactor;
            } else {
                if (Math.abs(maxAngle - minAngle) >= 180) {
                    if (3 >= vertiPosition) {
                        horizFactor = -90;
                    } else {
                        horizFactor = 90;
                    }
                } else {
                    if (maxAngle >= 90 && 0 >= minAngle) {
                        horizFactor = -90;
                    } else if ((maxAngle >= 0 && -90 >= minAngle) || (0 >= maxAngle && -90 >= minAngle)) {
                        horizFactor = 90;
                    }
                }
                minAngle = minAngle + horizFactor;
                maxAngle = maxAngle + horizFactor;
                deadZone1 = deadZone1 + horizFactor;
                deadZone2 = deadZone2 + horizFactor;

                tMin = minAngle;
                tDeadZone1 = deadZone1;
                minAngle = 90 + (90 - maxAngle);
                maxAngle = 90 + (90 - tMin);
                deadZone1 = 90 + (90 - deadZone2);
                deadZone2 = 90 + (90 - tDeadZone1);
            }

            var isMerge = false;
            if (deadZone.length === 1 && deadZone2 === maxAngle) {
                isMerge = true;
                maxAngle = deadZone1;
            } else if (deadZone.length === 1 && deadZone1 === minAngle) {
                isMerge = true;
                minAngle = deadZone2;
            } else if (deadZone.length === 1 && minAngle === maxAngle) {
                isMerge = true;
                minAngle = deadZone2;
                maxAngle = deadZone1;
            }

            // -1 .. 7
            var centerX = size + (horizPosition - 1) * (size * 2 / 8);
            var centerY = (vertiPosition + 1.5) * (size * 2 / 8) + cY;

            if (deadZone.length === 0 || isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, (minAngle) / 180 * Math.PI, (maxAngle) / 180 * Math.PI);
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, (minAngle) / 180 * Math.PI, (maxAngle) / 180 * Math.PI);
                ctx.closePath();
                ctx.fill();
            } else if ((deadZone.length === 1 || deadZone.length === 2) && !isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, (minAngle) / 180 * Math.PI, (deadZone1) / 180 * Math.PI);
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, (minAngle) / 180 * Math.PI, (deadZone1) / 180 * Math.PI);
                ctx.closePath();
                ctx.fill();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, (deadZone2) / 180 * Math.PI, (maxAngle) / 180 * Math.PI);
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, (deadZone2) / 180 * Math.PI, (maxAngle) / 180 * Math.PI);
                ctx.closePath();
                ctx.fill();
            }
        }
    }

    $('[data-script=artySector]').remove();
}