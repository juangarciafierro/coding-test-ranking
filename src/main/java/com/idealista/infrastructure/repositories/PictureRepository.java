package com.idealista.infrastructure.repositories;

import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class PictureRepository {

    private static PictureRepository INSTANCE;
    private ArrayList<PictureVO> pictureVOArrayList;

    private PictureRepository() {
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

    public static PictureRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PictureRepository();
        }
        return INSTANCE;
    }

    public ArrayList<PictureVO> getPictureVOArrayList() {
        return pictureVOArrayList;
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
}
