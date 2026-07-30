# Dojo Manager — Specifications Document

> **How to use this file:** This is your Specifications Document draft, written
> to match the PAT rubric section 1 (15 marks). Copy it into Word/Google Docs,
> build a title page + table of contents, and replace anything in _[square
> brackets]_ with your own details. Check the reference links still work before
> you hand in.

---

## Title Page (rebuild this as page 1 in Word)

- **Project title:** Dojo Manager — Karate Student & Class Management System
- **Document:** Specifications Document
- **Learner name:** _[your name]_
- **Exam number:** _[your exam number]_
- **Subject:** Information Technology (Grade 12 PAT 2026)
- **Date:** _[date]_

## Table of Contents

1. Problem Summary
2. Research and Motivation
3. Program Functions
4. Data Required

---

## 1. Problem Summary *(Rubric 1.1 — 4 marks)*

**Purpose of the program**

Many small karate dojos still keep their student records and weekly class
timetables on paper or in scattered notebooks and spreadsheets. This makes it
easy to lose information, hard to find a student's belt or contact details
quickly, and difficult to see the week's classes at a glance. **Dojo Manager**
is a desktop application that keeps all of a dojo's students and classes in one
simple, organised place, and saves everything automatically so no information is
lost between uses.

**Summary of functions**

The program lets the dojo:

- add, edit, delete and search **students** (with their belt rank and contact
  details);
- add, edit, delete and search the weekly **class schedule**;
- see a **dashboard** with quick totals and the classes running today;
- store and edit the dojo's own **contact and location** details, and open the
  address in Google Maps; and
- **export** a printable student list and a printable weekly timetable.

**Target user group(s)**

The program is designed for **one main user group: the dojo administrator (the
sensei or a helper who runs the front desk).** This person is not necessarily a
computer expert, so the program is built to be simple, clear and hard to break.
(There is no separate "student" or "public" login — the dojo staff use the
program to manage the dojo's own information.)

---

## 2. Research and Motivation *(Rubric 1.2 — 3 marks)*

**Research — existing / similar programs**

Before building Dojo Manager, I researched existing martial-arts and dojo
management programs to see what is already available and how they work:

1. **Kicksite** — a martial-arts-specific management system that has been
   available for over a decade. It handles student records, attendance, belt
   (rank) tracking, billing and reporting, and is aimed at traditional karate
   and taekwondo schools.
2. **Martialytics** — a martial-arts club system whose main strength is its
   attendance-and-grading engine: instructors mark attendance and log belt
   progression, and the software reports on who is ready to grade. It targets
   small-to-medium clubs.
3. **Zen Planner** — a larger, "all-in-one" gym and martial-arts platform that
   adds billing, class scheduling, a member app, marketing and detailed
   reporting. It suits bigger, established or multi-discipline schools, and the
   full package (with add-ons) can cost several hundred dollars a month.

**References (Harvard style — confirm the access date on the day you submit):**

- Kicksite. 2026. *Martial Arts Software.* [Online] Available at:
  https://kicksite.com [Accessed: 21 July 2026].
- Martialytics. 2026. *Martial Arts Management Software.* [Online] Available at:
  https://www.martialytics.com [Accessed: 21 July 2026].
- Zen Planner. 2026. *Martial Arts Software.* [Online] Available at:
  https://zenplanner.com [Accessed: 21 July 2026].

**Motivation — how my program is different and why I chose it**

Those existing programs are **large, paid, online (cloud/subscription)**
systems aimed at big schools, with many features a small local dojo does not
need (billing, payment gateways, mobile apps, marketing tools). They require an
internet connection and a monthly fee, and they store data on the company's
servers.

**Dojo Manager is different because it is:**

- **free and offline** — it runs on the dojo's own computer with no
  subscription and no internet needed;
- **simple and focused** — it only does what a small dojo actually needs
  (students + timetable + contact details), so it is easy to learn; and
- **fully owned by the dojo** — the data is stored locally on their own
  computer, not on someone else's servers.

I chose this project because I train at / know a karate dojo, so it is a real
problem I understand, and it is a good fit for the PAT because it needs
meaningful data stored permanently and a clear set of backend classes to do the
processing.

---

## 3. Program Functions *(Rubric 1.3 — 5 marks)*

The functions below are all for the **dojo administrator** user group.

**A. Student management**

1. Add a new student (name, age, belt rank, phone, email).
2. Edit an existing student's details.
3. Delete a student (with a confirmation prompt).
4. Search/filter students by name or belt rank.
5. Sort the student list by clicking any column heading.
6. Validate all input (valid name, realistic age, valid phone and email).
7. Export the full student list to a readable text file.

**B. Class schedule management**

8. Add a new class (name, day, start time, instructor, belt level).
9. Edit/update an existing class.
10. Delete a class (with a confirmation prompt).
11. Search/filter classes by name, day or instructor.
12. Sort the class list by clicking any column heading.
13. Prevent clashes — warn if an instructor is already booked at that day/time.
14. Export the weekly timetable (grouped by day) to a readable text file.

**C. Dashboard / overview**

15. Show the total number of students, classes and instructors.
16. Show a list of the classes scheduled for today (based on the current date).

**D. Dojo details & location**

17. View the dojo's name, sensei, address and contact details.
18. Edit the dojo's details (validated before saving).
19. Open the dojo's address in Google Maps in the web browser.

**E. Data storage (runs automatically)**

20. Save all students, classes and dojo details permanently to file.
21. Load all saved data automatically when the program starts.

---

## 4. Data Required *(Rubric 1.4 — 3 marks)*

The program stores three groups of data permanently.

**Student data** (one record per student)

| Data item | Type | Description |
|---|---|---|
| ID | integer | Unique number for each student |
| Name | string | Student's full name |
| Age | integer | Student's age in years |
| Belt rank | string | Current belt (White → Black) |
| Phone | string | Contact phone number |
| Email | string | Contact email address |

**Class data** (one record per scheduled class)

| Data item | Type | Description |
|---|---|---|
| ID | integer | Unique number for each class |
| Class name | string | Name of the class |
| Day of week | string | Day the class runs |
| Start time | string | Time the class starts |
| Instructor | string | Who teaches the class |
| Belt level | string | Belt range the class is for |

**Dojo data** (one record for the whole dojo)

| Data item | Type | Description |
|---|---|---|
| Dojo name | string | Name of the dojo |
| Sensei name | string | Head instructor |
| Address | string | Street address |
| City | string | City / country |
| Phone | string | Dojo phone number |
| Email | string | Dojo email address |
| Notes | string | Short description of the dojo |
