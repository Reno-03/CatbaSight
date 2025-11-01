package com.example.catbasightapp;

public class Destination {
    private String destinationID;
    private String name;
    private String description;
    private String barangay;
    private double distanceFromCityHall;
    private String pathMapImg;
    private String pathDestinationImg;
    private String urlToDirections;
    private double averageRating;

    public Destination(String destinationID, String name, String description, String barangay, double distanceFromCityHall,
                       String pathMapImg, String pathDestinationImg, String urlToDirections,
                       double averageRating) {
        this.destinationID = destinationID;
        this.name = name;
        this.description = description;
        this.barangay = barangay;
        this.distanceFromCityHall = distanceFromCityHall;
        this.pathMapImg = pathMapImg;
        this.pathDestinationImg = pathDestinationImg;
        this.urlToDirections = urlToDirections;
        this.averageRating = averageRating;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBarangay() {
        return barangay;
    }

    public double getDistanceFromCityHall() {
        return distanceFromCityHall;
    }

    public String getPathMapImg() {
        return pathMapImg;
    }

    public String getPathDestinationImg() {
        return pathDestinationImg;
    }

    public String getUrlToDirections() {
        return urlToDirections;
    }

    public double getAverageRating() {
        return averageRating;
    }

//    @Override
//    public String toString() {
//        return "Destination{" +
//                "destinationID=" + destinationID +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", barangay='" + barangay + '\'' +
//                ", distanceFromCityHall=" + distanceFromCityHall +
//                ", pathMapImg='" + pathMapImg + '\'' +
//                ", pathDestinationImg='" + pathDestinationImg + '\'' +
//                ", urlToDirections='" + urlToDirections + '\'' +
//                ", averageRating=" + averageRating +
//                '}';
//    }
}
