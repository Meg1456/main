package seedu.addressbook.data.person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a exam in the exam book.
 */
public class Exam implements ReadOnlyExam {

    public static final String EXAM_NAME_EXAMPLE = "Midterms";
    public static final String SUBJECT_NAME_EXAMPLE = "Mathematics";
    public static final String EXAM_DATE_EXAMPLE = "01-12-2018";
    public static final String EXAM_START_TIME_EXAMPLE = "09:00";
    public static final String EXAM_END_TIME_EXAMPLE = "10:00";
    public static final String EXAM_DETAILS_EXAMPLE = "Held in MPSH";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Exam date should be in the format DD-MM-YYYY and valid.";
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Exam time should be in 24 hours format HH:MM and valid.";
    public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS =
            "Exam time interval is inaccurate.";

    public static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";

    private String examName;
    private String subjectName;
    private String examDate;
    private String examStartTime;
    private String examEndTime;
    private String examDetails;
    private boolean isPrivate;

    /**
     * Validates a given exam.
     *
     * @throws IllegalValueException if given exam date or time string is invalid.
     * @throws IllegalValueException if time interval is invalid.
     */
    public Exam(String examName, String subjectName, String examDate, String examStartTime,
                String examEndTime, String examDetails, boolean isPrivate) throws IllegalValueException {
        this.examName = examName.trim();
        this.subjectName = subjectName.trim();
        String trimmedDate = examDate.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.examDate = trimmedDate;
        String trimmedStartTime = examStartTime.trim();
        String trimmedEndTime = examEndTime.trim();
        if (!isValidTime(trimmedStartTime) || !isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        try {
            if (!isValidTimeInterval(trimmedStartTime, trimmedEndTime)) {
                throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
            }
        } catch (IllegalValueException ive) {
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
        this.examStartTime = trimmedStartTime;
        this.examEndTime = trimmedEndTime;
        this.examDetails = examDetails.trim();
        this.isPrivate = isPrivate;
    }

    /**
     * Create a new exam from a given exam and details to be changed.
     *
     * @throws IllegalValueException if new exam date or time string is invalid.
     * @throws IllegalValueException if new time interval is invalid.
     */
    public Exam(ReadOnlyExam toEdit, Map<String, String> changedDetails) throws IllegalValueException {
        this.examName = toEdit.getExamName();
        this.subjectName = toEdit.getSubjectName();
        this.examDate = toEdit.getExamDate();
        this.examStartTime = toEdit.getExamStartTime();
        this.examEndTime = toEdit.getExamEndTime();
        this.examDetails = toEdit.getExamDetails();
        this.isPrivate = toEdit.isPrivate();

        for (Map.Entry<String, String> entry : changedDetails.entrySet()) {
            String attribute = entry.getKey();
            String newValue = entry.getValue();
            if ("examName".equals(attribute)) {
                this.examName = newValue.trim();
            } else if ("subjectName".equals(attribute)) {
                this.subjectName = newValue.trim();
            } else if ("examDate".equals(attribute)) {
                if (!isValidDate(newValue.trim())) {
                    throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
                }
                this.examDate = newValue.trim();
            } else if ("examStartTime".equals(attribute)) {
                if (!isValidTime(newValue.trim())) {
                    throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
                }
                this.examStartTime = newValue.trim();
            } else if ("examEndTime".equals(attribute)) {
                if (!isValidTime(newValue.trim())) {
                    throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
                }
                this.examEndTime = newValue.trim();
            } else if ("examDetails".equals(attribute)) {
                this.examDetails = newValue.trim();
            } else if ("isPrivate".equals(attribute)) {
                this.isPrivate = "y".equals(newValue.trim());
            }
        }
        if (!isValidTimeInterval(this.examStartTime, this.examEndTime)) {
            throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
        }
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

    /**
     * Checks if a given string is a valid time.
     */
    public static boolean isValidTime(String value) {
        return value.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Checks if a given time interval is valid
     */
    public static boolean isValidTimeInterval(String examStart, String examEnd) {
        boolean valid;
        String format = "HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalTime startTime = LocalTime.parse(examStart, formatter);
            LocalTime endTime = LocalTime.parse(examEnd, formatter);
            valid = startTime.isBefore(endTime);
        } catch (DateTimeParseException ex) {
            valid = false;
        }
        return valid;
    }

    public String getPrintableExamString(boolean showPrivate) {
        if (showPrivate && isPrivate) {
            return "private Exam: " + examName + " " + subjectName + " " + examDate + " "
                    + examStartTime + " " + examEndTime + " " + examDetails;
        } else if (isPrivate) {
            return "";
        }
        return "Exam: " + examName + " " + subjectName + " " + examDate + " "
                + examStartTime + " " + examEndTime + " " + examDetails;
    }

    @Override
    public String toString() {
        return examName + " Subject: " + subjectName + " Date: " + examDate
                + " Start Time: " + examStartTime + " End Time: " + examEndTime + " Details: " + examDetails;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyExam // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyExam) other)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(examName, subjectName, examDate, examStartTime, examEndTime, examDetails, isPrivate);
    }

    @Override
    public String getExamName() {
        return examName;
    }

    @Override
    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public String getExamDate() {
        return examDate;
    }

    @Override
    public String getExamStartTime() {
        return examStartTime;
    }

    @Override
    public String getExamEndTime() {
        return examEndTime;
    }

    @Override
    public String getExamDetails() {
        return examDetails;
    }

    @Override
    public boolean isPrivate() {
        return isPrivate;
    }

    /*
     * Checks if an exam clashes with another exam's time.

    public static boolean isOverlappingTime(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
    */

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
}
