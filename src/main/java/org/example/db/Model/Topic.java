package org.example.db.Model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "topics")
public class Topic {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String topicName;

    @DatabaseField
    private int exercisePoints;

    @DatabaseField
    private int homeworkPoints;

    @DatabaseField
    private int controlQuestionsPoints;

    @DatabaseField
    private int maxExercisePoints;

    @DatabaseField
    private int maxHomeworkPoints;

    @DatabaseField
    private int maxControlQuestionsPoints;

    @DatabaseField(canBeNull = true, foreign = true, foreignColumnName = "ulearnID", columnName = "student_ulearnID")
    private Student student;

    public Topic() {
    }

    public Topic(String topicName, int exercisePoints, int homeworkPoints, int controlQuestionsPoints,
                       int maxExercisePoints, int maxHomeworkPoints, int maxControlQuestionsPoints) {
        this.topicName = topicName;
        this.exercisePoints = exercisePoints;
        this.homeworkPoints = homeworkPoints;
        this.controlQuestionsPoints = controlQuestionsPoints;
        this.maxExercisePoints = maxExercisePoints;
        this.maxHomeworkPoints = maxHomeworkPoints;
        this.maxControlQuestionsPoints = maxControlQuestionsPoints;
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

    public int getMaxExercisePoints() {
        return maxExercisePoints;
    }

    public int getMaxHomeworkPoints() {
        return maxHomeworkPoints;
    }

    public int getMaxControlQuestionsPoints() {
        return maxControlQuestionsPoints;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
