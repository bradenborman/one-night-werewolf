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
    else if(playerRole == "ROBBER")
            doRobber();
    else if(playerRole == "INSOMNIAC")
            doInsomniac();
    else
        $("#roleNotSet").show();

}


function doRobber() {
    $("#robberRole").show();
    $("#allPlayers").show();
    $("#roleFeedback").text("Select the user you want to rob");
}


function doInsomniac() {
    //call and get new role for user
    $("#InsomniacRole").show();
    var imgSrc = "Robber.png"
    $("#InsomniacIMG").attr("src", "/imgs/" + imgSrc)
}

function executeRobber() {
 //Is called each time checkbox is changed and role is robber
        var checkedCount = $('.player:checked').length;
        if(checkedCount == 1)
        {
            var checkedVals = $('.player:checkbox:checked').map(function() {return this.value;}).get();
            $(".player:checkbox").attr("disabled", true);
            //todo make call and then update screen with new role
            $("#roleFeedback").text("Rob Completed!");
        }
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

function executeTroubleMaker() {
 //Is called each time checkbox is changed and role is TroubleMaker
        var checkedCount = $('.player:checked').length;
        if(checkedCount == 2)
        {
            var checkedVals = $('.player:checkbox:checked').map(function() {return this.value;}).get();
            //TODO
            //Make call with checkedVals[0] and checkedVals[1]
            $(".player:checkbox").attr("disabled", true);
            $("#roleFeedback").text("Roles Switched");
        }else {
            $("#roleFeedback").text(checkedCount + "  selected");
        }
}