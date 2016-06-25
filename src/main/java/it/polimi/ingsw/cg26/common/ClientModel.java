package it.polimi.ingsw.cg26.common;

import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ClientModel {

    ClientState getState();

    List<RegionDTO> getRegions();

    List<PlayerDTO> getPlayers();

    void setLocalPlayer(PlayerDTO localPlayer);

    void setCurrentPlayer(PlayerDTO currentPlayer);

    void setPlayers(List<PlayerDTO> players);

    void setPoliticDeck(PoliticDeckDTO politicDeck);

    void setCouncillorsPool(Collection<CouncillorDTO> councillorsPool);

    void setKingDeck(KingDeckDTO kingDeck);

    void setKingBalcony(BalconyDTO kingBalcony);

    void setRegions(List<RegionDTO> regions);

    void setNobilityTrack(NobilityTrackDTO nobilityTrack);

    void setKing(KingDTO king);

    void setMarket(MarketDTO market);
    
    void setColorBonuses(Map<CityColorDTO, BonusDTO> colorBonuses);

}
