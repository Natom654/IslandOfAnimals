package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Anaconda extends LiveForm implements Eating, Mortal, Movable, Reproducing {
    public Anaconda(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}