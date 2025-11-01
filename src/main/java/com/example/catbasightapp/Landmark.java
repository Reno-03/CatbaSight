package com.example.catbasightapp;

class Landmark extends Destination {
    private String history;

    public Landmark(String destinationID, String name, String description, String barangay, double distanceFromCityHall,
                    String pathMapImg, String pathDestinationImg, String urlToDirections,
                    double averageRating, String history) {
        super(destinationID, name, description, barangay, distanceFromCityHall, pathMapImg, pathDestinationImg,
                urlToDirections, averageRating);
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

//    @Override
//    public String toString() {
//        return "Landmark{" +
//                super.toString() +
//                ", history='" + history + '\'' +
//                '}';
//    }
}