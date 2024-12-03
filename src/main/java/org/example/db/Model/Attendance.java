package org.example.db.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "attendance")
public class Attendance {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int classes;

    @DatabaseField
    private int attendedClasses;

    @DatabaseField(canBeNull = true, foreign = true, foreignColumnName = "ulearnID", columnName = "student_ulearnID")
    private Student student;

    public Attendance() {
    }

    public Attendance(int classes, int attendedClasses) {
        this.classes = classes;
        this.attendedClasses = attendedClasses;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getClasses() {
        return classes;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }
}

