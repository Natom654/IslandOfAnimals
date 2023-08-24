package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Buffalo extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Buffalo(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
