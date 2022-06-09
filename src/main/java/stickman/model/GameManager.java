package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import stickman.memento.Memento;
import stickman.database.DataBase;
import stickman.level.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of GameEngine. Manages the running of the game.
 */
public class GameManager implements GameEngine {

    /**
     * The current level
     */
    private Level level;
    /*
    all data in database needs to be saved(DEEP COPY x 6) as well
     */

    /**
     * List of all level files
     */
    private List<String> levelFileNames;

    /**
     * the index of current level
     */
    private int levelNumber = 0;//save

    /**
     * the startTime of the level, used to calculate time elapsed
     */
    private long startTime = System.nanoTime();// when save start time to be calculated by system.nanoTime() - DataBase.getTimeElapsed()

    /**
     * Creates a GameManager object.
     * @param levels The config file containing the names of all the levels
     */
    public GameManager(String levels) {
        this.levelFileNames = this.readConfigFile(levels);

        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(levelNumber), this);
    }

    @Override
    public Level getCurrentLevel() {
        return this.level;
    }

    @Override
    public double getCurrentLevelScore() {
        return 0;
    }

    @Override
    public boolean moveToNextLevel() {
        //reset the time elapsed for next level
        DataBase.setTimeElapsed(0.0);
        startTime = System.nanoTime();

        //adding this level's score to total score
        DataBase.addTotalScore(DataBase.getCurrentLevelScore());

        //if has next level move to it, otherwise return false
        if (levelNumber < levelFileNames.size() - 1){//check if is the last level
            this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(++levelNumber), this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean jump() {
        return this.level.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.level.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.level.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.level.stopMoving();
    }

    /**
     * game engine tick updates the time elapsed in database
     */
    @Override
    public void tick() {
        this.level.tick();
        if (!this.getCurrentLevel().isActive()){ return; }

        //update time elapsed
        DataBase.setTimeElapsed((double) (System.nanoTime()- this.startTime) / 1000000000);

        //update level score
        DataBase.setCurrentLevelScore(DataBase.getCurrentLevelActionScore() + DataBase.getCurrentLevelTimeScore());
    }

    @Override
    public void shoot() {
        this.level.shoot();
    }

    @Override
    public void reset() {
        this.level = LevelBuilderImpl.generateFromFile(this.level.getSource(), this);
    }

    /**
     * Retrieves the list of level filenames from a config file
     * @param config The config file
     * @return The list of level names
     */
    @SuppressWarnings("unchecked")
    private List<String> readConfigFile(String config) {

        List<String> res = new ArrayList<String>();

        JSONParser parser = new JSONParser();

        try {

            Reader reader = new FileReader(config);

            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray levelFiles = (JSONArray) object.get("levelFiles");

            Iterator<String> iterator = (Iterator<String>) levelFiles.iterator();

            // Get level file names
            while (iterator.hasNext()) {
                String file = iterator.next();
                res.add("levels/" + file);
            }

            //get total life of hero and update Data base
            DataBase.setResidualLife(((Long)object.get("heroResidualLife")).intValue());

        } catch (IOException e) {
            System.exit(10);
            return null;
        } catch (ParseException e) {
            return null;
        }

        return res;
    }

    /**
     * saves current state of game engine()
     * @return
     */
    @Override
    public Memento save() {
        return new Memento(this);
    }

    @Override
    public int getLevelNumber() {
        return levelNumber;
    }

    public void restore(Memento memento) {
        //restore database
        DataBase.setResidualLife(memento.getResidualLife());
        DataBase.setTimeElapsed(memento.getTimeElapsed());
        DataBase.setCurrentLevelScore(memento.getCurrentLevelScore());
        DataBase.setTotalScore(memento.getTotalScore());
        DataBase.setCurrentLevelTimeScore(memento.getCurrentLevelTimeScore());
        DataBase.setCurrentLevelActionScore(memento.getCurrentLevelActionScore());
        this.startTime = System.nanoTime() - (long) memento.getTimeElapsed();
        //restore level number
        this.levelNumber = memento.getLevelNumber();

        //restore level
        String oldLevel = memento.getOldLevel();

        try {
            byte[] data = Base64.getDecoder().decode(oldLevel);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.level = (Level) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.print("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.print("IOException occurred.");
        }
    }

}