package eu.telecomnancy.rpg;

public class Main {
    public static void main(String[] args) {

        GameConfiguration config = GameConfiguration.getInstance();

        System.out.println(config.getDifficulty());
        System.out.println(config.getSizeMax());

        config.setDifficulty(3);
        config.setSizeMax(6);

        System.out.println(config.getDifficulty());
        System.out.println(config.getSizeMax());

        GameConfiguration config2 = GameConfiguration.getInstance();

        System.out.println(config2.getDifficulty());
        System.out.println(config2.getSizeMax());

        config2.setSizeMax(7);
        config2.setDifficulty(2);

        System.out.println(config.getDifficulty());
        System.out.println(config.getSizeMax());
    }
}
