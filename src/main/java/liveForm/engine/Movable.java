package liveForm.engine;

import MapIsland.MapLegend;
import MapIsland.Tile;
import settings.FamilyIdSettings;
import settings.Randomizer;

public interface Movable {
    default void startMove(LiveForm liveForm) {
        Tile currentTile = liveForm.getTile();
        if (isNewTileForMove(currentTile)) {
            FamilyIdSettings familyIdSettings = liveForm.getFamilyIdSettings();
            int totalPoints = getPointForMove(familyIdSettings);
            moveWithPoint(liveForm, familyIdSettings, currentTile, totalPoints);
            if (isTileRiver(currentTile)) {
                liveForm.die();
            }
        }
    }

    default void moveWithPoint(LiveForm liveForm, FamilyIdSettings familyIdSettings, Tile currentTile,
                               int totalPoints) {
        Tile newTile = getTileForMove(currentTile);
        int pointForMove = calcPointForMove(newTile);
        if (totalPoints >= pointForMove) {
            moveToNewTile(liveForm, familyIdSettings, currentTile, newTile);
            moveWithPoint(liveForm, familyIdSettings, newTile, totalPoints - pointForMove);
        }
    }

    default void moveToNewTile(LiveForm liveForm, FamilyIdSettings familyIdSettings, Tile currentTile, Tile newTile) {
        currentTile.getNumbersOfAnimals().get(familyIdSettings).decrementAndGet();
        newTile.getNumbersOfAnimals().get(familyIdSettings).incrementAndGet();
        liveForm.setTile(newTile);
    }

    default boolean isTileRiver(Tile tile) {
        return tile.getMapLegend().equals(MapLegend.RIVER);
    }

    default int getPointForMove(FamilyIdSettings familyIdSettings) {
        return familyIdSettings.getSpeed();
    }


    default int calcPointForMove(Tile tileForMove) {
        return switch (tileForMove.getMapLegend()) {
            case DEFAULT, MOUNTAIN -> 100;
            case RIVER -> 2;
            case FIELD -> 1;
        };
    }

    default boolean isNewTileForMove(Tile currentTile) {
        return !currentTile.getMovementTile().isEmpty();
    }

    default Tile getTileForMove(Tile currentTile) {
        int random = Randomizer.randomIntFromToNotInclude(0, currentTile.getMovementTile().size());
        return currentTile.getMovementTile().get(random);
    }
}
