package seedu.addressbook.data;

import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.UniqueExamList;
import seedu.addressbook.data.person.UniqueExamList.DuplicateExamException;
import seedu.addressbook.data.person.UniqueExamList.ExamNotFoundException;

/**
 * Represents the entire exams book. Contains the data of the exams book.
 */
public class ExamBook {

    private final UniqueExamList examList;

    /**
     * Creates an empty exam book.
     */
    public ExamBook() {
        examList = new UniqueExamList();
    }

    /**
     * Constructs an exam book with the given data.
     *
     * @param examList external changes to this will not affect this exam book
     */

    public ExamBook(UniqueExamList examList) {
        this.examList = new UniqueExamList(examList);
    }

    public static ExamBook empty() {
        return new ExamBook();
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicateExamException if an equivalent person already exists.
     */
    public void addExam(Exam toAdd) throws DuplicateExamException {
        examList.add(toAdd);
    }

    /**
     * Checks if an equivalent exam exists in the exam book.
     */
    public boolean containsExam(Exam key) {
        return examList.contains(key);
    }

    /**
     * Removes the equivalent exam from the exam book.
     *
     * @throws ExamNotFoundException if no such Person could be found.
     */
    public void removeExam(Exam toRemove) throws ExamNotFoundException {
        examList.remove(toRemove);
    }

    /**
     * Defensively copied UniqueExamList of all exam in the exam book at the time of the call.
     */
    public UniqueExamList getAllExam() {
        return new UniqueExamList(examList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExamBook // instanceof handles nulls
                && this.examList.equals(((ExamBook) other).examList));
    }

    @Override
    public int hashCode() {
        return examList.hashCode();
    }

    /**
     * Clears all exams from the exam book.
     */
    public void clear() {
        examList.clear();
    }
}
