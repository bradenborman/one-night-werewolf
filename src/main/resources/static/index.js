function startGame() {
    var username = prompt("Please enter your name: ");
    var lobbyNumber = "";
     $.post("/startNewLobby", function(data, status){
           window.location.href = "/lobby/" + data + "/?playerName=" + username
      });
}

function joinGame() {
    var lobbyNumber = prompt("Please enter ID of lobby: ");
    var username = prompt("Please enter your name: ");
    window.location.href = "/lobby/" + lobbyNumber + "/?playerName=" + username
}