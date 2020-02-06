package borman.onenight.models;

public enum Role {

    WEREWOLF1("WEREWOLF", "WEREWOLF", "Werewolf.png"),
    WEREWOLF2("WEREWOLF", "WEREWOLF", "Werewolf.png"),
    TROUBLEMAKER("TROUBLEMAKER", "VILLAGER", "Troublemaker.png"),
    ROBBER("ROBBER", "VILLAGER", "Robber.png"),
    INSOMNIAC("INSOMNIAC", "VILLAGER", "Insomniac.png"),
    DRUNK("DRUNK", "VILLAGER", "Drunk.png"),
    SEER("SEER", "VILLAGER", "Seer.png"),
    TANNER("TANNER", "TANNER", "Tanner.png"),
    HUNTER("HUNTER", "VILLAGER", "Hunter.png"),
    MINION("MINION", "WEREWOLF", "Minion.png"),
    VILLAGER1("VILLAGER", "VILLAGER", "Villager.png"),
    VILLAGER2("VILLAGER", "VILLAGER", "Villager.png"),
    VILLAGER3("VILLAGER", "VILLAGER", "Villager.png"),
    MASON1("MASON", "VILLAGER", "Mason.png"),
    MASON2("MASON", "VILLAGER", "Mason.png");

    private String displayName;
    private String teamName;
    private String imgSrc;

    Role(String displayName, String teamName, String imgSrc) {
        this.displayName = displayName;
        this.teamName = teamName;
        this.imgSrc = imgSrc;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
