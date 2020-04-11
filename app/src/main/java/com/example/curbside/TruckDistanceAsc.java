package com.example.curbside;

import java.util.Comparator;

public class TruckDistanceAsc implements Comparator<Truck> {
    @Override
    public int compare(Truck o1, Truck o2) {
        if (o1.getDistance() < o2.getDistance()) return -1;
        else return 1;
    }

}
