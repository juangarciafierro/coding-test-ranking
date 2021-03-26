package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;

import java.util.Comparator;

public class ScoreComparator implements Comparator<QualityAd> {

    @Override
    public int compare(QualityAd ad1, QualityAd ad2) {
        return (ad1.getScore() - ad2.getScore());
    }
}
