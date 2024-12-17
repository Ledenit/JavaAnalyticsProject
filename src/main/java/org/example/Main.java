package org.example;

import chartCreator.StudentsToChart;
import com.opencsv.exceptions.CsvException;
//import org.example.Model.Student;
import org.example.analyzer.Analyzer;
import org.example.csvParser.CSVParser;
import org.example.db.Model.Attendance;
import org.example.db.Model.Topic;
import org.example.db.Model.Student;
import org.example.db.dbRepository;
import org.example.vkApi.VkRepository;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//
//        String filePath = "Data/java-rtf.csv";
//        String attendance = "Data/attendance.csv";
//        try {
//            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//            List<Student> students = CSVParser.CSVToStudents(filePath, attendance);
//            VkRepository vk = new VkRepository();
//            for (Student student : students) {
//                var studentInfo = vk.searchUserInfoByName(student.getName());
//                vk.printUserInfo(studentInfo);
//                System.out.println(student.toString());
//            }
//        } catch (IOException | CsvException e) {
//            System.out.println("?????? ??? ?????? CSV: " + e.getMessage());
//        }

        SwingUtilities.invokeLater(() -> {
            try {
                dbRepository studentRepository = new dbRepository();
                studentRepository.connect();

                List<Student> students = studentRepository.getAllStudents();
                JFrame frame = new JFrame("Графики успеваемости и посещаемости");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1200, 700);

                JTabbedPane tabbedPane = new JTabbedPane();
                frame.add(tabbedPane, BorderLayout.CENTER);

                StudentsToChart chart = new StudentsToChart();
                Analyzer analyzer = new Analyzer();

                for (Student student : students) {
                    List<Topic> topics = studentRepository.getTopicsByStudent(student.getUlearnID());
                    List<Attendance> attendance = studentRepository.getAttendancesByStudent(student.getUlearnID());

                    Map.Entry<XYSeriesCollection, DefaultCategoryDataset> datasets = analyzer.studentAnalyze(topics, attendance);
                    XYSeriesCollection performanceDataset = datasets.getKey();
                    DefaultCategoryDataset attendanceDataset = (DefaultCategoryDataset) datasets.getValue();

                    JPanel studentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    JFreeChart performanceChart = chart.getPerformanceChart(student.getName(), performanceDataset);
                    ChartPanel performanceChartPanel = new ChartPanel(performanceChart);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.8;
                    gbc.weighty = 1.0;
                    studentPanel.add(performanceChartPanel, gbc);

                    JFreeChart attendanceChart = chart.getAttendanceChart(student.getName(), attendanceDataset);
                    ChartPanel attendanceChartPanel = new ChartPanel(attendanceChart);
                    gbc.gridx = 2;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.2;
                    gbc.weighty = 1.0;
                    studentPanel.add(attendanceChartPanel, gbc);

                    tabbedPane.addTab(student.getName(), studentPanel);
                }


                XYSeriesCollection overallScatterDataset = analyzer.createOverallScatterPlotData(students, studentRepository);
                JFreeChart overallScatterChart = chart.createScatterPlot("Все студенты", overallScatterDataset);
                ChartPanel overallScatterPanel = new ChartPanel(overallScatterChart);
                tabbedPane.addTab("Общий график", overallScatterPanel);

                JButton overallChartButton = new JButton("Показать общий график");
                overallChartButton.addActionListener(e -> {
                    JFrame scatterFrame = new JFrame("Общий график посещаемости и успеваемости");
                    scatterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    scatterFrame.setSize(1200, 700);

                    ChartPanel chartPanel = new ChartPanel(overallScatterChart);
                    scatterFrame.add(chartPanel, BorderLayout.CENTER);

                    scatterFrame.setVisible(true);
                });

                frame.add(overallChartButton, BorderLayout.SOUTH);

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}