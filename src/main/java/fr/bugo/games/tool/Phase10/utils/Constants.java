package fr.bugo.games.tool.Phase10.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Integer MINIMUM_PHASES = 1;
    public static Integer MAXIMUM_PHASES = 30;

    public static Integer MIN_CARDS = 5;
    public static Integer MAX_CARDS = 10;

    public static Integer MINIMUM_SET = 2;
    public static Integer MAXIMUM_SET = 8; // Too much ?

    public static Integer MINIMUM_RUN = 4;
    public static Integer MAXIMUM_RUN = 10;

    public static Integer MINIMUM_COLOR = 3;
    public static Integer MAXIMUM_COLOR = 10;

    public static Map<Integer, Integer> DISPATCH_CARDS_PROBABILITY_CHANCE = new HashMap<>() {{
        put(0, 0);
        put(1, 0);
        put(2, 0);
        put(3, 0);
        put(4, 0);
        put(5, 10);
        put(6, 15);
        put(7, 25);
        put(8, 40);
        put(9, 60);
        put(10, 70);
    }};

    public static String DEFAULT_SEED = "default";
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
}
