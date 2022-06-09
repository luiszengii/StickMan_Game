package stickman.memento;

import stickman.database.DataBase;
import stickman.level.Level;
import stickman.model.GameEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Memento {

    private int residualLife;
    private double timeElapsed;
    private int currentLevelActionScore;
    private int currentLevelTimeScore;
    private int currentLevelScore;
    private int totalScore;

    private String oldLevel;
    private int levelNumber;


    public Memento(GameEngine gameEngine){
        this.residualLife = DataBase.getResidualLife();
        this.timeElapsed = DataBase.getTimeElapsed();
        this.currentLevelTimeScore = DataBase.getCurrentLevelTimeScore();
        this.currentLevelActionScore = DataBase.getCurrentLevelActionScore();
        this.currentLevelScore = DataBase.getCurrentLevelScore();
        this.totalScore = DataBase.getTotalScore();
        this.oldLevel = "";

        this.levelNumber = gameEngine.getLevelNumber();

        //serialize model's current level
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            Level level = gameEngine.getCurrentLevel();
            oos.writeObject(level);
            oos.close();
            this.oldLevel = Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            this.oldLevel = null;
        }

    }

    public String getOldLevel() {
        return this.oldLevel;
    }

    public int getResidualLife() {
        return residualLife;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public int getCurrentLevelScore() {
        return this.currentLevelScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getCurrentLevelActionScore() {
        return currentLevelActionScore;
    }

    public int getCurrentLevelTimeScore() {
        return currentLevelTimeScore;
    }
}
