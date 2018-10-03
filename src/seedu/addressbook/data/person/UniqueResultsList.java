package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of results per subject. Does not allow null elements or duplicates.
 *
 * @see Results#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */

public class UniqueResultsList implements Iterable<Results> {
    private final List<Results> internalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateResultsException extends DuplicateDataException {
        protected DuplicateResultsException() {
            super("Operation would result in duplicate exams");
        }
    }

    /**
     * Signals that an operation targeting a specified result in the list would fail because
     * there is no such matching result in the list.
     */
    public static class ResultsNotFoundException extends Exception {}

    /**
     * Constructs empty result list.
     */
    public UniqueResultsList() {}

    /**
     * Constructs a result list with the given results.
     */
    public UniqueResultsList(Results... results) throws DuplicateResultsException {
        final List<Results> initialTags = Arrays.asList(results);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateResultsException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param results a collection of results
     * @throws DuplicateResultsException if the {@code results} contains duplicate results
     */
    public UniqueResultsList(Collection<Results> results) throws DuplicateResultsException {
        if (!Utils.elementsAreUnique(results)) {
            throw new DuplicateResultsException();
        }
        internalList.addAll(results);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueResultsList(UniqueResultsList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Checks if the list contains an equivalent result as the given argument.
     */
    public boolean contains(Results toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a result to the list.
     *
     * @throws DuplicateResultsException if the person to add is a duplicate of an existing results in the list.
     */
    public void add(Results toAdd) throws DuplicateResultsException {
        if (contains(toAdd)) {
            throw new DuplicateResultsException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent exam from the list.
     *
     * @throws ResultsNotFoundException if no such person could be found in the list.
     */
    public void remove(Results toRemove) throws ResultsNotFoundException {
        final boolean resultsFoundAndDeleted = internalList.remove(toRemove);
        if (!resultsFoundAndDeleted) {
            throw new ResultsNotFoundException();
        }
    }

    /**
     * Clears all results in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Results> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueResultsList // instanceof handles nulls
                && this.internalList.equals((
                (UniqueResultsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
