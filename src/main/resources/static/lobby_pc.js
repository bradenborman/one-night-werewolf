
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

        stompClient.subscribe('/one-night/drunk-played', function (json) {
              var response = JSON.parse(json.body)
              if(response.lobbyId == lobbyId) {

                    if(response.positionSwapped == "left")
                        $("#middle1").attr("src", "/imgs/" + response.imgSrc_old);

                    if(response.positionSwapped == "middle")
                        $("#middle2").attr("src", "/imgs/" + response.imgSrc_old);

                    if(response.positionSwapped == "right")
                        $("#middle3").attr("src", "/imgs/" + response.imgSrc_old);


                    if(response.playerId == playerId) {
                        if(response.positionSwapped == "left") {
                            $('.scene--card:eq( 0 )').children(".singleCard").addClass('is-flipped');
                            setTimeout(function(){ $('.scene--card:eq( 0 )').children(".singleCard").removeClass('is-flipped'); }, 3000);
                        }


                        if(response.positionSwapped == "middle") {
                            $('.scene--card:eq( 1 )').children(".singleCard").addClass('is-flipped');
                            setTimeout(function(){ $('.scene--card:eq( 1 )').children(".singleCard").removeClass('is-flipped'); }, 3000);
                        }

                        if(response.positionSwapped == "right") {
                            $('.scene--card:eq( 2 )').children(".singleCard").addClass('is-flipped');
                            setTimeout(function(){ $('.scene--card:eq( 2 )').children(".singleCard").removeClass('is-flipped'); }, 3000);
                        }
                    }

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

function executeDrunk() {
           $("#DrunkModal").show();
}

function doDrunkCall(action) {
       $("#cardIMG").fadeOut(1000)
        setTimeout(function(){
               $("#cardIMG").attr("src", "/imgs/back_of_card.jpg");
               $("#cardIMG").fadeIn(1000)
        }, 1200);
        var drunkAction = { userId: playerId, lobbyId: lobbyId, cardSelectedPosition: action};
        stompClient.send("/one-night/drunk-action", {}, JSON.stringify(drunkAction));
}

function closeDrunk() {
           $("#DrunkModal").hide();
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

function closeTroublemaker() {
           $("#TroubleMakerModal").hide();
}

function attemptTroublemaker() {

    var selected = []

       $('input:checkbox[name=TM_SELECT]').each(function()
       {
           if($(this).is(':checked')) {
            selected.push($(this).val())
           }

       });

     if(selected.length == 2)
        executeTroublemaker(selected)

}

function executeTroublemaker(selected) {

    var request = { troubleMakerId_ONE: selected[0], troubleMakerId_TWO: selected[1]};

    $.ajax({
           url: "/swap-cards",
           type:"POST",
           data: JSON.stringify(request),
           contentType:"application/json; charset=utf-8",
           dataType:"json",
           success: function(data){
                //NOT working
                console.log("success")
                console.log(data)
           }
     })

    completeTroublemaker()
}


function completeTroublemaker() {

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
        var path = "/steal/" + lastPlayerTouched + "/" + playerId + "/" + lobbyId
        $.get(path, function(data, status){
            $("#cardRobbed").text(data.newRoleForRobber);
            $("#StealImgOther").attr("src", "/imgs/" + data.imgSrc);
             setTimeout(function(){
                completeSteal()
             }, 600);
        });
}

function completeSteal() {

    var myOrgCard = $('#StealImgUser').attr('src');
    var newCard = $('#StealImgOther').attr('src');

    $("#StealModal").show();
    $(".stealCard").fadeOut(1500);

   setTimeout(function(){
        $("#StealImgUser").attr("src", newCard);
        $("#StealImgOther").attr("src", myOrgCard);
    }, 1490);

    $(".stealCard").fadeIn(300);

    setTimeout(function(){$("#StealModal").fadeOut(300); }, 4000);
}

function executeInsomniac() {
        var path = "/peek/" + playerId + "/" + lobbyId
        $.get(path, function(imgSrc, status){
           if(imgSrc != "No Role")
           {
               $("#peekedImg").attr("src", "/imgs/" + imgSrc);
               $("#PeekedModal").show();
               setTimeout(function(){$("#PeekedModal").fadeOut(300); }, 3000);
           }
        });
}

function isTimeToSetupGame(response) {

    //flip over card before game and each time response is heard
    $('.scene--card').children(".singleCard").removeClass('is-flipped');

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
         //Set my img for robber
          $("#StealImgUser").attr("src", "/imgs/" + data.imgSrc);
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

$(function() {

  var $contextMenuPlayerCard = $("#contextMenuPlayerCard");

    $("body").on("contextmenu", ".playingCard", function(e) {

        var cardImg = $('#cardIMG').attr('src');

        if(cardImg != "/imgs/back_of_card.jpg")
        {
            $contextMenuPlayerCard.css({
              display: "block",
              left: (e.pageX - 15),
              top: (e.pageY - 15)
            });
        }
    return false;
    });

    $contextMenuPlayerCard.on("click", "a", function() {
        $contextMenuPlayerCard.hide();
    });

    $("#contextMenuPlayerCard").mouseleave(function(){
    setTimeout(function(){
        $contextMenuPlayerCard.hide(50);
    }, 185);
    });

});

$(document).ready(function(){
    connect()
    lobbyId = $("#lobbyId").text()

    $(".scene--card").click(function(){
      $(this).children(".singleCard").toggleClass('is-flipped');
    });

    $(".drunkSelection").click(function(){
          closeDrunk()
          var action = $(this).attr('id')
          doDrunkCall(action)
    });


            $(".header").dblclick(function(){
                openTabs()
            });


});

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function openTabs() {

    var names = ["Jimmy", "Lemmy", "Kenny", "William", "Elizabeth", "Nancy", "Joshua", "Stephanie", "Kathleen", "Scott", "Debra", "Diane", "Kyle"]
        for (var i = 0; i < 4; i++) {
             var url = "/lobby/" + lobbyId + "/?playerName=" + names[i]
             window.open(url, '_blank');
             await sleep(100);
        }
}