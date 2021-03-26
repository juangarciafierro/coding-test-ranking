package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.List;

import com.idealista.infrastructure.repositories.AdRepository;
import com.idealista.infrastructure.repositories.PictureRepository;
import com.idealista.infrastructure.operations.OperationUtils;
import com.idealista.infrastructure.operations.ScoreCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsController {

    private ArrayList<QualityAd> qualityAdList;

    private static int MIN_SCORE = 40;

    @GetMapping("/quality")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<PublicAd>> publicListing() {

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/score")
    public ResponseEntity<Void> calculateScore() {

        AdRepository adRepository = AdRepository.getInstance();
        qualityAdList = adRepository.getQualityAdArrayList();

        if (qualityAdList != null) {
            PictureRepository pictureRepository = PictureRepository.getInstance();
            qualityAdList = OperationUtils.addPictureUrlToAds(qualityAdList,pictureRepository.getPictureVOArrayList());

            ScoreCalculator scoreCalculator = new ScoreCalculator();
            scoreCalculator.setQualityAdList(qualityAdList);
            scoreCalculator.calculate();

            qualityAdList = scoreCalculator.getQualityAdList();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private ArrayList<QualityAd> removeIrrelevantAds() {

        ArrayList<QualityAd> qualityAds = new ArrayList<>();
        qualityAdList.forEach((qualityAd -> {
            if (qualityAd.getScore() >= MIN_SCORE )
                qualityAds.add(qualityAd);
        }));

        return qualityAds;
    }

    private ArrayList<PublicAd> getPublicAdsFromQualityAds(ArrayList<QualityAd> qualityAds) {

        ArrayList<PublicAd> relevantQualityAdList = new ArrayList<>();
        qualityAds.forEach((qualityAd -> relevantQualityAdList.add(getPublicAdFromQualityAd(qualityAd))));
        return relevantQualityAdList;
    }

    private PublicAd getPublicAdFromQualityAd(QualityAd qualityAd) {

        PublicAd publicAd = new PublicAd();

        publicAd.setDescription(qualityAd.getDescription());
        publicAd.setGardenSize(qualityAd.getGardenSize());
        publicAd.setHouseSize(qualityAd.getHouseSize());
        publicAd.setId(qualityAd.getHouseSize());
        publicAd.setTypology(qualityAd.getTypology());

        return publicAd;
    }
}
