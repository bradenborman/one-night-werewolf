package borman.onenight.models;

import java.util.ArrayList;
import java.util.List;

public class RetrieveRollResponse {

    private String roleName;
    private String displayName;
    private String imgSrc;

    private List<String> middleCards = new ArrayList<>();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<String> getMiddleCards() {
        return middleCards;
    }

    public void setMiddleCards(List<String> middleCards) {
        this.middleCards = middleCards;
    }
}