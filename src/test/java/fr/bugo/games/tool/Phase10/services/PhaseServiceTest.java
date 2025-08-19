package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.TestcontainersConfiguration;
import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class PhaseServiceTest {

    @Autowired
    private PhaseService phaseService;

    @Test
    public void buildPhaseTest() throws CantBuildPhasePartException {
        Phase phase = phaseService.buildPhase(5550713716636528509L);
        Assertions.assertFalse(phase.getParts().isEmpty());
    }
}
