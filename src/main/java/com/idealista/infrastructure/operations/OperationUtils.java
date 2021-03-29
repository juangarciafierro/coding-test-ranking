package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.repositories.AdRepository;
import com.idealista.infrastructure.repositories.PictureRepository;

import java.util.ArrayList;

public class OperationUtils {

    public static ArrayList<QualityAd> addPictureUrlToAds(ArrayList<QualityAd> qualityAds, AdRepository adRepository, PictureRepository pictureRepository) {
        qualityAds.forEach(qualityAd -> {
            ArrayList<Integer> pictureIdList = adRepository.getPictureIdListById(qualityAd.getId());
            if (pictureIdList != null && !pictureIdList.isEmpty()) {
                ArrayList<String> urlList = new ArrayList<>();
                pictureIdList.forEach(pictureId -> {
                    urlList.add(pictureRepository.getPictureUrlById(pictureId));
                });
                qualityAd.setPictureUrls(urlList);
            }
        });
        return qualityAds;
    }
}
