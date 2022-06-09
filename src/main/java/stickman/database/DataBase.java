package stickman.database;

/**
 * this class is a singleton contains all the info needed by all classes
 * including residual life, time elapsed for each level, and total score
 * instance dataBase is public and open to all relevant classes to retrieve and modify data
 */
public final class DataBase {
    private static DataBase dataBase = new DataBase();
    public int residualLife;
    public double timeElapsed;
    public int currentLevelScore;
    public int totalScore;
    public int currentLevelTimeScore;
    public int currentLevelActionScore;

    private DataBase () {
        this.residualLife = 0;
        this.timeElapsed = 0.0;
        this.currentLevelScore = 0;
        this.totalScore = 0;
    }

    public static int getResidualLife(){
        return dataBase.residualLife;
    }

    public static double getTimeElapsed(){
        return dataBase.timeElapsed;
    }

    public static int getTotalScore(){
        return dataBase.totalScore;
    }

    public static void setResidualLife(int residualLife) {
        dataBase.residualLife = residualLife;
    }

    public static void setTimeElapsed(double timeElapsed) {
        dataBase.timeElapsed = timeElapsed;
    }

    public static void setCurrentLevelTimeScore(int score) {
        dataBase.currentLevelTimeScore = score;
    }

    public static void addCurrentLevelActionScore(int score) {
        dataBase.currentLevelActionScore += score;
    }

    public static void addTotalScore(int totalScore) {
        dataBase.totalScore += totalScore;
    }

    public static void setTotalScore(int totalScore) {
        dataBase.totalScore = totalScore;
    }

    public static int getCurrentLevelTimeScore() {
        return dataBase.currentLevelTimeScore;
    }

    public static int getCurrentLevelActionScore() {
        return dataBase.currentLevelActionScore;
    }

    public static void setCurrentLevelScore(int currentLevelScore) {
        dataBase.currentLevelScore = currentLevelScore;
    }

    public static int getCurrentLevelScore(){
        return dataBase.currentLevelScore;
    }

    public static void setCurrentLevelActionScore(int currentLevelActionScore) {
        dataBase.currentLevelActionScore = currentLevelActionScore;
    }
}
