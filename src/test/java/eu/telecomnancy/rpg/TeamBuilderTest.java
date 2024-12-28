package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TeamBuilderTest {

    @Test
    public void testTeamBuilderCreation() {
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();

        GameCharacter warrior = warriorCreator.createCharacter("Arthas");
        GameCharacter wizard = wizardCreator.createCharacter("Dumbledore");

        Builder builder = new TeamBuilder();
        builder.setName("Magic Warriors");
        builder.setCharacter(warrior);
        builder.setCharacter(wizard);
        
        // Création de l'équipe
        Team team = ((TeamBuilder) builder).getResult();


        assertNotNull(team, "L'équipe ne doit pas être nulle");
        assertEquals("Magic Warriors", team.getName(), "Le nom de l'équipe doit être 'Magic Warriors'");
        assertEquals(2, team.getPlayers().size(), "L'équipe doit avoir 2 membres");
        assertEquals("Arthas", team.getPlayers().get(0).getName(), "Le premier membre doit être 'Arthas'");
        assertEquals("Dumbledore", team.getPlayers().get(1).getName(), "Le second membre doit être 'Dumbledore'");
    }

    @Test
    public void testDirectorBuilderCreation() {
        CharacterCreator wizardCreator = new WizardCreator();
        CharacterCreator healerCreator = new HealerCreator();
        CharacterCreator warriorCreator = new WarriorCreator();

        GameCharacter wizard1 = wizardCreator.createCharacter("Albert");
        GameCharacter wizard2 = wizardCreator.createCharacter("Hector");
        GameCharacter wizard3 = wizardCreator.createCharacter("Tom");

        GameCharacter healer1 = healerCreator.createCharacter("Alice");
        GameCharacter healer2 = healerCreator.createCharacter("Bob");
        GameCharacter healer3 = healerCreator.createCharacter("Charlie");

        GameCharacter warrior1 = warriorCreator.createCharacter("David");
        GameCharacter warrior2 = warriorCreator.createCharacter("Eve");
        GameCharacter warrior3 = warriorCreator.createCharacter("Frank");

        DirectorBuilder director = new DirectorBuilder();

        Builder builder1 = director.constructWizardTeam((Wizard) wizard1, (Wizard) wizard2, (Wizard) wizard3);
        Team teamWizard = ((TeamBuilder) builder1).getResult();


        assertNotNull(teamWizard, "L'équipe ne doit pas être nulle");
        assertEquals("team wizard", teamWizard.getName(), "Le nom de l'équipe doit être 'team wizard'");
        assertEquals(3, teamWizard.getPlayers().size(), "L'équipe doit avoir 3 membres");
        assertEquals("Albert", teamWizard.getPlayers().get(0).getName(), "Le premier membre doit être 'Albert'");
        assertEquals("Hector", teamWizard.getPlayers().get(1).getName(), "Le second membre doit être 'Hector'");
        assertEquals("Tom", teamWizard.getPlayers().get(2).getName(), "Le second membre doit être 'Tom'");


        Builder builder2 = director.constructHealerTeam((Healer) healer1, (Healer) healer2, (Healer) healer3);
        Team teamHealer = ((TeamBuilder) builder2).getResult();

        assertNotNull(teamHealer, "L'équipe ne doit pas être nulle");
        assertEquals("team healer", teamHealer.getName(), "Le nom de l'équipe doit être 'team healer'");
        assertEquals(3, teamHealer.getPlayers().size(), "L'équipe doit avoir 3 membres");
        assertEquals("Alice", teamHealer.getPlayers().get(0).getName(), "Le premier membre doit être 'Alice'");
        assertEquals("Bob", teamHealer.getPlayers().get(1).getName(), "Le second membre doit être 'Bob'");
        assertEquals("Charlie", teamHealer.getPlayers().get(2).getName(), "Le second membre doit être 'Charlie'");

        Builder builder3 = director.constructWarriorTeam((Warrior) warrior1, (Warrior) warrior2, (Warrior) warrior3);
        Team teamWarrior = ((TeamBuilder) builder3).getResult();
        
        assertNotNull(teamWarrior, "L'équipe ne doit pas être nulle");
        assertEquals("team warrior", teamWarrior.getName(), "Le nom de l'équipe doit être 'team warrior'");
        assertEquals(3, teamWarrior.getPlayers().size(), "L'équipe doit avoir 3 membres");
        assertEquals("David", teamWarrior.getPlayers().get(0).getName(), "Le premier membre doit être 'David'");
        assertEquals("Eve", teamWarrior.getPlayers().get(1).getName(), "Le second membre doit être 'Eve'");
        assertEquals("Frank", teamWarrior.getPlayers().get(2).getName(), "Le second membre doit être 'Frank'");

        Builder builder4 = director.constructMixedTeam((Wizard) wizard1, (Healer) healer1, (Warrior) warrior1);
        Team teamMixed = ((TeamBuilder) builder4).getResult();

        assertNotNull(teamMixed, "L'équipe ne doit pas être nulle");
        assertEquals("mixed team", teamMixed.getName(), "Le nom de l'équipe doit être 'mixed team'");
        assertEquals(3, teamMixed.getPlayers().size(), "L'équipe doit avoir 3 membres");
        assertEquals("Albert", teamMixed.getPlayers().get(0).getName(), "Le premier membre doit être 'Albert'");
        assertEquals("Alice", teamMixed.getPlayers().get(1).getName(), "Le second membre doit être 'Alice'");
        assertEquals("David", teamMixed.getPlayers().get(2).getName(), "Le second membre doit être 'David'");
        
    }
}
