package fr.bugo.games.tool.Phase10.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhasePart {

    // *****************************************************************************************************************
    // ATTRIBUTES
    // *****************************************************************************************************************

    private List<Card> cards = new ArrayList<>();
    private EPhasePartType phaseType;

    // *****************************************************************************************************************
    // CONSTRUCTOR
    // *****************************************************************************************************************

    // *****************************************************************************************************************
    // PUBLIC METHODS
    // *****************************************************************************************************************

}
