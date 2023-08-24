import MapIsland.MapEngine;
import MapIsland.Statistics;
import settings.Colour;
import settings.GlobalSettings;
import settings.Logger;


import java.io.IOException;

public class Application {

    public static final String SETTINGS_FILE = "GlobalSettings.yaml";
    public static final String ISLAND_LOG_FILE = "Island.log";
    public static final String DATA_FORMAT = "HH:mm:ss";
    public static final int LOWER_NUMBER_OF_ANIMALS = 1;
    public static final int PAUSE_MILLIS = 1000;

    public static void main(String[] args) throws IOException {

        Logger logger = new Logger(ISLAND_LOG_FILE, DATA_FORMAT);
        Colour color = new Colour();

        GlobalSettings globalSettings = new GlobalSettings();

        globalSettings.defaultSettingFile(SETTINGS_FILE);

        globalSettings = globalSettings.loadSettingFile(SETTINGS_FILE);

        MapEngine mapEngine = new MapEngine(logger, globalSettings);
        mapEngine.startGenerating(LOWER_NUMBER_OF_ANIMALS);

        Statistics statistics = new Statistics(mapEngine, globalSettings, color, logger);
        statistics.initialization();


        while (true) {
            statistics.display();
            mapEngine.act();
            if (mapEngine.getTotalLiveCreatures() == 0) {
                break;
            }
            try {
                Thread.sleep(PAUSE_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}