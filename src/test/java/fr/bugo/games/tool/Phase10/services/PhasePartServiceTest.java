package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.BuildingPhaseState;
import fr.bugo.games.tool.Phase10.pojo.model.EPhasePartType;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class PhasePartServiceTest {

    @Autowired
    private PhasePartService phasePartService;

    @Test
    public void buildPhasePartWith10CardsTest() throws CantBuildPhasePartException {
        Random random = new Random(5550713716636528509L);
        BuildingPhaseState state = new BuildingPhaseState();
        int nbCards = 10;
        PhasePart part = phasePartService.buildPart(random, nbCards, state);
        Assertions.assertNotEquals(EPhasePartType.SET, part.getPhaseType());
        Assertions.assertEquals(nbCards, part.getCards().size());
    }
}
