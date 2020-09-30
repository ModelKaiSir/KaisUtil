package com.kais.controllers;

import com.kais.app.AppMain;
import com.kais.components.utils.UtilComponent;
import com.kais.components.utils.UtilsManager;
import com.kais.views.TestView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.WindowEvent;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import sun.plugin2.os.windows.FLASHWINFO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author QiuKai
 */
public class MainController implements Initializable {

    @FXML
    FlowPane main;

    @FXML
    FlowPane techTrans;

    private UtilsManager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        manager = AppMain.CONTEXT.getBean(UtilsManager.class);

        manager.setUtils(UtilComponent.UtilType.MAIN, main);
        manager.setUtils(UtilComponent.UtilType.TECH_TRANS, techTrans);
    }
}