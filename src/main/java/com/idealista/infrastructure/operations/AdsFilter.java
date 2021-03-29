package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;

import java.util.ArrayList;

public class AdsFilter {

    private ArrayList<QualityAd> qualityAdList;

    public void setQualityAdList(ArrayList<QualityAd> qualityAdList) {
        this.qualityAdList = qualityAdList;
    }

    public ArrayList<QualityAd> getRelevantAds() {
        ArrayList<QualityAd> relevantAds = new ArrayList<>();
        qualityAdList.forEach((qualityAd -> {
            if (qualityAd.getIrrelevantSince() == null) {
                relevantAds.add(qualityAd);
            }
        }));
        return relevantAds;
    }

    public ArrayList<QualityAd> getIrrelevantAds() {
        ArrayList<QualityAd> irrelevantAds = new ArrayList<>();
        qualityAdList.forEach((qualityAd -> {
            if (qualityAd.getIrrelevantSince() != null) {
                irrelevantAds.add(qualityAd);
            }
        }));
        return irrelevantAds;
    }
}
