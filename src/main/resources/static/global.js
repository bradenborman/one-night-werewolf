var playerId;
var lobbyId;
var playerRole;


$(document).ready(function(){
    lobbyId = $("#lobbyId").text()
    connect()


    //Bind events
    //$(document).on('change', '[type=checkbox]', function() {
    $(document).on('change', '.player', function() {

        var checkedCount = $('.player:checked').length;

        if(checkedCount == 2)
        {
            var checkedVals = $('.player:checkbox:checked').map(function() {
                return this.value;
            }).get();

            //TODO
            //Make call with checkedVals[0] and checkedVals[1]

            $(".player:checkbox").attr("disabled", true);
            $("#roleFeedback").text("Roles Switched");
        }else {
            $("#roleFeedback").text(checkedCount + "  selected");
        }

    });


});