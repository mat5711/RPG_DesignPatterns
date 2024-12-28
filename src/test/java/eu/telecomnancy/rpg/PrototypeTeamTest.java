package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PrototypeTeamTest {

    @Test
    public void testTeamCloning() {
        // Création des CharacterCreators
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();
        CharacterCreator healerCreator = new HealerCreator();

        // Création de l'équipe prototype
        Team prototypeTeam = new Team("Prototype Team");
        prototypeTeam.addPlayer(warriorCreator.createCharacter("Prototype Warrior"));
        prototypeTeam.addPlayer(wizardCreator.createCharacter("Prototype Wizard"));
        prototypeTeam.addPlayer(healerCreator.createCharacter("Prototype Healer"));

        // Clonage de l'équipe prototype
        Team clonedTeam = prototypeTeam.clone();

        // Vérifications
        assertNotNull(clonedTeam, "L'équipe clonée ne doit pas être nulle");
        assertNotSame(prototypeTeam, clonedTeam, "L'équipe clonée doit être une nouvelle instance");
        assertEquals(prototypeTeam.getPlayers().size(), clonedTeam.getPlayers().size(), "Les équipes doivent avoir le même nombre de membres");

        // Vérification du clonage profond
        for (int i = 0; i < prototypeTeam.getPlayers().size(); i++) {
            GameCharacter originalMember = prototypeTeam.getPlayers().get(i);
            GameCharacter clonedMember = clonedTeam.getPlayers().get(i);

            assertNotSame(originalMember, clonedMember, "Les membres doivent être des instances différentes");
            assertEquals(originalMember.getName(), clonedMember.getName(), "Les noms des membres doivent être identiques");
            assertEquals(originalMember.getHealth(), clonedMember.getHealth(), "Les points de vie doivent être identiques");
            assertEquals(originalMember.getClass(), clonedMember.getClass(), "Les classes des membres doivent être identiques");
        }

        // Modification du membre cloné pour vérifier l'indépendance
        clonedTeam.getPlayers().get(0).setHealth(250);
        assertNotEquals(prototypeTeam.getPlayers().get(0).getHealth(), clonedTeam.getPlayers().get(0).getHealth(), "Les modifications sur le clone ne doivent pas affecter l'original");
    }
}
