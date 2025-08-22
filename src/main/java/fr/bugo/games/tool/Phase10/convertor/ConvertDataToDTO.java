package fr.bugo.games.tool.Phase10.convertor;

import fr.bugo.games.tool.Phase10.pojo.dto.CardDTO;
import fr.bugo.games.tool.Phase10.pojo.dto.PhaseDTO;
import fr.bugo.games.tool.Phase10.pojo.dto.PhasePartDTO;
import fr.bugo.games.tool.Phase10.pojo.model.Card;
import fr.bugo.games.tool.Phase10.pojo.model.Phase;
import fr.bugo.games.tool.Phase10.pojo.model.PhasePart;

import java.util.ArrayList;
import java.util.List;

public class ConvertDataToDTO {

    // *****************************************************************************************************************
    // PUBLIC METHODS
    // *****************************************************************************************************************

    public static List<PhaseDTO> convertPhases(List<Phase> phases) {
        List<PhaseDTO> phasesDTO = new ArrayList<>();
        for (Phase phase : phases) {
            phasesDTO.add(convertPhase(phase));
        }
        return phasesDTO;
    }

    // *****************************************************************************************************************
    // PRIVATE METHODS
    // *****************************************************************************************************************

    private static PhaseDTO convertPhase(Phase phase) {
        List<PhasePartDTO> partsDTO = convertPhaseParts(phase.getParts());
        return new PhaseDTO(partsDTO);
    }

    private static List<PhasePartDTO> convertPhaseParts(List<PhasePart> phaseParts) {
        List<PhasePartDTO> partsDTO = new ArrayList<>();
        for (PhasePart part : phaseParts) {
            partsDTO.add(convertPhasePart(part));
        }
        return partsDTO;
    }

    private static PhasePartDTO convertPhasePart(PhasePart part) {
        List<CardDTO> cardsDTO = convertCards(part.getCards());
        String type = part.getPhaseType().name();
        return new PhasePartDTO(cardsDTO, type);
    }

    private static List<CardDTO> convertCards(List<Card> cards) {
        List<CardDTO> cardsDTO = new ArrayList<>();
        for (Card card : cards) {
            cardsDTO.add(convertCard(card));
        }
        return cardsDTO;
    }

    private static CardDTO convertCard(Card card) {
        String color = card.getColor().name();
        String number = card.getNumber().name();
        return new CardDTO(color, number);
    }

}
