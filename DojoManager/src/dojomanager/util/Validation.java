package dojomanager.util;

/**
 * Backend helper class with reusable data-validation checks.
 *
 * <p>Keeping the validation rules in one place means the interface classes
 * do not have to contain the checking logic themselves. They simply ask this
 * class whether a value is valid and then show an error message if it is not.
 * This supports defensive programming and keeps the working code separate
 * from the user interface.</p>
 *
 * <p>All methods are {@code static}, so they can be used without creating an
 * object, e.g. {@code Validation.isValidEmail(text)}.</p>
 */
public final class Validation {

    /** Private constructor: this class is only a holder of helper methods. */
    private Validation() {
    }

    /**
     * Checks that a piece of text is not empty (after removing spaces).
     *
     * @param text the text to check (may be {@code null})
     * @return {@code true} if the text contains at least one real character
     */
    public static boolean isPresent(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Checks that a name is present and only contains letters, spaces,
     * hyphens or apostrophes (so numbers/symbols are rejected).
     *
     * @param name the name to check
     * @return {@code true} if the name is valid
     */
    public static boolean isValidName(String name) {
        return isPresent(name) && name.trim().matches("[a-zA-Z][a-zA-Z '\\-]*");
    }

    /**
     * Checks that an age is within a realistic range for a dojo student.
     *
     * @param age the age in years
     * @return {@code true} if the age is between 4 and 100 (inclusive)
     */
    public static boolean isValidAge(int age) {
        return age >= 4 && age <= 100;
    }

    /**
     * Checks that a phone number is present and, once spaces are removed,
     * contains only digits and is a sensible length (10 to 13 digits).
     *
     * @param phone the phone number to check
     * @return {@code true} if the phone number is valid
     */
    public static boolean isValidPhone(String phone) {
        if (!isPresent(phone)) {
            return false;
        }
        String digits = phone.replace(" ", "");
        return digits.matches("\\d{10,13}");
    }

    /**
     * Checks that an email address has a basic valid shape, i.e. some text,
     * an {@code @} sign, more text, a dot and an ending (like ".com").
     *
     * @param email the email address to check
     * @return {@code true} if the email looks valid
     */
    public static boolean isValidEmail(String email) {
        return isPresent(email) && email.trim().matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,}");
    }
}
