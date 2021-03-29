package com.idealista.test.application.operations;

import com.idealista.application.api.PublicAd;
import com.idealista.application.api.QualityAd;
import com.idealista.application.operations.AdParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdParserTest {

    private static final String DESCRIPTION = "Description";
    private static final String CHALET = "CHALET";
    private static final int DUMMY_NUMBER = 10;

    @Test
    @DisplayName("should return parsed public ad from quality ad when method called")
    void shouldReturn() {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setTypology(CHALET);
        qualityAd.setGardenSize(DUMMY_NUMBER);
        qualityAd.setHouseSize(DUMMY_NUMBER);
        qualityAd.setDescription(DESCRIPTION);
        qualityAd.setScore(DUMMY_NUMBER);

        PublicAd parsedPublicAd = AdParser.parseQualityToPublicAd(qualityAd);

        assertEquals(qualityAd.getTypology(),parsedPublicAd.getTypology());
        assertEquals(qualityAd.getGardenSize(),parsedPublicAd.getGardenSize());
        assertEquals(qualityAd.getHouseSize(),parsedPublicAd.getHouseSize());
        assertEquals(qualityAd.getDescription(),parsedPublicAd.getDescription());
    }
}
