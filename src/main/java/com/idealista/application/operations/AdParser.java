package com.idealista.application.operations;

import com.idealista.application.api.PublicAd;
import com.idealista.application.api.QualityAd;

public class AdParser {
    
    public static PublicAd parseQualityToPublicAd(QualityAd qualityAd) {
        PublicAd publicAd = new PublicAd();
        publicAd.setDescription(qualityAd.getDescription());
        publicAd.setGardenSize(qualityAd.getGardenSize());
        publicAd.setHouseSize(qualityAd.getHouseSize());
        publicAd.setId(qualityAd.getId());
        publicAd.setTypology(qualityAd.getTypology());
        publicAd.setPictureUrls(qualityAd.getPictureUrls());

        return publicAd;
    }
}
