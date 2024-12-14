package edu.gatech.seclass.jobcompare6300;

import java.util.Comparator;

public class OfferScoreComparator implements Comparator<Offer> {

    private ComparisonSettings settings;

    public OfferScoreComparator(ComparisonSettings settings) {
        this.settings = settings;
    }

    @Override
    public int compare(Offer o1, Offer o2) {
        return Double.compare(o1.calculateScore(this.settings), o2.calculateScore(this.settings));
    }
}
