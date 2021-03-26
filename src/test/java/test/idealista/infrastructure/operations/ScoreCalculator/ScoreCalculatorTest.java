package test.idealista.infrastructure.operations.ScoreCalculator;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.operations.ScoreCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreCalculatorTest {

    private static String FLAT = "FLAT";
    private static String CHALET = "CHALET";
    private static String GARAGE = "GARAGE";

    private static String ATICO = "atico";
    private static String REFORMADO = "reformado";
    private static String CENTRICO = "centrico";
    private static String NUEVO = "nuevo";
    private static String LUMINOSO = "luminoso";

    private static String SHORT_DESCRIPTION = "short description";
    private static String ENOUGH_DESCRIPTION = StringUtils.repeat("*", 30);
    private static String LONG_DESCRIPTION = StringUtils.repeat("*", 50);;

    private ScoreCalculator scoreCalculator = new ScoreCalculator();

    @Test
    @DisplayName("should calculate negative score for generic empty ad")
    void shouldCalculateScoreForEmptyAd() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(CHALET);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(-10,score);
    }

    @Test
    @DisplayName("should calculate score for having a description")
    void shouldCalculateScoreForExistingDescription() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setDescription(SHORT_DESCRIPTION);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(-5,score);
    }

    @Test
    @DisplayName("should calculate score for size desription for a chalet")
    void shouldCalculateScoreForLongChaletDescription() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(CHALET);
        qualityAd.setDescription(LONG_DESCRIPTION);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(15,score);
    }

    @Test
    @DisplayName("should calculate score for size description for a flat")
    void shouldCalculateScoreForMiddleOrLongFlatDescription() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(FLAT);
        qualityAd.setDescription(LONG_DESCRIPTION);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(25,score);

        qualityAd.setDescription(ENOUGH_DESCRIPTION);
        score = calculateSpecificScore(qualityAd);
        assertEquals(5,score);
    }

    @Test
    @DisplayName("should calculate score for containing keywords in description")
    void shouldCalculateScoreForContainingKeywors() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setDescription(LUMINOSO+NUEVO+CENTRICO+REFORMADO+ATICO);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(20,score);
    }

    @Test
    @DisplayName("should calculate score for complete chalet ad")
    void shouldCalculateScoreForCompleteChalet() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(CHALET);
        qualityAd.setGardenSize(10);
        qualityAd.setHouseSize(10);
        qualityAd.setDescription(SHORT_DESCRIPTION);
        ArrayList<String> pictureUrlList = new ArrayList<>();
        pictureUrlList.add("dummyPictureUrl");
        qualityAd.setPictureUrls(pictureUrlList);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(45,score);

    }

    @Test
    @DisplayName("should calculate score for complete flat ad")
    void shouldCalculateScoreForCompleteFlat() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(FLAT);
        qualityAd.setHouseSize(10);
        qualityAd.setDescription(SHORT_DESCRIPTION);
        ArrayList<String> pictureUrlList = new ArrayList<>();
        pictureUrlList.add("dummyPictureUrl");
        qualityAd.setPictureUrls(pictureUrlList);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(45,score);
    }

    @Test
    @DisplayName("should calculate score for complete garage ad")
    void shouldCalculateScoreForCompleteGarage() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(GARAGE);
        ArrayList<String> pictureUrlList = new ArrayList<>();
        pictureUrlList.add("dummyPictureUrl");
        qualityAd.setPictureUrls(pictureUrlList);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(40,score);

        qualityAd.setDescription(SHORT_DESCRIPTION);
        score = calculateSpecificScore(qualityAd);
        assertEquals(45,score);
    }

    @Test
    @DisplayName("should calculate score for ad with existing pictures")
    void shouldCalculateScoreForExistingPictures() {

        //TODO Implement tests and functionality
        assert(false);
    }

    @Test
    @DisplayName("should calculate score for more than one ad")
    void shouldCalculateScoreForMoreThanOneAd(){
        ArrayList<QualityAd> qualityAds = new ArrayList<>();

        QualityAd qualityGarageAd = new QualityAd();
        qualityGarageAd.setTypology(GARAGE);
        ArrayList<String> pictureUrlList = new ArrayList<>();
        pictureUrlList.add("dummyPictureUrl");
        qualityGarageAd.setPictureUrls(pictureUrlList);

        QualityAd qualityFlatAd = new QualityAd();
        qualityFlatAd.setTypology(FLAT);
        qualityFlatAd.setHouseSize(10);
        qualityFlatAd.setDescription(SHORT_DESCRIPTION);
        qualityFlatAd.setPictureUrls(pictureUrlList);

        QualityAd qualityChaletAd = new QualityAd();
        qualityChaletAd.setTypology(CHALET);
        qualityChaletAd.setGardenSize(10);
        qualityChaletAd.setHouseSize(10);
        qualityChaletAd.setDescription(SHORT_DESCRIPTION);
        qualityChaletAd.setPictureUrls(pictureUrlList);

        qualityAds.add(qualityGarageAd);
        qualityAds.add(qualityFlatAd);
        qualityAds.add(qualityChaletAd);

        scoreCalculator.setQualityAdList(qualityAds);
        scoreCalculator.calculate();
        int garageScore = scoreCalculator.getQualityAdList().get(0).getScore();
        int chaletScore = scoreCalculator.getQualityAdList().get(2).getScore();
        int flatScore = scoreCalculator.getQualityAdList().get(1).getScore();

        assertEquals(40,garageScore);
        assertEquals(45,flatScore);
        assertEquals(45,chaletScore);


    }

    private int calculateSpecificScore(QualityAd qualityAd) {
        ArrayList<QualityAd> qualityAds = new ArrayList<>();
        qualityAds.add(qualityAd);
        scoreCalculator.setQualityAdList(qualityAds);
        scoreCalculator.calculate();
        return scoreCalculator.getQualityAdList().get(0).getScore();
    }
}
