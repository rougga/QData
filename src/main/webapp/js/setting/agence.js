$(document).ready(function () {
    let $status = $(".status");
    for (let i = 0; i < $status.length; i++) {
        let $stat = $($status[i]);
        $.get("/QData/GetAgenceStatus", {id: $stat.attr("data-id")}, function (data) {
            console.log(data.status);
            if (data.status) {
                $stat.html("<span class='text-center bg-success text-white p-1'>Online</span>");

                $(".updateBtn[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
            } else {
                $stat.html("<span class='text-center bg-danger text-white p-1'>Offline</span>");
                $(".updateBtn[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
            }

        });
    }
    
    
    $("#dbAdd").on("click", function () {
        $("#dbForm").attr("action", "/QData/AddDatabase");
        $("#exampleModalLabel").text("Ajouter une Agence:");
        $("#agence").val("");
        $("#zone").val("");
        $("#host").val("127.0.0.1");
        $("#port").val("8888");
        $("#id").val("");
        $("button:submit").text("Ajouter");
        $("#dbModal").modal('toggle');
        $("#dbModal").modal('toggle');
    });

    $("table").on("click", ".dbEdit", function () {
        $("#dbForm").attr("action", "/QData/EditDatabase");
        $("#exampleModalLabel").text("Modifier l'Agence:");
        $("#agence").val($(this).parent().parent().children(".agenceName").text());
        $("#zone").val($(this).parent().parent().children(".agenceZone").attr("data-zone"));
        $("#user").val($(this).parent().parent().children(".agenceUser").text());
        $("#pass").val($(this).parent().parent().children(".agencePass").text());
        $("#id").val($(this).attr("data-id"));
        $("button:submit").text("Modifier");
        $("#dbModal").modal('toggle');
        console.log();
    });
});