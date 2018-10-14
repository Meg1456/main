package seedu.addressbook.data.person;

/**
 * A read-only immutable interface for a Person in the exambook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyExam {

    String getSubjectName();
    String getExamName();
    String getExamDate();
    String getExamStartTime();
    String getExamEndTime();
    String getExamDetails();
    boolean isPrivate();
    String getPrintableExamString(boolean showPrivate);

    /**
     * Formats the exam as text to show all.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = getPrintableExamString(true);
        builder.append(stringChain);
        return builder.toString();
    }

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyExam other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getSubjectName().equals(this.getSubjectName()) // state checks here onwards
                && other.getExamName().equals(this.getExamName())
                && other.getExamDate().equals(this.getExamDate())
                && other.getExamStartTime().equals(this.getExamStartTime())
                && other.getExamEndTime().equals(this.getExamEndTime())
                && other.getExamDetails().equals(this.getExamDetails())
                && (other.isPrivate() == this.isPrivate()));
    }
}
