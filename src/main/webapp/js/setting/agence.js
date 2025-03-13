$(document).ready(function () {
    let $status = $(".status");
    for (let i = 0; i < $status.length; i++) {
        let $stat = $($status[i]);
        $.get("/QData/GetAgenceStatus", {id: $stat.attr("data-id")}, function (data) {
            //data is null
            if (data) {
                let version = data.version;
                if (!version) {
                    version = "0.0";
                }
                $stat.html("<span class='text-center bg-success text-white p-1'>Online</span><span class='text-center bg-secondary text-white p-1'>v" + version + "</span>");

                $(".updateBtn[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
                $(".mng[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
                $(".qstates[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
            } else {
                $stat.html("<span class='text-center bg-danger text-white p-1'>Offline</span>");
                $(".updateBtn[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
                $(".mng[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
                $(".qstates[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
            }

        });
    }
    $("#dbAddButton").on("click", function () {
        let zone = $("#zone").val();
        let hote = $("#host").val();
        let port = $("#port").val();
        let name = $("#agence").val();
        if (zone && hote && port && name) {
            $("#dbForm").submit();
        } else {
            alert("Remplir tous les champs !");
        }
    });

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
        let domain = $(this).parent().parent().children(".agenceHost").text();
        $("#dbForm").attr("action", "/QData/EditDatabase");
        $("#exampleModalLabel").text("Modifier l'Agence:");
        $("#agence").val($(this).parent().parent().children(".agenceName").text());
        $("#zone").val($(this).parent().parent().children(".agenceZone").attr("data-zone"));
        $("#port").val(domain.split(":")[1]);
        $("#host").val(domain.split(":")[0]);
        $("#id").val($(this).attr("data-id"));
        $("button:submit").text("Modifier");
        $("#dbModal").modal('toggle');
        console.log();
    });
});