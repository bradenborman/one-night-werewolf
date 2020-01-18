package borman.onenight.config;

import borman.onenight.models.GameData;
import borman.onenight.services.DataService;
import org.springframework.stereotype.Component;

@Component
public class StartupDataClearOut {

    DataService dataService;

    public StartupDataClearOut(DataService dataService) {
        this.dataService = dataService;
        dataService.writeDataToFile(new GameData());
    }
}
