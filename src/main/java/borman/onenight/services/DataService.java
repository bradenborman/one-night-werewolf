package borman.onenight.services;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

@Service
public class DataService {

    public String readJSONFileAsString() {
        try {
            File file = ResourceUtils.getFile("classpath:data.json");
            return new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            return "";
        }

    }


}
