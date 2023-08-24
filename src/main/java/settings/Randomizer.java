package settings;

import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
@NoArgsConstructor
public class Randomizer {


    public static int randomIntFromToNotInclude(int min, int max) {
        if (min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
