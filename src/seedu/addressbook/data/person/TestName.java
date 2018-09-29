package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Student's in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTestName(String)}
 */

public class TestName implements Printable {

    public static final String EXAMPLE = "Maths final";
    public static final String MESSAGE_TESTNAME_CONSTRAINTS = "The name of test can be spaces or alphanumeric characters";
    public static final String TESTNAME_VALIDATION_REGEX = "test1";

    public final String value;
    private boolean isPrivate;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */

    public TestName(String testName, boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;
        String trimmedTestName = testName.trim();
        if (!isValidTestName(trimmedTestName)) {
            throw new IllegalValueException(MESSAGE_TESTNAME_CONSTRAINTS);
        }
        this.value = trimmedTestName;
    }

    /**
     * Checks if a given string is a valid person phone number.
     */
    public static boolean isValidTestName (String test) {
        return test.matches(TESTNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof TestName // instanceof handles nulls
                    && this.value.equals(((TestName) other).value)); // state check
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
                return "{private Test Name: " + value + "}";
            } else {
                return "";
            }
        }
        return "Test Name: " + value;
    }
}
