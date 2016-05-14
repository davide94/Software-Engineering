package it.polimi.ingsw.cg26.view;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.actions.main.Acquire;
import it.polimi.ingsw.cg26.actions.main.Build;
import it.polimi.ingsw.cg26.actions.main.BuildKing;
import it.polimi.ingsw.cg26.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.actions.quick.AdditionalMainAction;
import it.polimi.ingsw.cg26.actions.quick.ChangeBPT;
import it.polimi.ingsw.cg26.actions.quick.ElectAsQuickAction;
import it.polimi.ingsw.cg26.actions.quick.EngageAssistant;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.observer.Observable;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.Update;

import java.util.List;

/**
 * 
 */
public class View extends Observable<Action> implements Observer<Update>, Runnable {

    private final String name;

    private final String token;

    private UserInterface userInterface;

    public View(String token, String name) {
        if (token == null || name == null)
            throw new NullPointerException();
        this.token = token;
        this.name = name;
        this.userInterface = new CLI(this);
    }

    @Override
    public void run() {
        this.userInterface.start();
    }

    @Override
    public void update(Update update) {
        System.out.println("New state: " + update.getState());
    }

    public String getName() {
        return this.name;
    }

    public void electAsMainAction(String region, PoliticColor politicColor) {
        notifyObservers(new ElectAsMainAction(this.token, region, politicColor));
    }

    public void acquire(String region, List<PoliticColor> cardsColors, int position) {
        notifyObservers(new Acquire(this.token, region, cardsColors, position));
    }

    public void build(String city) {
        notifyObservers(new Build(this.token, city));
    }

    public void buildKing(String city, List<PoliticColor> cards) {
        notifyObservers(new BuildKing(this.token, city, cards));
    }

    public void engageAssistant() {
        notifyObservers(new EngageAssistant(this.token));
    }

    public void changeBPT(String region) {
        notifyObservers(new ChangeBPT(this.token, region));
    }

    public void electAsQuickAction(String region, PoliticColor politicColor) {
        notifyObservers(new ElectAsQuickAction(this.token, region, politicColor));
    }

    public void additionalMainAction() {
        notifyObservers(new AdditionalMainAction(this.token));
    }

}