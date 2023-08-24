package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Fox extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Fox(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
