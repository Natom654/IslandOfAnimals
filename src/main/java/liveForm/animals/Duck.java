package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Duck extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Duck(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}