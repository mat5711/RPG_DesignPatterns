package eu.telecomnancy.rpg;

public class Main {
    public static void main(String[] args) {
        GameConfiguration config1 = GameConfiguration.getInstance();
        GameConfiguration config2 = GameConfiguration.getInstance();

        GameConfiguration config = GameConfiguration.getInstance();
        config.setDifficulty(2);
        System.out.println(config1 == config);
        System.out.println(config.getDifficulty());
        System.out.println(config1.getDifficulty());

    

    }
}
