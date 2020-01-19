
var stompClient = null;
var playerId;
var lobbyId;

function connect() {
    var socket = new SockJS('/one-night-werewolf-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/one-night/users-playing', function (json) {
                  var response = JSON.parse(json.body)

                  if(response.lobbyId == lobbyId) {
                      playerId = playerId == null ? response.generatedPlayerId : playerId;
                      regeneratePlayersPlaying(response)
                      isTimeToSetupGame(response)
                  } //Otherwise ignore -- For another lobby
        });

        var player = { username: $("#playerName").text(), lobbyPlaying: $("#lobbyId").text()};
        stompClient.send("/one-night/join-game", {}, JSON.stringify(player));

    });

}

function regeneratePlayersPlaying(response) {
    $("#playingList").empty()
    $("#playingAmount").text(response.playersInLobby.length)
    $.each(response.playersInLobby, function(i, obj) {
        if(obj.isReadyToStart != null && obj.isReadyToStart) {
            $("#playingList").append("<li style='color: green;'>" + obj.username  + "</li>")

            //Hide button if user is ready to go
            if(obj.playerId == playerId) {
                 $("#readyToStartBtn").hide(100)
            }
        }
        else {
            $("#playingList").append("<li style='color: red;'>" + obj.username  + "</li>")
        }
    });
}

function readyToStart() {
    var readyToStartObj = { playerId: playerId, lobbyPlaying: $("#lobbyId").text()};
    stompClient.send("/one-night/ready-to-start", {}, JSON.stringify(readyToStartObj));
}

function returnHome() {
    window.location.href = "/";
}

function isTimeToSetupGame(response) {

    //Hide Pregame and change text at top to started
    if(response.readyToStartGame != null && response.readyToStartGame) {
        $("#preGame").hide(200);
        $("#LobbyTxt").text("Please look at your card.")
        $("#gamePlay").show(200);
        makeCallToRetrieveInitialRoll(response);
    }

}

function makeCallToRetrieveInitialRoll() {

    var request = { playerId: playerId, lobbyPlaying: lobbyId};

     $.ajax({
       url: "/retrieve-roll",
       type:"POST",
       data: JSON.stringify(request),
       contentType:"application/json; charset=utf-8",
       dataType:"json",
       success: function(data){
         console.log(data)
         $("#cardIMG").attr("src", "/imgs/" + data.imgSrc);
         $("#middle1").attr("src", "/imgs/" + data.middleCards[0]);
         $("#middle2").attr("src", "/imgs/" + data.middleCards[1]);
         $("#middle3").attr("src", "/imgs/" + data.middleCards[2]);
       }
     })
}

$(document).ready(function(){
    connect()
    lobbyId = $("#lobbyId").text()
});