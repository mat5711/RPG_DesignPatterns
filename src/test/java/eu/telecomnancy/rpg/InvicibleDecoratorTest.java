package eu.telecomnancy.rpg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvicibleDecoratorTest {

    @Test
    void testReceiveAttack() {
        // Arrange
        GameCharacter character = new Healer("Hector");
        InvicibleDecorator decorator = new InvicibleDecorator(character);
        
        // Act
        decorator.receiveAttack(10);
        
        // Assert
        assertEquals(1, decorator.getHealth());
    }
}