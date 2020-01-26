
var stompClient = null;
var playerId;
var lobbyId;
var lastPlayerTouched;

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
    $("#TroubleMakerSelectList").empty()

    $("#playingAmount").text(response.playersInLobby.length)
    $.each(response.playersInLobby, function(i, obj) {
        if(obj.isReadyToStart != null && obj.isReadyToStart) {

            if(obj.playerId == playerId) {
                 $("#playingList").append('<span id="' + obj.playerId +'" class="badge badge-pill ME meReady">ME</span>')
            }else {
                $("#playingList").append('<span id="' + obj.playerId +'" class="badge badge-pill badge-success">' + obj.username +'</span>')
            }

            //Hide button if user is ready to go
            if(obj.playerId == playerId) {
                 $("#readyToStartBtn").hide(100)
                 $("#homeBtn").hide(100)
            }
        }
        else {
           if(obj.playerId == playerId) {
                $("#playingList").append('<span id="' + obj.playerId +'" class="badge badge-pill ME meNotReady">ME</span>')
            }else {
                $("#playingList").append('<span id="' + obj.playerId +'" class="badge badge-pill badge-secondary">' + obj.username +'</span>')
            }
        }

        //ALSO ADD TO TROUBLE MAKER SELECT LIST
        var input = '<input class="TM_SELECTChk" type="checkbox" name="TM_SELECT" value="' + obj.playerId +'"> ' + obj.username + '<br>'
        if(obj.playerId != playerId)
            $(input).appendTo('#TroubleMakerSelectList');

    });
}

function readyToStart() {
    var readyToStartObj = { playerId: playerId, lobbyPlaying: $("#lobbyId").text()};
    stompClient.send("/one-night/ready-to-start", {}, JSON.stringify(readyToStartObj));
}

function returnHome() {
    window.location.href = "/";
}

function executePeek() {
    var path = "/peek/" + lastPlayerTouched + "/" + lobbyId
    $.get(path, function(imgSrc, status){
       if(imgSrc != "No Role")
       {
           $("#peekedImg").attr("src", "/imgs/" + imgSrc);
           $("#PeekedModal").show();
           setTimeout(function(){$("#PeekedModal").fadeOut(300); }, 3000);
       }
    });

}

function openTroublemaker() {
           $("#TroubleMakerModal").show();
}



function executeTroublemaker() {

           $("#TroubleMakerSelectScreenScreen").hide();
           $("#TroubleMakerAnnimationScreen").show();
           $("#card1").addClass("moveCard1");
           $("#card2").addClass("moveCard2");

          //Put screens back to normal

          $(".TM_SELECTChk").prop("checked", false);

          setTimeout(function(){
                $("#TroubleMakerModal").fadeOut(300);
           }, 3000);
          setTimeout(function(){
               $("#TroubleMakerSelectScreenScreen").show();
               $("#TroubleMakerAnnimationScreen").hide();
          }, 3300);

}


function executeSteal() {
    //lastPlayerTouched
    //$("#peekedImg").attr("src", "/imgs/" + imgSrc);

    var myOrgCard = $('#StealImgUser').attr('src');
    var newCard = $('#StealImgOther').attr('src');

    $("#cardRobbed").text("_PLACEHOLDER_");

    $("#StealModal").show();
    $(".stealCard").fadeOut(1500);

   setTimeout(function(){
        $("#StealImgUser").attr("src", newCard);
        $("#StealImgOther").attr("src", myOrgCard);
    }, 1490);

    $(".stealCard").fadeIn(300);

    setTimeout(function(){$("#StealModal").fadeOut(300); }, 4000);

   // setTimeout(function(){$("#StealModal").fadeOut(300); }, 3000);
}

function isTimeToSetupGame(response) {

    //Hide Pregame and change text at top to started and flip over cards that were flipped pre game
    if(response.readyToStartGame != null && response.readyToStartGame) {
        $(".singleCard").removeClass("is-flipped");
        $("#LobbyTxt").text("")
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

$(function() {

  var $contextMenu = $("#contextMenu");

    $("body").on("contextmenu", ".badge", function(e) {

        lastPlayerTouched = $(this).attr('id')

        if(!$(this).hasClass("ME")) {
            $contextMenu.css({
              display: "block",
              left: (e.pageX - 15),
              top: (e.pageY - 15)
            });
        }
    return false;
    });

    $contextMenu.on("click", "a", function() {
     $contextMenu.hide();
    });

    $("#contextMenu").mouseleave(function(){
    setTimeout(function(){
        $contextMenu.hide(50);
    }, 185);
    });

});


$(document).ready(function(){
    connect()
    lobbyId = $("#lobbyId").text()

    $(".scene--card").click(function(){
      $(this).children(".singleCard").toggleClass('is-flipped');
    });


            //For quick testing
            var names = ["Jimmy", "Lemmy", "Kenny", "William", "Elizabeth", "Nancy", "Joshua", "Stephanie", "Kathleen", "Scott", "Debra", "Diane", "Kyle"]

            $(".header").dblclick(function(){
              for (var i = 0; i < 2; i++) {
                 var url = "/lobby/" + lobbyId + "/?playerName=" + names[i]
                 window.open(url, '_blank');
              }
            });


});

