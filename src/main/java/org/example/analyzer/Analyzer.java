package org.example.analyzer;

import org.example.db.Model.Attendance;
import org.example.db.Model.Topic;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class Analyzer {
    public Object[] studentAnalyze(List<Topic> topics, List<Attendance> attendance) {
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

        return new Object[]{scoresDataset, attendanceDataset};
    }
}
