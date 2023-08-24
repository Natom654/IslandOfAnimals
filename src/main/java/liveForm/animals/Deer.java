package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Deer extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Deer(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
