package eu.telecomnancy.rpg;

public class DirectorBuilder {
    
    //On définit dans le DirectorBuilder, des équipes types

    //voici un exemple
    public Builder constructWizardTeam(Wizard wizard1, Wizard wizard2, Wizard wizard3){
        Builder builder = new TeamBuilder();
        builder.setName("team wizard");
        builder.setCharacter(wizard1);
        builder.setCharacter(wizard2);
        builder.setCharacter(wizard3);
        return builder;
    }


    public Builder constructHealerTeam(Healer healer1, Healer healer2, Healer healer3){
        Builder builder = new TeamBuilder();
        builder.setName("team healer");
        builder.setCharacter(healer1);
        builder.setCharacter(healer2);
        builder.setCharacter(healer3);
        return builder;
    }

    public Builder constructWarriorTeam(Warrior warrior1, Warrior warrior2, Warrior warrior3){
        Builder builder = new TeamBuilder();
        builder.setName("team warrior");
        builder.setCharacter(warrior1);
        builder.setCharacter(warrior2);
        builder.setCharacter(warrior3);
        return builder;
    }

    public Builder constructMixedTeam(Wizard wizard, Healer healer, Warrior warrior){
        Builder builder = new TeamBuilder();
        builder.setName("mixed team");
        builder.setCharacter(wizard);
        builder.setCharacter(healer);
        builder.setCharacter(warrior);
        return builder;
    }

}
