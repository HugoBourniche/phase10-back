package fr.bugo.games.tool.Phase10.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

public class MathUtilsTest {

    @ParameterizedTest
    @ValueSource(longs = {-8533495432044473907L, -7041836977445324968L, 5157150467753200499L, -5085267954160165379L, -2013748173251291859L, -8948528272695172813L, 348823839727514382L, 5550713716636528509L, 9159072350836970095L, 3770245896921252977L})
    public void randomTest(long seed) {
        Random random = new Random(seed);
        int value = MathUtils.randomInt(random, 0, 1);
        System.out.println(value);
        Assertions.assertTrue(value >= 0);
        Assertions.assertTrue(value <= 1);
    }
}
