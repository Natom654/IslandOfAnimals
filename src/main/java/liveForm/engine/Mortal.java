package liveForm.engine;

import settings.FamilyIdSettings;

public interface Mortal {
    default void startDie(LiveForm liveForm) {
        if (isHaveWeight(liveForm)) {
            FamilyIdSettings familyIdSettings = liveForm.getFamilyIdSettings();
            lowerWeight(liveForm, familyIdSettings);
        }
    }

    default void lowerWeight(LiveForm liveForm, FamilyIdSettings familyIdSettings) {
        double weight = liveForm.getCurrentWeight() - familyIdSettings.getWeightLoss();
        liveForm.setCurrentWeight(weight);
        if (weight <= 0) {
            liveForm.die();
        }
    }

    default boolean isHaveWeight(LiveForm liveForm) {
        return liveForm.getCurrentWeight() > 0;
    }
}

