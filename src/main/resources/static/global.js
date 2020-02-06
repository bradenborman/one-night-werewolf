var playerId;
var lobbyId;
var playerRole;


$(document).ready(function(){
    lobbyId = $("#lobbyId").text()
    connect()


    //Bind events
    //$(document).on('change', '[type=checkbox]', function() {
    $(document).on('change', '.player', function() {
        console.log("Changed: " + playerRole)
        if(playerRole == "TROUBLEMAKER")
            executeTroubleMaker()
        else if(playerRole == "ROBBER")
            executeRobber()
    });


});