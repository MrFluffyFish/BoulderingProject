package com.dansoft.bouldering;

import java.util.ArrayList;
import java.util.List;

public class CurrentFilters {
    private ArrayList<Integer> styleIDs;
    private ArrayList<Float> gradeBoundaries;

    public CurrentFilters() {
        this.styleIDs = new ArrayList<>();
        this.gradeBoundaries = new ArrayList<>();
    }

    public ArrayList<Integer> getStyleIDs() {
        return styleIDs;
    }
    public void setStyleIDs(ArrayList<Integer> styleIDs) {
        this.styleIDs = styleIDs;
    }

    public ArrayList<Float> getGradeBoundaries() {
        return gradeBoundaries;
    }

    public void setGradeBoundaries(ArrayList<Float> gradeBoundaries) {
        this.gradeBoundaries = gradeBoundaries;
    }
}
