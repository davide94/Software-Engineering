package it.polimi.ingsw.cg26.model;

import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.observer.Observable;

import java.util.Set;

/**
 * 
 */
public class GameLogic extends Observable {

    /**
     * 
     */
    public void GameLogic() {
        // TODO implement here
    }

    /**
     * 
     */
    public void newPlayer() {
        // TODO implement here
    }

    /**
     * 
     */
    public void start() {
        // TODO implement here
    }

    /**
     * @return
     */
    public PoliticCard draw() {
        // TODO implement here
        return null;
    }

    /**
     * @param region 
     * @param color
     */
    public void elect(String region, String color) {
        // TODO implement here
    }

    /**
     * @param
     */
    public void acquireBPT(PoliticCard[] c, String regions, int i) {
        // TODO implement here
    }

    /**
     * @param city 
     */
    public void build(String city, BusinessPermissionTile b) {
        // TODO implement here
    }

    /**
     * @param city 
     */
    public void buildKing(String city, PoliticCard[] c) {
        // TODO implement here
    }

    /**
     * 
     */
    public void engageAssistant() {
        // TODO implement here
    }

    /**
     * @param region
     */
    public void changeBPT(String region) {
        // TODO implement here
    }

    /**
     * @param region 
     * @param color
     */
    public void electWithAssistant(String region, String color) {
        // TODO implement here
    }

    /**
     * 
     */
    public void additionalMainAction() {
        // TODO implement here
    }

    /**
     * @param a
     */
    public void sell(Set<Sellable> a) {
        // TODO implement here
    }

    /**
     * 
     */
    public void buy() {
        // TODO implement here
    }


}