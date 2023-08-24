package liveForm.animals;

import liveForm.engine.Eating;
import liveForm.engine.LiveForm;
import liveForm.engine.Mortal;
import liveForm.engine.Reproducing;
import settings.FamilyIdSettings;

public class Caterpillar extends LiveForm implements Mortal, Reproducing, Eating {
    public Caterpillar(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}