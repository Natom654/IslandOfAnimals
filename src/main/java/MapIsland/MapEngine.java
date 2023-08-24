package MapIsland;

import liveForm.engine.LiveForm;
import liveForm.engine.LiveFormFactory;
import lombok.Getter;
import lombok.Setter;
import settings.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
@Getter
@Setter
public class MapEngine {
    private final Logger logger;
    private final GlobalSettings globalSettings;
    private final AtomicLong indexNumberOfCreatures = new AtomicLong(0);
    private final List<FamilyIdSettings> familyIdSettingsList = new ArrayList<>();
    private final Map<Long, LiveForm> creatureList = new HashMap<>();
    private final List<LiveForm> creatureWaitList = Collections.synchronizedList(new ArrayList<>());

    private final Map<FamilyIdSettings, AtomicInteger> worldNumbersOfAnimals = new HashMap<>();
    private final Map<FamilyIdSettings, Integer> originalNumbersOfAnimals = new HashMap<>();
    private final Map<Integer, Tile> mapList = new HashMap<>();
    private final AtomicInteger turn = new AtomicInteger(0);

    public MapEngine(Logger logger, GlobalSettings globalSettings) {
        this.logger = logger;
        this.globalSettings = globalSettings;
    }

    public void startGenerating(int lowerNumberOfAnimals) {
        logger.logMessage("Creating a map...", false);
        createMap();
        logger.logMessage("Calculating pathways...", false);
        generatePathwaysForMap();
        logger.logMessage("Loading liveForms...", false);
        loadFamilyIdSettings();
        logger.logMessage("Fill tiles with liveForms...", false);
        fillAllTilesWithCreature(lowerNumberOfAnimals);
        copyNumberOfAnimalsToOriginal();

        logger.logMessage("Island generation finished.\n  - Map tiles: "
                + Statistics.DECIMAL_FORMAT.format(mapList.size())
                + "\n  - Types of liveForms: " + familyIdSettingsList.size()
                + "\n  - Total liveForms: " + Statistics.DECIMAL_FORMAT.format(indexNumberOfCreatures), false);
    }

    private void loadEmptyFamilyIdSettingsToWorld() {
        familyIdSettingsList.forEach(familyIdSettings
                -> worldNumbersOfAnimals.put(familyIdSettings, new AtomicInteger()));
    }

    private void loadEmptyFamilyIdSettingsToTiles() {
        mapList.values().forEach(tile -> tile.loadEmptyFamilyIdSettings(familyIdSettingsList));
    }

    private MapLegend getMapLegendFromSymbol(String symbol) {
        if (!globalSettings.getMapLegendRevers().containsKey(symbol)) {
            logger.logMessage("Map type '" + symbol + "' not found.", true);
            return MapLegend.DEFAULT;
        }
        return globalSettings.getMapLegendRevers().get(symbol);
    }

    private String getMapSymbolFromLegend(MapLegend mapLegend) {
        if (!globalSettings.getMapLegend().containsKey(mapLegend)) {
            logger.logMessage("Map type '" + mapLegend + "' symbol not found.", true);
            return globalSettings.getMapLegend().get(MapLegend.DEFAULT);
        }
        return globalSettings.getMapLegend().get(mapLegend);
    }

    private void createMap() {
        globalSettings.createMapLegendRevers();
        int index = 0;
        index = generateMultiMapTitle(index, globalSettings.getMapSizeWidth() + 2, MapLegend.DEFAULT);
        for (Integer rowNumber : globalSettings.getMap().keySet()) {
            index = generateMultiMapTitle(index, 1, MapLegend.DEFAULT);
            String rowMap = globalSettings.getMap().get(rowNumber);
            for (char oneTileMap : rowMap.toCharArray()) {
                MapLegend mapLegend = getMapLegendFromSymbol(String.valueOf(oneTileMap));
                index = createSaveTitleMap(index, mapLegend);
            }
            index = generateMultiMapTitle(index, 1, MapLegend.DEFAULT);
        }
        index = generateMultiMapTitle(index, globalSettings.getMapSizeWidth() + 2, MapLegend.DEFAULT);
        if (globalSettings.getMapSizeWidth() * globalSettings.getMapSizeHeight() > index) {
            logger.logMessage("Map not found for index from [" + index + "] to ["
                    + globalSettings.getMapSizeWidth() * globalSettings.getMapSizeHeight() + "].", true);
            for (int i = index; i < globalSettings.getMapSizeWidth() * globalSettings.getMapSizeHeight(); i++) {
                index = createSaveTitleMap(index, MapLegend.DEFAULT);
            }
        }
    }

    private void generatePathwaysForMap() {
        mapList.values().stream()
                .parallel()
                .forEach(this::createPathwaysForTile);
    }

    private void createPathwaysForTile(Tile tile) {
        List<Tile> movementTile = tile.getMovementTile();
        int index = tile.getIndex();
        int oneRow = globalSettings.getMapSizeWidth() + 2;
        createMovementTileList(index - 1, movementTile);
        createMovementTileList(index + 1, movementTile);
        createMovementTileList(index - oneRow, movementTile);
        createMovementTileList(index - oneRow - 1, movementTile);
        createMovementTileList(index - oneRow + 1, movementTile);
        createMovementTileList(index + oneRow, movementTile);
        createMovementTileList(index + oneRow - 1, movementTile);
        createMovementTileList(index + oneRow + 1, movementTile);
    }

    private void createMovementTileList(int index, List<Tile> movementTile) {
        if (mapList.containsKey(index) && mapList.get(index).isMoving()) {
            movementTile.add(mapList.get(index));
        }
    }
    private int generateMultiMapTitle(int index, int numbers, MapLegend mapLegend) {
        for (int i = 0; i < numbers; i++) {
            index = createSaveTitleMap(index, mapLegend);
        }
        return index;
    }

    private int createSaveTitleMap(int index, MapLegend mapLegend) {
        index++;
        String symbol = getMapSymbolFromLegend(mapLegend);
        ColourCode colorCode = globalSettings.getMapColor().get(mapLegend);
        Tile tile = new Tile(index, mapLegend, symbol, colorCode);
        mapList.put(index, tile);
        return index;
    }

    private void loadFamilyIdSettings() {
        globalSettings.loadFamilyIdSettingsList(familyIdSettingsList, this);
        loadEmptyFamilyIdSettingsToWorld();
        loadEmptyFamilyIdSettingsToTiles();
    }

    private void fillAllTilesWithCreature(int lowerNumberOfAnimals) {
        mapList.values().stream()
                .parallel()
                .forEach(tile -> createFamilyCreature(tile, lowerNumberOfAnimals));
    }

    private void createFamilyCreature(Tile tile, int lowerNumberOfAnimals) {
        if (!tile.isReproducing()) {
            return;
        }
        for (FamilyIdSettings familyIdSettings : familyIdSettingsList) {
            int max = familyIdSettings.getMaxInTitle() * lowerNumberOfAnimals;
            if (max > 0) {
                int min = 5 * max / 100;
                int maxNumberInTitle = Randomizer.randomIntFromToNotInclude(min, max);
                for (int i = 0; i < maxNumberInTitle; i++) {
                    createLiveForm(familyIdSettings, tile);
                }
            }
        }
    }

    public void createLiveForm(FamilyIdSettings familyId, Tile tile) {
        long id = generateObjectId();
        LiveForm liveForm = newObjectCreature(familyId, id);
        if (liveForm == null) {
            generateObjectIdDecrement();
            logger.logMessage("Can't create liveForm familyId [" + familyId + "].", true);
        } else {
            liveForm.setTile(tile);
            addCreatureToWaitList(liveForm);
        }
    }

    private void addAndLiveNewCreateFromWaitList() {
        creatureWaitList.forEach(this::addAndLiveCreatureToWordList);
        creatureWaitList.clear();
    }

    private void addCreatureToWaitList(LiveForm liveForm) {
        creatureWaitList.add(liveForm);
    }

    private void addAndLiveCreatureToWordList(LiveForm liveForm) {
        creatureList.put(liveForm.getId(), liveForm);
        liveForm.live();
    }

    private LiveForm newObjectCreature(FamilyIdSettings familyId, long id) {
        return LiveFormFactory.createLiveForm(id, familyId.getName(), familyId.getMaxWeight(), familyId);
    }

    private long generateObjectId() {
        return indexNumberOfCreatures.incrementAndGet();
    }

    private void generateObjectIdDecrement() {
        indexNumberOfCreatures.decrementAndGet();
    }

    public void incrementWorldAnimalNumbers(FamilyIdSettings familyId) {
        worldNumbersOfAnimals.get(familyId).incrementAndGet();
    }

    public void decrementWorldAnimalNumbers(FamilyIdSettings familyId) {
        worldNumbersOfAnimals.get(familyId).decrementAndGet();
    }

    private void copyNumberOfAnimalsToOriginal() {
        addAndLiveNewCreateFromWaitList();
        worldNumbersOfAnimals.forEach((key, value) -> originalNumbersOfAnimals.put(key, value.get()));
    }

    public void act() {
        prepWorldListForTurn();
        turn.incrementAndGet();
        creatureList.values().stream()
                .parallel()
                .filter(LiveForm::isLive)
                .forEach(LiveForm::act);
    }

    private void prepWorldListForTurn() {
        deleteDeadAnimals();
        addAndLiveNewCreateFromWaitList();
        clearAnimalsTileList();
        addAnimalsToTileList();
        shuffleAndCountAnimalsTileList();
    }

    private void deleteDeadAnimals() {
        creatureList.entrySet().removeIf(map -> !map.getValue().isLive());
    }

    private void clearAnimalsTileList() {
        mapList.values().stream()
                .parallel()
                .forEach(Tile::clearAnimalsList);
    }

    private void addAnimalsToTileList() {
        for (LiveForm liveForm : creatureList.values()) {
            Tile tile = liveForm.getTile();
            int familyIdNumber = liveForm.getFamilyIdSettings().getFamilyIdNumber();
            tile.addAnimalToList(familyIdNumber, liveForm);
        }
    }

    private void shuffleAndCountAnimalsTileList() {
        mapList.values().stream()
                .parallel()
                .forEach(Tile::shuffleAndCountAnimalsList);
    }

    public int getTotalLiveCreatures() {
        return creatureList.size();
    }
}

