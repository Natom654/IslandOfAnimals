package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Bear extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Bear(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
