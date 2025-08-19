package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.TestcontainersConfiguration;
import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Random;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class PhasePartServiceTest {


    // *****************************************************************************************************************
    // SERVICES
    // *****************************************************************************************************************

    @Autowired
    private PhasePartService phasePartService;


    // *****************************************************************************************************************
    // NUMBER CARDS VARIATION TESTS
    // *****************************************************************************************************************

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    public void buildPhasePartWithNotEnoughCardsTest(int nbCards) {
        Random random = new Random(5550713716636528509L);
        BuildingPhaseState state = new BuildingPhaseState();
        try {
            phasePartService.buildPart(random, nbCards, state);
            Assertions.fail("Can't build part with less than 10 cards.");
        } catch (CantBuildPhasePartException ignored) {}
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void buildPhasePartWithoutRunOrColorCardsPossibleTest(Integer nbCards) throws CantBuildPhasePartException {
        Random random = new Random();
        BuildingPhaseState state = new BuildingPhaseState();
        PhasePart part = phasePartService.buildPart(random, nbCards, state);
        Assertions.assertNotEquals(EPhasePartType.COLOR, part.getPhaseType());
        Assertions.assertNotEquals(EPhasePartType.RUN, part.getPhaseType());
        Assertions.assertEquals(nbCards, part.getCards().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    public void buildPhasePartWithoutRunCardsPossibleTest(Integer nbCards) throws CantBuildPhasePartException {
        Random random = new Random();
        BuildingPhaseState state = new BuildingPhaseState();
        PhasePart part = phasePartService.buildPart(random, nbCards, state);
        Assertions.assertNotEquals(EPhasePartType.RUN, part.getPhaseType());
        Assertions.assertEquals(nbCards, part.getCards().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6, 7, 8})
    public void buildPhasePartEverythingPossibleTest(Integer nbCards) throws CantBuildPhasePartException {
        Random random = new Random();
        BuildingPhaseState state = new BuildingPhaseState();
        PhasePart part = phasePartService.buildPart(random, nbCards, state);
        Assertions.assertEquals(nbCards, part.getCards().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 10})
    public void buildPhasePartWithoutSetCardsPossibleTest(Integer nbCards) throws CantBuildPhasePartException {
        Random random = new Random();
        BuildingPhaseState state = new BuildingPhaseState();
        PhasePart part = phasePartService.buildPart(random, nbCards, state);
        Assertions.assertNotEquals(EPhasePartType.SET, part.getPhaseType());
        Assertions.assertEquals(nbCards, part.getCards().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
    public void buildPhasePartWithTooMuchCardsTest(int nbCards) {
        Random random = new Random(5550713716636528509L);
        BuildingPhaseState state = new BuildingPhaseState();
        try {
            phasePartService.buildPart(random, nbCards, state);
            Assertions.fail("Can't build part with more than 10 cards.");
        } catch (CantBuildPhasePartException ignored) {}
    }

}
