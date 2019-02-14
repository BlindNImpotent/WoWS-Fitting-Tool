function drawTorpedoes(currentIndex, launchers)
{
    if (launchers !== undefined && launchers !== null) {
        var torpedoesCanvas = currentIndex.find('[data-type=torpedoesCanvas]');
        var ctx = torpedoesCanvas[0].getContext('2d');

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

        for (var i = 0; i < launchers.length; i++) {
            var current = launchers[i];

            var minAngle = current.horizSector[0];
            var maxAngle = current.horizSector[1];
            var vertiPosition = current.position[0];
            var horizPosition = current.position[1];
            var horizFactor = Math.PI / -2;

            var deadZone = current.deadZone;
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

            horizPosition = horizPosition + (1 - horizPosition) * 2;

            if ((horizPosition < 1 && (minAngle > 0 && maxAngle > 0)) || (horizPosition > 1 && (minAngle < 0 && maxAngle < 0))) {
                var tempMin = -180 + minAngle;
                var tempMax = -180 + maxAngle;
                var tempDz1 = -180 + deadZone1;
                var tempDz2 = -180 + deadZone2;
                minAngle = tempMin;
                maxAngle = tempMax;
                deadZone1 = tempDz1;
                deadZone2 = tempDz2;
            }

            if (minAngle < 0 && maxAngle > 0) {
                minAngle = minAngle + 90 * (horizPosition - 1.0);
                maxAngle = maxAngle + 90 * (horizPosition - 1.0);
                deadZone1 = deadZone1 + 90 * (horizPosition - 1.0);
                deadZone2 = deadZone2 + 90 * (horizPosition - 1.0);
            }

            // -1 .. 7
            var centerX = size + (horizPosition - 1) * (size * 2 / 8);
            var centerY = (vertiPosition + 1.5) * (size * 2 / 8) + cY;

            if (deadZone.length === 0 || isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();
            } else if ((deadZone.length === 1 || deadZone.length === 2) && !isMerge) {
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (deadZone1 / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, horizFactor + (minAngle / 180 * Math.PI), horizFactor + (deadZone1 / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size * 4, horizFactor + (deadZone2 / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.globalAlpha = 0.1;
                ctx.arc(centerX, centerY, size / 5, horizFactor + (deadZone2 / 180 * Math.PI), horizFactor + (maxAngle / 180 * Math.PI));
                ctx.closePath();
                ctx.fill();
            }
        }
    }

    $('[data-script=torpSector]').remove();
}