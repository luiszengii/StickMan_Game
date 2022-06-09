package stickman.entity.still;

import stickman.entity.GameObject;

/**
 * The win message displayed after collecting the flag.
 */
public class GameOver extends GameObject {

    /**
     * Constructs a new GameOver object.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public GameOver(double x, double y) {
        super("gameOver.png", x, y, 320, 320, Layer.EFFECT);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
