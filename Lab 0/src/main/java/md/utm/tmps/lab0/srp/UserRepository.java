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
    
    public boolean save(User user) {
        if (user != null) {
            users.add(user);
            return true;
        }
        return false;
    }
    

    public User findByEmail(String email) {
        return users.stream()
                   .filter(user -> user.getEmail().equals(email))
                   .findFirst()
                   .orElse(null);
    }
    
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
    
    
    public int getUserCount() {
        return users.size();
    }
    

    public void clear() {
        users.clear();
    }
}