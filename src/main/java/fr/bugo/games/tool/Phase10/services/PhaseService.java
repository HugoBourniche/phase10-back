package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.exception.NumberPhasesConsistencyException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import fr.bugo.games.tool.Phase10.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.bugo.games.tool.Phase10.utils.Constants.DISPATCH_CARDS_PROBABILITY_CHANCE;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_PHASES;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAX_CARDS;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_PHASES;
import static fr.bugo.games.tool.Phase10.utils.Constants.MIN_CARDS;

@Service
public class PhaseService {

    // *****************************************************************************************************************
    // SERVICES
    // *****************************************************************************************************************

    @Autowired
    PhasePartService phasePartService;

    // *****************************************************************************************************************
    // PUBLIC METHODS
    // *****************************************************************************************************************

    /**
     * Build a number of phases with randomness built from a seed
     * @param randomSeed long number to put into the random object
     * @param nbPhases Number of phases object to build
     * @return List of phases to do
     * @throws CantBuildPhasePartException when there are errors during the phase building
     */
    public List<Phase> buildPhases(long randomSeed, int nbPhases) throws CantBuildPhasePartException, NumberPhasesConsistencyException {
        if ( MINIMUM_PHASES > nbPhases || nbPhases > MAXIMUM_PHASES) {
            throw new NumberPhasesConsistencyException(nbPhases);
        }
        List<Phase> phases = new ArrayList<>();
        Random random = new Random(randomSeed);
        for (int i = 0; i < nbPhases; i++) {
            Phase phase = buildPhase(random);
            // Ensure unicity of each phase
            if (hasAlreadyThePhase(phases, phase)) {
                i--;
            } else {
                phases.add(phase);
            }
        }
        return phases;
    }

    /**
     * Build phase part. Pick random number of cards and build phases with this number.
     * @param random Random object already built
     * @return Phase object
     * @throws CantBuildPhasePartException when there are errors with the building part
     */
    public Phase buildPhase(Random random) throws CantBuildPhasePartException {
        Phase phase = new Phase();
        int nbCardsInPhase = MathUtils.randomInt(random, MIN_CARDS, MAX_CARDS);
        List<Integer> partsToBuild = divideCardIntoPhaseParts(random, nbCardsInPhase);
        BuildingPhaseState state = new BuildingPhaseState();
        for (Integer nbCards : partsToBuild) {
            PhasePart part = phasePartService.buildPart(random, nbCards, state);
            phase.getParts().add(part);
            state.typeAdded(part.getPhaseType());
        }
        return phase;
    }

//    public int computePhaseComplexity(Phase phase) {
//        int complexity = 0;
//
//        for (Card card : phase.getCards()) {
//
//        }
//        return complexity;
//    }

    // *****************************************************************************************************************
    // PRIVATE METHODS
    // *****************************************************************************************************************

    /**
     * Divide the remaining cards number into multiple number stacks
     * @param random Random object
     * @param nbCardsRemaining Number of cards to divide
     * @return List representing the divided nb cards
     */
    private List<Integer> divideCardIntoPhaseParts(Random random, int nbCardsRemaining) {
        List<Integer> parts = new ArrayList<>();
        int nbCardsInPart = nbCardsRemaining;
        if (MathUtils.runChance(random, DISPATCH_CARDS_PROBABILITY_CHANCE.get(nbCardsRemaining))) {
            nbCardsInPart = MathUtils.randomInt(random, 2, nbCardsRemaining - 2);
            parts.addAll(divideCardIntoPhaseParts(random, nbCardsRemaining - nbCardsInPart));
        }
        parts.add(nbCardsInPart);
        return parts;
    }

    /**
     * Check if the Phase toCompare is already in the list of phases
     * @param phases List of phases to check into
     * @param toCompare Phase to compare with others
     * @return The list of phases contains or not the phase
     */
    private boolean hasAlreadyThePhase(List<Phase> phases, Phase toCompare) {
        for (Phase phase : phases) {
            if (toCompare.getParts().size() != phase.getParts().size()) {
                continue;
            }

            for (int index = 0; index < toCompare.getParts().size(); index++) {
                PhasePart toComparePart = toCompare.getParts().get(index);
                PhasePart part = phase.getParts().get(index);
                if (toComparePart.getCards().size() == part.getCards().size()) {
                    if (toComparePart.getPhaseType().equals(part.getPhaseType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
