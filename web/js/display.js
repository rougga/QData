$(document).ready(function () {
    moment.locale('fr', {
        months: 'Janvier_Février_Mars_avril_mai_juin_juillet_août_septembre_octobre_novembre_décembre'.split('_'),
        monthsShort: 'Janv._Févr._Mars_Avr._Mai_Juin_Juil._Août_Sept._Oct._Nov._Déc.'.split('_'),
        monthsParseExact: true,
        weekdays: 'Dimanche_Lundi_Mardi_Mercredi_Jeudi_Vendredi_Samedi'.split('_'),
        weekdaysShort: 'Dim._Lun._Mar._Mer._Jeu._Ven._Sam.'.split('_'),
        weekdaysMin: 'Di_Lu_Ma_Me_Je_Ve_Sa'.split('_'),
        weekdaysParseExact: true,
        longDateFormat: {
            LT: 'HH:mm',
            LTS: 'HH:mm:ss',
            L: 'DD/MM/YYYY',
            LL: 'D MMMM YYYY',
            LLL: 'D MMMM YYYY HH:mm',
            LLLL: 'dddd D MMMM YYYY HH:mm'
        },
        calendar: {
            sameDay: '[Aujourd’hui à] LT',
            nextDay: '[Demain à] LT',
            nextWeek: 'dddd [à] LT',
            lastDay: '[Hier à] LT',
            lastWeek: 'dddd [dernier à] LT',
            sameElse: 'L'
        },
        relativeTime: {
            future: 'dans %s',
            past: 'il y a %s',
            s: 'quelques secondes',
            m: 'une minute',
            mm: '%d minutes',
            h: 'une heure',
            hh: '%d heures',
            d: 'un jour',
            dd: '%d jours',
            M: 'un mois',
            MM: '%d mois',
            y: 'un an',
            yy: '%d ans'
        },
        dayOfMonthOrdinalParse: /\d{1,2}(er|e)/,
        ordinal: function (number) {
            return number + (number === 1 ? 'er' : 'e');
        },
        meridiemParse: /PD|MD/,
        isPM: function (input) {
            return input.charAt(0) === 'M';
        },
        // In case the meridiem units are not separated around 12, then implement
        // this function (look at locale/id.js for an example).
        // meridiemHour : function (hour, meridiem) {
        //     return /* 0-23 hour, given meridiem token and hour 1-12 */ ;
        // },
        meridiem: function (hours, minutes, isLower) {
            return hours < 12 ? 'PD' : 'MD';
        },
        week: {
            dow: 1, // Monday is the first day of the week.
            doy: 4  // Used to determine first week of the year.
        }
    });
    function toggleFullScreen(elem) {
        if ((document.fullScreenElement !== undefined && document.fullScreenElement === null) || (document.msFullscreenElement !== undefined && document.msFullscreenElement === null) || (document.mozFullScreen !== undefined && !document.mozFullScreen) || (document.webkitIsFullScreen !== undefined && !document.webkitIsFullScreen)) {
            if (elem.requestFullScreen) {
                elem.requestFullScreen();
            } else if (elem.mozRequestFullScreen) {
                elem.mozRequestFullScreen();
            } else if (elem.webkitRequestFullScreen) {
                elem.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
            } else if (elem.msRequestFullscreen) {
                elem.msRequestFullscreen();
            }
        } else {
            if (document.cancelFullScreen) {
                document.cancelFullScreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            }
        }
    }
    $("body").on('click', function () {
        toggleFullScreen(document.body);
    });
    var marquee = $('div.marquee');
    marquee.each(function () {
        var mar = $(this), indent = mar.width();
        mar.marquee = function () {
            indent--;
            mar.css('text-indent', indent);
            if (indent < -1 * mar.children('div.marquee-text').width()) {
                indent = mar.width();
            }
        };
        mar.data('interval', setInterval(mar.marquee, 1000 / 60));
    });
    var getWeather = function () {
        $.getJSON("http://api.openweathermap.org/data/2.5/weather?q=Rabat,Morocco&APPID=37e60bb4041c616c61e2f0534aec11a9", function (data) {
            $("#forcast").html(" - "+data.main.temp+"°F - "+data.name+" - " +data.weather[0].main);
        });
    };

    var updateTables = function () {
        $.getJSON("./test.json", function (data) {
            
            
            console.log(data);
        });
    };
    updateTables();
    var updateAll = function () {

        $("#clock").html(moment().format('HH:mm -'));
        $("#date").html(moment().format(' dddd DD MMM YYYY'));
        getWeather();
    };
    setInterval(updateAll(), 10000);
});