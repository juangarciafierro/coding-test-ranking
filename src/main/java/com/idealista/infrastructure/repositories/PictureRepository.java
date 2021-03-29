package com.idealista.infrastructure.repositories;

import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PictureRepository {

    private ArrayList<PictureVO> pictureVOArrayList;

    public PictureRepository() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        if (context != null) {
            context.scan("com.idealista.infrastructure.persistence");
            context.refresh();
            InMemoryPersistence dataBase = context.getBean(InMemoryPersistence.class);
            if (dataBase != null) {
                pictureVOArrayList = new ArrayList<>(dataBase.getPictures());
            }
        }
    }

    public String getPictureQualityByUrl(String pictureUrl) {
        String quality = null;
        for (PictureVO pictureVO : pictureVOArrayList ) {
            if (pictureVO.getUrl().contains(pictureUrl)) {
                quality = pictureVO.getQuality();
            }
        }
        return quality;
    }

    public String getPictureUrlById(int id) {
        for (PictureVO pictureVO : pictureVOArrayList ) {
            if (pictureVO.getId() == id ) {
                return pictureVO.getUrl();
            }
        }
        return null;
    }
}
