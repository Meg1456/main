package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrades(String)}
 */

public class Grades implements Printable {

    public static final String EXAMPLE = "95/100";
    public static final String MESSAGE_PHONE_CONSTRAINTS = "The Grades can be digits out of total. No spaces";
    public static final String PHONE_VALIDATION_REGEX = "test1";

    public final String value;
    private boolean isPrivate;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */

    public Grades(String grade, boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;
        String trimmedGrades = grade.trim();
        if (!isValidGrades(trimmedGrades)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedGrades;
    }


    /**
     * Checks if a given string is a valid person phone number.
     */
    public static boolean isValidGrades (String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.value.equals(((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Grades: " + value + "}";
            } else {
                return "";
            }
        }
        return "Grades: " + value;
    }
}