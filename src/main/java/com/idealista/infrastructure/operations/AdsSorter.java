package com.idealista.infrastructure.operations;

import com.idealista.infrastructure.api.QualityAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdsSorter {

    private ArrayList<QualityAd> qualityAdList;

    public ArrayList<QualityAd> getQualityAdList() {
        return qualityAdList;
    }

    public void setQualityAdList(ArrayList<QualityAd> qualityAdList) {
        this.qualityAdList = qualityAdList;
    }

    public void sort(){
        //Calculate sorting points
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        scoreCalculator.setQualityAdList(qualityAdList);
        scoreCalculator.calculate();
        qualityAdList = scoreCalculator.getQualityAdList();

        //Sort with Collections.sort and custom comparator
        Collections.sort(qualityAdList, new ScoreComparator());
    }


}
