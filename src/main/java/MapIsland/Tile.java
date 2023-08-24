package MapIsland;

import liveForm.engine.LiveForm;
import lombok.Getter;
import lombok.Setter;
import settings.ColourCode;
import settings.FamilyIdSettings;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
@Setter
public class Tile {
    private final int index;
    private final MapLegend mapLegend;
    private final String symbol;
    private final ColourCode colorCode;
    private final List<Tile> movementTile = new ArrayList<>();
    private final Map<FamilyIdSettings, AtomicInteger> numbersOfAnimals = new HashMap<>();
    private final Map<Integer, List<LiveForm>> listOfAnimals = new HashMap<>();
    private final Map<Integer, AtomicInteger> indexListOfAnimals = new HashMap<>();

    public Tile(int index, MapLegend mapLegend, String symbol, ColourCode colorCode) {
        this.index = index;
        this.mapLegend = mapLegend;
        this.symbol = symbol;
        this.colorCode = colorCode;
    }

    public boolean isReproducing() {
        return switch (mapLegend) {
            case DEFAULT, MOUNTAIN, RIVER -> false;
            case FIELD -> true;
        };
    }

    public boolean isMoving() {
        return switch (mapLegend) {
            case DEFAULT, MOUNTAIN -> false;
            case RIVER, FIELD -> true;
        };
    }

    public void incrementAnimalNumbers(FamilyIdSettings familyIdSettings) {
        numbersOfAnimals.get(familyIdSettings).incrementAndGet();
    }

    public void decrementAnimalNumbers(FamilyIdSettings familyIdSettings) {
        numbersOfAnimals.get(familyIdSettings).decrementAndGet();
    }

    public void decrementIndexListOfAnimals(int familyIdNumber) {
        indexListOfAnimals.get(familyIdNumber).decrementAndGet();
    }

    public void loadEmptyFamilyIdSettings(List<FamilyIdSettings> familyIdSettingsList) {
        familyIdSettingsList.forEach(familyIdSettings -> {
            numbersOfAnimals.put(familyIdSettings, new AtomicInteger());
            listOfAnimals.put(familyIdSettings.getFamilyIdNumber(), new ArrayList<>());
            indexListOfAnimals.put(familyIdSettings.getFamilyIdNumber(), new AtomicInteger());
        });

    }

    public void addAnimalToList(int familyIdNumber, LiveForm liveForm) {
        listOfAnimals.get(familyIdNumber).add(liveForm);
    }

    public void clearAnimalsList() {
        for (List<LiveForm> creatureList : listOfAnimals.values()) {
            creatureList.clear();
        }
        for (AtomicInteger indexForList : indexListOfAnimals.values()) {
            indexForList.set(-1);
        }
    }

    public void shuffleAndCountAnimalsList() {
        for (Map.Entry<Integer, List<LiveForm>> familyIdAnimalList : listOfAnimals.entrySet()) {
            if (!familyIdAnimalList.getValue().isEmpty()) {
                Collections.shuffle(familyIdAnimalList.getValue());
                indexListOfAnimals.get(familyIdAnimalList.getKey()).set(familyIdAnimalList.getValue().size() - 1);
            }
        }
    }
}
