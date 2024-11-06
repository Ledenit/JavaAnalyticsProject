package org.example.Model;

import java.util.HashMap;
import java.util.Map;

public class TopicScores {
    private final Map<String, MaxScores> topicMaxScores;

    public TopicScores() {
        topicMaxScores = new HashMap<>();
    }

    public void addTopicMaxScores(String topicName, int maxExercisePoints, int maxHomeworkPoints, int maxControlQuestionsPoints) {
        topicMaxScores.put(topicName, new MaxScores(maxExercisePoints, maxHomeworkPoints, maxControlQuestionsPoints));
    }

    public MaxScores getMaxScoresForTopic(String topicName) {
        return topicMaxScores.get(topicName);
    }

    public static class MaxScores {
        private final int maxExercisePoints;
        private final int maxHomeworkPoints;
        private final int maxControlQuestionsPoints;

        public MaxScores(int maxExercisePoints, int maxHomeworkPoints, int maxControlQuestionsPoints) {
            this.maxExercisePoints = maxExercisePoints;
            this.maxHomeworkPoints = maxHomeworkPoints;
            this.maxControlQuestionsPoints = maxControlQuestionsPoints;
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
    }
}
