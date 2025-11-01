package com.example.catbasightapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MainInterfaceController implements Initializable {
    @FXML
    private Button about_btn;

    @FXML
    private AnchorPane about_form;

    @FXML
    private Button list_btn;

    @FXML
    private AnchorPane list_form;

    @FXML
    private Button map_btn;

    @FXML
    private AnchorPane map_form;

    @FXML
    private ListView<Destination> destination_list;

    @FXML
    private Label destination_name;

    @FXML
    private ImageView map_placeholder;

    @FXML
    private ChoiceBox<String> destination_choices;

    @FXML
    private ChoiceBox<Integer> rating_choices;

    @FXML
    private Button submit_rating_btn;

    @FXML
    private Slider rate_slider;

    @FXML
    private Button submit_user_rating_btn;

    int user_rating_experience;

    int index = 0;

    private static final int MAX_DESCRIPTION_LENGTH = 60;

    HashMap<String, Destination> destination_rating_map = new HashMap<String, Destination>();

    Integer[] ratingChoices = {1, 2, 3, 4, 5};

    Image logo_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
            "/com/example/catbasightapp/imagefiles/catbasight_icon.png")));

    public void initialize(URL url, ResourceBundle resourceBundle) {
        SharedData.setChosenDestination(0);
        loadContent(SharedData.getChosenDestination());

        rate_slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                user_rating_experience = Math.round((float)rate_slider.getValue());
            }
        });

        loadListCell();

        for (Destination destination : SharedData.getDestinations()) {
            destination_rating_map.put(destination.getName(), destination);
        }

        rating_choices.getItems().addAll(ratingChoices);
        destination_choices.getItems().addAll(destination_rating_map.keySet());
    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == map_btn) {
            map_form.setVisible(true);
            list_form.setVisible(false);
            about_form.setVisible(false);
        } else if (event.getSource() == list_btn) {
            list_form.setVisible(true);
            map_form.setVisible(false);
            about_form.setVisible(false);
        } else if (event.getSource() == about_btn) {
            about_form.setVisible(true);
            list_form.setVisible(false);
            map_form.setVisible(false);
        }
    }

    // Initializes the List View
    private void loadListCell() {
        // Set the custom cell factory
        destination_list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Destination> call(ListView<Destination> listView) {
                return new MyListCell();
            }
        });

        // Add items to the ListView
        destination_list.getItems().addAll(SharedData.getDestinations());

        destination_list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !destination_list.getSelectionModel().isEmpty()) {
                SharedData.setChosenDestination(destination_list.getSelectionModel().getSelectedItem());

                try {
                    loadDescription(null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    static class MyListCell extends ListCell<Destination> {
        @Override
        protected void updateItem(Destination item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                // Create an HBox to hold the image, title, and description
                HBox cellBox = new HBox(10); // Adjust spacing between elements
                cellBox.setAlignment(Pos.CENTER_LEFT);

                // Create an ImageView for the image
                String imagePath = item.getPathDestinationImg();
                ImageView imageView = new ImageView(
                        new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
                imageView.setFitWidth(100); // Set the width as needed
                imageView.setFitHeight(100); // Set the height as needed

                // Create a VBox to hold the title and description
                VBox textContainer = new VBox(5); // Adjust spacing between title and description
                textContainer.setAlignment(Pos.CENTER_LEFT);

                // Set the text for the title and description
                Label titleText = new Label(item.getName());
                titleText.setStyle("-fx-font-weight: bold;");

                // Limit description to 30 characters and append "..."
                String originalDescription = item.getDescription();
                String truncatedDescription = originalDescription.length() > MAX_DESCRIPTION_LENGTH ?
                        originalDescription.substring(0, MAX_DESCRIPTION_LENGTH) + "..." :
                        originalDescription;

                Label descriptionText = new Label(truncatedDescription);
                descriptionText.setFont(new Font("Arial", 12));

                // Create a Hbox for Rating
                HBox ratingContainer = new HBox(5);
                ratingContainer.setAlignment(Pos.CENTER_LEFT);

                // Rating Image and Text
                String starImgPath = "/com/example/catbasightapp/imagefiles/star_icon.png";
                ImageView starImgView = new ImageView(
                        new Image(Objects.requireNonNull(getClass().getResource(starImgPath)).toExternalForm()));
                starImgView.setFitWidth(25); // Set the width as needed
                starImgView.setFitHeight(25); // Set the height as needed

                Label averageText = new Label(String.format("%.2f", item.getAverageRating()));
                averageText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

                // Add the Rating Image and Text to the HBox
                ratingContainer.getChildren().addAll(starImgView, averageText);

                // Add the title and description labels to the VBox
                textContainer.getChildren().addAll(titleText, descriptionText, ratingContainer);

                // Add the image and VBox to the HBox
                cellBox.getChildren().addAll(imageView, textContainer);

                // Set the HBox as the graphic of the cell
                setGraphic(cellBox);
            }
        }
    }

    // Initializes the map content
    public void loadContent(Destination chosen_destination) {
        destination_name.setText(chosen_destination.getName());

        Image destination_img = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(chosen_destination.getPathMapImg())));
        map_placeholder.setImage(destination_img);
    }

    // This function changes the list -> observable list to sort it real-time
    private void refreshListView(List<Destination> sortedDestinations) {
        ObservableList<Destination> observableDestinations = FXCollections.observableArrayList(sortedDestinations);
        destination_list.setItems(observableDestinations);
    }

    @FXML
    void sortByAlphabetically(ActionEvent event) {
        List<Destination> sortedList = new ArrayList<>(SharedData.getDestinations());
        sortedList.sort(Comparator.comparing(Destination::getName));
        refreshListView(sortedList);
    }

    @FXML
    void sortByRating(ActionEvent event) {
        List<Destination> sortedList = new ArrayList<>(SharedData.getDestinations());
        sortedList.sort(Comparator.comparingDouble(Destination::getAverageRating).reversed());
        refreshListView(sortedList);
    }

    @FXML
    void backDestination(ActionEvent event) {
        index--;

        if (index >= 0) {
            SharedData.setChosenDestination(index);
            loadContent(SharedData.getChosenDestination());
        } else {
            index++;
        }
    }

    @FXML
    void loadCredits(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("appCredits.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.getIcons().add(logo_img);
        stage.setTitle("CatbaSight Credits");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loadDescription(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        if (SharedData.getChosenDestination() instanceof Landmark) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("landmarkDescription.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.getIcons().add(logo_img);
            stage.setTitle("CatbaSight Landmark Description");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                    "/com/example/catbasightapp/beachesResortDescription.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.getIcons().add(logo_img);
            stage.setTitle("CatbaSight Beach / Resort Description");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    void loadDirections(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mapDirections.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Google Maps Directions to " + SharedData.getChosenDestination().getName());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void nextDestination(ActionEvent event) {
        index++;

        if (index < SharedData.getDestinations().size()) {
            SharedData.setChosenDestination(index);
            loadContent(SharedData.getChosenDestination());
        } else {
            index--;
        }
    }

    @FXML
    void submitRatingDestination(ActionEvent event) {
        submit_rating_btn.setVisible(false);

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String sql;

            Destination chosenUserDestination = destination_rating_map.get(destination_choices.getValue());
            String ID = chosenUserDestination.getDestinationID();

            sql = "SELECT MAX(RatingID) FROM catbasight_db.destinationratings";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int maximumRatingID = resultSet.getInt(1) + 1;

                String rating = String.valueOf(rating_choices.getValue());

                sql = "INSERT INTO catbasight_db.destinationratings (RatingID, DestinationID, RatingValue) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, maximumRatingID);
                preparedStatement.setString(2, ID);
                preparedStatement.setString(3, rating);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null Entries");
        }
    }

    @FXML
    void submitUserRating(ActionEvent event) {
        submit_user_rating_btn.setVisible(false);

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String sql;

            sql = "SELECT MAX(UserRatingID) FROM catbasight_db.ratingexperience";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int maximumRatingID = resultSet.getInt(1) + 1;

                String rating = String.valueOf(rating_choices.getValue());

                sql = "INSERT INTO catbasight_db.ratingexperience (UserRatingID, UserRating) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, maximumRatingID);
                preparedStatement.setInt(2, user_rating_experience);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null Entries");
        }


    }
}