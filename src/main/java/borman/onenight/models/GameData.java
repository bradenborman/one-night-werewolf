package borman.onenight.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameData {

    @JsonProperty("test")
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
