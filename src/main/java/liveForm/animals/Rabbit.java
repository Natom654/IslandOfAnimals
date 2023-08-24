package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Rabbit extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Rabbit(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}