(function($){
	$.fn.calendar = function(options){

		options = $.extend({
			daysName	:	['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
			setMonth	:	['1','2','3','4','5','6','7','8','9','10','11','12'],
			prevMonth	:	'prev month',
			nextMonth	:	'next month',
			holiday		:	['1.1','3.1','5.5','5.25']
		}, options);

		var area		= this;
		var layout = '<div class="control">'+
					 '	<a href="#self" class="prevMon">'+options.prevMonth+'</a>'+
					  '	<a href="#self" class="goToday">오늘</a>'+
					 '	<a href="#self" class="nextMon">'+options.nextMonth+'</a>'+
					 '</div>'+
					 '<div class="calendarBox"></div>';
		area.html(layout)

		var calendalBox = area.children('.calendarBox');
		var date		= new Date();
		var months		= options.setMonth;
		var days		= new Array(31,28,31,30,31,30,31,31,30,31,30,31);
		var week		= 0;
		var	day			= date.getDate();
		var control		= '';
		var holidayMax  = options.holiday.length;

		var thisDay		= new Date();

		var makeCalendar = function(month){
			var year  = date.getYear(),
				month = date.getMonth(),
				table = '';

			date.setMonth(date.getMonth()-1);
			var pmonth = date.getMonth();
			date.setMonth(date.getMonth()+1);

			if(year<=200){year+=1900;}
			if(year%4==0 && year!=1900) days[1]=29;

			var today = year+'년 '+months[month];
			var total = days[month];

			D = date;
			D.setDate(1);
			D = D.getDay();
			
			table += '<table cellpadding="0" cellspacing="0">'+
					 '	<tbody>'+
					 '		<tr class="month">'+
					 '			<th colspan="7">'+today+'</th>'+
					 '		</tr>'+
					 '		<tr class="day">'+
					 '			<th class="sun">'+options.daysName[0]+'</th>'+
					 '			<th>'+options.daysName[1]+'</th>'+
					 '			<th>'+options.daysName[2]+'</th>'+
					 '			<th>'+options.daysName[3]+'</th>'+
					 '			<th>'+options.daysName[4]+'</th>'+
					 '			<th>'+options.daysName[5]+'</th>'+
					 '			<th class="sat">'+options.daysName[6]+'</th>'+
					 '		</tr>';

			for(i=1; i<=D; i++){
				if(week==0){table += '<tr>';}
				table += '<td class="prevMonth">'+(days[pmonth]-D+i)+'일</td>'; //이전달
				week++;
			}
			for(i=1; i<=total; i++){
				var newDate = new Date();
				var	thisMon = date.getMonth() + 1;
				var thisDate = i;
				var holiday = thisMon+'.'+thisDate;
				var todayBoolean = date.getYear() == newDate.getYear() && date.getMonth() == newDate.getMonth();

				if(day==i && todayBoolean)
					table += '<td class="today">'+
							 '	<a href="#self">'+
							 '		<span>'+i+'일</span>'+
							 '		<p class="done">15<span>(1)</span></p>'+
							 '		<p class="yet">8<span></span></p>'+
							 '	</a>'+
							 '</td>'; //today
				else if(week == 0)
					table += '<td class="sunday">'+
							 '	<a href="#self">'+
							 '		<span>'+i+'일</span>'+
							 '		<p class="done">11<span></span></p>'+
							 '		<p class="yet"><span></span></p>'+
							 '	</a>'+
							 '</td>'; //sun
				else if(week == 6)
					table += '<td class="saturday">'+
							 '	<a href="#self">'+
							 '		<span>'+i+'일</span>'+
							 '		<p class="done">30<span></span></p>'+
							 '		<p class="yet"><span></span></p>'+
							 '	</a>'+
							 '</td>'; //sat
				else{
					var flag = false;
					for(a=0; a<holidayMax; a++){
						if(options.holiday[a] == holiday){
							flag = true;
							break;
						}
					}
					table += (flag) ? '<td class="holiday">'+
									  '	<a href="#self">'+
									  '		<span>'+i+'일</span>'+
									  '		<p class="done">30<span>(1)</span></p>'+
									  '		<p class="yet"><span></span></p>'+
									  '</a>'+
									  '</td>'
									: '<td>'+
									  '	<a href="#self">'+
									  '		<span>'+i+'일</span>'+
									  '		<p class="done"><span></span></p>'+
									  '		<p class="yet">5<span>(2)</span></p>'+
									  '	</a>'+
									  '</td>'; // holiday, nomal
				}

				week++;

				if(week==7){
					table += '</tr>';
					week=0;
				}
			}
			for(i=1; week!=0; i++){
				if(week==0){table += '<tr>';}
				table += '<td class="nextMonth">'+i+'일</td>'; //다음달
				week++;
				if(week==7){
					table += '</tr>';
					week=0;
				}
			}
			table += '	</tbody>';
			table += '</table>';

			calendalBox.html(table);
		}

		makeCalendar();

		$('#calendar .prevMon').click(function(){
			date.setMonth(date.getMonth()-1);
			makeCalendar();
		});

		$('#calendar .goToday').click(function(){
			date = new Date();

			date.setMonth(date.getMonth());
			makeCalendar();
		});

		$('#calendar .nextMon').click(function(){
			date.setMonth(date.getMonth()+1);
			makeCalendar();
		});

	}
})(jQuery);