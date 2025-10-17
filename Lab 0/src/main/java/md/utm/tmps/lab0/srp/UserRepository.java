package md.utm.tmps.lab0.srp;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository class - Responsible ONLY for user data persistence/storage
 * This follows SRP because it has only one responsibility: managing user storage
 */
public class UserRepository {
    private List<User> users;
    
    public UserRepository() {
        this.users = new ArrayList<>();
    }
    
    /**
     * Saves a user to the repository
     * @param user the user to save
     * @return true if saved successfully
     */
    public boolean save(User user) {
        if (user != null) {
            users.add(user);
            return true;
        }
        return false;
    }
    
    /**
     * Finds a user by email
     * @param email the email to search for
     * @return the user if found, null otherwise
     */
    public User findByEmail(String email) {
        return users.stream()
                   .filter(user -> user.getEmail().equals(email))
                   .findFirst()
                   .orElse(null);
    }
    
    /**
     * Gets all users
     * @return list of all users
     */
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
    
    /**
     * Gets the count of stored users
     * @return number of users in repository
     */
    public int getUserCount() {
        return users.size();
    }
    
    /**
     * Removes all users from repository
     */
    public void clear() {
        users.clear();
    }
}