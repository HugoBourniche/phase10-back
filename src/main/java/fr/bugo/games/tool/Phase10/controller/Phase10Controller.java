package fr.bugo.games.tool.Phase10.controller;

import fr.bugo.games.tool.Phase10.services.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/phase10")
public class Phase10Controller {

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
        return new ResponseEntity<>("Phase 10 tool back application", HttpStatus.OK);
    }
}
