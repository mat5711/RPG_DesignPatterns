package eu.telecomnancy.rpg;

public class GameConfiguration {
    private static GameConfiguration instance;
    int difficulty;
    int sizeMax;

    //pattern singleton, d'où le constructeur privé
    private GameConfiguration() {
        this.difficulty = 1;
        this.sizeMax = 10;
    }


    public static GameConfiguration getInstance() {
        if (instance == null) { //Attention : ne pas mettre ici this.instance car le instance est static donc on peut y accéder de n'importe où, donc il ne faut pas mettre this ici
            instance = new GameConfiguration();
        }
        return instance;
    }


    //On construit les getter :
    public int getDifficulty() {
        return this.difficulty;
    }

    public int getSizeMax() {
        return this.sizeMax;
    }

    //On construit les setter :
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setSizeMax(int sizeMax) {
        this.sizeMax = sizeMax;
    }
}
