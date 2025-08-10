package fr.bugo.games.tool.Phase10.pojo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuildingPhaseState {

    // *****************************************************************************************************************
    // ATTRIBUTES
    // *****************************************************************************************************************

    private int nbColorPart = 0;
    private int nbRunPart = 0;
    private int nbSetPart = 0;

    // *****************************************************************************************************************
    // CONSTRUCTOR
    // *****************************************************************************************************************


    // *****************************************************************************************************************
    // PUBLIC METHOD
    // *****************************************************************************************************************

    public void typeAdded(EPhasePartType type) {
        switch (type) {
            case COLOR: this.nbColorPart++;break;
            case RUN: this.nbRunPart++;break;
            case SET: this.nbSetPart++;break;
        }
    }
}
