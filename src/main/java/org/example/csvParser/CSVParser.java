package org.example.csvParser;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.Model.Attendance;
import org.example.Model.Student;
import org.example.Model.Topic;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVParser {
    public static String Homework;
    public static String Quiz;
    public static String Exercise;

    public static Map<String, int[]> parseAttendance(String attendance) throws IOException, CsvException {
        Map<String, int[]> attendances = new HashMap<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(attendance))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            reader.readNext();
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                String studentName = line[1].trim();
                int attendedClasses = Integer.parseInt(line[7].trim());
                int classes = Integer.parseInt(line[8].trim());
                attendances.put(studentName, new int[]{classes, attendedClasses});
            }
        }

        return attendances;
    }

    public static List<Student> CSVToStudents(String filePath, String attendance) throws IOException, CsvException {
        List<Student> students = new ArrayList<>();
        List<String> topicNames = new ArrayList<>();
        List<Integer> topicInd = new ArrayList<>();
        Map<String, int[]> attendances = parseAttendance(attendance);
        Map<String, List<Integer>> topicTask = new HashMap<>();
        Map<Integer, Integer> taskMaxScore = new HashMap<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] topics = reader.readNext();
            String[] tasks = reader.readNext();
            String[] maxScores = reader.readNext();

            Exercise = tasks[3];
            Homework = tasks[4];
            Quiz = tasks[5];

            List<Integer> currentTask = new ArrayList<>();
            for (int i = 1; i < topics.length; i++) {
                String topic = topics[i].trim();
                if (!topic.isEmpty() && topic.contains(".")) {
                    if (!topicNames.isEmpty()) {
                        String preTopic = topicNames.get(topicNames.size() - 1);
                        topicTask.put(preTopic, new ArrayList<>(currentTask));
                        currentTask.clear();
                    }
                    topicNames.add(topic);
                    topicInd.add(i);
                }
                if (i >= 6) {
                    String task = tasks[i].trim();
                    if (Exercise.equals(task) || Homework.equals(task) || Quiz.equals(task)) {
                        currentTask.add(i);
                        if (!maxScores[i].trim().isEmpty() && maxScores[i].matches("\\d+")) {
                            taskMaxScore.put(i, Integer.parseInt(maxScores[i].trim()));
                        } else {
                            taskMaxScore.put(i, 0);
                        }
                    }
                }
            }

            if (!currentTask.isEmpty() && !topicNames.isEmpty()) {
                String lastTopic = topicNames.get(topicNames.size() - 1);
                topicTask.put(lastTopic, new ArrayList<>(currentTask));
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                students.add(createStudent(line, topicNames, tasks, taskMaxScore, topicTask, attendances));
            }
        }

        return students;
    }

    private static Student createStudent(String[] studentInfo, List<String> topicNames, String[] tasks,
                                         Map<Integer, Integer> taskMaxScore, Map<String, List<Integer>> topicTask,
                                         Map<String, int[]> attendances) {

        String name = studentInfo[0];
        String ulearnID = studentInfo[1];
        String group = studentInfo[2];

        Student student = new Student(name, ulearnID, group);

        if (attendances.containsKey(name)) {
            int[] attendanceData = attendances.get(name);
            student.setAttendance(new Attendance(attendanceData[0], attendanceData[1]));
        }

        for (String topicName : topicNames) {
            int[] scores = new int[3];
            List<Integer> taskInd = topicTask.get(topicName);

            if (taskInd != null) {
                for (Integer ind : taskInd) {
                    String taskType = tasks[ind].trim();
                    int points = 0;
                    String pointsStr = studentInfo[ind];
                    if (!pointsStr.isEmpty() && pointsStr.matches("\\d+")) {
                        points = Integer.parseInt(pointsStr);
                    }

                    if (Exercise.equals(taskType)) {
                        scores[0] += points;
                    } else if (Homework.equals(taskType)) {
                        scores[1] += points;
                    } else if (Quiz.equals(taskType)) {
                        scores[2] += points;
                    }
                }
            }

            int maxExercisePoints = 0;
            int maxHomeworkPoints = 0;
            int maxControlQuestionsPoints = 0;

            if (taskInd != null) {
                for (Integer taskIndex : taskInd) {
                    String taskType = tasks[taskIndex].trim();
                    int maxScore = taskMaxScore.getOrDefault(taskIndex, 0);

                    if (Exercise.equals(taskType)) {
                        maxExercisePoints = maxScore;
                    } else if (Homework.equals(taskType)) {
                        maxHomeworkPoints = maxScore;
                    } else if (Quiz.equals(taskType)) {
                        maxControlQuestionsPoints = maxScore;
                    }
                }
            }

            student.addTopic(new Topic(topicName, scores[0], scores[1], scores[2], maxExercisePoints, maxHomeworkPoints, maxControlQuestionsPoints));
        }

        return student;
    }
}
