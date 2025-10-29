package md.utm.tmps.lab0.srp;

/**
 * UserValidator class - Responsible ONLY for validating user data
 * This follows SRP because it has only one responsibility: validation logic
 */
public class UserValidator {

    public boolean isValid(User user) {
        return isNameValid(user.getName()) && 
               isEmailValid(user.getEmail()) && 
               isAgeValid(user.getAge());
    }
    
    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }
    
    private boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    private boolean isAgeValid(int age) {
        return age > 0 && age < 150;
    }
    

    public String getValidationError(User user) {
        if (!isNameValid(user.getName())) {
            return "Name must be at least 2 characters long";
        }
        if (!isEmailValid(user.getEmail())) {
            return "Email must contain @ and . symbols";
        }
        if (!isAgeValid(user.getAge())) {
            return "Age must be between 1 and 149";
        }
        return "";
    }
}