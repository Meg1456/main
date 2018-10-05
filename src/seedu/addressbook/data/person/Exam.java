package seedu.addressbook.data.person;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;
/**
 * Represents a exam in the exam book.
 */
public class Exam implements Printable {

    public static final String SUBJECT_NAME_EXAMPLE = "Mathematics";
    public static final String EXAM_NAME_EXAMPLE = "Midterms";
    public static final String EXAM_DATE_EXAMPLE = "01122018";
    public static final String EXAM_START_TIME_EXAMPLE = "0900";
    public static final String EXAM_END_TIME_EXAMPLE = "1000";
    public static final String EXAM_DETAILS_EXAMPLE = "Held in MPSH";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Exam date should be in the format DDMMYYY and valid";
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Exam time should be in 24 hours format and valid";
    public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS =
            "Exam time interval is inaccurate";
    public static final String DATE_VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
    public static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";

    private String subjectName;
    private String examName;
    private String examDate;
    private String examStartTime;
    private String examEndTime;
    private String examDetails;
    private boolean isPrivate;


    public Exam(){}

    /**
     * Validates given exams.
     *
     * @throws IllegalValueException if given exam date string is invalid.
     */
    public Exam(String subjectName, String examName, String examDate, String examStartTime,
                String examEndTime, String examDetails, boolean isPrivate) throws IllegalValueException {
        this.subjectName = subjectName.trim();
        this.examName = examName.trim();
        String trimmedDate = examDate.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.examDate = toDate(trimmedDate);
        String trimmedStartTime = examStartTime.trim();
        String trimmedEndTime = examEndTime.trim();
        if (!isValidTime(trimmedStartTime) || !isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        if (!isValidTimeInterval(trimmedStartTime, trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
        this.examStartTime = toTime(trimmedStartTime);
        this.examEndTime = toTime(trimmedEndTime);
        this.examDetails = examDetails.trim();
        this.isPrivate = isPrivate;
    }

    /**
     * Checks if a given string is a valid date.
     */
    public static boolean isValidDate(String value) {
        try {
            String dateValue = toDate(value);
            return dateValue.matches(DATE_VALIDATION_REGEX);
        } catch (IllegalValueException e) {
            return false;
        }
    }

    /**
     * Checks if a given string is a valid time.
     */
    public static boolean isValidTime(String value) {
        try {
            String timeValue = toTime(value);
            return timeValue.matches(TIME_VALIDATION_REGEX);
        } catch (IllegalValueException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return subjectName + " - " + examName + " Date: " + examDate
                + " Start Time: " + examStartTime + " End Time: " + examEndTime + " Details: " + examDetails;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Exam // instanceof handles nulls
                && this.examName.equals(((Exam) other).examName)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(examDate, examDate, examStartTime, examEndTime, examDetails);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        if (isPrivate()) {
            if (showPrivate) {
                return "{private Exam: " + subjectName + " " + examName + " " + examDate + " "
                        + examStartTime + " " + examEndTime + " " + examDetails + "}";
            } else {
                return "";
            }
        }
        return "Exam: " + subjectName + " " + examName + " " + examDate + " "
                + examStartTime + " " + examEndTime + " " + examDetails;
    }

    /*
     * Checks if an exam clashes with another exam's time.

    public static boolean isOverlappingTime(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
    */
    public String getSubjectName() {
        return subjectName;
    }

    public String getExamName() {
        return examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public String getExamDetails() {
        return examDetails;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    //
    //* Checks if the exam overlaps with another exams
    //
    //public boolean isOverlapping(Exam exam2) {
    //LocalTime start1 = toLocalTime("HH:mm", examStartTime);
    //LocalTime start2 = toLocalTime("HH:mm", exam2.getExamStartTime());
    //LocalTime end1 = toLocalTime("HH:mm", examEndTime);
    //LocalTime end2 = toLocalTime("HH:mm", exam2.getExamEndTime());
    //return isOverlappingTime(start1, end1, start2, end2);
    //}
    //

    /**
     * Checks if a given time interval is valid
     */
    public boolean isValidTimeInterval(String examStart, String examEnd) {
        try {
            String examStartTime = toTime(examStart);
            String examEndTime = toTime(examEnd);
            LocalTime startTime = toLocalTime("HH:mm", examStartTime);
            LocalTime endTime = toLocalTime("HH:mm", examEndTime);
            return startTime.isBefore(endTime);
        } catch (IllegalValueException e) {
            return false;
        }
    }

    /**
     * Converts a string to LocalTime object
     * @param format The format string
     * @param value The time string
     * @return LocalTime object
     */
    public static LocalTime toLocalTime(String format, String value) {
        LocalTime time = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            time = LocalTime.parse(value, formatter);
        } catch (DateTimeParseException ex) {
            throw new DateTimeParseException("Wrong format", value, 0, ex.getCause());
        }
        return time;
    }

    /**
     * Converts a string to date format string
     * @param value The string
     * @return String date
     */
    public static String toDate(String value) throws IllegalValueException {
        if (isCorrectDateFormat(value)) {
            return value;
        } else if (value.length() != 8) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String dateValue = new StringBuilder(value).insert(4, "/").insert(2, "/").toString();
        return dateValue;
    }

    /**
     * Converts a string to time format string
     * @param value The string
     * @return String time
     */
    public static String toTime(String value) throws IllegalValueException {
        if (isCorrectTimeFormat(value)) {
            return value;
        } else if (value.length() != 4) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        String timeValue = new StringBuilder(value).insert(2, ":").toString();
        return timeValue;
    }

    /**
     * Checks if a given string is already in date format
     */
    private static boolean isCorrectDateFormat(String value) {
        boolean validDate = value.length() == 10 && value.charAt(2) == '/' && value.charAt(5) == '/';
        return validDate;
    }

    /**
     * Checks if a given string is already in time format
     */
    private static boolean isCorrectTimeFormat(String value) {
        boolean validTime = value.length() == 5 && value.charAt(2) == ':';
        return validTime;
    }

}
