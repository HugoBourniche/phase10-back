package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.exception.NumberPhasesConsistencyException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.Card;
import fr.bugo.games.tool.Phase10.pojo.model.EColor;
import fr.bugo.games.tool.Phase10.pojo.model.ENumber;
import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import fr.bugo.games.tool.Phase10.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static fr.bugo.games.tool.Phase10.utils.Constants.DISPATCH_CARDS_PROBABILITY_CHANCE;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAXIMUM_PHASES;
import static fr.bugo.games.tool.Phase10.utils.Constants.MAX_CARDS;
import static fr.bugo.games.tool.Phase10.utils.Constants.MINIMUM_PHASES;
import static fr.bugo.games.tool.Phase10.utils.Constants.MIN_CARDS;
import static fr.bugo.games.tool.Phase10.utils.Constants.PHASE_COMPLEXITY_PER_TYPE_AND_NB_CARDS;

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

        return sortPhasesByComplexity(phases);
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

    /**
     * Build default phases from original game
     * @return List of base game phases.
     */
    public List<Phase> buildDefaultPhases() {
        Phase p1 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B)
                )
        );

        Phase p2 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.RUN,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.C),
                        new Card(EColor.WILD, ENumber.D)
                )
        );

        Phase p3 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.RUN,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.C),
                        new Card(EColor.WILD, ENumber.D)
                )
        );

        Phase p4 = createPhase(
                createPhasePart(EPhasePartType.RUN,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.C),
                        new Card(EColor.WILD, ENumber.D),
                        new Card(EColor.WILD, ENumber.E),
                        new Card(EColor.WILD, ENumber.F),
                        new Card(EColor.WILD, ENumber.G)
                )
        );

        Phase p5 = createPhase(
                createPhasePart(EPhasePartType.RUN,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.C),
                        new Card(EColor.WILD, ENumber.D),
                        new Card(EColor.WILD, ENumber.E),
                        new Card(EColor.WILD, ENumber.F),
                        new Card(EColor.WILD, ENumber.G),
                        new Card(EColor.WILD, ENumber.H)
                )
        );

        Phase p6 = createPhase(
                createPhasePart(EPhasePartType.RUN,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.C),
                        new Card(EColor.WILD, ENumber.D),
                        new Card(EColor.WILD, ENumber.E),
                        new Card(EColor.WILD, ENumber.F),
                        new Card(EColor.WILD, ENumber.G),
                        new Card(EColor.WILD, ENumber.H),
                        new Card(EColor.WILD, ENumber.I)
                )
        );

        Phase p7 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B)
                )
        );


        Phase p8 = createPhase(
                createPhasePart(EPhasePartType.COLOR,
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE),
                        new Card(EColor.RED, ENumber.NONE)
                )
        );

        Phase p9 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B)
                )
        );

        Phase p10 = createPhase(
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A),
                        new Card(EColor.WILD, ENumber.A)
                ),
                createPhasePart(EPhasePartType.SET,
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B),
                        new Card(EColor.WILD, ENumber.B)
                )
        );

        return List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

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

    // Complexity

    private List<Phase> sortPhasesByComplexity(List<Phase> phases) {
        Map<Integer, List<Phase>> mapPhasesByComplexity = new HashMap<>();
        for (Phase phase : phases) {
            int complexity = computePhaseComplexity(phase);
            List<Phase> sortedPhases = new ArrayList<>();
            if (mapPhasesByComplexity.containsKey(complexity)) {
                sortedPhases = mapPhasesByComplexity.get(complexity);
            }
            sortedPhases.add(phase);
            mapPhasesByComplexity.put(complexity, sortedPhases);
        }
        List<Phase> result = new ArrayList<>();
        for (Integer complexity : mapPhasesByComplexity.keySet().stream().sorted().toList()) {
            mapPhasesByComplexity.get(complexity).sort(Comparator.comparingInt(a -> a.getParts().size()));
            result.addAll(mapPhasesByComplexity.get(complexity));
        }
        return result;
    }

    private int computePhaseComplexity(Phase phase) {
        int complexity = 0;
        for (PhasePart part : phase.getParts()) {
            int nbCards = part.getCards().size();
            complexity += nbCards;
            complexity += PHASE_COMPLEXITY_PER_TYPE_AND_NB_CARDS.get(part.getPhaseType()).get(nbCards);
        }
        return complexity;
    }

    // Default phase builder utils

    private Phase createPhase(PhasePart... parts) {
        Phase phase = new Phase();
        phase.setParts(List.of(parts));
        return phase;
    }

    private PhasePart createPhasePart(EPhasePartType type, Card... cards) {
        PhasePart part = new PhasePart();
        part.setPhaseType(type);
        part.setCards(List.of(cards));
        return part;
    }


}
