package md.utm.tmps.lab0.srp;

/**
 * Single Responsibility Principle (SRP) Demonstration
 * 
 * PRINCIPLE: A class should have only one reason to change.
 * Each class should have only one responsibility or job.
 * 
 * BAD EXAMPLE: A class that handles both user data AND user persistence
 * GOOD EXAMPLE: Separate classes for User data, UserValidator, and UserRepository
 */

/**
 * User class - Responsible ONLY for holding user data
 * This follows SRP because it has only one responsibility: representing user data
 */
public class User {
    private String name;
    private String email;
    private int age;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("User{name='%s', email='%s', age=%d}", name, email, age);
    }
}