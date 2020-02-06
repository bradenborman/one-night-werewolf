function closeModal() {
    $("#ActionModal").hide();
    $("#roleFeedback").text("");
}

function doAction() {
    $("#UsersRole").text(playerRole);
    $("#ActionModal").show();
    getUsersRoleHTML()
}

function getUsersRoleHTML() {

    $(".role").hide();
    $(".supplier").hide();

    if(playerRole == "WEREWOLF")
        doWerewolf();
    else if(playerRole == "TROUBLEMAKER")
        doTroubleMaker();
    else
        $("#roleNotSet").show();

}



function doWerewolf() {
//TODO call out and get amount of werewolves in play. Show cards accordingly

    $("#werewolfRole").show();
    $("#middleCards").show();
}

function doTroubleMaker() {
    $("#troubleMakerRole").show();
    $("#allPlayers").show();
}