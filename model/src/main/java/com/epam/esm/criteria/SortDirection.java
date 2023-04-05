package com.epam.esm.criteria;

public enum SortDirection {
    DATE(0), NAME(0);

    public final Integer rate;

    SortDirection(Integer rate) {
        this.rate = rate;
    }

    public boolean isAsc() {
        return rate > 0;
    }

    public boolean isDesc() {
        return rate < 0;
    }

    public boolean isUnsorted() {
        return rate == 0;
    }
}
