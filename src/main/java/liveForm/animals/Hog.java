package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Hog extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Hog(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
