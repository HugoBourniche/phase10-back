package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.Card;
import fr.bugo.games.tool.Phase10.pojo.model.EColor;
import fr.bugo.games.tool.Phase10.pojo.model.ENumber;
import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import fr.bugo.games.tool.Phase10.utils.MathUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_COLOR;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_RUN;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_SET;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_COLOR;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_RUN;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_SET;

@Service
public class PhasePartService {


    // *****************************************************************************************************************
    // PUBLIC METHODS
    // *****************************************************************************************************************

    public PhasePart buildPart(Random random, int nbCards, BuildingPhaseState state) throws CantBuildPhasePartException {
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

    // *****************************************************************************************************************
    // PRIVATE METHODS
    // *****************************************************************************************************************

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
