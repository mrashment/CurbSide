package com.example.curbside;

import java.util.Comparator;

public class TruckDistanceAsc implements Comparator<Truck> {
    @Override
    public int compare(Truck o1, Truck o2) {
        if (o1.getDistance() == -1 && o2.getDistance() == -1) {
            return 0;
        } else if (o1.getDistance() == -1) {
            return 1;
        } else if (o2.getDistance() == -1) {
            return -1;
        }
        if (o1.getDistance() < o2.getDistance()) return -1;
        else return 1;
    }

}
