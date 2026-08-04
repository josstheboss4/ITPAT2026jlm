# Dojo Manager — IT PAT Project

A basic Java (NetBeans / Swing) program for managing a karate dojo:
students and class schedules.

## What it does

| Feature | Details |
|---------|---------|
| **Home** | Welcome screen with student and class counts |
| **Students** | Add, edit, delete students (name, age, belt, phone, email) |
| **Class Schedule** | Add, edit, delete classes (day, time, instructor, belt level) |
| **Saving** | Data is saved automatically to the `dojo_data` folder (this is the database) |

## How to open in NetBeans (after cloning)

1. Open **NetBeans**.
2. Go to **File → Open Project** (do **not** open a single `.xml` file).
3. Browse to the **`DojoManager`** folder inside the cloned repo  
   (e.g. `C:\sandbox\ITPAT2026jlm\DojoManager`).
4. Open that project, then press the green **Run** button  
   (main class is already set to `dojomanager.DojoManagerApp`).

### If you get a `build-impl.xml` / `nbproject\nbproject` error

That means an old/broken NetBeans build file is on the laptop. Fix it like this:

1. Close the project in NetBeans.
2. In File Explorer, open the `DojoManager\nbproject` folder.
3. Delete these if they exist: `build-impl.xml`, `genfiles.properties`, and the `private` folder.
4. Pull the latest code from GitHub (or copy the fixed `nbproject` files).
5. In NetBeans: **File → Open Project** → select `DojoManager` again.

### Quick run without NetBeans

Double-click **`Start Dojo Manager.bat`** (needs Java installed and on PATH).

## Project structure (for your write-up)

```
DojoManager
├── src/dojomanager
│   ├── DojoManagerApp.java      ← starts the program
│   ├── model/
│   │   ├── Student.java         ← student data
│   │   └── KarateClass.java     ← class schedule data
│   ├── data/
│   │   └── DataManager.java     ← backend (save/load files)
│   └── gui/
│       ├── MainFrame.java       ← main window + tabs
│       ├── HomePanel.java       ← home screen
│       ├── StudentPanel.java    ← students GUI
│       └── SchedulePanel.java   ← schedule GUI
└── dojo_data/                   ← DATABASE / test data (students.dat, classes.dat, dojo_info.dat)
```

## Where is the database / test data?

Open **`dojo_data/`** inside this folder. The three `.dat` files are the
program's secondary storage:

| File | What it is |
|------|------------|
| `students.dat` | Sample students (test data) |
| `classes.dat` | Sample class timetable (test data) |
| `dojo_info.dat` | Dojo name, address, contact |

The program loads from these files on start and saves changes back into them.

## Ideas for your PAT document

- **Problem:** Dojos struggle to keep track of students and weekly class times on paper.
- **Solution:** A simple desktop program to store students and schedules in one place.
- **Users:** Sensei / dojo admin.
- **Hardware/software:** Windows PC, Java, NetBeans.
- **Testing:** Add a student, edit them, delete them, add a class, restart the app and check data is still there.

## Deadline tip (due 3 August)

You already have a working basic program. Next steps for marks:

1. Take screenshots of each screen for your documentation.
2. Write your problem statement around your real dojo.
3. Optional extras if you have time: search box, attendance list, or a simple login screen.
