package com.idealista.application.operations;

import com.idealista.application.api.QualityAd;
import com.idealista.application.repositories.PictureRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;

public class ScoreCalculator {

    private ArrayList<QualityAd> qualityAdList;

    private static final String HD = "HD";
    private static final String SD = "SD";
    private static final String FLAT = "FLAT";
    private static final String CHALET = "CHALET";
    private static final String GARAGE = "GARAGE";
    private static final int MAX_SCORE = 100;
    private static final int MIN_SCORE = 40;
    private static final String ATICO = "atico";
    private static final String REFORMADO = "reformado";
    private static final String CENTRICO = "centrico";
    private static final String NUEVO = "nuevo";
    private static final String LUMINOSO = "luminoso";

    public ArrayList<QualityAd> getQualityAdList() {
        return qualityAdList;
    }

    public void setQualityAdList(ArrayList<QualityAd> qualityAdList) {
        this.qualityAdList = qualityAdList;
    }

    public void calculate() {
        qualityAdList.forEach((qualityAd -> {
            calculateScore(qualityAd);
            updateIfIrrelevantAd(qualityAd);
        }));
    }

    private void updateIfIrrelevantAd(QualityAd qualityAd) {
        if (qualityAd.getScore() != null && qualityAd.getScore() < MIN_SCORE) {
            if (qualityAd.getIrrelevantSince() == null) {
                qualityAd.setIrrelevantSince(new Date());
            }
        }
    }

    private void calculateScore(QualityAd qualityAd) {
        int score = 0;
        score += getScoreFromPhotos(qualityAd);
        score += getScoreFromDescription(qualityAd);

        if (FLAT.equals(qualityAd.getTypology())) {
            score += getScoreFromFlatDescriptionSize(qualityAd);
        } else if (CHALET.equals(qualityAd.getTypology())) {
            score += getScoreFromChaletDescriptionSize(qualityAd);
        }

        score += getScoreFromKeyWords(qualityAd);

        if (FLAT.equals(qualityAd.getTypology())) {
            score += getScoreFromCompleteFlatAd(qualityAd);
        } else if (CHALET.equals(qualityAd.getTypology())) {
            score += getScoreFromCompleteChaletAd(qualityAd);
        } else if (GARAGE.equals(qualityAd.getTypology())) {
            score += getScoreFromCompleteGarageAd(qualityAd);
        }

        if (score <= MAX_SCORE) {
            qualityAd.setScore(score);
        } else {
            qualityAd.setScore(MAX_SCORE);
        }
    }

    private int getScoreFromCompleteGarageAd(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getPictureUrls() != null && qualityAd.getPictureUrls().size() > 0) {
            score = 40;
        }
        return score;
    }

    private int getScoreFromCompleteChaletAd(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null && qualityAd.getDescription().length() > 0 &&
                qualityAd.getPictureUrls() != null && qualityAd.getPictureUrls().size() > 0 &&
                qualityAd.getHouseSize() != null && qualityAd.getHouseSize() > 0 &&
                qualityAd.getGardenSize() != null && qualityAd.getGardenSize() > 0) {
            score = 40;
        }
        return score;
    }

    private int getScoreFromCompleteFlatAd(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null && qualityAd.getDescription().length() > 0 &&
                qualityAd.getPictureUrls() != null && qualityAd.getPictureUrls().size() > 0 &&
                qualityAd.getHouseSize() != null && qualityAd.getHouseSize() > 0) {
            score = 40;
        }
        return score;
    }

    private int getScoreFromKeyWords(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null) {
            String description = qualityAd.getDescription().toLowerCase();
            if (description.contains(LUMINOSO)) {
                score += 5;
            }
            if (description.contains(NUEVO)) {
                score += 5;
            }
            if (description.contains(CENTRICO)) {
                score += 5;
            }
            if (description.contains(REFORMADO)) {
                score += 5;
            }
            if (description.contains(ATICO)) {
                score += 5;
            }
        }

        return score;
    }

    private int getScoreFromChaletDescriptionSize(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null && qualityAd.getDescription().length() >= 50) {
            score = 20;
        }
        return score;
    }

    private int getScoreFromFlatDescriptionSize(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null) {
            if (qualityAd.getDescription().length() >= 50) {
                score = 30;
            } else if (qualityAd.getDescription().length() >= 20) {
                score = 10;
            }
        }
        return score;
    }

    private int getScoreFromDescription(QualityAd qualityAd) {
        int score = 0;
        if (qualityAd.getDescription() != null && qualityAd.getDescription().length() > 0) {
            score = 5;
        }
        return score;
    }

    private int getScoreFromPhotos(QualityAd qualityAd) {
        // 1) Sin foto -> -10 puntos. Cada foto: 20 puntos si (HD) o 10 si no.
        int score = 0;
        if (qualityAd.getPictureUrls() == null || qualityAd.getPictureUrls().size() == 0) {
            score = -10;
        } else {
            ArrayList<String> pictureUrls = new ArrayList<>(qualityAd.getPictureUrls());
            for (String pictureUrl : pictureUrls) {
                String quality = getPictureRepository().getPictureQualityByUrl(pictureUrl);
                if (HD.equals(quality)) {
                    score += 20;
                } else if (SD.equals(quality)) {
                    score += 10;
                }
            }
        }
        return score;
    }

    private PictureRepository getPictureRepository() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        PictureRepository pictureRepository = null;
        if (context != null) {
            context.scan("com.idealista.application.repositories");
            context.refresh();
            pictureRepository = context.getBean(PictureRepository.class);
        }
        return pictureRepository;
    }
}
