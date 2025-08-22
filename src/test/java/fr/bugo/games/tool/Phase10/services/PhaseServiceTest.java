package fr.bugo.games.tool.Phase10.services;

import fr.bugo.games.tool.Phase10.TestcontainersConfiguration;
import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class PhaseServiceTest {

    @Autowired
    private PhaseService phaseService;

    @Test
    public void buildPhaseTest() throws CantBuildPhasePartException {
        // 7834664765466812985L
        List<Phase> phase = phaseService.buildPhases(5550713716636528509L, 1);

        Assertions.assertEquals(1, phase.size());
        Assertions.assertFalse(phase.getFirst().getParts().isEmpty());
    }
}
