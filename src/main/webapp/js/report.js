$(document).ready(function () {
    var colsToShow = {
        gbl: ".0,.1,.2,.3,.4,.10,.13",
        emp: ".0,.1,.2,.3,.4,.10,.13",
        empser: ".0,.1,.2,.3,.4,.5,.11,.14",
        gch: ".0,.1,.2,.3,.4,.10,.13",
        gchser: ".0,.1,.2,.3,.4,.5,.11,.14",
        ndt: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        tht: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        thtt: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        tha: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        thsa: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        cnx: ".0,.1,.2,.3,.4,.5",
        remp: ".0,.1,.3,.10,.11,.12,.13,.14,.15",
        ser: ".0,.1,.2,.3,.4,.5,.6,.7,.8",
        sgch: ".0,.1,.2,.3,.4,.5,.6,.7",
        apl: ".0,.1,.2,.7,.8,.9,.10,.11",
        gla: ".0,.1,.8,.9,.10,.11,.12,.13,.14,.15",
        glt: ".0,.1,.2,.3,.4,.5,.6,.7,.8,.15"
    };
    var colsToHide = {
        glt: ".8",
        gla: ".8"
    };
    var t;
    var showCols = function (type) {
        t = type;
        switch (type) {
            case "gbl":
                $(colsToShow.gbl).show();
                break;
            case "emp" :
                $(colsToShow.emp).show();
                break;
            case "empser":
                $(colsToShow.empser).show();
                break;
            case "gch":
                $(colsToShow.gch).show();
                break;
            case "gchser":
                $(colsToShow.gchser).show();
                break;
            case "tht":
                $(colsToShow.tht).show();
                break;
            case "thtt":
                $(colsToShow.thtt).show();
                break;
            case "tha":
                $(colsToShow.tha).show();
                break;
            case "thsa":
                $(colsToShow.thsa).show();
                break;
            case "ndtt":
                $(colsToShow.ndt).show();
                break;
            case "ndta":
                $(colsToShow.ndt).show();
                break;
            case "ndtsa":
                $(colsToShow.ndt).show();
                break;
            case "cnx":
                $(colsToShow.cnx).show();
                break;
            case "remp":
                $(colsToShow.remp).show();
                break;
            case "ser":
                $(colsToShow.ser).show();
                break;
            case "sgch":
                $(colsToShow.sgch).show();
                break;
            case "apl":
                $(colsToShow.apl).show();
                break;
            case "gla":
                $(colsToShow.gla).show();
                break;
            case "glt":
                $(colsToShow.glt).show();
                break;
            default :
                $(colsToShow.gbl).show();
                break;
        }
        ;

    };
    var hideCols = function (type) {
        t = type;
        switch (type) {
            case "glt":
                $(colsToHide.glt).hide();
                break;
            case "gla":
                $(colsToHide.gla).hide();
                break;
        }

    };
    var rowSpan = function () {
        var elements = $('.db');
        var ids = [];
        for (var i = 0; i < elements.length; i++) {
            if (ids.indexOf($(elements[i]).attr("data-id")) === -1) {
                ids.push($(elements[i]).attr("data-id"));
            } else {
                $(elements[i]).hide();
            }
        }
        for (var i = 0; i < ids.length; i++) {
            $('.db[data-id=' + ids[i] + ']:first').attr("rowspan", $('.db[data-id=' + ids[i] + ']').length);
        }
    };


    $("td").hide();
    $("th").hide();
    $(".Sous-Totale").parent("tr").addClass("bg-warning");
    $(".Totale").parent("tr").addClass("appColor").addClass("text-white").removeClass("border-dark").children(".Totale").removeClass("border-dark");
    $("#refresh").on('click', function () {
        $("#filterForm").attr("action", "");

        $("#filterForm").submit();
    });

    $("#excel").on('click', function () {
    });
    $("#pdf").on('click', function () {
    });
    $("#plus").on('click', function () {
        if ($(this).text() === "PLUS >>") {
            $(this).text("MOIN <<");
            $("td").show();
            $("th").show();
            rowSpan();
            hideCols(t);
        } else {
            $("td").hide();
            $("th").hide();
            showCols(t);
            rowSpan();
            $(this).text("PLUS >>");
        }
    });
    $("#date1").on('change', function () {
        if ($(this).val() > $("#date2").val()) {
            alert("La date Du est plus gros que la date Au.");
            $("#date2").val(new Date().toLocaleDateString('en-CA'));
        }
        addDateToSessionStorage();
        updateLinks();
    });
    $("#date2").on('change', function () {
        if ($("#date1").val() > $("#date2").val()) {
            alert("La date Du est plus gros que la date Au.");
            $("#date2").val(new Date().toLocaleDateString('en-CA'));
        }
        addDateToSessionStorage();
        updateLinks();
    });
    $("#today").on('click', function () {
        $("#date1").val(moment().format('YYYY-MM-DD'));
        $("#date2").val(moment().format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#yesterday").on('click', function () {
        $("#date1").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'days').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#cWeek").on('click', function () {
        $("#date1").val(moment().startOf('week').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('week').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lWeek").on('click', function () {
        $("#date1").val(moment().subtract(1, 'week').startOf('week').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'week').endOf('week').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#cMonth").on('click', function () {
        $("#date1").val(moment().startOf('month').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('month').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lMonth").on('click', function () {
        $("#date1").val(moment().subtract(1, 'month').startOf('month').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'month').endOf('month').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#cYear").on('click', function () {
        $("#date1").val(moment().startOf('year').format('YYYY-MM-DD'));
        $("#date2").val(moment().endOf('year').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });
    $("#lYear").on('click', function () {
        $("#date1").val(moment().subtract(1, 'year').startOf('year').format('YYYY-MM-DD'));
        $("#date2").val(moment().subtract(1, 'year').endOf('year').format('YYYY-MM-DD'));
        addDateToSessionStorage();
        updateLinks();
    });

    showCols(type);
    rowSpan();
    var getCheckedBoxes = function () {
        var searchIDs = $(".check:checked").map(function () {
            if ($(this).val() !== "on") {
                return $(this).val();
            }

        }).get();
        return searchIDs;
    };
    var getCheckedZones = function () {
        var searchIDs = $(".zoneCheckbox:checked").map(function () {
            if ($(this).val() !== "on") {
                return $(this).val();
            }

        }).get();
        return searchIDs;
    };
    var addDateToSessionStorage = function () {
        sessionStorage.setItem("date1", $("#date1").val());
        sessionStorage.setItem("date2", $("#date2").val());
    };
    var filterDate = function (date) {
        if (date) {
            return date;
        } else {
            return moment().format("YYYY-MM-DD");
        }
    };
    var updateLinks = function () {
        var ids = getCheckedBoxes();
        var checkedZonesIds = getCheckedZones();
        var date1 = filterDate(sessionStorage.getItem("date1"));
        var date2 = filterDate(sessionStorage.getItem("date2"));

        var agencesLink = "";
        let exportExcelURL = $("#excel").attr("href");
        let exportPdfURL = $("#pdf").attr("href");
        for (i = 0; i < ids.length; i++) {
            agencesLink += "&agences=" + ids[i];
        }
        agencesLink += "&date1=" + date1 + "&date2=" + date2;
        $("#excel").attr("href",exportExcelURL+agencesLink);
        $("#pdf").attr("href",exportPdfURL+agencesLink);
        $.each($(".d"), function (i, v) {
            var link = $(v).attr("href");
            link = link.substring(0, link.indexOf("d=d") + 3);
            link += agencesLink;
            $(v).attr("href", link);
        });
        sessionStorage.setItem("dbs", JSON.stringify(ids));
        sessionStorage.setItem("selectedZones", JSON.stringify(checkedZonesIds));
        console.log("agences ids:");
        console.log(ids);
        console.log("zone ids:");
        console.log(checkedZonesIds);
    };

    let refreshSelectAllZonesCheckbox = function () {
        if ($(".check").length === $(".check:checked").length && $(".zoneCheckbox").length === $(".zoneCheckbox:checked").length) {
            $("#selectAllZones").prop("checked", true);
        } else {
            $("#selectAllZones").prop("checked", false);
        }
    };

    $("#zones").on('change', ".check", function () {
        let id_zone = $(this).attr("data-zoneId");
        let agencesInZone = $("input[data-zoneId=" + id_zone + "]");
        let agencesSelectedInZone = $("input[data-zoneId=" + id_zone + "]:checked");
        if (agencesInZone.length === agencesSelectedInZone.length) {
            $("input[value=" + id_zone + "]").prop("checked", true);
        } else {
            $("input[value=" + id_zone + "]").prop("checked", false);
        }
        sessionStorage.setItem("selectedZones", JSON.stringify(getCheckedZones()));
        sessionStorage.setItem("dbs", JSON.stringify(getCheckedBoxes()));
        refreshSelectAllZonesCheckbox();
        updateLinks();

    });
    $("#zones").on('change', ".zoneCheckbox", function () {
        let selectedZones = getCheckedZones();
        // change selected agences
        let $checkbox = $(this);
        if ($checkbox.prop("checked")) {
            let id_zone = $checkbox.val();
            $("input[data-zoneId=" + id_zone + "]").prop("checked", true);
        } else {
            let id_zone = $checkbox.val();
            $("input[data-zoneId=" + id_zone + "]").prop("checked", false);
        }
        sessionStorage.setItem("selectedZones", JSON.stringify(getCheckedZones()));
        sessionStorage.setItem("dbs", JSON.stringify(getCheckedBoxes()));
        refreshSelectAllZonesCheckbox();
        updateLinks();
    });
    $(".ck-text").on('click', function () {

    });

    $(".zone-ck-text").on('click', function () {

    });
    $("#selectAllZones").on('change', function () {
        if ($(this).prop("checked")) {
            $(".zoneCheckbox").prop("checked", true);
            $(".check").prop("checked", true);
        } else {
            $(".zoneCheckbox").prop("checked", false);
            $(".check").prop("checked", false);
        }
        sessionStorage.setItem("selectedZones", JSON.stringify(getCheckedZones()));
        sessionStorage.setItem("dbs", JSON.stringify(getCheckedBoxes()));
        updateLinks();
    });
    var checkCheckBoxes = function () {
        var arr = JSON.parse(sessionStorage.getItem("dbs"));
        if (arr === null) {
            $(".check").prop("checked", true);
        } else {
            $(".check").prop("checked", false);
            for (var i = 0; i < arr.length; i++) {
                $(".check[value='" + arr[i] + "']").prop("checked", true);
            }
        }

    };
    var checkZoneCheckBoxes = function () {
        var arr = JSON.parse(sessionStorage.getItem("selectedZones"));
        if (arr === null) {
            $(".zoneCheckbox").prop("checked", true);
        } else {
            $(".zoneCheckbox").prop("checked", false);
            for (var i = 0; i < arr.length; i++) {
                $(".zoneCheckbox[value='" + arr[i] + "']").prop("checked", true);
            }
        }

    };
    var setDates = function () {
        var date1 = filterDate(sessionStorage.getItem("date1"));
        var date2 = filterDate(sessionStorage.getItem("date2"));
        $("#date1").val(date1);
        $("#date2").val(date2);

    };
    checkCheckBoxes();
    checkZoneCheckBoxes();
    setDates();
    updateLinks();
    refreshSelectAllZonesCheckbox();
});
