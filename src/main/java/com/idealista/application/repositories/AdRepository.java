package com.idealista.application.repositories;

import com.idealista.application.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AdRepository {

    private ArrayList<QualityAd> qualityAdArrayList;
    private InMemoryPersistence dataBase;

    public AdRepository() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        if (context != null) {
            context.scan("com.idealista.infrastructure.persistence");
            context.refresh();
            dataBase = context.getBean(InMemoryPersistence.class);
        }
    }

    public ArrayList<QualityAd> getQualityAdArrayList() {
        if (dataBase != null) {
            qualityAdArrayList = new ArrayList<>();
            dataBase.getAds().forEach((adVO -> qualityAdArrayList.add(convertToEntity(adVO))));
        }
        return qualityAdArrayList;
    }

    public ArrayList<Integer> getPictureIdListById(int id) {
        if (dataBase != null) {
            qualityAdArrayList = new ArrayList<>();
            dataBase.getAds().forEach((adVO -> qualityAdArrayList.add(convertToEntity(adVO))));
        }
        ArrayList<Integer> pictureIdList = null;
        if (dataBase.getAdById(id) != null) {
            pictureIdList = new ArrayList(dataBase.getAdById(id).getPictures());
        }
        return pictureIdList;
    }

    private QualityAd convertToEntity(AdVO adVO) {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setDescription(adVO.getDescription());
        qualityAd.setGardenSize(adVO.getGardenSize());
        qualityAd.setHouseSize(adVO.getHouseSize());
        qualityAd.setId(adVO.getId());
        qualityAd.setTypology(adVO.getTypology());
        qualityAd.setIrrelevantSince(adVO.getIrrelevantSince());
        qualityAd.setScore(adVO.getScore());

        return qualityAd;
    }
}
