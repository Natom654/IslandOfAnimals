package liveForm.animals;

import liveForm.engine.*;
import settings.FamilyIdSettings;

public class Wolf extends LiveForm implements Eating, Mortal, Movable, Reproducing {

    public Wolf(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }

}
