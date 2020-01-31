function connect() {
    var socket = new SockJS('/one-night-werewolf-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/one-night/users-playing', function (json) {
                  var response = JSON.parse(json.body)
                  if(response.lobbyId == lobbyId) {
                      console.log(response)
                      playerId = playerId == null ? response.generatedPlayerId : playerId;
                      isTimeToSetupGame(response)
                  }
        });

        var player = { username: $("#playerName").text(), lobbyPlaying: lobbyId};
        stompClient.send("/one-night/join-game", {}, JSON.stringify(player));

    });

}


function readyToStart() {
    var readyToStartObj = { playerId: playerId, lobbyPlaying: $("#lobbyId").text()};
    stompClient.send("/one-night/ready-to-start", {}, JSON.stringify(readyToStartObj));
    $("#startBtn").css({"border": "2px solid green", "box-shadow": "2px 4px green"});
}

function isTimeToSetupGame(response) {

    if(response.readyToStartGame != null && response.readyToStartGame) {
        $("#startBtn").fadeOut()
        $("#actionBtn").prop( "disabled", false);
        makeCallToRetrieveInitialRoll();
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
         playerRole = data.displayName;
         $("#myCardImg").attr("src", "/imgs/" + data.imgSrc);
       }
     })
}