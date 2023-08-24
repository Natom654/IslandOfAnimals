package liveForm.animals;

import liveForm.engine.LiveForm;
import liveForm.engine.Reproducing;
import settings.FamilyIdSettings;

public class Plants extends LiveForm implements Reproducing {
    public Plants(long id, String name, FamilyIdSettings familyId, double currentWeight) {
        super(id, name, familyId, currentWeight);
    }
}
