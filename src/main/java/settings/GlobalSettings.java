package settings;

import MapIsland.MapEngine;
import MapIsland.MapLegend;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import exceptions.GlobalSettingsException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class GlobalSettings {
    public static final String NAME = "name";
    public static final String LEGEND = "legend";
    public static final String SYMBOL = "symbol";
    public static final String MAX_WEIGHT = "maxWeight";
    public static final String WEIGHT_LOSS = "weightLoss";
    public static final String MAX_IN_TITLE = "maxInTitle";
    public static final String SPEED = "speed";
    public static final String WEIGHT_FOR_FULL = "weightForFull";
    public static final String MODE_OF_NUTRITION = "modeOfNutrition";
    public static final String SHOW_ON_MAP = "showOnMap";
    public static final String EAT_PROBABILITY_FAMILY_ID_2 = "EatProbability_FamilyId_2";
    public static final String EAT_PROBABILITY_FAMILY_ID_3 = "EatProbability_FamilyId_3";
    public static final String EAT_PROBABILITY_FAMILY_ID_6 = "EatProbability_FamilyId_6";
    public static final String EAT_PROBABILITY_FAMILY_ID_7 = "EatProbability_FamilyId_7";
    public static final String EAT_PROBABILITY_FAMILY_ID_8 = "EatProbability_FamilyId_8";
    public static final String EAT_PROBABILITY_FAMILY_ID_9 = "EatProbability_FamilyId_9";
    public static final String EAT_PROBABILITY_FAMILY_ID_10 = "EatProbability_FamilyId_10";
    public static final String EAT_PROBABILITY_FAMILY_ID_11 = "EatProbability_FamilyId_11";
    public static final String EAT_PROBABILITY_FAMILY_ID_12 = "EatProbability_FamilyId_12";
    public static final String EAT_PROBABILITY_FAMILY_ID_13 = "EatProbability_FamilyId_13";
    public static final String EAT_PROBABILITY_FAMILY_ID_14 = "EatProbability_FamilyId_14";
    public static final String EAT_PROBABILITY_FAMILY_ID_15 = "EatProbability_FamilyId_15";
    public static final String EAT_PROBABILITY_FAMILY_ID_16 = "EatProbability_FamilyId_16";
    public static final String CARNIVORES = "CARNIVORES";
    public static final String HERBIVORES = "HERBIVORES";
    public static final String OMNIVOROUS = "OMNIVOROUS";
    public static final String TRUE = "true";
    private int mapSizeWidth;
    private int mapSizeHeight;
    private final Map<Integer, HashMap<String, String>> settings = new HashMap<>();
    private final Map<Integer, String> map = new TreeMap<>();
    private final EnumMap<MapLegend, ColourCode> mapColor = new EnumMap<>(MapLegend.class);
    private final EnumMap<MapLegend, String> mapLegend = new EnumMap<>(MapLegend.class);
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final EnumMap<MapLegend, String> mapPicture = new EnumMap<>(MapLegend.class);
    private final EnumMap<LineCode, String> statisticLine = new EnumMap<>(LineCode.class);
    private final Map<String, MapLegend> mapLegendRevers = new HashMap<>();
    private ColourCode mapColorBackGround;
    private int statisticSizeOfNames;
    private int statisticSizeFrameAfterNames;
    private String statisticNameSymbol;
    private ColourCode statisticNameColorTurn;
    private ColourCode statisticNameColorLine;
    private ColourCode statisticNameColorPredator;
    private ColourCode statisticNameColorHerbivore;
    private ColourCode statisticNameColorOmnivorous;
    private ColourCode statisticNameColorUnknown;
    private ColourCode statisticNumbersColor;
    private ColourCode statisticNumbersPositiveColor;
    private ColourCode statisticNumbersNegativeColor;
    private int reproductionChance;

    public void defaultSettingFile(String fileName) throws IOException {
        createDefaultSettingFile();
        saveDefaultSettingFile(fileName);
    }

    private void saveDefaultSettingFile(String fileName) throws IOException {
        ObjectMapper mapper = new YAMLMapper();
        mapper.writeValue(new File(fileName), this);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private void createDefaultSettingFile() {
        HashMap<String, String> liveForm;

        mapSizeWidth = 100;
        mapSizeHeight = 20;
        reproductionChance = 30;

        mapLegend.put(MapLegend.DEFAULT, "@");
        mapLegend.put(MapLegend.MOUNTAIN, "@");
        mapLegend.put(MapLegend.RIVER, "~");
        mapLegend.put(MapLegend.FIELD, ".");

        mapColorBackGround = ColourCode.BACK_WHITE;
        mapColor.put(MapLegend.DEFAULT, ColourCode.FONT_GRAY);
        mapColor.put(MapLegend.MOUNTAIN, ColourCode.FONT_GRAY);
        mapColor.put(MapLegend.RIVER, ColourCode.FONT_BLUE);
        mapColor.put(MapLegend.FIELD, ColourCode.FONT_GREEN);

        mapPicture.put(MapLegend.DEFAULT, "\u26F0");
        mapPicture.put(MapLegend.MOUNTAIN, "\u26F0");
        mapPicture.put(MapLegend.RIVER, "\uD83C\uDF0A");
        mapPicture.put(MapLegend.FIELD, "\uD83C\uDF43");

        statisticLine.put(LineCode.LINE_UP_RIGHT, "\u255A");
        statisticLine.put(LineCode.LINE_UP_LEFT, "\u255D");
        statisticLine.put(LineCode.LINE_DOWN_RIGHT, "\u2554");
        statisticLine.put(LineCode.LINE_DOWN_LEFT, "\u2557");
        statisticLine.put(LineCode.LINE_HORIZONTAL, "\u2550");
        statisticLine.put(LineCode.LINE_VERTICAL, "\u2551");
        statisticLine.put(LineCode.LINE_VERTICAL_LEFT, "\u2563");
        statisticLine.put(LineCode.LINE_VERTICAL_RIGHT, "\u2560");

        statisticNameColorLine = ColourCode.FONT_DARK_BROWN;
        statisticNameColorPredator = ColourCode.FONT_DARK_RED;
        statisticNameColorHerbivore = ColourCode.FONT_DARK_GREEN;
        statisticNameColorOmnivorous = ColourCode.FONT_VINOUS;
        statisticNameColorUnknown = ColourCode.FONT_GRAY;
        statisticNameColorTurn = ColourCode.FONT_DARK_BLUE;

        statisticNumbersColor = ColourCode.FONT_AMBER;
        statisticNumbersPositiveColor = ColourCode.FONT_DARK_GREEN;
        statisticNumbersNegativeColor = ColourCode.FONT_DARK_RED;

        statisticSizeOfNames = 12;
        statisticSizeFrameAfterNames = 20;
        statisticNameSymbol = "*";

        map.put(1, "...................................................~~...............................................");
        map.put(2, "..................................................~~................................................");
        map.put(3, "......@.@......................................~~~..................................................");
        map.put(4, "......@@@~~~..................................~~...........................@.@......................");
        map.put(5, "........@..~~~..............................~~~~~~..........................@@@.......~~............");
        map.put(6, ".............~~...........................@@~...~~~.................................~~~~............");
        map.put(7, "..............~~.........~~~~~~@@.......@@~.......~~.......@........................~~~......~~@....");
        map.put(8, "...............~~~~.....~~~...~~@@@@..@@@~.........~~........................................~@.....");
        map.put(9, "..................~~....~~......~~@@@@@@~...........~~..............................................");
        map.put(10, "..................~~~~~~..........~~~~~~.............~~.............................................");
        map.put(11, "..................~~..................................~~............................................");
        map.put(12, ".................~~....................................~~.........................@@@@..............");
        map.put(13, "................~~......................................~~~~~~...................@@@@@@.............");
        map.put(14, "...@@..........~~............................................~~.................~@@@@@@.............");
        map.put(15, "...............~~........@....................................~~.............~~~~@@@@...............");
        map.put(16, "..............~~.........@@@................@@.................~~...........~~......................");
        map.put(17, ".............~~...........@@@..............@@@..................~~........~~........................");
        map.put(18, "............~~............@@@....................................~~....~~~..........................");
        map.put(19, "............~~....................................................~~~~~.............................");
        map.put(20, "............~~......................................................................................");

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Wolf");
        liveForm.put(LEGEND, "*olf");
        liveForm.put(SYMBOL, "W");
        liveForm.put(MAX_WEIGHT, "50");
        liveForm.put(WEIGHT_LOSS, "5");
        liveForm.put(MAX_IN_TITLE, "30");
        liveForm.put(SPEED, "3");
        liveForm.put(WEIGHT_FOR_FULL, "8");
        liveForm.put(MODE_OF_NUTRITION, CARNIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_6, "10");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_7, "15");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_8, "60");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "80");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_10, "60");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_11, "70");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_12, "15");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_13, "10");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_14, "40");

        settings.put(1, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Anaconda");
        liveForm.put(LEGEND, "*naconda");
        liveForm.put(SYMBOL, "A");
        liveForm.put(MAX_WEIGHT, "15");
        liveForm.put(WEIGHT_LOSS, "1.5");
        liveForm.put(MAX_IN_TITLE, "30");
        liveForm.put(SPEED, "1");
        liveForm.put(WEIGHT_FOR_FULL, "3");
        liveForm.put(MODE_OF_NUTRITION, CARNIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_3, "15");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_8, "20");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "40");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_14, "10");

        settings.put(2, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Fox");
        liveForm.put(LEGEND, "*ox");
        liveForm.put(SYMBOL, "F");
        liveForm.put(MAX_WEIGHT, "8");
        liveForm.put(WEIGHT_LOSS, "0.8");
        liveForm.put(MAX_IN_TITLE, "30");
        liveForm.put(SPEED, "2");
        liveForm.put(WEIGHT_FOR_FULL, "2");
        liveForm.put(MODE_OF_NUTRITION, CARNIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_8, "70");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_14, "60");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_15, "40");

        settings.put(3, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Bear");
        liveForm.put(LEGEND, "*ear");
        liveForm.put(SYMBOL, "B");
        liveForm.put(MAX_WEIGHT, "500");
        liveForm.put(WEIGHT_LOSS, "50");
        liveForm.put(MAX_IN_TITLE, "5");
        liveForm.put(SPEED, "2");
        liveForm.put(WEIGHT_FOR_FULL, "80");
        liveForm.put(MODE_OF_NUTRITION, CARNIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_2, "80");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_6, "40");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_7, "80");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_8, "80");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_10, "70");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_11, "70");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_12, "50");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_13, "20");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_14, "10");

        settings.put(4, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Eagle");
        liveForm.put(LEGEND, "*agle");
        liveForm.put(SYMBOL, "E");
        liveForm.put(MAX_WEIGHT, "6");
        liveForm.put(WEIGHT_LOSS, "0.6");
        liveForm.put(MAX_IN_TITLE, "20");
        liveForm.put(SPEED, "3");
        liveForm.put(WEIGHT_FOR_FULL, "1");
        liveForm.put(MODE_OF_NUTRITION, CARNIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_3, "10");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_8, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_14, "80");

        settings.put(5, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Horse");
        liveForm.put(LEGEND, "*orse");
        liveForm.put(SYMBOL, "H");
        liveForm.put(MAX_WEIGHT, "400");
        liveForm.put(WEIGHT_LOSS, "40");
        liveForm.put(MAX_IN_TITLE, "20");
        liveForm.put(SPEED, "4");
        liveForm.put(WEIGHT_FOR_FULL, "60");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");

        settings.put(6, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Deer");
        liveForm.put(LEGEND, "*eer");
        liveForm.put(SYMBOL, "D");
        liveForm.put(MAX_WEIGHT, "300");
        liveForm.put(WEIGHT_LOSS, "30");
        liveForm.put(MAX_IN_TITLE, "20");
        liveForm.put(SPEED, "4");
        liveForm.put(WEIGHT_FOR_FULL, "50");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");
        // FamilyId
        settings.put(7, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Rabbit");
        liveForm.put(LEGEND, "*abbit");
        liveForm.put(SYMBOL, "R");
        liveForm.put(MAX_WEIGHT, "2");
        liveForm.put(WEIGHT_LOSS, "0.2");
        liveForm.put(MAX_IN_TITLE, "150");
        liveForm.put(SPEED, "2");
        liveForm.put(WEIGHT_FOR_FULL, "0.45");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");

        settings.put(8, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Mouse");
        liveForm.put(LEGEND, "*ouse");
        liveForm.put(SYMBOL, "M");
        liveForm.put(MAX_WEIGHT, "0.05");
        liveForm.put(WEIGHT_LOSS, "0.005");
        liveForm.put(MAX_IN_TITLE, "500");
        liveForm.put(SPEED, "1");
        liveForm.put(WEIGHT_FOR_FULL, "0.01");
        liveForm.put(MODE_OF_NUTRITION, OMNIVOROUS);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_15, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");

        settings.put(9, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Goat");
        liveForm.put(LEGEND, "*oat");
        liveForm.put(SYMBOL, "G");
        liveForm.put(MAX_WEIGHT, "60");
        liveForm.put(WEIGHT_LOSS, "6");
        liveForm.put(MAX_IN_TITLE, "140");
        liveForm.put(SPEED, "3");
        liveForm.put(WEIGHT_FOR_FULL, "10");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");

        settings.put(10, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Sheep");
        liveForm.put(LEGEND, "*heep");
        liveForm.put(SYMBOL, "S");
        liveForm.put(MAX_WEIGHT, "70");
        liveForm.put(WEIGHT_LOSS, "7");
        liveForm.put(MAX_IN_TITLE, "140");
        liveForm.put(SPEED, "3");
        liveForm.put(WEIGHT_FOR_FULL, "15");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");

        settings.put(11, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Hog");
        liveForm.put(LEGEND, "h*g");
        liveForm.put(SYMBOL, "O");
        liveForm.put(MAX_WEIGHT, "400");
        liveForm.put(WEIGHT_LOSS, "40");
        liveForm.put(MAX_IN_TITLE, "50");
        liveForm.put(SPEED, "2");
        liveForm.put(WEIGHT_FOR_FULL, "50");
        liveForm.put(MODE_OF_NUTRITION, OMNIVOROUS);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_9, "50");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_15, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");
        // FamilyId
        settings.put(12, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Buffalo");
        liveForm.put(LEGEND, "b*ffalo");
        liveForm.put(SYMBOL, "U");
        liveForm.put(MAX_WEIGHT, "700");
        liveForm.put(WEIGHT_LOSS, "70");
        liveForm.put(MAX_IN_TITLE, "10");
        liveForm.put(SPEED, "4");
        liveForm.put(WEIGHT_FOR_FULL, "0.15");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");
        // FamilyId
        settings.put(13, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Duck");
        liveForm.put(LEGEND, "duc*");
        liveForm.put(SYMBOL, "K");
        liveForm.put(MAX_WEIGHT, "1");
        liveForm.put(WEIGHT_LOSS, "0.1");
        liveForm.put(MAX_IN_TITLE, "200");
        liveForm.put(SPEED, "4");
        liveForm.put(WEIGHT_FOR_FULL, "0.15");
        liveForm.put(MODE_OF_NUTRITION, OMNIVOROUS);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_15, "90");
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");
        // FamilyId
        settings.put(14, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Caterpillar");
        liveForm.put(LEGEND, "*aterpillar");
        liveForm.put(SYMBOL, "C");
        liveForm.put(MAX_WEIGHT, "0.01");
        liveForm.put(WEIGHT_LOSS, "0.001");
        liveForm.put(MAX_IN_TITLE, "1000");
        liveForm.put(MODE_OF_NUTRITION, HERBIVORES);
        liveForm.put(SHOW_ON_MAP, TRUE);
        liveForm.put(EAT_PROBABILITY_FAMILY_ID_16, "100");
        // FamilyId
        settings.put(15, liveForm);

        liveForm = new HashMap<>();
        liveForm.put(NAME, "Plants");
        liveForm.put(LEGEND, "*lants");
        liveForm.put(SYMBOL, "P");
        liveForm.put(MAX_WEIGHT, "200");
        liveForm.put(MAX_IN_TITLE, "1000");
        // FamilyId
        settings.put(16, liveForm);
    }

    public GlobalSettings loadSettingFile(String fileName) throws IOException {
        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(new File(fileName), GlobalSettings.class);
    }

    public void loadFamilyIdSettingsList(List<FamilyIdSettings> familyIdSettingsList, MapEngine mapEngine) {
        for (Map.Entry<Integer, HashMap<String, String>> entryGlobalSettings : this.settings.entrySet()) {
            int familyIdNumber = entryGlobalSettings.getKey();
            HashMap<String, String> valuesHash = entryGlobalSettings.getValue();

            String name = valuesHash.getOrDefault(NAME, "Unknown");
            String legend = valuesHash.getOrDefault(LEGEND, "u*known");
            String symbol = valuesHash.getOrDefault(SYMBOL, "U");
            double maxWeight = Double.parseDouble(valuesHash.getOrDefault(MAX_WEIGHT, "1"));
            double weightLoss = Double.parseDouble(valuesHash.getOrDefault(WEIGHT_LOSS, "0"));
            int maxInTitle = Integer.parseInt(valuesHash.getOrDefault(MAX_IN_TITLE, "1"));
            int speed = Integer.parseInt(valuesHash.getOrDefault(SPEED, "0"));
            FoodChain foodChain = FoodChain.valueOf(valuesHash.getOrDefault(MODE_OF_NUTRITION, "NONE"));
            boolean isShowOnMap = Boolean.parseBoolean(valuesHash.getOrDefault(SHOW_ON_MAP, "false"));

            FamilyIdSettings currentFamilyIdSettings = new FamilyIdSettings(familyIdNumber, mapEngine, name, legend, symbol,
                    maxWeight, maxInTitle, speed, foodChain, isShowOnMap, weightLoss);
            familyIdSettingsList.add(currentFamilyIdSettings);
        }

        // EatProbability
        for (Map.Entry<Integer, HashMap<String, String>> entryGlobalSettings : this.settings.entrySet()) {
            int familyIdNumber = entryGlobalSettings.getKey();
            HashMap<String, String> valuesHash = entryGlobalSettings.getValue();

            FamilyIdSettings currentFamilyIdSettings = getFamilyIdSettings(mapEngine, familyIdNumber);

            Map<Integer, Integer> numberEatProbabilityList = valuesHash.entrySet().stream()
                    .filter(element -> element.getKey().startsWith("EatProbability_FamilyId"))
                    .collect(Collectors.toMap(key -> Integer.parseInt(key.getKey()
                                    .replace("EatProbability_FamilyId_", "")),
                            value -> Integer.parseInt(value.getValue())));
            for (Map.Entry<Integer, Integer> entry : numberEatProbabilityList.entrySet()) {
                int idNumber = entry.getKey();
                int probability = entry.getValue();
                if (probability <= 0) {
                    throw new GlobalSettingsException("EatProbability value is 0. familyIdNumber: "
                            + familyIdNumber + ", idNumber: " + idNumber + ", probability: " + probability);
                }
                FamilyIdSettings probabilityFamilyIdSettings = getFamilyIdSettings(mapEngine, idNumber);
                currentFamilyIdSettings.addElementToEatProbabilityList(probabilityFamilyIdSettings, probability);
            }
        }
    }

    private FamilyIdSettings getFamilyIdSettings(MapEngine mapEngine, int idNumber) {
        Optional<FamilyIdSettings> optionalFamilyIdSettings = mapEngine.getFamilyIdSettingsList().stream()
                .filter(idSettings -> idSettings.getFamilyIdNumber() == idNumber)
                .findFirst();
        return optionalFamilyIdSettings.orElseThrow();
    }

    public void createMapLegendRevers() {
        for (Map.Entry<MapLegend, String> mapLegendStringEntry : mapLegend.entrySet()) {
            mapLegendRevers.put(mapLegendStringEntry.getValue(), mapLegendStringEntry.getKey());
        }
    }
}


