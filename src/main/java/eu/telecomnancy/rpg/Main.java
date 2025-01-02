package eu.telecomnancy.rpg;

public class Main {
    public static void main(String[] args) {

        GameCharacter healer = new Healer("John");
        GameCharacter healer2 = healer.clone();
        System.out.println(healer2.getName());

        healer2.setHealth(5);
        healer2.addExperiencePoints(1000);


        Warrior attaquant = new Warrior("méchant");

        GameCharacter armoredDecorator = new ArmoredDecorator(healer2, 0.4);
        armoredDecorator = new InvicibleDecorator(armoredDecorator);

        healer2.addExperiencePoints(1000);


        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());


        armoredDecorator.receiveAttack(5);
        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());


        attaquant.attack(armoredDecorator);
        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());


        attaquant.attack(armoredDecorator);
        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());

        armoredDecorator = Decorator.unwrapSpecificDecorator(armoredDecorator, ArmoredDecorator.class);


        attaquant.attack(armoredDecorator);
        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());
        System.out.println(armoredDecorator instanceof GameCharacter);
        System.out.println(armoredDecorator instanceof Decorator);
        System.out.println(armoredDecorator instanceof Healer);

        System.out.println("niveau : " + armoredDecorator.getLevel());


        armoredDecorator = Decorator.unwrapSpecificDecorator(armoredDecorator, InvicibleDecorator.class);
        attaquant.attack(armoredDecorator);
        System.out.println("santé armoredDecorator : " + armoredDecorator.getHealth());
        System.out.println("santé healer 2 : " + healer2.getHealth());
        System.out.println(armoredDecorator instanceof GameCharacter);
        System.out.println(armoredDecorator instanceof Decorator);
        System.out.println(armoredDecorator instanceof Healer);

        System.out.println("niveau : " + armoredDecorator.getLevel());
    }
}
