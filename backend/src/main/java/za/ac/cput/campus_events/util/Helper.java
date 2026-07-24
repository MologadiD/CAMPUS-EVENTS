package za.ac.cput.campus_events.util;
/*
    Mologadi Dikgale
    student no: 231016263
 */

public class Helper {

    private Helper() {}

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidMobile(String mobile) {
        if (isNullOrEmpty(mobile)) return false;
        return mobile.matches("\\d{10}");
    }

    public static boolean isPositiveInt(int value) {
        return value > 0;
    }

    public static boolean isPositiveDouble(double value) {
        return value > 0;
    }

    public static boolean isPositiveLong(Long value) {
        return value != null && value > 0;
    }

    public static boolean isValidCapacity(Integer capacity) {
        return capacity != null && capacity > 0;
    }

    public static boolean isNotNull(Object value) {
        return value != null;
    }
}
