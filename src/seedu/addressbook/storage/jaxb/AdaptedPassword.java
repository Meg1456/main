package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.common.Utils;

/**
 * JAXB-friendly adapted address book data holder class.
 */
@XmlRootElement(name = "Password")
public class AdaptedPassword {
    @XmlValue
    private String password;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedPassword() {}

    public AdaptedPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAnyRequiredFieldMissing() {
        return Utils.isAnyNull(password);
    }
}
