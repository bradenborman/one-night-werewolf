function startGame() {

    var username = prompt("Please enter your name: ");
    var lobbyNumber = "";
     $.post("/startNewLobby", function(data, status){
           window.location.href = "/lobby/" + data + "/?playerName=" + username
      });
}