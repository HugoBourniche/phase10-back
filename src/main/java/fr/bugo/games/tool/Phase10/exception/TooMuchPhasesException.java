package fr.bugo.games.tool.Phase10.exception;

public class TooMuchPhasesException extends Exception {
    public TooMuchPhasesException(Integer nbPhases) {
        super("Too much phases maximum 30, requested " + nbPhases);
    }
}
