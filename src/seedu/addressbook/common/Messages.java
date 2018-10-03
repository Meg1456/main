package seedu.addressbook.common;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESSBOOK = "Person could not be found in address book";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE = "Launch command format: "
            + "java seedu.addressbook.Main [STORAGE_FILE_PATH]";
    public static final String MESSAGE_WELCOME = "Welcome to your Address Book!";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";
    public static final String MESSAGE_INSUFFICIENT_PRIVILEGE = "Insufficient Privilege.\n"
            + "Requires privilege level of %s, but current level is %s";
    public static final String MESSAGE_USING_EXAMS_FILE = "Using exams file : %1$s";
    public static final String MESSAGE_USING_STATISTICS_FILE = "Using statistics file : %1$s";
}
