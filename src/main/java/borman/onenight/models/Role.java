package borman.onenight.models;

public enum Role {

    WEREWOLF1("WEREWOLF", "WEREWOLF", ""),
    WEREWOLF2("WEREWOLF", "WEREWOLF", ""),
    TROUBLEMAKER("TROUBLEMAKER", "VILLAGER", ""),
    ROBBER("ROBBER", "VILLAGER", ""),
    INSOMNIAC("INSOMNIAC", "VILLAGER", ""),
    DRUNK("DRUNK", "VILLAGER", ""),
    SEER("SEER", "VILLAGER", ""),
    TANNER("TANNER", "TANNER", ""),
    HUNTER("HUNTER", "VILLAGER", ""),
    MINION("MINION", "WEREWOLF", ""),
    VILLAGER1("VILLAGER", "VILLAGER", ""),
    VILLAGER2("VILLAGER", "VILLAGER", ""),
    VILLAGER3("VILLAGER", "VILLAGER", ""),
    MASON1("MASON", "VILLAGER", ""),
    MASON2("MASON", "VILLAGER", "");

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
