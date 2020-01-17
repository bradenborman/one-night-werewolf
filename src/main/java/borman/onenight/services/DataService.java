package borman.onenight.services;

import borman.onenight.models.GameData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DataService {

    public GameData readJsonFile() {
        try {
            File initialFile = new File("src/main/resources/data.json");
            InputStream inputStream = new FileInputStream(initialFile);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, GameData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void writeDataToFile(GameData gameData) {
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
