package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Eagle extends LiveForm implements Movable, Mortal, Eating, Reproducing {
    public Eagle(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
