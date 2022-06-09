package stickman.memento;

import stickman.model.GameEngine;

public class MementoCaretaker {
    private GameEngine gameEngine;
    private Memento memento;

    public MementoCaretaker(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.memento = null;
    }

    public void quicksave(){
        System.out.println("game saved");
        this.memento = this.gameEngine.save();
    }

    public void quickload(){
        if (this.memento != null) {
            System.out.println("game loading");
            this.gameEngine.restore(this.memento);
        } else {
            System.out.println("no memento");
        }
    }
}