package borman.onenight.services;

import borman.onenight.models.GameData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DataService {

    public String readJSONFileAsString() {
        try {
            File initialFile = new File("src/main/resources/data.json");
            InputStream inputStream = new FileInputStream(initialFile);
            ObjectMapper mapper = new ObjectMapper();
            GameData data = mapper.readValue(inputStream, GameData.class);
            return data.getTest();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public void writeDataToFile() {

        GameData gameData = new GameData();
        gameData.setTest("second");

        try {
            File initialFile = new File("src/main/resources/data.json");
            InputStream inputStream = new FileInputStream(initialFile);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(initialFile, gameData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
