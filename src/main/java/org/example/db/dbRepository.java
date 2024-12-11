package org.example.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.example.db.Model.Attendance;
import org.example.db.Model.Student;
import org.example.db.Model.Topic;

import java.sql.SQLException;
import java.util.List;


public class dbRepository {
    private  final String URL = "jdbc:sqlite:C:\\Users\\georg\\sqllite\\students.db";

    private Dao<Student, String> studentDao = null;
    private Dao<Topic, Integer> topicDao = null;
    private Dao<Attendance, Integer> attendanceDao = null;
    private ConnectionSource connectionSource = null;

    public void connect() throws SQLException{
        connectionSource = new JdbcConnectionSource(URL);
        studentDao = DaoManager.createDao(connectionSource, Student.class);
        topicDao = DaoManager.createDao(connectionSource, Topic.class);
        attendanceDao = DaoManager.createDao(connectionSource, Attendance.class);
    }

    public void createTable() throws SQLException{
        TableUtils.createTable(connectionSource, Student.class);
        TableUtils.createTable(connectionSource, Topic.class);
        TableUtils.createTable(connectionSource, Attendance.class);
    }

    public void saveStudent(org.example.Model.Student student) throws SQLException {
        Student newStudent = new Student(student.getUlearnId(), student.getName(), student.getGroup());
        if (student.getAttendance() != null) {
            Attendance newAttendance = new Attendance(student.getAttendance().getClasses(), student.getAttendance().getAttendedClasses());
            newAttendance.setStudent(newStudent);
            attendanceDao.create(newAttendance);
        }

        for (org.example.Model.Topic topic : student.getTopics()) {
            Topic newTopic = new Topic(topic.getTopicName(), topic.getExercisePoints(),
                    topic.getHomeworkPoints(), topic.getcontrolQuestionsPoints(),
                    topic.getMaxExercisePoints(), topic.getMaxHomeworkPoints(), topic.getMaxControlQuestionsPoints());
            newTopic.setStudent(newStudent);
            topicDao.create(newTopic);
        }

        studentDao.create(newStudent);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDao.queryForAll();
    }

    public void close() throws Exception {
        connectionSource.close();
    }
}
