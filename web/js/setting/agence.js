$(document).ready(function () {
    let $status = $(".status");
    for (let i = 0; i < $status.length; i++) {
        let $stat = $($status[i]);
        $.get("/QData/GetAgenceStatus", {id: $stat.attr("data-id")}, function (data) {
            console.log(data.status);
            if (data.status) {
                $stat.html("<span class='text-center bg-success text-white p-1'>Online</span>");
                
                $(".dbUpdateToday[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
                $(".dbUpdateAll[data-id='" + $stat.attr("data-id") + "']").removeClass("disabled");
            } else {
                $stat.html("<span class='text-center bg-danger text-white p-1'>Offline</span>");
                $(".dbUpdateToday[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
                $(".dbUpdateAll[data-id='" + $stat.attr("data-id") + "']").addClass("disabled");
            }

        });
    }
});