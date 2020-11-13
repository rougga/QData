$(document).ready(function () {
    var colsToShow = {
        gbl: ".0,.1,.2,.3,.4,.10,.13",
        emp: ".0,.1,.2,.3,.4,.10,.13",
        empser: ""
    };
    var t;
    var hidCols = function (type) {
        t=type;
        switch (type) {
            case "gbl":
                $(colsToShow.gbl).show();
                break;
            case "emp":
                $(colsToShow.emp).show();
                break;
                
        };
    };
    $(".db1 th,thead tr th ,.db1").hide();

    $(".db1 th:first").show().attr("rowspan", "16");
    $(".db1:last").addClass("bg-warning");
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
            $(".db1,thead tr th").show();
            $(this).text("MOIN <<");
            $(".db1 th:first").show().attr("rowspan", "16");
        } else {
            $(".db1,thead tr th").hide();
            hidCols(t);
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
});