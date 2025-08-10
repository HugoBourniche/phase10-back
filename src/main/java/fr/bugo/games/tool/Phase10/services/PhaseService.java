package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.Card;
import fr.bugo.games.tool.Phase10.pojo.model.EColor;
import fr.bugo.games.tool.Phase10.pojo.model.ENumber;
import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import fr.bugo.games.tool.Phase10.utils.MathUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static fr.bugo.games.tool.Phase10.utils.Constants.DISPATCH_CARDS_PROBABILITY_CHANCE;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_COLOR;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_RUN;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_SET;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAX_CARDS;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_COLOR;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_RUN;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_SET;
import static fr.bugo.games.tool.Phase10.utils.Constants.MIN_CARDS;

@Service
public class PhaseService {

    // *****************************************************************************************************************
    // SERVICES
    // *****************************************************************************************************************

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
            PhasePart part = buildPart(random, nbCards, state);
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

    // -- Phase Builder Helper --

    private PhasePart buildPart(Random random, int nbCards, BuildingPhaseState state) throws CantBuildPhasePartException {
        List<EPhasePartType> partPossibilities = new ArrayList<>(Arrays.stream(EPhasePartType.values()).toList());
        if (!canBuildColorPart(nbCards)) {
            partPossibilities.remove(EPhasePartType.COLOR);
        }
        if (!canBuildRunPart(nbCards)) {
            partPossibilities.remove(EPhasePartType.RUN);
        }
        if (!canBuildSetPart(nbCards)) {
            partPossibilities.remove(EPhasePartType.SET);
        }
        if (partPossibilities.isEmpty()) {
            throw new CantBuildPhasePartException(nbCards);
        }

        return switch (MathUtils.pickOne(random, partPossibilities)) {
            case COLOR -> buildColorPart(nbCards, state.getNbColorPart());
            case RUN -> buildRunPart(nbCards);
            case SET -> buildSetPart(nbCards, state.getNbSetPart());
        };
    }

    private PhasePart buildColorPart(int nbCards, int existingPhaseWithColor) {
        List<Card> cards = new ArrayList<>();
        for (int index = 0; index < nbCards; index++) {
            EColor color = EColor.values()[existingPhaseWithColor];
            cards.add(new Card(color, ENumber.NONE));
        }
        return new PhasePart(cards, EPhasePartType.COLOR);
    }

    private PhasePart buildRunPart(int nbCards) {
        List<Card> cards = new ArrayList<>();
        for (int index = 0; index < nbCards; index++) {
            ENumber number = ENumber.values()[index];
            cards.add(new Card(EColor.WILD, number));
        }
        return new PhasePart(cards, EPhasePartType.RUN);
    }

    private PhasePart buildSetPart(int nbCards, int existingPhasesWithSet) {
        List<Card> cards = new ArrayList<>();
        for (int index = 0; index < nbCards; index++) {
            ENumber number = ENumber.values()[existingPhasesWithSet];
            cards.add(new Card(EColor.WILD, number));
        }
        return new PhasePart(cards, EPhasePartType.SET);
    }

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

    /**
     * Can build a color part of cards. The cards have a minimum and maximum possible for each type.
     * @param nbCards Number of cards to have.
     * @return A color part type can be built from this number of cards
     */
    private boolean canBuildColorPart(int nbCards) {
        return MathUtils.isInRange(MINIMUM_COLOR, MAXIMUM_COLOR, nbCards);
    }


    /**
     * Can build a run part of cards. The cards have a minimum and maximum possible for each type.
     * @param nbCards Number of cards to have.
     * @return A run part type can be built from this number of cards
     */
    private boolean canBuildRunPart(int nbCards) {
        return MathUtils.isInRange(MINIMUM_RUN, MAXIMUM_RUN, nbCards);
    }

    /**
     * Can build a set part of cards. The cards have a minimum and maximum possible for each type.
     * @param nbCards Number of cards to have.
     * @return A set part type can be built from this number of cards
     */
    private boolean canBuildSetPart(int nbCards) {
        return MathUtils.isInRange(MINIMUM_SET, MAXIMUM_SET, nbCards);
    }
}
