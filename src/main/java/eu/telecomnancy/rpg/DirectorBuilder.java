package eu.telecomnancy.rpg;

public class DirectorBuilder {
    
    //On définit dans le DirectorBuilder, des équipes types

    //voici un exemple
    public void constructWizardTeam(Builder builder, Wizard wizard1, Wizard wizard2, Wizard wizard3){
        builder.setName("team wizard");
        builder.setCharacter(wizard1);
        builder.setCharacter(wizard2);
        builder.setCharacter(wizard3);
    }

    //Ensuite si on veut créer cette team, dans le main :
    //Builder builder = new TeamBuilder();
    //DirectorWizard director = new DirectorWizard();
    //director.constructWizardTeam(builder, wizard1, wizard2, wizard3);
    //(on a bien sûr créer wizard 1,2,3)
    //Team teamWizard = builder.getResult();
}
