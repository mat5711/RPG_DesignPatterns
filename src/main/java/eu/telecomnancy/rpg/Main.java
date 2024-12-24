package eu.telecomnancy.rpg;

public class Main {
    public static void main(String[] args) {
        
        //on définit notre game
        GameConfiguration game = GameConfiguration.getInstance();
        GameConfiguration game2 = GameConfiguration.getInstance();
        game.setDifficulty(30);
        game2.setDifficulty(40);
        System.out.println(game.getDifficulty());
        System.out.println(game2.getDifficulty());

        // Attention ce n'est pas la bonne méthode pour créer un personnage, on n'utilise pas les design patterns
        GameCharacter healer = new Healer("John");

        // On utilise notre FactoryMethod pour créer nos personnage : 
        CharacterCreator healerCreator = new HealerCreator(); //on n'a pas défini de constructeur dans la classe HealerCreator, on utilise celui par défaut
        GameCharacter healer1 = healerCreator.createCharacter("healer1Name");
        GameCharacter healer2 = healerCreator.createCharacter("healer2Name");
        //Donc on a besoin que d'un seul healerCreator pour créer autant de healer qu'on veut
        // et pareil pour les autres types évidemment
        //Donc avec la factory method, on peut créer des personnages de manière plus simple et plus rapide,
        //car on n'a pas besoin de faire de new ..., juste on appelle la méthode et le new est fait dans la méthode

    }
}
