package fr.bugo.games.tool.Phase10.controller;

import fr.bugo.games.tool.Phase10.convertor.ConvertDataToDTO;
import fr.bugo.games.tool.Phase10.exception.CantBuildPhasePartException;
import fr.bugo.games.tool.Phase10.exception.NumberPhasesConsistencyException;
import fr.bugo.games.tool.Phase10.pojo.dto.PhaseDTO;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import fr.bugo.games.tool.Phase10.pojo.responses.PhasesResponse;
import fr.bugo.games.tool.Phase10.services.PhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/phase10")
public class Phase10Controller {

    // *****************************************************************************************************************
    // CONSTANTS
    // *****************************************************************************************************************

    private static final Logger LOGGER = LoggerFactory.getLogger(Phase10Controller.class);

    // *****************************************************************************************************************
    // SERVICES
    // *****************************************************************************************************************

    @Autowired
    private PhaseService phaseService;

    // *****************************************************************************************************************
    // GET REQUESTS
    // *****************************************************************************************************************

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        LOGGER.info("phase10/info called");
        return new ResponseEntity<>("Phase 10 tool back application", HttpStatus.OK);
    }

    @GetMapping("/phases")
    public ResponseEntity<?> phases(@RequestParam Integer numberPhases) {
        LOGGER.info("phase10/phases?numberPhases={} called", numberPhases);
        Random random = new Random();
        long seed = 7834664765466812985L; // random.nextLong();
        LOGGER.info("Seed {} generated", seed);
        List<Phase> phases;
        try {
            phases = phaseService.buildPhases(seed, numberPhases);
        } catch (CantBuildPhasePartException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NumberPhasesConsistencyException e) {
            return new ResponseEntity<>("Il faut entrer un nombre entre 1 et 30: " + numberPhases, HttpStatus.FORBIDDEN);
        }
        List<PhaseDTO> phasesDTO = ConvertDataToDTO.convertPhases(phases);
        PhasesResponse response = new PhasesResponse(phasesDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
