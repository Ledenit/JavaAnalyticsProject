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
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

                for (Student student : students) {
                    List<Topic> topics = studentRepository.getTopicsByStudent(student.getUlearnID());
                    List<Attendance> attendance = studentRepository.getAttendancesByStudent(student.getUlearnID());

                    Analyzer analyzer = new Analyzer();
                    Object[] datasets = analyzer.studentAnalyze(topics, attendance);
                    XYSeriesCollection performanceDataset = (XYSeriesCollection) datasets[0];
                    DefaultCategoryDataset attendanceDataset = (DefaultCategoryDataset) datasets[1];

                    StudentsToChart chart = new StudentsToChart();
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

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}