package seedu.addressbook.logic;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.storage.Storage;
import seedu.addressbook.storage.StorageFile;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic {
    private Storage storage;
    private AddressBook addressBook;
    private StatisticsBook statisticsBook;

    /** The list of person shown to the user most recently.  */
    private List<? extends ReadOnlyPerson> lastShownList = Collections.emptyList();


    public Logic() throws Exception {
        setStorage(initializeStorage());
        setAddressBook(storage.load());
        setStatisticsBook(storage.loadStatistics());
    }

    Logic(Storage storageFile, AddressBook addressBook, StatisticsBook statisticsBook) {
        setStorage(storageFile);
        setAddressBook(addressBook);
        setStatisticsBook(statisticsBook);
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setStatisticsBook(StatisticsBook statisticsBook) {
        this.statisticsBook = statisticsBook;
    }
    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile.InvalidStorageFilePathException if the target file path is incorrect.
     * @throws StorageFile.InvalidInitialisationException if the JAXB set up has error
     */
    private StorageFile initializeStorage()
            throws StorageFile.InvalidStorageFilePathException,
            StorageFile.InvalidInitialisationException {
        return new StorageFile();
    }

    public String getStorageFilePath() {
        return storage.getPath();
    }

    public String getStorageFilePathStatistics() {
        return storage.getPathStatistics();
    }

    /**
     * Unmodifiable view of the current last shown list.
     */
    public List<ReadOnlyPerson> getLastShownList() {
        return Collections.unmodifiableList(lastShownList);
    }

    protected void setLastShownList(List<? extends ReadOnlyPerson> newList) {
        lastShownList = newList;
    }

    /**
     * Parses the user command, executes it, and returns the result.
     * @throws Exception if there was any problem during command execution.
     */
    public CommandResult execute(String userCommandText) throws Exception {
        Command command = new Parser().parseCommand(userCommandText);
        CommandResult result = execute(command);
        recordResult(result);
        return result;
    }

    /**
     * Executes the command, updates storage if the command can potentially mutate data,
     * and returns the result.
     *
     * @param command user command
     * @return result of the command
     * @throws Exception if there was any problem during command execution.
     */
    private CommandResult execute(Command command) throws Exception {
        command.setData(addressBook, statisticsBook, lastShownList);
        CommandResult result = command.execute();
        if (command.isMutating()) {
            storage.save(addressBook);
            storage.saveStatistics(statisticsBook);
        }
        return result;
    }

    /** Updates the {@link #lastShownList} if the result contains a list of Persons. */
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> personList = result.getRelevantPersons();
        if (personList.isPresent()) {
            lastShownList = personList.get();
        }
    }
}
