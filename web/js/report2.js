$(document).ready(function () {
    var colsToShow = {
        gbl: ".0,.1,.2,.3,.4,.10,.13",
        emp: ".0,.1,.2,.3,.4,.10,.13",
        empser: ".0,.1,.2,.3,.4,.5,.11,.14",
        gch: ".0,.1,.2,.3,.4,.10,.13",
        gchser: ".0,.1,.2,.3,.4,.5,.11,.14",
        ndt: ".0,.8,.9,.10,.11,.12,.13,.14,.15,.16,.17,.18",
        cnx: ".0,.1,.2,.3,.4,.5",
        remp: ".0,.1,.2,.10,.11,.12,.13,.14,.15",
        ser: ".0,.1,.2,.3,.4,.5,.6,.7,.8",
        sgch: ".0,.1,.2,.3,.4",
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
            case "gchserv":
                $(colsToShow.gchser).show();
                break;
            case "ndt":
                $(colsToShow.ndt).show();
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
        $(".db1 .0").hide();
        $(".db1 th:first").show().attr("rowspan", "20000");
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
    $("td").hide();
    $("th").hide();
    $(".Sous-Totale").parent("tr").addClass("bg-warning");
    $("#excel").on('click', function () {
        $("#format").val("excel");
        $("#printForm").submit();
    });
    $("#word").on('click', function () {
        $("#format").val("word");
        $("#printForm").submit();
    });
    $("#pdf").on('click', function () {
        $("#format").val("pdf");
        $("#printForm").submit();
    });
    $("#plus").on('click', function () {
        if ($(this).text() === "PLUS >>") {
            $(this).text("MOIN <<");
            $("td").show();
            $("th").show();
        } else {
            $("td").hide();
            $("th").hide();
            showCols(t);
            $(this).text("PLUS >>");
        }
    });
    $("#date1").on('change', function () {
        if ($(this).val() > $("#date2").val()) {
            alert("La date Du est plus gros que la date Au.");
            $("#date2").val(new Date().toLocaleDateString('en-CA'));
        }
    });
    $("#date2").on('change', function () {
        if ($("#date1").val() > $("#date2").val()) {
            alert("La date Du est plus gros que la date Au.");
            $("#date2").val(new Date().toLocaleDateString('en-CA'));
        }
    });
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
});


