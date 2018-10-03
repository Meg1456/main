package seedu.addressbook.stubs;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.storage.Storage;

/**
 * Class used to apply DI
 * */
public class StorageStub extends Storage {
    private String path;
    private String pathExam;

    public StorageStub(String filePath) {
        path = filePath;
    }

    public StorageStub(String filePath, String filePathExam) {
        path = filePath;
        pathExam = filePathExam;
    }
    /**Stub function*/
    public void save(AddressBook addressBook){
        //this is blank on purpose
    }
    /**Stub function*/
    public void saveExam(ExamBook examBook){
        //this is blank on purpose
    }

    public AddressBook load() {
        return new AddressBook();
    }
    public String getPath() {
        return path;
    }
    public ExamBook loadExam() {
        return new ExamBook();
    }
    public String getPathExam() {
        return pathExam;
    }
}
