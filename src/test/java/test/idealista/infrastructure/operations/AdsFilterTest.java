package test.idealista.infrastructure.operations;

import com.idealista.application.api.QualityAd;
import com.idealista.application.operations.AdsFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class AdsFilterTest {

    private static final int ADS_QUANTITY = 4;

    private AdsFilter adsFilter = new AdsFilter();

    @BeforeEach
    void generateRelevantAndIrrelevantAds() {
        ArrayList<QualityAd> qualityAdArrayList = new ArrayList<>();

        for (int quantity = 0; quantity < ADS_QUANTITY; quantity++) {
            QualityAd qualityAd = new QualityAd();
            if (quantity >= ADS_QUANTITY / 2) {
                qualityAd.setIrrelevantSince(new Date());
            }
            qualityAdArrayList.add(qualityAd);
        }
        adsFilter.setQualityAdList(qualityAdArrayList);
    }

    @Test
    @DisplayName("should return relevant ads when getRelevantAds() is called")
    void shouldReturnRelevantAdsWhenMethodCalled() {
        ArrayList<QualityAd> relevantQualityAdList = adsFilter.getRelevantAds();
        relevantQualityAdList.forEach(qualityAd -> {
            assert(qualityAd.getIrrelevantSince() == null);
        });
        assert(relevantQualityAdList.size() == 2);
    }

    @Test
    @DisplayName("should return irrelevant ads when getIrrelevantAds() is called")
    void shouldReturnIrrelevantAdsWhenMethodCalled() {
        ArrayList<QualityAd> irrelevantQualityAdList = adsFilter.getIrrelevantAds();
        irrelevantQualityAdList.forEach(qualityAd -> {
            assert(qualityAd.getIrrelevantSince() != null);
        });
        assert(irrelevantQualityAdList.size() == 2);
    }
}
