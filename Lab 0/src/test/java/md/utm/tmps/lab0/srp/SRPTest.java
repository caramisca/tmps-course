package md.utm.tmps.lab0.srp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for Single Responsibility Principle demonstration
 * Tests that each class has only one responsibility and works correctly
 */
class SRPTest {
    
    private UserValidator validator;
    private UserRepository repository;
    
    @BeforeEach
    void setUp() {
        validator = new UserValidator();
        repository = new UserRepository();
    }
    
    @Test
    @DisplayName("✅ SRP - User class should only handle user data")
    void testUserDataResponsibility() {
        User user = new User("John Doe", "john@example.com", 25);
        
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals(25, user.getAge());
        
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
        
        System.out.println("✅ SRP User test passed - User class correctly handles only data");
    }
    
    @Test
    @DisplayName("✅ SRP - UserValidator should only handle validation")
    void testUserValidationResponsibility() {
        User validUser = new User("John Doe", "john@example.com", 25);
        User invalidUser = new User("", "invalid-email", -5);
        
        assertTrue(validator.isValid(validUser));
        assertFalse(validator.isValid(invalidUser));
        
        String error = validator.getValidationError(invalidUser);
        assertFalse(error.isEmpty());
        assertTrue(error.contains("Name"));
        
        System.out.println("✅ SRP Validator test passed - UserValidator correctly handles only validation");
    }
    
    @Test
    @DisplayName("✅ SRP - UserRepository should only handle storage")
    void testUserStorageResponsibility() {
        User user1 = new User("John", "john@example.com", 25);
        User user2 = new User("Jane", "jane@example.com", 30);
        
        assertTrue(repository.save(user1));
        assertTrue(repository.save(user2));
        assertEquals(2, repository.getUserCount());
        
        User found = repository.findByEmail("john@example.com");
        assertNotNull(found);
        assertEquals("John", found.getName());
        
        assertEquals(2, repository.findAll().size());
        
        repository.clear();
        assertEquals(0, repository.getUserCount());
        
        System.out.println("✅ SRP Repository test passed - UserRepository correctly handles only storage");
    }
    
    @Test
    @DisplayName("✅ SRP - All classes work together without coupling")
    void testSRPIntegration() {
        User user = new User("Alice", "alice@example.com", 28);
        
        // Each class does its own job independently
        assertTrue(validator.isValid(user));
        assertTrue(repository.save(user));
        
        User retrieved = repository.findByEmail("alice@example.com");
        assertNotNull(retrieved);
        assertTrue(validator.isValid(retrieved));
        
        System.out.println("✅ SRP Integration test passed - All classes work together with proper separation of concerns");
    }
}