package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.repositories.PictureRepository;

import java.util.ArrayList;
import java.util.Date;

public class ScoreCalculator {

    private ArrayList<QualityAd> qualityAdList;

    private static String HD = "HD";
    private static String SD = "SD";
    private static String FLAT = "FLAT";
    private static String CHALET = "CHALET";
    private static String GARAGE = "GARAGE";
    private static int MAX_SCORE = 100;
    private static int MIN_SCORE = 40;
    private static String ATICO = "atico";
    private static String REFORMADO = "reformado";
    private static String CENTRICO = "centrico";
    private static String NUEVO = "nuevo";
    private static String LUMINOSO = "luminoso";

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
                String quality = PictureRepository.getInstance().getPictureQualityByUrl(pictureUrl);
                if (HD.equals(quality)) {
                    score += 20;
                } else if (SD.equals(quality)) {
                    score += 10;
                }
            }
        }
        return score;
    }
}
