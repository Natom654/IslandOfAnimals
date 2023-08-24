package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Horse extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Horse(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
