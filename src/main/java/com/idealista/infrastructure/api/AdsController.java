package com.idealista.infrastructure.api;

import com.idealista.infrastructure.operations.*;
import com.idealista.infrastructure.repositories.AdRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {

    private ArrayList<QualityAd> qualityAdList;

    @GetMapping("/quality")
    public ResponseEntity<List<QualityAd>> qualityListing() {

        if (qualityAdList != null && !qualityAdList.isEmpty()) {
            AdsFilter adsFilter = new AdsFilter();
            adsFilter.setQualityAdList(qualityAdList);
            return ResponseEntity.ok(adsFilter.getIrrelevantAds());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<PublicAd>> publicListing() {

        if (qualityAdList != null && !qualityAdList.isEmpty()) {
            Collections.sort(qualityAdList, new ScoreComparator());
            Collections.reverse(qualityAdList);
            AdsFilter adsFilter = new AdsFilter();
            adsFilter.setQualityAdList(qualityAdList);
            ArrayList<QualityAd> relevantQualityAds = adsFilter.getRelevantAds();
            ArrayList<PublicAd> relevantPublicAds = new ArrayList<>();
            relevantQualityAds.forEach((qualityAd -> {
                relevantPublicAds.add(AdParser.parseQualityToPublicAd(qualityAd));
            }));
            return ResponseEntity.ok(relevantPublicAds);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/score")
    public ResponseEntity<Void> calculateScore() {

        AdRepository adRepository = AdRepository.getInstance();
        qualityAdList = adRepository.getQualityAdArrayList();
        if (qualityAdList != null) {
            qualityAdList = OperationUtils.addPictureUrlToAds(qualityAdList);
            ScoreCalculator scoreCalculator = new ScoreCalculator();
            scoreCalculator.setQualityAdList(qualityAdList);
            scoreCalculator.calculate();
            qualityAdList = scoreCalculator.getQualityAdList();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
