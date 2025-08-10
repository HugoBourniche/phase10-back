package fr.bugo.games.tool.Phase10.exception;

public class CantBuildPhasePartException extends Exception {

    public CantBuildPhasePartException(int nbCards) {
        super("Can't build any part for a phase with " + nbCards + " card(s)");
    }
}
