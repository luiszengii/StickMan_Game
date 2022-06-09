package stickman.model;

import stickman.memento.Memento;
import stickman.level.Level;

import java.io.Serializable;

/**
 * Interface for the GameEngine. Describes the necessary behaviour
 * for running the game.
 */
public interface GameEngine extends Serializable {

    /**
     * Gets the current running level.
     * @return The current level
     */
    Level getCurrentLevel();

    /**
     * gets the score of the current level only
     * @return the current level score
     */
    double getCurrentLevelScore();

    /**
     * replace current level with the next one
     * return true if replace successfully
     * false if no next level
     * @return
     */
    boolean moveToNextLevel();

    /**
     * Makes the player jump.
     * @return Whether the input had an effect
     */
    boolean jump();

    /**
     * Moves the player left.
     * @return Whether the input had an effect
     */
    boolean moveLeft();

    /**
     * Moves the player right.
     * @return Whether the input had an effect
     */
    boolean moveRight();

    /**
     * Stops player movement.
     * @return Whether the input had an effect
     */
    boolean stopMoving();

    /**
     * Updates the scene every frame.
     */
    void tick();

    /**
     * Makes the player shoot.
     */
    void shoot();

    /**
     * Restarts the level.
     */
    void reset();

    /**
     * to save a snapshot of the current game
     * @return a Memento object contains the game engine and the database
     */
    Memento save();

    int getLevelNumber();

    void restore(Memento memento);
}
