function closeModal() {
    $("#ActionModal").hide();
}

function doAction() {
    $("#UsersRole").text(playerRole);
    $("#ActionModal").show();
    $("#userRoleActionPlaceholder").html(getUsersRoleHTML());
}



function getUsersRoleHTML() {

    if(playerRole == "WEREWOLF")
        $("#werewolfRole").show();
    else
        $("#roleNotSet").show();

}