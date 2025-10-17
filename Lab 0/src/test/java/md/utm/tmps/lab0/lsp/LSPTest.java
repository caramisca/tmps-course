package md.utm.tmps.lab0.lsp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit 5 tests for Liskov Substitution Principle demonstration
 * Tests that subclasses can be substituted for their base classes
 */
class LSPTest {
    
    private Eagle eagle;
    private Sparrow sparrow;
    private Penguin penguin;
    private BirdKeeper keeper;
    
    @BeforeEach
    void setUp() {
        eagle = new Eagle("Golden Eagle");
        sparrow = new Sparrow("House Sparrow");
        penguin = new Penguin("Emperor Penguin");
        keeper = new BirdKeeper();
    }
    
    @Test
    @DisplayName("✅ LSP - All birds can be treated as Bird objects")
    void testBirdSubstitution() {
        // All birds should work as Bird objects
        Bird[] birds = {eagle, sparrow, penguin};
        
        for (Bird bird : birds) {
            assertNotNull(bird.getName());
            assertNotNull(bird.getSpecies());
            // These should not throw exceptions (LSP compliance)
            assertDoesNotThrow(() -> bird.eat());
            assertDoesNotThrow(() -> bird.sleep());
            assertDoesNotThrow(() -> bird.makeSound());
        }
        
        System.out.println("✅ LSP Bird substitution test passed - All birds work as Bird objects");
    }
    
    @Test
    @DisplayName("✅ LSP - Flying birds implement Flyable correctly")
    void testFlyableBirds() {
        assertTrue(eagle instanceof Flyable);
        assertTrue(sparrow instanceof Flyable);
        assertFalse(penguin instanceof Flyable);
        
        // Flying birds should have proper flying capabilities
        assertEquals(6000, eagle.getMaxAltitude());
        assertEquals(80.0, eagle.getFlyingSpeed());
        
        assertEquals(100, sparrow.getMaxAltitude());
        assertEquals(24.0, sparrow.getFlyingSpeed());
        
        System.out.println("✅ LSP Flyable test passed - Flying birds implement Flyable correctly");
    }
    
    @Test
    @DisplayName("✅ LSP - BirdKeeper works with any Bird subclass")
    void testBirdKeeperSubstitution() {
        // BirdKeeper should work with any Bird without knowing the specific type
        assertDoesNotThrow(() -> keeper.careForBird(eagle));
        assertDoesNotThrow(() -> keeper.careForBird(sparrow));
        assertDoesNotThrow(() -> keeper.careForBird(penguin));
        
        // Should work with collections of mixed bird types
        List<Bird> mixedBirds = Arrays.asList(eagle, sparrow, penguin);
        assertDoesNotThrow(() -> keeper.feedAllBirds(mixedBirds));
        
        System.out.println("✅ LSP BirdKeeper test passed - Works with any Bird subclass");
    }
    
    @Test
    @DisplayName("✅ LSP - Flying operations work with Flyable birds")
    void testFlyingOperations() {
        List<Flyable> flyingBirds = Arrays.asList(eagle, sparrow);
        
        // Should work with any Flyable implementation
        for (Flyable flyableBird : flyingBirds) {
            assertDoesNotThrow(() -> keeper.makeBirdFly(flyableBird));
            assertTrue(flyableBird.getMaxAltitude() > 0);
            assertTrue(flyableBird.getFlyingSpeed() > 0);
        }
        
        assertDoesNotThrow(() -> keeper.organizeFlightTraining(flyingBirds));
        
        System.out.println("✅ LSP Flying operations test passed - Works with any Flyable bird");
    }
    
    @Test
    @DisplayName("✅ LSP - Penguin has correct non-flying behavior")
    void testPenguinSpecialBehavior() {
        // Penguin should work as a Bird
        assertDoesNotThrow(() -> keeper.careForBird(penguin));
        
        // Penguin should have its own specific behaviors
        assertDoesNotThrow(() -> penguin.swim());
        assertDoesNotThrow(() -> penguin.slide());
        assertEquals(8.0, penguin.getSwimmingSpeed());
        
        // Penguin should NOT be Flyable
        assertFalse(penguin instanceof Flyable);
        
        System.out.println("✅ LSP Penguin test passed - Non-flying bird works correctly");
    }
    
    @Test
    @DisplayName("✅ LSP - Behavioral substitution test")
    void testBehavioralSubstitution() {
        // Create an array of Bird objects
        Bird[] birds = {eagle, sparrow, penguin};
        
        // Each bird should behave correctly when called through Bird interface
        for (Bird bird : birds) {
            // These should work for all birds (LSP compliance)
            String name = bird.getName();
            String species = bird.getSpecies();
            
            assertNotNull(name);
            assertNotNull(species);
            assertFalse(name.isEmpty());
            assertFalse(species.isEmpty());
            
            // toString should work for all
            String birdString = bird.toString();
            assertTrue(birdString.contains(name));
            assertTrue(birdString.contains(species));
        }
        
        System.out.println("✅ LSP Behavioral substitution test passed - All birds behave correctly through base interface");
    }
}