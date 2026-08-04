# Dojo Manager — PAT Project Guide (READ ME FIRST)

This is a plain-English guide to the whole PAT project: what it is, how the
program is built, how it works, and what is still left to finish. Read it
top-to-bottom — it also doubles as **interview preparation**,
because your teacher will ask you to explain your own code.

---

## 1. What is this project?

**The problem:** Small karate dojos often keep their student records and weekly
class timetables on paper or scattered spreadsheets. Details get lost, and it's
hard to find a student's belt/contact info or see the week's classes quickly.

**The solution:** **Dojo Manager** is a Java desktop program (NetBeans / Swing)
that keeps all of a dojo's students and classes in one place, saves everything
automatically, and lets the dojo admin manage it all from a simple, friendly
window.

**Who uses it:** One user group — the **dojo administrator / sensei**.

---

## 2. What's in this folder

```
PAT project/
├── DojoManager/                      ← the actual program (Java code)
│   ├── src/dojomanager/              ← all the source code
│   ├── dojo_data/                    ← DATABASE / test data (.dat files)
│   └── Start Dojo Manager.bat        ← quick way to run the program
│
├── Specifications Document (.md / .pdf / .docx)   ← PAT Section 1 (15 marks)
├── Design Document (.md / .pdf / .docx)           ← PAT Section 2 (30 marks)
├── Technical and Testing Document (.md/.pdf/.docx)← PAT Section 4 (15 marks)
├── NSC ... PAT Task Guidelines 2026.pdf           ← the official rules
└── NSC ... PAT Rubric 2026.pdf                    ← how it's marked
```

> Each document exists in three formats: **`.md`** (the editable source),
> **`.pdf`** (nice to read/print), and **`.docx`** (edit in Word to add your
> name and screenshots).

---

## 3. How to run the program

**Easiest way:** double-click **`DojoManager/Start Dojo Manager.bat`**.

**In NetBeans:**
1. File → Open Project → choose the `DojoManager` folder.
2. Right-click `DojoManagerApp.java` → **Run File** (or press the green ▶).

All data is saved into the **`DojoManager/dojo_data`** folder — that folder
**is the database / test data**. It already contains sample students and
classes so the screens are not empty when you open the program.

---

## 4. How the program is built (the important part)

The code is split into **four layers** so that the "look" (interface) is kept
separate from the "work" (logic and storage). This separation is one of the
things the rubric rewards.

```
dojomanager
├── DojoManagerApp        → starts the program
├── model/                → the DATA classes (the "shape" of the data)
│   ├── Student
│   ├── KarateClass
│   └── DojoInfo
├── data/                 → the STORAGE + LOGIC class
│   └── DataManager
├── util/                 → helper class
│   └── Validation
└── gui/                  → the SCREENS (interface only)
    ├── MainFrame, HomePanel, StudentPanel,
    ├── SchedulePanel, LocationPanel
    └── UITheme
```

### The classes, in plain English

**Model classes (they just hold data):**

- **`Student`** — holds one student's info: id, name, age, belt rank, phone,
  email. It has a constructor, get/set methods for each field, and a `toString`.
- **`KarateClass`** — holds one class: id, name, day, start time, instructor,
  belt level. Same style of methods.
- **`DojoInfo`** — holds the dojo's own details: name, sensei, address, city,
  phone, email, notes. Also has `getFullAddress()` used for Google Maps.

> All three "implement Serializable", which just means a whole object can be
> saved to a file in one step.

**`DataManager` (the brain — this is the most important class):**

This is the only class that touches the files. It:
- keeps the lists of students and classes (and the dojo info) in memory;
- **loads** all data from the files when the program starts;
- **saves** the data back to the files whenever something changes;
- provides add / edit / delete / find methods for students and classes;
- has extra logic: `hasClassClash(...)`, `getInstructorCount()`,
  `getClassesForDay(...)`, `getTodayName()`, and the two report exports
  (`exportStudentList()`, `exportTimetable()`).

**`Validation` (a small helper):**

Static methods that check input before it is saved: `isValidName`,
`isValidAge`, `isValidPhone`, `isValidEmail`, `isPresent`. Keeping the checks
here means the screens don't have to contain the checking logic themselves.

**GUI classes (only the interface):**

- **`MainFrame`** — the main window with the header and the four tabs.
- **`HomePanel`** — the dashboard: totals (students, classes, instructors) and
  a "Today's classes" list.
- **`StudentPanel`** — the students table with add/edit/delete/search/sort and
  an "Export List" button.
- **`SchedulePanel`** — the class timetable table with the same tools plus
  "Export Timetable".
- **`LocationPanel`** — shows/edits the dojo's details and opens Google Maps.
- **`UITheme`** — one place that defines all the colours, fonts and button
  styles (the soft pink-and-white theme), so every screen looks consistent.

The golden rule of the design: **the GUI classes never touch the files. They
always ask `DataManager` to do it.**

---

## 5. How the data is stored (the database / test data)

If you are looking for the **database** or **test data**, open:

**`DojoManager/dojo_data/`**

Data is saved **locally** (on the dojo's own computer) in that folder, in
three files. These `.dat` files **are** the program's secondary storage
(the "database"):

| File | Holds |
|---|---|
| `students.dat` | The list of all students (sample test data included) |
| `classes.dat` | The list of all classes (sample test data included) |
| `dojo_info.dat` | The single dojo details record |

The program can also **export** two readable text reports into the same folder
when you click the Export buttons: `student_list.txt` and `class_timetable.txt`.

---

## 6. The key algorithms (know these for the interview)

**Saving & loading:** When the program starts, `DataManager` reads each `.dat`
file and rebuilds the lists (wrapped in try-catch so a missing/damaged file
won't crash it). Every add/edit/delete writes the whole list back to the file.

**Validation:** Before a student/class is saved, the screen calls the
`Validation` methods. If a check fails, a clear message is shown and nothing is
saved.

**Clash check:** Before saving a class, `hasClassClash(...)` loops through the
existing classes; if the same instructor is already booked on the same day at
the same time, it returns true and the program shows a warning.

**Search & sort:** Typing in the Find box re-fills the table with only the rows
that match; clicking a column header sorts the table.

**Today's classes:** `getTodayName()` gets the current weekday, and
`getClassesForDay(...)` filters the classes for that day to show on the Home
screen.

---

## 7. Feature list (what it can do)

- Add / edit / delete / search / sort **students**
- Add / edit / delete / search / sort **classes**, with clash protection
- **Dashboard**: live totals + today's classes
- **Dojo details** editing + **Open in Google Maps**
- **Validation** on all inputs (name, age, phone, email)
- **Auto-save** and auto-load of all data
- **Export** a student list and a weekly timetable to text files

---

## 8. How this maps to the PAT rubric

| PAT section | Marks | Where it lives |
|---|---|---|
| Specifications Document | 15 | `Specifications Document.*` |
| Design Document | 30 | `Design Document.*` |
| Coding | 40 | the `DojoManager` program |
| Technical & Testing Document | 15 | `Technical and Testing Document.*` |

---

## 9. What is still left to finish (your to-do list)

- [ ] Put your **name, exam number and dates** on every document title page.
- [ ] Add **screenshots** where the documents say `[ADD SCREENSHOT]`.
- [ ] Actually **run the tests** and fill in the results tables in the Testing
      document (get a second tester, e.g. your cousin, for Test Set 2).
- [ ] Complete **Section 4.1 (Externally Sourced Code)** honestly — list any
      borrowed code and any AI help, with the prompts you used.
- [ ] Verify the **research reference links** in the Specifications document.
- [ ] Write your final **evaluation** paragraph (Section 4.5).

---

## 10. Honesty & interview note (please read)

Your teacher marks the code **with an interview**. Be ready to explain your own
work in your own words. The IEB rule is that **borrowed or AI-generated code
must be 20% or less** of your program and must be declared in section 4.1 of
the Technical & Testing Document.

Before the interview, make sure you can confidently explain:
1. How `DataManager` saves and loads data.
2. What the `Validation` class does and why it's separate from the screens.
3. How the class-clash check works.
4. Why the GUI classes call `DataManager` instead of touching the files.

If you can explain those four things in your own words, you're in good shape.
