//$("#userTbl,#extraTbl,#goalTbl,#dbTbl").hide();
$(document).ready(function () {
    //table show/hide
   // $("#userTbl,#extraTbl,#goalTbl,#dbTbl").hide();
    $("#cibleBtn").on('click', function () {
        $("#cibleTbl").slideDown("slow");
        $("#userTbl,#extraTbl,#goalTbl,#dbTbl").slideUp("slow");
        //color
        $("#userBtn,#extraBtn,#goalBtn,#dbBtn").removeClass("btn-primary").addClass("btn-secondary");
        $("#cibleBtn").addClass("btn-primary").removeClass("btn-secondary");
    });
    $("#userBtn").on('click', function () {
        $("#userTbl").slideDown("slow");
        $("#cibleTbl,#extraTbl,#goalTbl,#dbTbl").slideUp("slow");
        //color
        $("#cibleBtn,#extraBtn,#goalBtn,#dbBtn").removeClass("btn-primary").addClass("btn-secondary");
        $("#userBtn").addClass("btn-primary").removeClass("btn-secondary");
    });
    $("#extraBtn").on('click', function () {
        $("#extraTbl").slideDown("slow");
        $("#cibleTbl,#userTbl,#goalTbl,#dbTbl").slideUp("slow");
        //color
        $("#userBtn,#cibleBtn,#goalBtn,#dbBtn").removeClass("btn-primary").addClass("btn-secondary");
        $("#extraBtn").addClass("btn-primary").removeClass("btn-secondary");
    });
    $("#goalBtn").on('click', function () {
        $("#goalTbl").slideDown("slow");
        $("#cibleTbl,#userTbl,#extraTbl,#dbTbl").slideUp("slow");
        //color
        $("#userBtn,#cibleBtn,#extraBtn,#dbBtn").removeClass("btn-primary").addClass("btn-secondary");
        $("#goalBtn").addClass("btn-primary").removeClass("btn-secondary");
    });
    $("#dbBtn").on('click', function () {
        $("#dbTbl").slideDown("slow");
        $("#cibleTbl,#userTbl,#extraTbl,#goalTbl").slideUp("slow");
        //color
        $("#userBtn,#cibleBtn,#extraBtn,#goalBtn").removeClass("btn-primary").addClass("btn-secondary");
        $("#dbBtn").addClass("btn-primary").removeClass("btn-secondary");
    });
    $(".clickable-row,.clickable-row2,.clickable-row3,.clickable-row4").css("cursor","pointer");
    //select row from table

    //cible
    $(".clickable-row").click(function () {
        if ($(this).hasClass("bg-warning")) {
            $(this).removeClass('bg-warning');
            $("#cibleDlt").attr("href", "#");
            $("#cibleEdit").removeAttr("data-toggle");
            $("#cibleEdit").removeAttr("data-target");


        } else {
            $(this).addClass('bg-warning').siblings().removeClass('bg-warning');
            var id = $(this).find("th").attr("data-id");
            $("#cibleDlt").attr("href", "./Delete?type=cible&id=" + id);
            $("#cibleEdit").attr("data-toggle", "modal");
            $("#cibleEdit").attr("data-target", "#cibleModal");
            $("#serviceName").hide();
            $("#servicePlace").val($(this).find("th").text());
            $("#servicePlace").attr("type", "text").attr("disabled", "disabled");
            $("#serviceName").val($(this).find("th").attr("data-id"));
            $("#cibleD").val($($(this).find("td")[2]).find("b").text());
            var cibleA = $($(this).find("td")[0]).find("b").text().split(":");
            var cibleT = $($(this).find("td")[1]).find("b").text().split(":");
            $("#cibleAH").val(cibleA[0]);
            $("#cibleAM").val(cibleA[1]);
            $("#cibleAS").val(cibleA[2]);

            $("#cibleTH").val(cibleT[0]);
            $("#cibleTM").val(cibleT[1]);
            $("#cibleTS").val(cibleT[2]);
        }
    });


    //extra
    $(".clickable-row2").click(function () {
        if ($(this).hasClass("bg-warning")) {
            $(this).removeClass('bg-warning');
            $("#extraDlt").attr("href", "#");
        } else {
            $(this).addClass('bg-warning').siblings().removeClass('bg-warning');
            var id = $(this).find("th").attr("data-id");
            $("#extraDlt").attr("href", "./Delete?type=extra&id=" + id);
        }
    });
    
    //goal
    $(".clickable-row4").click(function () {
        if ($(this).hasClass("bg-warning")) {
            $(this).removeClass('bg-warning');
        } else {
            $(this).addClass('bg-warning').siblings().removeClass('bg-warning');
            
        }
    });
    
   /* $(".clickable-row5").click(function () {
        if ($(this).hasClass("bg-warning")) {
            $(this).removeClass('bg-warning');
        } else {
            $(this).addClass('bg-warning').siblings().removeClass('bg-warning');
        }
    });*/
    
    //user
    $(".clickable-row3").click(function () {
        if ($(this).hasClass("bg-warning")) {
            $(this).removeClass('bg-warning');
            $("#userDlt").attr("href", "#");
            $("#userEdit").removeAttr("data-toggle");
            $("#userEdit").removeAttr("data-target");
        } else {
            $(this).addClass('bg-warning').siblings().removeClass('bg-warning');
            var username = $(this).find("th").text();
            $("#userDlt").attr("href", "./Delete?type=user&username=" + username);
            $("#userEdit").attr("data-toggle", "modal");
            $("#userEdit").attr("data-target", "#userModal");
        }
    });
    // remove already added services
    var serviceNames = $(".clickable-row th");
    var selectOptions = $("#serviceName").children("option");

    for (var i = 1; i < selectOptions.length; i++) {
        for (var j = 0; j < serviceNames.length; j++) {
            if ($(selectOptions[i]).text().trim() === $(serviceNames[j]).text().trim()) {
                $(selectOptions[i]).hide();
            }
        }
    }
    //change modal 
    $("#cibleAdd").on('click', function () {
        $("#exampleModalLabel").text("Ajouter Cible :");
        $("#cibleSubmit").text("Ajouter");
        $(".clickable-row").removeClass('bg-warning');
        $("#cibleDlt").attr("href", "#");
        $("#cibleEdit").removeAttr("data-toggle");
        $("#cibleEdit").removeAttr("data-target");
        $("#serviceName").removeAttr("disabled");
        $("#serviceName").val('0');
        $("#cibleD").val("0");
        $("#cibleAH").val(0);
        $("#cibleAM").val(0);
        $("#cibleAS").val(0);
        $("#cibleTH").val(0);
        $("#cibleTM").val(0);
        $("#cibleTS").val(0);
        $("#cibleForm").attr("action", "./Add");
        $("#servicePlace").val("");
        $("#servicePlace").attr("type", "hidden").attr("disabled", "disabled");
        $("#serviceName").show();

    });
    $("#cibleEdit").on('click', function () {
        $("#exampleModalLabel").text("Modifier Cible :");
        $("#cibleSubmit").text("Enregistrer");
        $("#cibleForm").attr("action", "./Edit");
        console.log($("#serviceName").val());
    });

    //user
    $("#userAdd").on('click', function () {
        $("#userModalLabel").text("Ajouter Utilisateur :");
        $("#userSubmit").text("Ajouter");
        $(".clickable-row3").removeClass('bg-warning');
        $("#userDlt").attr("href", "#");
        $("#userEdit").removeAttr("data-toggle");
        $("#userEdit").removeAttr("data-target");
        $("#grade").val("0");
        $("#userForm").attr("action", "./Add");

    });
    $("#userEdit").on('click', function () {
        $("#userModalLabel").text("Modifier Utilisateur :");
        $("#userSubmit").text("Enregistrer");
        $("#userForm").attr("action", "./Edit");
        console.log($("#serviceName").val());
    });


});