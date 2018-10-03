package seedu.addressbook.data.person;

//import java.util.Set;

/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Subject implements Printable {

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    private String subjectName;
    private String subjectDetails;
    //private Set<Exam> subjectExams;
    private boolean isPrivate;

    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Subject: " + subjectName + " " + subjectDetails + "}";
            } else {
                return "";
            }
        }
        return "Subject: " + subjectName + " " + subjectDetails;
    }




}
