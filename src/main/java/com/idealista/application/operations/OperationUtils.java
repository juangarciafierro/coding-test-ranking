package com.idealista.application.operations;

import com.idealista.application.api.QualityAd;
import com.idealista.application.repositories.AdRepository;
import com.idealista.application.repositories.PictureRepository;

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
