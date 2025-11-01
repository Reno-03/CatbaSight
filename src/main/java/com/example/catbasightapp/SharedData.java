package com.example.catbasightapp;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private static final List<Destination> destinations = new ArrayList<>();
    private static Destination chosenDestination;

    public static List<Destination> getDestinations() {
        return destinations;
    }

    public static void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public static void clearDestinations() {
        destinations.clear();
    }

    public static void setChosenDestination(int index) {
        chosenDestination = destinations.get(index);
    }

    public static void setChosenDestination(Destination destination) {
        chosenDestination = destination;
    }

    public static Destination getChosenDestination() {
        return chosenDestination;
    }
}