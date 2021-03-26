package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.PictureVO;

import java.util.ArrayList;

public class OperationUtils {

    public static ArrayList<QualityAd> addPictureUrlToAds(ArrayList<QualityAd> qualityAds, ArrayList<PictureVO> pictureVOArrayList) {

        for (int i = 0; i < qualityAds.size(); i++) {
            ArrayList<String> urlList = new ArrayList<>();
            urlList.add(pictureVOArrayList.get(i).getUrl());
            qualityAds.get(i).setPictureUrls(urlList);
        }

        return qualityAds;
    }
}
