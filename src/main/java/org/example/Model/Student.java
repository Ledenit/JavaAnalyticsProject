package org.example.Model;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final String ulearnID;
    private final String group;
    private Attendance attendance;
    private final List<Topic> topics;


    public Student(String name, String ulearnId, String group){
        this.ulearnID = ulearnId;
        this.name = name;
        this.group = group;
        this.topics = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUlearnId() {
        return ulearnID;
    }

    public String getGroup() {
        return group;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }
}
