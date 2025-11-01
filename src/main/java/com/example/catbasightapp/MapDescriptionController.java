package com.example.catbasightapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MapDescriptionController implements Initializable {
    @FXML
    private Label barangay_placeholder;

    @FXML
    private Label description_placeholder;

    @FXML
    private Label name_placeholder;

    @FXML
    private Label distance_placeholder;

    @FXML
    private Label history_placeholder;

    @FXML
    private ImageView image_placeholder;

    @FXML
    private Label average_placeholder;

    @FXML
    private Label contact_placeholder;

    @FXML
    private Label owner_placeholder;

    @FXML
    private Label range_placeholder;

    @FXML
    private ImageView star_1;

    @FXML
    private ImageView star_2;

    @FXML
    private ImageView star_3;

    @FXML
    private ImageView star_4;

    @FXML
    private ImageView star_5;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image destination_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(SharedData.getChosenDestination().getPathDestinationImg())));

        Destination chosenDestination = SharedData.getChosenDestination();
        if (chosenDestination instanceof Landmark) {
            Landmark landmark = (Landmark) chosenDestination;

            history_placeholder.setText(landmark.getHistory());

        } else if (chosenDestination instanceof BeachesResort) {
            BeachesResort beachesResort = (BeachesResort) chosenDestination;

            String range_text = "PHP " + beachesResort.getMinimumPrice() + " - " + beachesResort.getMaximumPrice();
            range_placeholder.setText(range_text);
            average_placeholder.setText(String.valueOf(beachesResort.getAveragePrice()));
            owner_placeholder.setText(beachesResort.getOwnerName());
            contact_placeholder.setText("+63" + beachesResort.getOwnerContact());
        }

        barangay_placeholder.setText(SharedData.getChosenDestination().getBarangay());
        description_placeholder.setText(SharedData.getChosenDestination().getDescription());
        name_placeholder.setText(SharedData.getChosenDestination().getName());
        distance_placeholder.setText(String.valueOf(SharedData.getChosenDestination().getDistanceFromCityHall()));
        image_placeholder.setImage(destination_img);

        setStars();
    }

    private void setStars() {
        int visibleStars = (int) Math.round(SharedData.getChosenDestination().getAverageRating());

        star_1.setVisible(visibleStars >= 1);
        star_2.setVisible(visibleStars >= 2);
        star_3.setVisible(visibleStars >= 3);
        star_4.setVisible(visibleStars >= 4);
        star_5.setVisible(visibleStars >= 5);
    }
}
