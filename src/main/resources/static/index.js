
var stompClient = null;

function connect() {
    var socket = new SockJS('/one-night-werewolf-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/one-night/users-playing', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function joinGame() {
    alert("sending")
    stompClient.send("/one-night/join-game", {}, "test");
}

function showGreeting(message) {
    alert(message)
}

$(document).ready(function(){
    connect()
});