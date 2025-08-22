package fr.bugo.games.tool.Phase10.exception;

import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_PHASES;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_PHASES;

public class NumberPhasesConsistencyException extends Exception {
    public NumberPhasesConsistencyException(Integer nbPhases) {
        super("Number of phase must be between " + MINIMUM_PHASES + " and " + MAXIMUM_PHASES + ". Your request: " + nbPhases);
    }
}
