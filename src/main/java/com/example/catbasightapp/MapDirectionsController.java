package com.example.catbasightapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MapDirectionsController implements Initializable {

    @FXML
    private Label destination_name;

    @FXML
    private WebView myWeb;

    private WebEngine engine;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        destination_name.setText(SharedData.getChosenDestination().getName());

        engine = myWeb.getEngine();
        engine.load(SharedData.getChosenDestination().getUrlToDirections());
    }

    @FXML
    void quitDirections(ActionEvent event) {
        stage = (Stage) myWeb.getScene().getWindow();
        stage.close();
    }
}
