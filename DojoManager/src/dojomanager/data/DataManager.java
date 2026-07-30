package dojomanager.data;

import dojomanager.model.DojoInfo;
import dojomanager.model.KarateClass;
import dojomanager.model.Student;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Backend class that handles all secondary storage for the program.
 *
 * <p>This is the most significant backend class. It keeps the lists of
 * students and classes (and the dojo's own details) in memory while the
 * program runs, and it reads and writes those lists to files in the
 * {@code dojo_data} folder so the data is kept permanently between runs.</p>
 *
 * <p>The GUI (user interface) classes never touch the files directly. They
 * always ask this class to add, edit, delete, find, save or load data. This
 * keeps the working code separated from the interface code.</p>
 */
public class DataManager {

    /** Folder (created automatically) where all data files are stored. */
    private static final String DATA_FOLDER = "dojo_data";
    /** File that stores the list of students. */
    private static final String STUDENTS_FILE = DATA_FOLDER + File.separator + "students.dat";
    /** File that stores the list of classes. */
    private static final String CLASSES_FILE = DATA_FOLDER + File.separator + "classes.dat";
    /** File that stores the single dojo information record. */
    private static final String DOJO_FILE = DATA_FOLDER + File.separator + "dojo_info.dat";

    /** All students currently loaded in memory. */
    private List<Student> students;
    /** All classes currently loaded in memory. */
    private List<KarateClass> classes;
    /** The dojo's own details (name, address, contact, etc.). */
    private DojoInfo dojoInfo;
    /** The next ID number that will be given to a new student. */
    private int nextStudentId;
    /** The next ID number that will be given to a new class. */
    private int nextClassId;

    /**
     * Creates the data manager, makes sure the data folder exists and loads
     * any previously saved data from file. If no data exists yet, some
     * sample data is added so the program is not empty on first run.
     */
    public DataManager() {
        students = new ArrayList<>();
        classes = new ArrayList<>();
        dojoInfo = new DojoInfo();
        nextStudentId = 1;
        nextClassId = 1;
        ensureDataFolder();
        loadAll();
    }

    /**
     * Creates the {@code dojo_data} folder if it does not already exist.
     */
    private void ensureDataFolder() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * Loads the students, classes and dojo info from their files (if the
     * files exist). Each read is wrapped in a try-catch so that a missing or
     * damaged file cannot crash the program. If no students and no classes
     * are found, sample data is added instead.
     */
    @SuppressWarnings("unchecked")
    private void loadAll() {
        File studentFile = new File(STUDENTS_FILE);
        if (studentFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(studentFile))) {
                students = (List<Student>) in.readObject();
                for (Student s : students) {
                    if (s.getId() >= nextStudentId) {
                        nextStudentId = s.getId() + 1;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                students = new ArrayList<>();
            }
        }

        File classFile = new File(CLASSES_FILE);
        if (classFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(classFile))) {
                classes = (List<KarateClass>) in.readObject();
                for (KarateClass c : classes) {
                    if (c.getId() >= nextClassId) {
                        nextClassId = c.getId() + 1;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                classes = new ArrayList<>();
            }
        }

        File dojoFile = new File(DOJO_FILE);
        if (dojoFile.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dojoFile))) {
                dojoInfo = (DojoInfo) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                dojoInfo = new DojoInfo();
            }
        }

        if (students.isEmpty() && classes.isEmpty()) {
            addSampleData();
        }
    }

    /**
     * Adds a set of example students and classes the very first time the
     * program is run, so the tables are not empty for testing and marking.
     */
    private void addSampleData() {
        addStudent(new Student(0, "Thabo Molefe", 14, "Yellow Belt", "0821112233", "thabo@email.com"));
        addStudent(new Student(0, "Aisha Patel", 16, "Green Belt", "0834445566", "aisha@email.com"));
        addStudent(new Student(0, "Johan Botha", 12, "White Belt", "0847778899", "johan@email.com"));
        addStudent(new Student(0, "Lerato Dlamini", 15, "Orange Belt", "0812223344", "lerato@email.com"));
        addStudent(new Student(0, "Ethan Naidoo", 17, "Blue Belt", "0825556677", "ethan@email.com"));

        addClass(new KarateClass(0, "Beginners Class", "Monday", "16:00", "Sensei Lee", "White - Yellow"));
        addClass(new KarateClass(0, "Kids Class", "Monday", "17:00", "Sensei Patel", "All belts"));
        addClass(new KarateClass(0, "Intermediate Class", "Tuesday", "17:00", "Sensei Lee", "Orange - Green"));
        addClass(new KarateClass(0, "Kata Practice", "Tuesday", "18:00", "Sensei Khan", "Green - Brown"));
        addClass(new KarateClass(0, "Intermediate Class", "Wednesday", "17:00", "Sensei Lee", "Orange - Green"));
        addClass(new KarateClass(0, "Sparring / Kumite", "Thursday", "17:30", "Sensei Khan", "Green - Black"));
        addClass(new KarateClass(0, "Advanced / Black Belt", "Friday", "18:00", "Sensei Khan", "Brown - Black"));
        addClass(new KarateClass(0, "Teen Class", "Friday", "16:00", "Sensei Patel", "All belts"));
        addClass(new KarateClass(0, "Kids Class", "Saturday", "09:00", "Sensei Lee", "All belts"));
        addClass(new KarateClass(0, "Open Dojo / Free Training", "Saturday", "11:00", "Sensei Lee", "All belts"));
        addClass(new KarateClass(0, "Grading Prep", "Sunday", "10:00", "Sensei Khan", "All belts"));
    }

    /**
     * Saves the current list of students to the students file.
     * Any input/output problem is caught so the program does not crash.
     */
    public void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STUDENTS_FILE))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.err.println("Could not save students: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of classes to the classes file.
     * Any input/output problem is caught so the program does not crash.
     */
    public void saveClasses() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CLASSES_FILE))) {
            out.writeObject(classes);
        } catch (IOException e) {
            System.err.println("Could not save classes: " + e.getMessage());
        }
    }

    /**
     * Saves the dojo information record to the dojo info file.
     * Any input/output problem is caught so the program does not crash.
     */
    public void saveDojoInfo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DOJO_FILE))) {
            out.writeObject(dojoInfo);
        } catch (IOException e) {
            System.err.println("Could not save dojo info: " + e.getMessage());
        }
    }

    /**
     * Returns the dojo information record.
     *
     * @return the current {@link DojoInfo} object
     */
    public DojoInfo getDojoInfo() {
        return dojoInfo;
    }

    /**
     * Replaces the dojo information with new details and saves it to file.
     *
     * @param info the updated {@link DojoInfo} to store
     */
    public void updateDojoInfo(DojoInfo info) {
        this.dojoInfo = info;
        saveDojoInfo();
    }

    /**
     * Returns the full list of students.
     *
     * @return the list of all {@link Student} objects in memory
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Adds a new student, gives it the next unique ID and saves the list.
     *
     * @param student the {@link Student} to add (its ID is set automatically)
     */
    public void addStudent(Student student) {
        student.setId(nextStudentId++);
        students.add(student);
        saveStudents();
    }

    /**
     * Updates an existing student (matched by ID) and saves the list.
     *
     * @param updated the {@link Student} holding the new details; its ID is
     *                used to find the record that must be replaced
     */
    public void updateStudent(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updated.getId()) {
                students.set(i, updated);
                saveStudents();
                return;
            }
        }
    }

    /**
     * Deletes the student with the given ID and saves the list.
     *
     * @param id the ID of the student to remove
     */
    public void deleteStudent(int id) {
        students.removeIf(s -> s.getId() == id);
        saveStudents();
    }

    /**
     * Finds and returns a single student by ID.
     *
     * @param id the ID to search for
     * @return the matching {@link Student}, or {@code null} if none is found
     */
    public Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns the full list of classes.
     *
     * @return the list of all {@link KarateClass} objects in memory
     */
    public List<KarateClass> getClasses() {
        return classes;
    }

    /**
     * Adds a new class, gives it the next unique ID and saves the list.
     *
     * @param karateClass the {@link KarateClass} to add (ID set automatically)
     */
    public void addClass(KarateClass karateClass) {
        karateClass.setId(nextClassId++);
        classes.add(karateClass);
        saveClasses();
    }

    /**
     * Updates an existing class (matched by ID) and saves the list.
     *
     * @param updated the {@link KarateClass} holding the new details; its ID
     *                is used to find the record that must be replaced
     */
    public void updateClass(KarateClass updated) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId() == updated.getId()) {
                classes.set(i, updated);
                saveClasses();
                return;
            }
        }
    }

    /**
     * Deletes the class with the given ID and saves the list.
     *
     * @param id the ID of the class to remove
     */
    public void deleteClass(int id) {
        classes.removeIf(c -> c.getId() == id);
        saveClasses();
    }

    /**
     * Finds and returns a single class by ID.
     *
     * @param id the ID to search for
     * @return the matching {@link KarateClass}, or {@code null} if none found
     */
    public KarateClass findClassById(int id) {
        for (KarateClass c : classes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Checks whether a class would clash with an existing one, i.e. the same
     * instructor is already booked on the same day at the same time. The class
     * being edited is ignored so it does not clash with itself.
     *
     * @param id         the ID of the class being added/edited (use 0 for a
     *                   brand-new class)
     * @param day        the day of the week to check
     * @param time       the start time to check
     * @param instructor the instructor to check
     * @return {@code true} if another class already uses that instructor at
     *         that day and time
     */
    public boolean hasClassClash(int id, String day, String time, String instructor) {
        for (KarateClass c : classes) {
            if (c.getId() != id
                    && c.getDayOfWeek().equalsIgnoreCase(day)
                    && c.getStartTime().equals(time)
                    && c.getInstructor().equalsIgnoreCase(instructor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many different instructors are teaching classes.
     *
     * @return the number of unique instructor names in the class list
     */
    public int getInstructorCount() {
        List<String> seen = new ArrayList<>();
        for (KarateClass c : classes) {
            String name = c.getInstructor();
            if (name != null && !seen.contains(name)) {
                seen.add(name);
            }
        }
        return seen.size();
    }

    /**
     * Returns the name of today's day of the week, e.g. "Monday".
     *
     * @return today's weekday name as used in the class list
     */
    public String getTodayName() {
        return LocalDate.now().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    /**
     * Finds all classes that run on a given day of the week.
     *
     * @param day the day to match, e.g. "Monday"
     * @return a list of the {@link KarateClass} objects on that day
     */
    public List<KarateClass> getClassesForDay(String day) {
        List<KarateClass> result = new ArrayList<>();
        for (KarateClass c : classes) {
            if (c.getDayOfWeek() != null && c.getDayOfWeek().equalsIgnoreCase(day)) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Writes a neat, readable text report of all students to
     * {@code dojo_data/student_list.txt}.
     *
     * @return the report {@link File} that was created, or {@code null} if it
     *         could not be written
     */
    public File exportStudentList() {
        File report = new File(DATA_FOLDER + File.separator + "student_list.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(report))) {
            writer.write(dojoInfo.getDojoName() + " - Student List");
            writer.newLine();
            writer.write("Generated: " + LocalDate.now());
            writer.newLine();
            writer.write("Total students: " + students.size());
            writer.newLine();
            writer.newLine();
            writer.write(String.format("%-4s %-22s %-4s %-14s %-13s %s",
                    "ID", "Name", "Age", "Belt", "Phone", "Email"));
            writer.newLine();
            writer.write("-------------------------------------------------------------------------------");
            writer.newLine();
            for (Student s : students) {
                writer.write(String.format("%-4d %-22s %-4d %-14s %-13s %s",
                        s.getId(), s.getName(), s.getAge(), s.getBeltRank(),
                        s.getPhone(), s.getEmail()));
                writer.newLine();
            }
            return report;
        } catch (IOException e) {
            System.err.println("Could not export student list: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes a neat, readable text report of the weekly class timetable to
     * {@code dojo_data/class_timetable.txt}, grouped by day of the week.
     *
     * @return the report {@link File} that was created, or {@code null} if it
     *         could not be written
     */
    public File exportTimetable() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"};
        File report = new File(DATA_FOLDER + File.separator + "class_timetable.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(report))) {
            writer.write(dojoInfo.getDojoName() + " - Weekly Class Timetable");
            writer.newLine();
            writer.write("Generated: " + LocalDate.now());
            writer.newLine();
            writer.newLine();
            for (String day : days) {
                writer.write(day.toUpperCase());
                writer.newLine();
                List<KarateClass> dayClasses = getClassesForDay(day);
                if (dayClasses.isEmpty()) {
                    writer.write("   (no classes)");
                    writer.newLine();
                } else {
                    for (KarateClass c : dayClasses) {
                        writer.write(String.format("   %-6s %-26s %-14s %s",
                                c.getStartTime(), c.getClassName(),
                                c.getInstructor(), c.getBeltLevel()));
                        writer.newLine();
                    }
                }
                writer.newLine();
            }
            return report;
        } catch (IOException e) {
            System.err.println("Could not export timetable: " + e.getMessage());
            return null;
        }
    }
}
