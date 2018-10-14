package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of exams. Does not allow null elements or duplicates.
 *
 * @see Exam#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniqueExamList implements Iterable<Exam> {

    private final List<Exam> internalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateExamException extends DuplicateDataException {
        protected DuplicateExamException() {
            super("Operation would result in duplicate exams");
        }
    }

    //
    //* Signals that an operation would have violated the 'no overlapping timings' property of the list.
    //*
    //public static class OverlappingTimeException extends Exception {
    //protected OverlappingTimeException() {
    //super("Operation would result in overlapping exam timings");
    //}
    //}

    /**
     * Signals that an operation targeting a specified exam in the list would fail because
     * there is no such matching exam in the list.
     */
    public static class ExamNotFoundException extends Exception {}

    /**
     * Constructs empty exam list.
     */
    public UniqueExamList() {}

    /**
     * Constructs a exam list with the given exams.
     */
    public UniqueExamList(Exam... exams) throws DuplicateExamException {
        final List<Exam> initialTags = Arrays.asList(exams);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateExamException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param exams a collection of exams
     * @throws DuplicateExamException if the {@code exams} contains duplicate exams
     */
    public UniqueExamList(Collection<Exam> exams) throws DuplicateExamException {
        if (!Utils.elementsAreUnique(exams)) {
            throw new DuplicateExamException();
        }
        internalList.addAll(exams);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueExamList(UniqueExamList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Checks if the list contains an equivalent exam as the given argument.
     */
    public boolean contains(ReadOnlyExam toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a exam to the list.
     *
     * @throws DuplicateExamException if the exam to add is a duplicate of an existing exam in the list.
     */
    public void add(Exam toAdd) throws DuplicateExamException {
        if (contains(toAdd)) {
            throw new DuplicateExamException();
        }
        //for (Exam e: internalList){
        //      if (toAdd.isOverlapping(e)){
        //        throw new OverlappingTimeException();
        //    }
        //}
        internalList.add(toAdd);
        sort();
    }

    /**
     * Removes the equivalent exam from the list.
     *
     * @throws ExamNotFoundException if no such person could be found in the list.
     */
    public void remove(ReadOnlyExam toRemove) throws ExamNotFoundException {
        final boolean examFoundAndDeleted = internalList.remove(toRemove);
        if (!examFoundAndDeleted) {
            throw new ExamNotFoundException();
        }
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyExam}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyExam> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * Clears all exams in list.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Finds the equivalent exam from the list.
     *
     * @throws ExamNotFoundException if no such exam could be found in the list.
     */
    public Exam find(ReadOnlyExam exam) throws ExamNotFoundException {
        //TODO: Fix potato
        for (Exam p: internalList) {
            if (p.equals(exam)) {
                return p;
            }
        }
        throw new ExamNotFoundException();
    }

    /**
     * Sorts all exams in list by their subject names.
     */
    public void sort() {
        internalList.sort((Exam name1, Exam name2)->name1.getSubjectName().compareToIgnoreCase(name2.getSubjectName()));
    }

    @Override
    public Iterator<Exam> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueExamList // instanceof handles nulls
                && this.internalList.equals((
                (UniqueExamList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
