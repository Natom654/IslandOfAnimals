package liveForm.engine;

import MapIsland.Tile;
import settings.FamilyIdSettings;
import settings.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface Eating {
    default void startEat(LiveForm liveForm) {
        FamilyIdSettings familyIdSettings = liveForm.getFamilyIdSettings();
        if (!familyIdSettings.getEatProbabilityList().isEmpty()) {
            Tile currentTile = liveForm.getTile();
            FamilyIdSettings foodFamilyIdSettings = selectFamilyForFood(familyIdSettings, currentTile);
            if (foodFamilyIdSettings != null && checkEatProbability(familyIdSettings, foodFamilyIdSettings)) {
                LiveForm animalForFood = selectAnimalForFood(currentTile, foodFamilyIdSettings);
                if (animalForFood != null) {
                    eatAnimal(liveForm, familyIdSettings, animalForFood, currentTile);
                }
            }
        }
    }

    default boolean checkEatProbability(FamilyIdSettings familyIdSettings, FamilyIdSettings foodFamilyIdSettings) {
        return (Randomizer.randomIntFromToNotInclude(1, 101)
                <= familyIdSettings.getEatProbabilityList().get(foodFamilyIdSettings));
    }

    default void eatAnimal(LiveForm liveForm, FamilyIdSettings familyIdSettings, LiveForm foodAnimal,
                           Tile currentTile) {
        foodAnimal.die();
        currentTile.decrementIndexListOfAnimals(familyIdSettings.getFamilyIdNumber());
        double familyIdSettingsMaxWeight = familyIdSettings.getMaxWeight();
        double weightFoodForEat = Math.min(foodAnimal.getCurrentWeight(), familyIdSettingsMaxWeight);
        double totalFoodForPredator = Math.min(liveForm.getCurrentWeight() + weightFoodForEat,
                familyIdSettingsMaxWeight);
        liveForm.setCurrentWeight(totalFoodForPredator);
    }

    default LiveForm selectAnimalForFood(Tile currentTile, FamilyIdSettings foodFamilyIdSettings) {
        int familyIdNumber = foodFamilyIdSettings.getFamilyIdNumber();
        int indexForListOfAnimal = currentTile.getIndexListOfAnimals().get(familyIdNumber).get();
        if (indexForListOfAnimal > -1) {
            LiveForm animalForFood = currentTile.getListOfAnimals().get(familyIdNumber).get(indexForListOfAnimal);
            if (!animalForFood.isLive() || animalForFood.getTile().getIndex() != currentTile.getIndex()) {
                currentTile.decrementIndexListOfAnimals(familyIdNumber);
                return selectAnimalForFood(currentTile, foodFamilyIdSettings);
            }
            return animalForFood;
        }
        return null;
    }

    default FamilyIdSettings selectFamilyForFood(FamilyIdSettings familyIdSettings, Tile tile) {
        List<FamilyIdSettings> foodAvailableList = new ArrayList<>();
        for (Map.Entry<FamilyIdSettings, AtomicInteger> tileTypeAnimal : tile.getNumbersOfAnimals().entrySet()) {
            if (familyIdSettings.getEatProbabilityList().containsKey(tileTypeAnimal.getKey())
                    && tileTypeAnimal.getValue().get() > 0) {
                foodAvailableList.add(tileTypeAnimal.getKey());
            }
        }
        if (!foodAvailableList.isEmpty()) {
            return foodAvailableList.get(Randomizer.randomIntFromToNotInclude(0, foodAvailableList.size()));
        }
        return null;
    }
}
