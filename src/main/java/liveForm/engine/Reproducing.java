package liveForm.engine;

import MapIsland.Tile;
import settings.FamilyIdSettings;
import settings.GlobalSettings;
import settings.Randomizer;

public interface Reproducing {
    default void startReproduction(LiveForm liveForm) {
        FamilyIdSettings familyIdSettings = liveForm.getFamilyIdSettings();
        Tile tile = liveForm.getTile();
        GlobalSettings globalSettings = liveForm.getFamilyIdSettings().getMapEngine().getGlobalSettings();
        if (checkHungryStatus(liveForm, familyIdSettings)
                && isSameFamilyInTile(familyIdSettings, tile)
                && isNewTileAvailableWithCapacityReproduction(familyIdSettings, tile)
                && checkReproductionChange(globalSettings)) {
            reproduction(liveForm, familyIdSettings, tile);
        }
    }

    default boolean checkHungryStatus(LiveForm liveForm, FamilyIdSettings familyIdSettings) {
        return (liveForm.getCurrentWeight() * 100 / familyIdSettings.getMaxWeight()) > 50;
    }

    default boolean checkReproductionChange(GlobalSettings globalSettings) {
        return Randomizer.randomIntFromToNotInclude(0, 100) < globalSettings.getReproductionChance();
    }

    default boolean isNewTileAvailableWithCapacityReproduction(FamilyIdSettings familyIdSettings, Tile tile) {
        return tile.getNumbersOfAnimals().get(familyIdSettings).get() < familyIdSettings.getMaxInTitle();
    }

    default boolean isSameFamilyInTile(FamilyIdSettings familyIdSettings, Tile tile) {
        return tile.getNumbersOfAnimals().get(familyIdSettings).get() > 1;
    }

    default void reproduction(LiveForm liveForm, FamilyIdSettings familyIdSettings, Tile tile) {
        familyIdSettings.getMapEngine().createLiveForm(familyIdSettings, tile);
        changeWeightAfterReproduction(liveForm);
    }

    default void changeWeightAfterReproduction(LiveForm liveForm) {
        liveForm.setCurrentWeight(liveForm.getCurrentWeight() / 2);
    }
}