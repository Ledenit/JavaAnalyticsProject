package org.example.Model;

public class Topic {
    private final String topicName;
    private final int exercisePoints;
    private final int homeworkPoints;
    private final int controlQuestionsPoints;

    public Topic(String topicName, int exercisePoints, int homeworkPoints, int controlQuestionsPoints) {
        this.topicName = topicName;
        this.exercisePoints = exercisePoints;
        this.homeworkPoints = homeworkPoints;
        this.controlQuestionsPoints = controlQuestionsPoints;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getExercisePoints() {
        return exercisePoints;
    }

    public int getHomeworkPoints() {
        return homeworkPoints;
    }

    public int getcontrolQuestionsPoints() {
        return controlQuestionsPoints;
    }
}
