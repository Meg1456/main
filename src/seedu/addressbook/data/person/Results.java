package seedu.addressbook.data.person;

import java.util.Objects;
import seedu.addressbook.data.exception.IllegalValueException;
/**
 * Represents a exam in the exam book.
 */
public class Results implements Printable {

    public static final String SUBJECT_NAME_EXAMPLE = "Mathematics";
    public static final String EXAM_NAME_EXAMPLE = "Midterms";
    public static final String PERCENTILE_EXAMPLE = "75";
    public static final String MARKSSCORED_EXAMPLE = "27";
    public static final String EXAMTOTAL_EXAMPLE = "30";
    public static final String MAXMIN_EXMAPLE = "27 10"; //maximum score and minimum score

    private String subjectName;
    private String examName;
    private String percentile;
    private String marksScored;
    private String examTotal;
    private String maxMin;
    private boolean isPrivate;

    public static final String PERCENTILE_CONSTRAINTS = "Percentile must only contain whole numbers";
    public static final String PERCENTILE_VALIDATION_REGEX = "\\d+";

    public static final String MARKS_SCORED_CONSTRAINTS = "May include up to decimal places only";
    public static final String MARKS_SCORED_VALIDATION_REGEX = "\\d+(\\.\\d+)?";

    public static final String EXAM_TOTAL_CONSTRAINTS = "Exam total must only contain whole numbers";
    public static final String EXAM_TOTAL_VALIDATION_REGEX = "\\d+";

    public static final String MAX_MIN_CONSTRAINTS = "Max-Min value can contain two whole numbers separated by a space";
    public static final String MAX_MIN_VALIDATION_REGEX = ".+";


    public Results() {}

    /**
     * Validates given results.
     *
     * @throws IllegalValueException if given results string is invalid.
     */
    public Results(String subjectName, String examName, String percentile, String marksScored,
                String examTotal, String maxMin, boolean isPrivate) throws IllegalValueException {

        this.subjectName = subjectName.trim();
        this.examName = examName.trim();

        String trimmedPercentile = percentile.trim();
        if (!isValidPercentile(trimmedPercentile)) {
            throw new IllegalValueException(PERCENTILE_CONSTRAINTS);
        }
        this.percentile = trimmedPercentile;

        String trimmedMarksScored = marksScored.trim();
        if (!isValidMarksScored(trimmedMarksScored)) {
            throw new IllegalValueException(MARKS_SCORED_CONSTRAINTS);
        }
        this.marksScored = trimmedMarksScored;

        String trimmedExamTotal = examTotal.trim();
        if (!isValidExamTotal(trimmedExamTotal)) {
            throw new IllegalValueException(EXAM_TOTAL_CONSTRAINTS);
        }
        this.examTotal = trimmedExamTotal;

        String trimmedMaxMin = maxMin.trim();
        if (!isValidMaxMin(trimmedMaxMin)) {
            throw new IllegalValueException(MAX_MIN_CONSTRAINTS);
        }
        this.maxMin = trimmedMaxMin;

        this.isPrivate = isPrivate;
    }

    /**
     * Checks if a given string is a valid percentile.
     */
    public static boolean isValidPercentile(String value) {
            return value.matches(PERCENTILE_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid marks scored.
     */
    public static boolean isValidMarksScored(String value) {
        return value.matches(MARKS_SCORED_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid exam total.
     */
    public static boolean isValidExamTotal(String value) {
        return value.matches(EXAM_TOTAL_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid entry of max-min score.
     */
    public static boolean isValidMaxMin(String value) {
        return value.matches(MAX_MIN_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return subjectName + " - " + examName + " Marks Scored: " + marksScored
                + " Exam total: " + examTotal + " Percentile: " + percentile + " Max-Min Score: " + maxMin;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Results // instanceof handles nulls
                && this.examName.equals(((Results) other).examName)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(percentile, marksScored, examTotal, maxMin);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Results: " + subjectName + " " + examName + " " + marksScored + " "
                        + examTotal + " " + percentile + " " + maxMin + "}";
            } else {
                return "";
            }
        }
        return "Exam: " + subjectName + " " + examName + " " + marksScored + " "
                + examTotal + " " + percentile + " " + maxMin;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public String getExamName() {
        return examName;
    }

    public String getPercentile() {
        return percentile;
    }

    public String getMarksScored() {
        return marksScored;
    }

    public String getExamTotal() {
        return examTotal;
    }

    public String getMaxMin() {
        return maxMin;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
