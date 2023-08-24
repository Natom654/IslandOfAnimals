package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Goat extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Goat(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}