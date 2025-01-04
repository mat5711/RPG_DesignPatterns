package eu.telecomnancy.rpg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvicibleDecoratorTest {

    @Test
    void testReceiveAttack() {
        // Arrange
        GameCharacter character = new Healer("Hector");
        InvincibleDecorator decorator = new InvincibleDecorator(character);
        
        // Act
        decorator.receiveAttack(1000);
        
        // Assert
        assertEquals(1, decorator.getHealth());
    }
}