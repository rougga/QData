$(document).ready(function () {
    var sites = $("#site");
    var services = $("#service option");
    
    var filterServices = function (db_id){
        $(services).show();
        for(var i =0;i<services.length;i++){
            if($(services[i]).attr("data-db-id")!=db_id){
                $(services[i]).hide();
            }
        }
    };
    
    sites.on('change',function () {
        filterServices($(this).val());
    });
    
});