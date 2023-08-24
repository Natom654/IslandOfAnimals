package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Sheep extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Sheep(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
