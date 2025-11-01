package com.example.catbasightapp;

class BeachesResort extends Destination {
    private String ownerName;
    private String ownerContact;
    private double minimumPrice;
    private double maximumPrice;

    public BeachesResort(String destinationID, String name, String description, String barangay, double distanceFromCityHall,
                          String pathMapImg, String pathDestinationImg, String urlToDirections,
                          double averageRating, String ownerName, String ownerContact, double minimumPrice,
                          double maximumPrice) {
        super(destinationID, name, description, barangay, distanceFromCityHall, pathMapImg, pathDestinationImg,
                urlToDirections, averageRating);
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

//    @Override
//    public String toString() {
//        return "BeachesResorts{" +
//                super.toString() +
//                ", ownerName='" + ownerName + '\'' +
//                ", ownerContact='" + ownerContact + '\'' +
//                ", minimumPrice=" + minimumPrice +
//                ", maximumPrice=" + maximumPrice +
//                '}';
//    }

    public double getAveragePrice() {
        return (getMaximumPrice() + getMinimumPrice()) / 2.0;
    }
}