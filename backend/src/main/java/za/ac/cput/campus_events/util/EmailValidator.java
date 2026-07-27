package za.ac.cput.campus_events.util;

public class EmailValidator {
    public static boolean isValid(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
