package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamBuilderTest {

    // ==============================================================================
    // 1) TEAM BUILDER CREATION (testTeamBuilderCreation)
    // ==============================================================================
    @Nested
    class SimpleTeamBuilderCreationTests {
        private Builder builder;
        private Team team;

        @BeforeEach
        void setup() {
            CharacterCreator warriorCreator = new WarriorCreator();
            CharacterCreator wizardCreator = new WizardCreator();

            GameCharacter warrior = warriorCreator.createCharacter("Arthas");
            GameCharacter wizard = wizardCreator.createCharacter("Dumbledore");

            // Construction via TeamBuilder
            builder = new TeamBuilder();
            builder.setName("Magic Warriors");
            builder.setCharacter(warrior);
            builder.setCharacter(wizard);

            team = ((TeamBuilder) builder).getResult();
        }

        @Test
        void testTeamNotNull() {
            assertNotNull(team, "L'équipe ne doit pas être nulle");
        }

        @Test
        void testTeamName() {
            assertEquals("Magic Warriors", team.getName(), 
                "Le nom de l'équipe doit être 'Magic Warriors'");
        }

        @Test
        void testTeamSize() {
            assertEquals(2, team.getPlayers().size(), 
                "L'équipe doit avoir 2 membres");
        }

        @Test
        void testFirstMemberName() {
            assertEquals("Arthas", team.getPlayers().get(0).getName(), 
                "Le premier membre doit être 'Arthas'");
        }

        @Test
        void testSecondMemberName() {
            assertEquals("Dumbledore", team.getPlayers().get(1).getName(), 
                "Le second membre doit être 'Dumbledore'");
        }
    }

    // ==============================================================================
    // 2) DIRECTOR BUILDER CREATION (testDirectorBuilderCreation)
    // ==============================================================================
    @Nested
    class DirectorBuilderCreationTests {

        private GameCharacter wizard1, wizard2, wizard3;
        private GameCharacter healer1, healer2, healer3;
        private GameCharacter warrior1, warrior2, warrior3;
        private DirectorBuilder director;

        @BeforeEach
        void setup() {
            CharacterCreator wizardCreator = new WizardCreator();
            CharacterCreator healerCreator = new HealerCreator();
            CharacterCreator warriorCreator = new WarriorCreator();

            // Wizards
            wizard1 = wizardCreator.createCharacter("Albert");
            wizard2 = wizardCreator.createCharacter("Hector");
            wizard3 = wizardCreator.createCharacter("Tom");
            // Healers
            healer1 = healerCreator.createCharacter("Alice");
            healer2 = healerCreator.createCharacter("Bob");
            healer3 = healerCreator.createCharacter("Charlie");
            // Warriors
            warrior1 = warriorCreator.createCharacter("David");
            warrior2 = warriorCreator.createCharacter("Eve");
            warrior3 = warriorCreator.createCharacter("Frank");

            // Director
            director = new DirectorBuilder();
        }

        // --------------------------------------------------------------------------
        // 2.1 WizardTeam
        // --------------------------------------------------------------------------
        @Nested
        class WizardTeam {
            private Team teamWizard;

            @BeforeEach
            void setupWizardTeam() {
                Builder builderWizard = director.constructWizardTeam(
                        (Wizard) wizard1, (Wizard) wizard2, (Wizard) wizard3);
                teamWizard = ((TeamBuilder) builderWizard).getResult();
            }

            @Test
            void testTeamWizardNotNull() {
                assertNotNull(teamWizard, "L'équipe ne doit pas être nulle");
            }

            @Test
            void testTeamWizardName() {
                assertEquals("team wizard", teamWizard.getName(), 
                    "Le nom de l'équipe doit être 'team wizard'");
            }

            @Test
            void testTeamWizardSize() {
                assertEquals(3, teamWizard.getPlayers().size(), 
                    "L'équipe doit avoir 3 membres");
            }

            @Test
            void testTeamWizardMembers1() {
                assertEquals("Albert", teamWizard.getPlayers().get(0).getName(), 
                    "Le premier membre doit être 'Albert'");
            }

            @Test
            void testTeamWizardMembers2() {
                assertEquals("Hector", teamWizard.getPlayers().get(1).getName(), 
                    "Le second membre doit être 'Hector'");
            }

            @Test
            void testTeamWizardMembers3() {
                assertEquals("Tom", teamWizard.getPlayers().get(2).getName(), 
                    "Le troisième membre doit être 'Tom'");
            }
        }

        // --------------------------------------------------------------------------
        // 2.2 HealerTeam
        // --------------------------------------------------------------------------
        @Nested
        class HealerTeam {
            private Team teamHealer;

            @BeforeEach
            void setupHealerTeam() {
                Builder builderHealer = director.constructHealerTeam(
                        (Healer) healer1, (Healer) healer2, (Healer) healer3);
                teamHealer = ((TeamBuilder) builderHealer).getResult();
            }

            @Test
            void testTeamHealerNotNull() {
                assertNotNull(teamHealer, "L'équipe ne doit pas être nulle");
            }

            @Test
            void testTeamHealerName() {
                assertEquals("team healer", teamHealer.getName(), 
                    "Le nom de l'équipe doit être 'team healer'");
            }

            @Test
            void testTeamHealerSize() {
                assertEquals(3, teamHealer.getPlayers().size(), 
                    "L'équipe doit avoir 3 membres");
            }

            @Test
            void testTeamHealerMembers1() {
                assertEquals("Alice", teamHealer.getPlayers().get(0).getName(), 
                    "Le premier membre doit être 'Alice'");
            }

            @Test
            void testTeamHealerMembers2() {
                assertEquals("Bob", teamHealer.getPlayers().get(1).getName(), 
                    "Le second membre doit être 'Bob'");
            }

            @Test
            void testTeamHealerMembers3() {
                assertEquals("Charlie", teamHealer.getPlayers().get(2).getName(), 
                    "Le troisième membre doit être 'Charlie'");
            }
        }

        // --------------------------------------------------------------------------
        // 2.3 WarriorTeam
        // --------------------------------------------------------------------------
        @Nested
        class WarriorTeam {
            private Team teamWarrior;

            @BeforeEach
            void setupWarriorTeam() {
                Builder builderWarrior = director.constructWarriorTeam(
                        (Warrior) warrior1, (Warrior) warrior2, (Warrior) warrior3);
                teamWarrior = ((TeamBuilder) builderWarrior).getResult();
            }

            @Test
            void testTeamWarriorNotNull() {
                assertNotNull(teamWarrior, "L'équipe ne doit pas être nulle");
            }

            @Test
            void testTeamWarriorName() {
                assertEquals("team warrior", teamWarrior.getName(), 
                    "Le nom de l'équipe doit être 'team warrior'");
            }

            @Test
            void testTeamWarriorSize() {
                assertEquals(3, teamWarrior.getPlayers().size(), 
                    "L'équipe doit avoir 3 membres");
            }

            @Test
            void testTeamWarriorMembers1() {
                assertEquals("David", teamWarrior.getPlayers().get(0).getName(),
                    "Le premier membre doit être 'David'");
            }

            @Test
            void testTeamWarriorMembers2() {
                assertEquals("Eve", teamWarrior.getPlayers().get(1).getName(),
                    "Le second membre doit être 'Eve'");
            }

            @Test
            void testTeamWarriorMembers3() {
                assertEquals("Frank", teamWarrior.getPlayers().get(2).getName(),
                    "Le troisième membre doit être 'Frank'");
            }
        }

        // --------------------------------------------------------------------------
        // 2.4 MixedTeam
        // --------------------------------------------------------------------------
        @Nested
        class MixedTeam {
            private Team teamMixed;

            @BeforeEach
            void setupMixedTeam() {
                Builder builderMixed = director.constructMixedTeam(
                    (Wizard) wizard1, (Healer) healer1, (Warrior) warrior1);
                teamMixed = ((TeamBuilder) builderMixed).getResult();
            }

            @Test
            void testTeamMixedNotNull() {
                assertNotNull(teamMixed, "L'équipe ne doit pas être nulle");
            }

            @Test
            void testTeamMixedName() {
                assertEquals("mixed team", teamMixed.getName(), 
                    "Le nom de l'équipe doit être 'mixed team'");
            }

            @Test
            void testTeamMixedSize() {
                assertEquals(3, teamMixed.getPlayers().size(), 
                    "L'équipe doit avoir 3 membres");
            }

            @Test
            void testTeamMixedMembers1() {
                assertEquals("Albert", teamMixed.getPlayers().get(0).getName(),
                    "Le premier membre doit être 'Albert'");
            }

            @Test
            void testTeamMixedMembers2() {
                assertEquals("Alice", teamMixed.getPlayers().get(1).getName(),
                    "Le second membre doit être 'Alice'");
            }

            @Test
            void testTeamMixedMembers3() {
                assertEquals("David", teamMixed.getPlayers().get(2).getName(),
                    "Le troisième membre doit être 'David'");
            }
        }
    }
}
