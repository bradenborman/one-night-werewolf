
var stompClient = null;
var playerId;

function connect() {
    var socket = new SockJS('/one-night-werewolf-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/one-night/users-playing', function (greeting) {
                  var response = JSON.parse(greeting.body)
                  playerId = playerId == null ? response.generatedPlayerId : playerId;
                  regeneratePlayersPlaying(response)
        });

        var player = { username: $("#playerName").text(), lobbyPlaying: $("#lobbyId").text()};
        stompClient.send("/one-night/join-game", {}, JSON.stringify(player));

    });

}

function regeneratePlayersPlaying(response) {
    $("#playingList").empty()
    $("#playingAmount").text(playingList.length)
    $.each(response.playersInLobby, function(i, obj) {
        if(obj.isReadyToStart) {
            $("#playingList").append("<li style='color: green;'>" + obj.username  + "</li>")

            console.log("obj.playerId: " + obj.playerId)
            console.log("playerId: " + playerId)
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

$(document).ready(function(){
    connect()
});


