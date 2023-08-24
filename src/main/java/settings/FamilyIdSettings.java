package settings;

import MapIsland.MapEngine;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FamilyIdSettings {
    private final MapEngine mapEngine;
    private final String name;
    private final String legend;
    private final String symbol;
    private final int familyIdNumber;
    private final double maxWeight;
    private final double weightLoss;
    private final int maxInTitle;
    private final int speed;
    private final FoodChain foodChain;
    private final boolean isShowOnMap;

    private final Map<FamilyIdSettings, Integer> eatProbabilityList = new HashMap<>();

    public FamilyIdSettings(int familyIdNumber, MapEngine mapEngine, String name, String legend, String symbol,
                            double maxWeight, int maxInTitle, int speed, FoodChain foodChain, boolean isShowOnMap,
                            double weightLoss) {
        this.familyIdNumber = familyIdNumber;
        this.mapEngine = mapEngine;
        this.name = name;
        this.legend = legend;
        this.symbol = symbol;
        this.maxWeight = maxWeight;
        this.maxInTitle = maxInTitle;
        this.speed = speed;
        this.foodChain = foodChain;
        this.isShowOnMap = isShowOnMap;
        this.weightLoss = weightLoss;
    }

    //
    public FoodChain getModeOfNutrition() {
        return foodChain;
    }

    public void addElementToEatProbabilityList(FamilyIdSettings familyIdSettings, Integer probability) {
        eatProbabilityList.put(familyIdSettings, probability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyIdSettings that = (FamilyIdSettings) o;

        return familyIdNumber == that.familyIdNumber;
    }

    @Override
    public int hashCode() {
        return familyIdNumber;
    }
}

