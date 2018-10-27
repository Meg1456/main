package seedu.addressbook.data.person;

import java.util.Optional;
import java.util.Set;

import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.ui.Formatter;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    Name getName();
    Phone getPhone();
    Email getEmail();
    Address getAddress();
    Optional<Account> getAccount();
    Fees getFees();
    Set<Exam> getExams();
    Set<Assessment> getAssessments();
    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the person's internal tags.
     */
    Set<Tag> getTags();


    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableString(
                true,
                getName(),
                getPhone(),
                getEmail(),
                getAddress(),
                getFees());

        builder.append(stringChain)
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        for (Exam exam : getExams()) {
            builder.append("\n");
            if (exam.isPrivate()) {
                builder.append("{");
            }
            builder.append(exam);
            if (exam.isPrivate()) {
                builder.append("}");
            }
        }
        getAccount().ifPresent(a -> builder.append(" User Type:").append(a.getPrintableString(true)));
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableString(
                false,
                getName(),
                getPhone(),
                getEmail(),
                getAddress(),
                getFees());
        builder.append(stringChain)
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }

        getAccount().ifPresent(a -> builder.append(" User Type:").append(a.getPrintableString(true)));
        return builder.toString();
    }

    /**
     * Formats the person as text, showing name and fees.
     */
    default String getAsTextShowFee() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableString(
                true,
                getName(),
                getFees());
        builder.append(stringChain);
        return builder.toString();
    }

    /**
     * Formats the person as text, showing name and assessments.
     */
    default String getAsTextShowAssess() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableString(
                true,
                getName());
        builder.append(stringChain);
        for (Assessment assessment : getAssessments()) {
            builder.append("Assessment: ").append(assessment).append("\n");
        }
        return builder.toString();
    }

    /**
     * Formats the person as text, showing name and exams.
     */
    default String getAsTextShowExam() {
        final StringBuilder builder = new StringBuilder();
        final String stringChain = Formatter.getPrintableString(
                true,
                getName());
        builder.append(stringChain);
        for (Exam exam : getExams()) {
            if (exam.isPrivate()) {
                continue;
            }
            builder.append("Exam: ").append(exam).append("\n");
        }
        return builder.toString();
    }
}
