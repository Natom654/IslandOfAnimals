package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Mouse extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Mouse(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
