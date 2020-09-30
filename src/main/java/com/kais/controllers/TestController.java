package com.kais.controllers;

import com.kais.views.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    Pane root;

    @FXML
    Region region;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        region.setClip(new Text("Hello Word"));
    }
}
