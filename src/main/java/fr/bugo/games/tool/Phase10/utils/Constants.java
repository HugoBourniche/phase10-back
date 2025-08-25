package fr.bugo.games.tool.Phase10.utils;

import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;

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

    public static Map<EPhasePartType, Map<Integer, Integer>> PHASE_COMPLEXITY_PER_TYPE_AND_NB_CARDS = new HashMap<>() {{
        put(EPhasePartType.COLOR, new HashMap<>() {{
            put(3, 0); // 24
            put(4, 1);
            put(5, 3);
            put(6, 3);
            put(7, 4);
            put(8, 5);
            put(9, 6);
            put(10, 8);
        }});
        put(EPhasePartType.RUN, new HashMap<>() {{
            put(4, 1); // ?
            put(5, 2);
            put(6, 3);
            put(7, 4);
            put(8, 5);
            put(9, 6);
            put(10, 6);
        }});
        put(EPhasePartType.SET, new HashMap<>() {{
            put(2, 1); // 8
            put(3, 3);
            put(4, 5);
            put(5, 6);
            put(6, 8);
            put(7, 9);
            put(8, 10);
        }});
    }};

    public static String DEFAULT_SEED = "default";
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
}
