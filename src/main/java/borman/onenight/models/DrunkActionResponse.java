package borman.onenight.models;

public class DrunkActionResponse {

    private String lobbyId;
    private String playerId;
    private String positionSwapped;
    private String imgSrc_old;
    private String imgSrc_new;

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPositionSwapped() {
        return positionSwapped;
    }

    public void setPositionSwapped(String positionSwapped) {
        this.positionSwapped = positionSwapped;
    }

    public String getImgSrc_old() {
        return imgSrc_old;
    }

    public void setImgSrc_old(String imgSrc_old) {
        this.imgSrc_old = imgSrc_old;
    }

    public String getImgSrc_new() {
        return imgSrc_new;
    }

    public void setImgSrc_new(String imgSrc_new) {
        this.imgSrc_new = imgSrc_new;
    }
}