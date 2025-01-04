package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterVisitorTest {

    // ---------------------------------------------
    // 1) BuffVisitor
    // ---------------------------------------------
    @Nested
    class BuffVisitorTests {
        private Warrior warrior;
        private Wizard wizard;
        private Healer healer;
        private CharacterVisitor buff;

        @BeforeEach
        void setup() {
            warrior = new Warrior("TestWarrior"); // strength=7
            wizard = new Wizard("TestWizard");     // intelligence=5
            healer = new Healer("TestHealer");     // wisdom=3

            buff = new BuffVisitor();
            // On applique le buff
            warrior.accept(buff);
            wizard.accept(buff);
            healer.accept(buff);
        }

        @Test
        void testWarriorStrengthAfterBuff() {
            assertEquals(15, warrior.getStrength(), "La force du Warrior doit augmenter de 8");
        }

        @Test
        void testWizardIntelligenceAfterBuff() {
            assertEquals(13, wizard.getIntelligence(), "L'intelligence du Wizard doit augmenter de 8");
        }

        @Test
        void testHealerWisdomAfterBuff() {
            assertEquals(11, healer.getWisdom(), "La sagesse du Healer doit augmenter de 8");
        }
    }

    // ---------------------------------------------
    // 2) DamageVisitor
    // ---------------------------------------------
    @Nested
    class DamageVisitorTests {
        private Warrior warrior;
        private Wizard wizard;
        private Healer healer;
        private CharacterVisitor damage;

        @BeforeEach
        void setup() {
            warrior = new Warrior("TestWarrior"); // health=30
            wizard = new Wizard("TestWizard");     // health=40
            healer = new Healer("TestHealer");     // health=50

            damage = new DamageVisitor();
            // On applique le damage 1 fois
            warrior.accept(damage); // => 22
            wizard.accept(damage);  // => 32
            healer.accept(damage);  // => 42
        }

        @Test
        void testWarriorHealthAfterOneDamage() {
            assertEquals(22, warrior.getHealth(), "Le Warrior doit perdre 8 HP");
        }

        @Test
        void testWizardHealthAfterOneDamage() {
            assertEquals(32, wizard.getHealth(), "Le Wizard doit perdre 8 HP");
        }

        @Test
        void testHealerHealthAfterOneDamage() {
            assertEquals(42, healer.getHealth(), "Le Healer doit perdre 8 HP");
        }
    }

    // ---------------------------------------------
    // 3) DamageVisitorUnderZero
    // ---------------------------------------------
    @Nested
    class DamageVisitorUnderZeroTests {
        private Warrior warrior;
        private Wizard wizard;
        private Healer healer;
        private CharacterVisitor damage;

        @BeforeEach
        void setup() {
            warrior = new Warrior("TestWarrior"); // HP=30
            wizard = new Wizard("TestWizard");     // HP=40
            healer = new Healer("TestHealer");     // HP=50

            damage = new DamageVisitor();

            // On applique plusieurs fois, jusqu’à descendre < 1
            // Warrior : 4 fois => 30->22->14->6->1
            warrior.accept(damage);
            warrior.accept(damage);
            warrior.accept(damage);
            warrior.accept(damage);

            // Wizard : 5 fois => 40->32->24->16->8->1
            wizard.accept(damage);
            wizard.accept(damage);
            wizard.accept(damage);
            wizard.accept(damage);
            wizard.accept(damage);

            // Healer : 8 fois => 50->42->34->26->18->10->2->1->1
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
            healer.accept(damage);
        }

        @Test
        void testWarriorHPnotBelow1() {
            assertEquals(1, warrior.getHealth(),
                "En dessous de 0, les HP du Warrior doivent être maintenus à 1");
        }

        @Test
        void testWizardHPnotBelow1() {
            assertEquals(1, wizard.getHealth(),
                "En dessous de 0, les HP du Wizard doivent être maintenus à 1");
        }

        @Test
        void testHealerHPnotBelow1() {
            assertEquals(1, healer.getHealth(),
                "En dessous de 0, les HP du Healer doivent être maintenus à 1");
        }
    }

    // ---------------------------------------------
    // 4) HealVisitor
    // ---------------------------------------------
    @Nested
    class HealVisitorTests {
        private Warrior warrior;
        private Wizard wizard;
        private Healer healer;
        private CharacterVisitor heal;

        @BeforeEach
        void setup() {
            warrior = new Warrior("TestWarrior");
            wizard = new Wizard("TestWizard");
            healer = new Healer("TestHealer");

            // On modifie leurs HP avant de healer
            warrior.setHealth(80);
            wizard.setHealth(80);
            healer.setHealth(80);

            heal = new HealVisitor();
            warrior.accept(heal); // 80->88
            wizard.accept(heal);  // 80->88
            healer.accept(heal);  // 80->88
        }

        @Test
        void testWarriorHeal() {
            assertEquals(88, warrior.getHealth(), "Le Warrior doit gagner 8 HP");
        }

        @Test
        void testWizardHeal() {
            assertEquals(88, wizard.getHealth(), "Le Wizard doit gagner 8 HP");
        }

        @Test
        void testHealerHeal() {
            assertEquals(88, healer.getHealth(), "Le Healer doit gagner 8 HP");
        }
    }

    // ---------------------------------------------
    // 5) ConsecutiveVisitors
    // ---------------------------------------------
    @Nested
    class ConsecutiveVisitorsTests {

        @Nested
        class WarriorConsecutiveVisitor {
            private Warrior warrior;
            private CharacterVisitor buff;
            private CharacterVisitor damage;
            private CharacterVisitor heal;

            @BeforeEach
            void setup() {
                warrior = new Warrior("TestWarrior"); // HP=30, strength=7
                buff = new BuffVisitor();
                damage = new DamageVisitor();
                heal = new HealVisitor();

                // buff => +8 strength => 15
                warrior.accept(buff);
                // damage => -8 HP => 22
                warrior.accept(damage);
                // heal => +8 HP => 30
                warrior.accept(heal);
            }

            @Test
            void testWarriorHealthAfterSequence() {
                assertEquals(30, warrior.getHealth(),
                    "HP après buff, damage, heal doit revenir à 30");
            }

            @Test
            void testWarriorStrengthAfterSequence() {
                assertEquals(15, warrior.getStrength(),
                    "La force après buff doit être de 15");
            }
        }

        @Nested
        class WizardConsecutiveVisitor {
            private Wizard wizard;
            private CharacterVisitor buff;
            private CharacterVisitor damage;
            private CharacterVisitor heal;

            @BeforeEach
            void setup() {
                wizard = new Wizard("TestWizard"); // HP=40, intelligence=5
                buff = new BuffVisitor();
                damage = new DamageVisitor();
                heal = new HealVisitor();

                // buff => +8 => intelligence=13
                wizard.accept(buff);
                // damage => -8 HP => 32
                wizard.accept(damage);
                // damage => -8 HP => 24
                wizard.accept(damage);
                // heal => +8 => 32
                wizard.accept(heal);
            }

            @Test
            void testWizardHealthAfterSequence() {
                assertEquals(32, wizard.getHealth(),
                    "Le Wizard doit finir à 32 HP après buffs/damage/heal");
            }

            @Test
            void testWizardIntelligenceAfterSequence() {
                assertEquals(13, wizard.getIntelligence(),
                    "L'intelligence doit être 13 après buff");
            }
        }

        @Nested
        class HealerConsecutiveVisitor {
            private Healer healer;
            private CharacterVisitor buff;
            private CharacterVisitor damage;
            private CharacterVisitor heal;

            @BeforeEach
            void setup() {
                healer = new Healer("TestHealer"); // HP=50, wisdom=3
                buff = new BuffVisitor();
                damage = new DamageVisitor();
                heal = new HealVisitor();

                // buff => wisdom=11
                healer.accept(buff);
                // re-buff => wisdom=19
                healer.accept(buff);
                // damage => -8 HP => 42
                healer.accept(damage);
                // heal => +8 HP => 50
                healer.accept(heal);
            }

            @Test
            void testHealerHealthAfterSequence() {
                assertEquals(50, healer.getHealth(),
                    "Le Healer doit finir à 50 HP");
            }

            @Test
            void testHealerWisdomAfterSequence() {
                assertEquals(19, healer.getWisdom(),
                    "La sagesse doit être 19 après le buff");
            }
        }
    }

}
