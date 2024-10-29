package org.example.Model;

public class Attendance {
    private final int classes;
    private final int attendedClasses;

    public Attendance(int classes, int attendedClasses) {
        this.classes = classes;
        this.attendedClasses = attendedClasses;
    }

    public int getClasses() {
        return classes;
    }
    public int getAttendedClasses() {
        return attendedClasses;
    }
}
