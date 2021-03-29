package test.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.operations.ScoreComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreComparatorTest {

    private static final int MAX_SCORE = 100;
    private static final int STEP = 10;

    @Test
    @DisplayName("should sort ads from lowest to highest score when used in Collections.sort")
    void shouldSortAdsFromLowestToHighestScore() {
        ArrayList<QualityAd> qualityAdArrayList = new ArrayList<>();

        for (int score = 0; score <= MAX_SCORE; score += STEP){
            QualityAd qualityAd = new QualityAd();
            qualityAd.setScore(score);
            qualityAdArrayList.add(qualityAd);
        }
        Collections.sort(qualityAdArrayList, new ScoreComparator());

        for (int score = 0, index = 0; score <= MAX_SCORE; score += STEP, index++) {
            int scoreFromSortedAd = qualityAdArrayList.get(index).getScore();
            assertEquals(score,scoreFromSortedAd);
        }
    }
}
