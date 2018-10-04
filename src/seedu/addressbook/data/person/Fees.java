package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;


/**
 * Represents a Person's fees due in the address book.
 * Guarantees: immutable;
 */
public class Fees implements Printable {
    public static final String EXAMPLE = "$3560.98";
    public static final String MESSAGE_FEES_CONSTRAINTS = "Person's fees should have 2 decimal places.";
    //public static final String FEES_VALIDATION_REGEX = "^(?=.*\\d)\\d*(?:\\.\\d\\d)?$";
    public static final String FEES_VALIDATION_REGEX = "[0-9]+([,.][0-9]{1,2})?";

    public final String value;

    /**
     * Validates given Fees.
     *
     * @throws IllegalValueException if given fees string is invalid.
     */
    public Fees(String fees) throws IllegalValueException {
        if (!isValidFees(fees)) {
            throw new IllegalValueException(MESSAGE_FEES_CONSTRAINTS);
        }
        this.value = fees;
    }

    /**
     * Initialises all fees of everyone to 0.
     */
    public Fees() {
        this.value = "0.00";
    }

    /**
     * Checks if a given string is a valid person fee.
     *
     */

    public static boolean isValidFees(String test) {
        return test.matches(FEES_VALIDATION_REGEX);
    }

    /*@Override
    public String toString() {
        return Float.toString(value);
    }*/

    public boolean isPrivate() {
        return true;
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Fees: " + value + "}";
            } else {
                return "";
            }
        }
        return "Fees: " + value;
    }
}
