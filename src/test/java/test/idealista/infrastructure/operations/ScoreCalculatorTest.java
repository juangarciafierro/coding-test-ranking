package test.idealista.infrastructure.operations;

import com.idealista.application.api.QualityAd;
import com.idealista.application.operations.ScoreCalculator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoreCalculatorTest {

    private static final String FLAT = "FLAT";
    private static final String CHALET = "CHALET";
    private static final String GARAGE = "GARAGE";

    private static final String ATICO = "atico";
    private static final String REFORMADO = "reformado";
    private static final String CENTRICO = "centrico";
    private static final String NUEVO = "nuevo";
    private static final String LUMINOSO = "luminoso";

    private static final String SHORT_DESCRIPTION = "short description";
    private static final String ENOUGH_DESCRIPTION = StringUtils.repeat("*", 30);
    private static final String LONG_DESCRIPTION = StringUtils.repeat("*", 50);;

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

    @Test
    @DisplayName("should update irrelevant date if score is lower tan MIN_SCORE and date is not set")
    void shouldUpdateIrrelevantDateIfScoreIsLowAndDateNotSet() {
        QualityAd qualityAd = new QualityAd();
        int score = calculateSpecificScore(qualityAd);
        assertEquals(-10,score);
        Date date = qualityAd.getIrrelevantSince();
        assertNotNull(date);
    }

    @Test
    @DisplayName("should not update irrelevant date if score is lower tan MIN_SCORE but date is set")
    void shouldNotUpdateIrrelevantDateIfScoreIsLowButDateIsSet() {
        Date expectedDate = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        Date anotherDate = new Date();
        QualityAd qualityAd = new QualityAd();
        qualityAd.setIrrelevantSince(expectedDate);
        int score = calculateSpecificScore(qualityAd);
        assertEquals(-10,score);
        Date date = qualityAd.getIrrelevantSince();
        assertNotNull(date);
        assertEquals(expectedDate,date);
    }

    private int calculateSpecificScore(QualityAd qualityAd) {
        ArrayList<QualityAd> qualityAds = new ArrayList<>();
        qualityAds.add(qualityAd);
        scoreCalculator.setQualityAdList(qualityAds);
        scoreCalculator.calculate();
        return scoreCalculator.getQualityAdList().get(0).getScore();
    }
}
