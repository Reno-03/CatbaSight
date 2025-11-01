package com.example.catbasightapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image logo_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/com/example/catbasightapp/imagefiles/catbasight_icon.png")));

        // CALL TO FETCH: SQL DATABASE -> OOP DATA STRUCTURE
        fetchDestinationsFromDatabase();

        // initialize the window
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CatbaSight");
        stage.getIcons().add(logo_img);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void fetchDestinationsFromDatabase() {

        // WE NEED TO ACCESS TWO TABLES
        List<String> tables = Arrays.asList("catbasight_db.landmarks", "catbasight_db.beachesResorts");

        DatabaseConnection connectNow = new DatabaseConnection();

        try {
            for (String table : tables) {
                // CREATE A CONNECTION
                try (Connection connection = connectNow.getConnection()) {
                    String sql = "SELECT * FROM " + table;

                    // EXECUTE THE STATEMENT TO FETCH DETAILS
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                         ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            String destinationID = resultSet.getString("DestinationID");
                            String name = resultSet.getString("Name");
                            String description = resultSet.getString("Description");
                            String barangay = resultSet.getString("Barangay");
                            double distanceFromCityHall = resultSet.getDouble("DistanceFromCityHall");
                            String pathMapImg = resultSet.getString("PathMapImg");
                            String pathDestinationImg = resultSet.getString("PathDestinationImg");
                            String urlToDirections = resultSet.getString("URLToDirections");

                            // EXECUTE THE STATEMENT TO FETCH AVERAGE RATING
                            String rating_sql = "SELECT AVG(RatingValue) AS AverageRating FROM " +
                                    "catbasight_db.destinationratings WHERE DestinationID = ?";
                            try (PreparedStatement preparedStatementRating = connection.prepareStatement(rating_sql)) {

                                // CHANGE THE 1ST ? PARAMETER AS THE DESTINATIONID
                                preparedStatementRating.setString(1, destinationID);

                                try (ResultSet resultSetRating = preparedStatementRating.executeQuery()) {
                                    if (resultSetRating.next()) {
                                        // FETCH THE AVERAGE RATING BASED ON DESTINATION ID
                                        double averageRating = resultSetRating.getDouble("AverageRating");

                                        // ADD THE DESTINATION IF IT IS A LANDMARK
                                        if (destinationID.startsWith("LDM")) {
                                            String history = resultSet.getString("History");

                                            Landmark landmark = new Landmark(destinationID, name, description, barangay,
                                                    distanceFromCityHall, pathMapImg, pathDestinationImg,
                                                    urlToDirections, averageRating, history);

                                            // ADD TO THE DATA STRUCTURE
                                            SharedData.addDestination(landmark);
                                        }

                                        // ADD THE DESTINATION IF IT IS A BEACHESRESORT
                                        else {
                                            String ownerName = resultSet.getString("OwnerName");
                                            String ownerContact = resultSet.getString("OwnerContact");
                                            double minimumPrice = resultSet.getDouble("MinimumPrice");
                                            double maximumPrice = resultSet.getDouble("MaximumPrice");

                                            BeachesResort myBeachesResort = new BeachesResort(destinationID, name,
                                                    description, barangay, distanceFromCityHall, pathMapImg,
                                                    pathDestinationImg, urlToDirections, averageRating, ownerName,
                                                    ownerContact, minimumPrice, maximumPrice);

                                            // ADD TO THE DATA STRUCTURE
                                            SharedData.addDestination(myBeachesResort);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    // Handle or log the exception appropriately
                    e.printStackTrace();
                }
            }
        } finally {
            ;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}