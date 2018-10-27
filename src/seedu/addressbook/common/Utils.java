package seedu.addressbook.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods
 */
public class Utils {

    /**
     * Checks whether any of the given items are null.
     */
    public static boolean isAnyNull(Object... items) {
        for (Object item : items) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a given string is a valid date.
     */
    public static boolean isValidDate(String value) {
        boolean valid;
        final String format = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            String parsedDate = LocalDate.parse(value, formatter).format(formatter);
            valid = value.equals(parsedDate);
        } catch (DateTimeParseException ex) {
            valid = false;
        }
        return valid;
    }
}
