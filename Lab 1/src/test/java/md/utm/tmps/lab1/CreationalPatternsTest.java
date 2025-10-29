package md.utm.tmps.lab1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Explicit imports to avoid conflict between JUnit's @Order and domain Order class
import md.utm.tmps.lab1.domain.models.OrderIdGenerator;
import md.utm.tmps.lab1.domain.models.Pizza;
import md.utm.tmps.lab1.domain.models.Beverage;
import md.utm.tmps.lab1.domain.models.Coffee;
import md.utm.tmps.lab1.domain.models.Tea;
import md.utm.tmps.lab1.domain.models.Juice;
import md.utm.tmps.lab1.domain.models.Meal;
import md.utm.tmps.lab1.domain.factory.*;

/**
 * Comprehensive test suite for all Creational Design Patterns
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreationalPatternsTest {
    
    @BeforeEach
    public void setUp() {
        // Reset singleton for consistent testing
        OrderIdGenerator.getInstance().reset();
    }
    
    @Test
    @Order(1)
    @DisplayName("Singleton Pattern: Single instance across application")
    public void testSingletonPattern() {
        OrderIdGenerator instance1 = OrderIdGenerator.getInstance();
        OrderIdGenerator instance2 = OrderIdGenerator.getInstance();
        
        // Verify same instance
        assertSame(instance1, instance2, "Should return the same instance");
        
        // Verify unique ID generation
        int id1 = instance1.generateOrderId();
        int id2 = instance2.generateOrderId();
        
        assertTrue(id2 > id1, "IDs should be incrementing");
        assertEquals(id1 + 1, id2, "IDs should increment by 1");
        
        System.out.println("✓ Singleton test passed - Single instance manages all order IDs!");
    }
    
    @Test
    @Order(2)
    @DisplayName("Builder Pattern: Flexible pizza construction")
    public void testBuilderPattern() {
        // Test simple pizza
        Pizza simplePizza = new Pizza.Builder("Small", "Thin")
                .cheese(true)
                .build();
        
        assertEquals("Small", simplePizza.getSize());
        assertEquals("Thin", simplePizza.getCrustType());
        assertTrue(simplePizza.hasCheese());
        assertFalse(simplePizza.hasPepperoni());
        
        // Test complex pizza with many toppings
        Pizza complexPizza = new Pizza.Builder("Large", "Thick")
                .cheese(true)
                .pepperoni(true)
                .mushrooms(true)
                .olives(true)
                .bacon(true)
                .onions(true)
                .addExtraTopping("Pineapple")
                .addExtraTopping("Jalapeños")
                .build();
        
        assertEquals("Large", complexPizza.getSize());
        assertTrue(complexPizza.hasCheese());
        assertTrue(complexPizza.hasPepperoni());
        assertTrue(complexPizza.hasMushrooms());
        assertTrue(complexPizza.hasOlives());
        assertTrue(complexPizza.hasBacon());
        assertTrue(complexPizza.hasOnions());
        assertEquals(2, complexPizza.getExtraToppings().size());
        assertTrue(complexPizza.getExtraToppings().contains("Pineapple"));
        
        System.out.println("✓ Builder test passed - Pizzas built with flexible configuration!");
    }
    
    @Test
    @Order(3)
    @DisplayName("Factory Method Pattern: Beverage creation")
    public void testFactoryMethodPattern() {
        // Test coffee creation
        Beverage coffee = BeverageFactory.createCoffee("Medium", "Espresso");
        assertNotNull(coffee);
        assertTrue(coffee instanceof Coffee);
        assertEquals("Coffee", coffee.getName());
        assertEquals("Medium", coffee.getSize());
        
        // Test tea creation
        Beverage tea = BeverageFactory.createTea("Large", "Green");
        assertNotNull(tea);
        assertTrue(tea instanceof Tea);
        assertEquals("Tea", tea.getName());
        
        // Test juice creation
        Beverage juice = BeverageFactory.createJuice("Small", "Orange");
        assertNotNull(juice);
        assertTrue(juice instanceof Juice);
        assertEquals("Juice", juice.getName());
        
        // Test generic factory method
        Beverage genericCoffee = BeverageFactory.createBeverage("coffee", "Large", "Latte");
        assertNotNull(genericCoffee);
        assertTrue(genericCoffee instanceof Coffee);
        
        // Test invalid type
        assertThrows(IllegalArgumentException.class, () -> {
            BeverageFactory.createBeverage("soda", "Medium", "Cola");
        });
        
        System.out.println("✓ Factory Method test passed - Beverages created without exposing logic!");
    }
    
    @Test
    @Order(4)
    @DisplayName("Abstract Factory Pattern: Meal family creation")
    public void testAbstractFactoryPattern() {
        // Test Vegetarian Meal Factory
        MealFactory vegFactory = new VegetarianMealFactory();
        Meal vegMeal = vegFactory.createMeal();
        
        assertNotNull(vegMeal);
        assertEquals("Vegetarian", vegMeal.getMealType());
        assertNotNull(vegMeal.getPizza());
        assertNotNull(vegMeal.getBeverage());
        assertTrue(vegMeal.getPizza().hasOnions());
        assertTrue(vegMeal.getBeverage() instanceof Juice);
        
        // Test Meat Lovers Meal Factory
        MealFactory meatFactory = new MeatLoversMealFactory();
        Meal meatMeal = meatFactory.createMeal();
        
        assertNotNull(meatMeal);
        assertEquals("Meat Lovers", meatMeal.getMealType());
        assertTrue(meatMeal.getPizza().hasPepperoni());
        assertTrue(meatMeal.getPizza().hasBacon());
        assertTrue(meatMeal.getBeverage() instanceof Coffee);
        
        // Test Italian Meal Factory
        MealFactory italianFactory = new ItalianMealFactory();
        Meal italianMeal = italianFactory.createMeal();
        
        assertNotNull(italianMeal);
        assertEquals("Italian Classic", italianMeal.getMealType());
        assertNotNull(italianMeal.getPizza());
        assertNotNull(italianMeal.getBeverage());
        
        System.out.println("✓ Abstract Factory test passed - Meal families created consistently!");
    }
    
    @Test
    @Order(5)
    @DisplayName("Integration: All patterns working together")
    public void testIntegration() {
        // Create order (uses Singleton for ID)
        md.utm.tmps.lab1.domain.models.Order order = new md.utm.tmps.lab1.domain.models.Order();
        assertTrue(order.getOrderId() >= 1000);
        
        // Add pizza (uses Builder)
        Pizza pizza = new Pizza.Builder("Medium", "Regular")
                .cheese(true)
                .pepperoni(true)
                .build();
        order.addPizza(pizza);
        
        // Add beverage (uses Factory Method)
        Beverage beverage = BeverageFactory.createCoffee("Large", "Cappuccino");
        order.addBeverage(beverage);
        
        // Add meal (uses Abstract Factory)
        MealFactory factory = new VegetarianMealFactory();
        Meal meal = factory.createMeal();
        order.addMeal(meal);
        
        // Verify order contents
        assertEquals(1, order.getPizzas().size());
        assertEquals(1, order.getBeverages().size());
        assertEquals(1, order.getMeals().size());
        
        // Verify total calculation
        double total = order.calculateTotal();
        assertTrue(total > 0, "Order total should be positive");
        
        System.out.println("✓ Integration test passed - All patterns work together seamlessly!");
    }
    
    @Test
    @Order(6)
    @DisplayName("Thread Safety: Singleton in concurrent environment")
    public void testSingletonThreadSafety() throws InterruptedException {
        final OrderIdGenerator[] instances = new OrderIdGenerator[10];
        Thread[] threads = new Thread[10];
        
        // Create multiple threads accessing singleton
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = OrderIdGenerator.getInstance();
            });
            threads[i].start();
        }
        
        // Wait for all threads
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Verify all threads got the same instance
        for (int i = 1; i < instances.length; i++) {
            assertSame(instances[0], instances[i], 
                    "All threads should get the same singleton instance");
        }
        
        System.out.println("✓ Thread safety test passed - Singleton is thread-safe!");
    }
}
