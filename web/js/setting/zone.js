$(document).ready(function () {
    $("#dbAdd").on("click", function () {
        $("#dbForm").attr("action", "/QData/AddZone");
        $("#exampleModalLabel").text("Ajouter une Zone:");
        $("#zoneName").val("");
        $("#zoneCity").val("");
        $("#zoneCode").val("");
        $("button:submit").text("Ajouter");
        $("#dbModal").modal('toggle');
    });

    $("table").on("click", ".dbEdit", function () {
        $("#dbForm").attr("action", "/QData/EditZone");
        $("#exampleModalLabel").text("Modifier la Zone:");
        $("#zoneName").val($(this).parent().parent().children(".zoneName").text());
        $("#zoneCity").val($(this).parent().parent().children(".zoneCity").text());
        $("#zoneCode").val($(this).parent().parent().children(".zoneCode").text());
        $("#zoneId").val($(this).attr("data-id"));
        $("button:submit").text("Modifier");
        $("#dbModal").modal('toggle');
        console.log();
    });
    
    

});