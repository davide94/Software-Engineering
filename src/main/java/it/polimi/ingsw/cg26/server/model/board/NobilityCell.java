package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class NobilityCell {

    /**
     *
     */
    private int index;

    /**
     *
     */
    private NobilityCell next;

    /**
     *
     */
    private RewardTile reward;


    /**
     * Default constructor
     */
    private NobilityCell(int index, NobilityCell next, RewardTile reword) {
        if (index < 0)
            throw new IllegalArgumentException();
        if (reword == null)
            throw new NullPointerException();
        this.index = index;
        this.next = next;
        this.reward = reword;
    }

    public static NobilityCell createNobilityCell(int index, NobilityCell next, RewardTile reward) {
        return new NobilityCell(index, next, reward);
    }

    public NobilityCellDTO getState() {

        return new NobilityCellDTO(index, reward.getState());
    }

    /**
     * @return
     */
    public NobilityCell next() {
        return this.next;
    }

    /**
     * @return
     */
    public Boolean hasNext() {
        return this.next != null;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return this.index;
    }

    public void apply(Player player) {
        reward.apply(player);
    }

}