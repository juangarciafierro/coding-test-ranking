package com.idealista.infrastructure.api.repositories;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class AdRepository {

    private static AdRepository INSTANCE;
    private ArrayList<QualityAd> qualityAdArrayList;

    private AdRepository() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        if (context != null) {
            context.scan("com.idealista.infrastructure.persistence");
            context.refresh();
            InMemoryPersistence dataBase = context.getBean(InMemoryPersistence.class);
            if (dataBase != null) {
                qualityAdArrayList = new ArrayList<>();
                dataBase.getAds().forEach((adVO -> qualityAdArrayList.add(convertToEntity(adVO))));
            }
        }
    }

    public static AdRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdRepository();
        }
        return INSTANCE;
    }

    private QualityAd convertToEntity(AdVO adVO) {
        // TODO Create factory
        QualityAd qualityAd = new QualityAd();
        qualityAd.setDescription(adVO.getDescription());
        qualityAd.setGardenSize(adVO.getGardenSize());
        qualityAd.setHouseSize(adVO.getHouseSize());
        qualityAd.setId(adVO.getHouseSize());
        qualityAd.setTypology(adVO.getTypology());
        qualityAd.setIrrelevantSince(adVO.getIrrelevantSince());
        qualityAd.setScore(adVO.getScore());

        return qualityAd;
    }

    public ArrayList<QualityAd> getQualityAdArrayList() {
        return qualityAdArrayList;
    }
}
