package com.idealista.infrastructure.repositories;

import com.idealista.infrastructure.api.QualityAd;
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
            if (dataBase != null) {
                qualityAdArrayList = new ArrayList<>();
                dataBase.getAds().forEach((adVO -> qualityAdArrayList.add(convertToEntity(adVO))));
            }
        }
    }

    public ArrayList<QualityAd> getQualityAdArrayList() {
        return qualityAdArrayList;
    }

    public ArrayList<Integer> getPictureIdListById(int id) {
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
