package org.example.analyzer;

import org.example.db.Model.Attendance;
import org.example.db.Model.Student;
import org.example.db.Model.Topic;
import org.example.db.dbRepository;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Analyzer {
    public Map.Entry<XYSeriesCollection, DefaultCategoryDataset> studentAnalyze(List<Topic> topics, List<Attendance> attendance) {
        XYSeries studentScores = new XYSeries("Баллы студента");
        XYSeries maxScores = new XYSeries("Максимальные баллы");
        for (int i = 0; i < topics.size(); i++) {
            Topic topic = topics.get(i);

            double studentScore = topic.getExercisePoints() + topic.getHomeworkPoints() + topic.getControlQuestionsPoints();
            double maxScore = topic.getMaxExercisePoints() + topic.getMaxHomeworkPoints() + topic.getMaxControlQuestionsPoints();

            studentScores.add(i + 1, studentScore);
            maxScores.add(i + 1, maxScore);
        }

        XYSeriesCollection scoresDataset = new XYSeriesCollection();
        scoresDataset.addSeries(studentScores);
        scoresDataset.addSeries(maxScores);

        DefaultCategoryDataset attendanceDataset = new DefaultCategoryDataset();
        attendanceDataset.addValue(attendance.getFirst().getAttendedClasses(), "Посещенные занятия", "Посетил");
        attendanceDataset.addValue(attendance.getFirst().getClasses(), "Общее количество занятий", "Всего");

        return new AbstractMap.SimpleEntry<>(scoresDataset, attendanceDataset);
    }

    public XYSeriesCollection createOverallScatterPlotData(List<Student> students, dbRepository repository) throws SQLException {
        XYSeries scatterSeries = new XYSeries("Посещаемость и баллы");

        for (Student student : students) {
            List<Attendance> attendanceList = repository.getAttendancesByStudent(student.getUlearnID());
            List<Topic> topics = repository.getTopicsByStudent(student.getUlearnID());

            double attendanceRate =  (double) attendanceList.getFirst().getAttendedClasses() / attendanceList.getFirst().getClasses();

            double totalScore = 0;

            for (Topic topic : topics) {
                totalScore += topic.getExercisePoints() + topic.getHomeworkPoints() + topic.getControlQuestionsPoints();
            }

            scatterSeries.add(attendanceRate * 100, totalScore); // Проценты
        }

        return new XYSeriesCollection(scatterSeries);
    }
}
