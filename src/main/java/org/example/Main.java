package org.example;

import com.opencsv.exceptions.CsvException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.example.Model.Student;
import org.example.csvParser.CSVParser;
import org.example.vkApi.VkRepository;


import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "Data/java-rtf.csv";
        String attendance = "Data/attendance.csv";
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            List<Student> students = CSVParser.CSVToStudents(filePath, attendance);
            VkRepository vk = new VkRepository();
            for (Student student : students) {
                var studentInfo = vk.searchUserInfoByName(student.getName());
                vk.printUserInfo(studentInfo);
                System.out.println(student.toString());
            }
        } catch (IOException | CsvException e) {
            System.out.println("Ошибка при чтении CSV: " + e.getMessage());
        }
    }
}