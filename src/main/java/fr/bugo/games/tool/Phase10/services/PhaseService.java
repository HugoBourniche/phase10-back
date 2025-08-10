package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
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
import static fr.bugo.games.tool.Phase10.utils.Constants.MAX_CARDS;
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

    public Phase buildPhase(long randomSeed) throws CantBuildPhasePartException {
        Random random = new Random(randomSeed);
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
        int nbCardsInPart = MathUtils.randomInt(random, 2, nbCardsRemaining);
        parts.add(nbCardsInPart);
        int remainingCards = nbCardsRemaining - nbCardsInPart;
        if (MathUtils.runChance(random, DISPATCH_CARDS_PROBABILITY_CHANCE.get(remainingCards))) {
            parts.addAll(divideCardIntoPhaseParts(random, remainingCards));
        }
        return parts;
    }

}
