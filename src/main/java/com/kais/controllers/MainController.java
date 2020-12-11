package com.kais.controllers;

import com.kais.app.AppMain;
import com.kais.components.utils.UtilComponent;
import com.kais.components.utils.UtilsManager;
import com.kais.domain.User;
import com.kais.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.JDBC;

import java.net.URL;
import java.sql.JDBCType;
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