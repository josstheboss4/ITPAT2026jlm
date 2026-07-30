package dojomanager.model;

import java.io.Serializable;

/**
 * Backend class that holds the dojo's own details (name, contact and address).
 *
 * <p>There is only ever one {@code DojoInfo} object in the program. It is the
 * "shape" of the data saved to and loaded from the {@code dojo_info.dat} file
 * and implements {@link Serializable} so it can be stored in one step.</p>
 *
 * <p>All fields are private (information hiding) and are reached through public
 * accessor (get) and mutator (set) methods.</p>
 */
public class DojoInfo implements Serializable {
    /** Version number used by Java when saving/loading this object. */
    private static final long serialVersionUID = 1L;

    /** The name of the dojo. */
    private String dojoName;
    /** The head instructor's name. */
    private String senseiName;
    /** The dojo's street address. */
    private String address;
    /** The dojo's city / country. */
    private String city;
    /** The dojo's contact phone number. */
    private String phone;
    /** The dojo's contact email address. */
    private String email;
    /** A short description / notes about the dojo. */
    private String notes;

    /**
     * Creates the dojo info with sample default details. These can be changed
     * by the user on the Location screen.
     */
    public DojoInfo() {
        this.dojoName = "Shobu Karate Dojo";
        this.senseiName = "Sensei Lee";
        this.address = "12 Main Road";
        this.city = "Johannesburg, South Africa";
        this.phone = "011 555 0199";
        this.email = "info@shobudojo.co.za";
        this.notes = "Traditional Shotokan karate for all ages.";
    }

    /**
     * Accessor for the dojo name.
     *
     * @return the dojo name
     */
    public String getDojoName() {
        return dojoName;
    }

    /**
     * Mutator for the dojo name.
     *
     * @param dojoName the new dojo name
     */
    public void setDojoName(String dojoName) {
        this.dojoName = dojoName;
    }

    /**
     * Accessor for the head instructor's name.
     *
     * @return the sensei's name
     */
    public String getSenseiName() {
        return senseiName;
    }

    /**
     * Mutator for the head instructor's name.
     *
     * @param senseiName the new sensei's name
     */
    public void setSenseiName(String senseiName) {
        this.senseiName = senseiName;
    }

    /**
     * Accessor for the street address.
     *
     * @return the street address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Mutator for the street address.
     *
     * @param address the new street address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Accessor for the city / country.
     *
     * @return the city / country text
     */
    public String getCity() {
        return city;
    }

    /**
     * Mutator for the city / country.
     *
     * @param city the new city / country text
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Accessor for the phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Mutator for the phone number.
     *
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Accessor for the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Mutator for the email address.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accessor for the notes / description.
     *
     * @return the notes text
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Mutator for the notes / description.
     *
     * @param notes the new notes text
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Builds the full address (street plus city) used when searching Google
     * Maps for the dojo's location.
     *
     * @return the street address and city joined together
     */
    public String getFullAddress() {
        return address + ", " + city;
    }
}
