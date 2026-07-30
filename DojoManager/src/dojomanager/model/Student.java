package dojomanager.model;

import java.io.Serializable;

/**
 * Backend class that represents one karate student at the dojo.
 *
 * <p>This class holds all the information stored about a student and is used
 * as the "shape" of the data that is written to and read from the
 * {@code students.dat} file. It implements {@link Serializable} so that a
 * whole {@code Student} object can be saved to file in one step.</p>
 *
 * <p>The class only stores data and gives controlled access to it through
 * accessor (get) and mutator (set) methods. All fields are private so the
 * data is hidden and can only be changed through these methods
 * (information hiding).</p>
 */
public class Student implements Serializable {
    /** Version number used by Java when saving/loading this object. */
    private static final long serialVersionUID = 1L;

    /** Unique ID number for the student (set by the DataManager). */
    private int id;
    /** The student's full name. */
    private String name;
    /** The student's age in years. */
    private int age;
    /** The student's current belt rank (e.g. "White Belt"). */
    private String beltRank;
    /** The student's contact phone number. */
    private String phone;
    /** The student's contact email address. */
    private String email;

    /**
     * Empty constructor. Needed so an "empty" student can be created and
     * filled in later using the set methods.
     */
    public Student() {
    }

    /**
     * Creates a student with all details supplied at once.
     *
     * @param id       the unique ID (usually 0 for a new student, the
     *                 DataManager replaces it with the real ID)
     * @param name     the student's full name
     * @param age      the student's age in years
     * @param beltRank the student's belt rank
     * @param phone    the student's phone number
     * @param email    the student's email address
     */
    public Student(int id, String name, int age, String beltRank, String phone, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.beltRank = beltRank;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Accessor for the student's ID.
     *
     * @return the unique ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator for the student's ID.
     *
     * @param id the new ID number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor for the student's name.
     *
     * @return the full name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for the student's name.
     *
     * @param name the new full name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for the student's age.
     *
     * @return the age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Mutator for the student's age.
     *
     * @param age the new age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Accessor for the student's belt rank.
     *
     * @return the belt rank text
     */
    public String getBeltRank() {
        return beltRank;
    }

    /**
     * Mutator for the student's belt rank.
     *
     * @param beltRank the new belt rank text
     */
    public void setBeltRank(String beltRank) {
        this.beltRank = beltRank;
    }

    /**
     * Accessor for the student's phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Mutator for the student's phone number.
     *
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Accessor for the student's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Mutator for the student's email address.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Builds a short text description of the student, used when the object
     * needs to be shown as text (for example in a list).
     *
     * @return the name followed by the belt rank in brackets
     */
    @Override
    public String toString() {
        return name + " (" + beltRank + ")";
    }
}
