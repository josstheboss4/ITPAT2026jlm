package dojomanager.model;

import java.io.Serializable;

/**
 * Backend class that represents one scheduled karate class at the dojo.
 *
 * <p>This class holds the details of a single class in the weekly timetable
 * and is the "shape" of the data saved to and loaded from the
 * {@code classes.dat} file. It implements {@link Serializable} so a whole
 * {@code KarateClass} object can be stored in one step.</p>
 *
 * <p>All fields are private (information hiding) and are reached through public
 * accessor (get) and mutator (set) methods.</p>
 */
public class KarateClass implements Serializable {
    /** Version number used by Java when saving/loading this object. */
    private static final long serialVersionUID = 1L;

    /** Unique ID number for the class (set by the DataManager). */
    private int id;
    /** The name of the class, e.g. "Beginners Class". */
    private String className;
    /** The day of the week the class runs on, e.g. "Monday". */
    private String dayOfWeek;
    /** The start time of the class, e.g. "16:00". */
    private String startTime;
    /** The instructor who teaches the class. */
    private String instructor;
    /** The belt level/range the class is for, e.g. "All belts". */
    private String beltLevel;

    /**
     * Empty constructor. Needed so an "empty" class can be created and filled
     * in later using the set methods.
     */
    public KarateClass() {
    }

    /**
     * Creates a class with all details supplied at once.
     *
     * @param id         the unique ID (usually 0 for a new class, the
     *                   DataManager replaces it with the real ID)
     * @param className  the name of the class
     * @param dayOfWeek  the day the class runs
     * @param startTime  the start time of the class
     * @param instructor the instructor who teaches it
     * @param beltLevel  the belt level/range for the class
     */
    public KarateClass(int id, String className, String dayOfWeek, String startTime,
                       String instructor, String beltLevel) {
        this.id = id;
        this.className = className;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.instructor = instructor;
        this.beltLevel = beltLevel;
    }

    /**
     * Accessor for the class ID.
     *
     * @return the unique ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator for the class ID.
     *
     * @param id the new ID number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor for the class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Mutator for the class name.
     *
     * @param className the new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Accessor for the day of the week.
     *
     * @return the day the class runs on
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Mutator for the day of the week.
     *
     * @param dayOfWeek the new day the class runs on
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Accessor for the start time.
     *
     * @return the start time text
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Mutator for the start time.
     *
     * @param startTime the new start time text
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Accessor for the instructor.
     *
     * @return the instructor's name
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Mutator for the instructor.
     *
     * @param instructor the new instructor's name
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Accessor for the belt level.
     *
     * @return the belt level/range text
     */
    public String getBeltLevel() {
        return beltLevel;
    }

    /**
     * Mutator for the belt level.
     *
     * @param beltLevel the new belt level/range text
     */
    public void setBeltLevel(String beltLevel) {
        this.beltLevel = beltLevel;
    }

    /**
     * Builds a short text description of the class, used when the object needs
     * to be shown as text.
     *
     * @return the class name followed by the day and start time
     */
    @Override
    public String toString() {
        return className + " - " + dayOfWeek + " " + startTime;
    }
}
