package borman.onenight.models;

public class DrunkActionRequest {

    private String userId;
    private String lobbyId;
    private String cardSelectedPosition;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getCardSelectedPosition() {
        return cardSelectedPosition;
    }

    public void setCardSelectedPosition(String cardSelectedPosition) {
        this.cardSelectedPosition = cardSelectedPosition;
    }
}