package fr.bugo.games.tool.Phase10.utils;

import java.util.List;
import java.util.Random;

public class MathUtils {

    // *****************************************************************************************************************
    // RANDOM UTILS
    // *****************************************************************************************************************

    /**
     *
     * @param random Random object
     * @param min Minimum bound
     * @param max Maximum bound
     * @return Value between the bounds included
     */
    public static int randomInt(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     *
     * @param random Random Object to generate random elements
     * @param probability Number between 0 and 100
     * @return True if the number is below or equal to the probability
     */
    public static boolean runChance(Random random, int probability) {
        return MathUtils.randomInt(random, 0, 100) <= probability;
    }

    /**
     * Pick randomly an element from the list with equal probabilities
     * @param random Random object
     * @param elements List of element containing the one to pick up
     * @return An element of the list
     */
    public static <T> T pickOne(Random random, List<T> elements) {
        return elements.get(randomInt(random, 0, elements.size() - 1));
    }

    // *****************************************************************************************************************
    // NUMBER UTILS
    // *****************************************************************************************************************

    /**
     * Is the value inside the range, including the bound values
     * @param lowerBound Lower range value
     * @param upperBound Upper range value
     * @param value integer to compare
     * @return true if lowerBound <= value <= upperBound
     */
    public static boolean isInRange(int lowerBound, int upperBound, int value) {
        return lowerBound <= value && value <= upperBound;
    }

}
