package liveForm.engine;

import MapIsland.Tile;
import exceptions.ActionException;
import lombok.Getter;
import lombok.Setter;
import settings.FamilyIdSettings;
import settings.Randomizer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class LiveForm {
    protected final long id;
    protected final String name;
    protected final FamilyIdSettings familyIdSettings;
    protected final List<Actions> actionsTurnList = new ArrayList<>();
    protected final List<Actions> actionsAlwaysList = new ArrayList<>();
    protected double currentWeight;
    protected boolean isLive = false;
    protected Tile tile;

    protected LiveForm(long id, String name, FamilyIdSettings familyIdSettings, double currentWeight) {
        this.id = id;
        this.familyIdSettings = familyIdSettings;
        this.currentWeight = currentWeight;
        this.name = name + "-" + id;
        init();
    }

    private void init() {
        if (this instanceof Mortal) {
            actionsAlwaysList.add(Actions.MORTAL);
        }
        if (this instanceof Eating) {
            actionsTurnList.add(Actions.EATING);
        }
        if (this instanceof Movable) {
            actionsTurnList.add(Actions.MOVABLE);
        }
        if (this instanceof Reproducing) {
            actionsTurnList.add(Actions.REPRODUCING);
        }
    }

    public boolean isLive() {
        return isLive;
    }

    public void act() {
        if (!isLive) {
            return;
        }

        if (!actionsAlwaysList.isEmpty()) {
            for (Actions actions : actionsAlwaysList) {
                if (isLive()) {
                    if (actions == Actions.MORTAL) {
                        ((Mortal) this).startDie(this);
                    } else {
                        throw new ActionException("No always action for " + this.name);
                    }
                }
            }
        }

        if (!actionsTurnList.isEmpty() && isLive) {
            switch (actionsTurnList.get(Randomizer.randomIntFromToNotInclude(0, actionsTurnList.size()))) {
                case EATING -> ((Eating) this).startEat(this);
                case MOVABLE -> ((Movable) this).startMove(this);
                case REPRODUCING -> ((Reproducing) this).startReproduction(this);
                default -> throw new ActionException("No turn action for " + this.name);
            }
        }
    }

    public void die() {
        isLive = false;
        familyIdSettings.getMapEngine().decrementWorldAnimalNumbers(familyIdSettings);
        tile.decrementAnimalNumbers(familyIdSettings);
    }

    public void live() {
        familyIdSettings.getMapEngine().incrementWorldAnimalNumbers(familyIdSettings);
        tile.incrementAnimalNumbers(familyIdSettings);
        isLive = true;
    }

}

