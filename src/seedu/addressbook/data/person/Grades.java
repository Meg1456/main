package seedu.addressbook.data.person;

import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;
/**
 * Represents student's value.s
 */
public class Grades implements Printable {

    public static final String GRADE_EXAMPLE = "27";

    public static final String GRADE_CONSTRAINTS = "May include up two decimal places only";
    public static final String GRADE_VALIDATION_REGEX = "\\d+(\\.\\d+)?";

    private int value;

    private Person person;

    /**
     * Validates given results.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    /*public Grades(int grade) throws IllegalValueException {

        this.value = grade;
    } */

    public Grades(int grade) {

        this.value = grade;
    }

    /**
     * Checks if a given string is a valid marks scored.
     */
    public static boolean isValidGrades(String value) {
        return value.matches(GRADE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return " Grade: " + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.value == (((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(value);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "Grade: " + value;
    }

    public int getValue() {
        return value;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
