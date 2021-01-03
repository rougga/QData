$(document).ready(function () {
    let $status = $(".status");
    for (let i=0;i<$status.length;i++) {
        let $stat = $($status[i]);
        $.get("/QData/GetAgenceStatus",{id:$stat.attr("data-id")},function (data) {   
            console.log(data.status);
            if(data.status){
                $stat.html("<span class='text-center bg-success text-white p-1'>Online</span>");
            }else{
                $stat.html("<span class='text-center bg-danger text-white p-1'>Offline</span>");
            }
                       
        });
    }
});