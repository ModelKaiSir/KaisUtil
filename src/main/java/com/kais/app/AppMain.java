package com.kais.app;

import com.kais.components.utils.UtilsManager;
import com.kais.core.OcrClient;
import com.kais.views.AbstractFxmlView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 程序入口
 *
 * @author QiuKai
 */
public class AppMain extends Application {

    static final int WIDTH = 800;
    static final int HEIGHT = 500;

    static final String APPLICATION_CONTEXT_XML = "spring/spring.xml";

    /**
     *
     * spring Context
     */
    public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = loadFxml("fxml/main.fxml");
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("工具");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (WindowEvent e) -> {

            Platform.exit();
        });
    }

    public static void main(String[] args) {

        launch(args);
    }

    /**
     * 加载fxml布局
     *
     * @param fxml
     * @return
     */
    public static Parent loadFxml(String fxml){

        try {

            Resource resource = new ClassPathResource(fxml);
            return FXMLLoader.load(resource.getURL());
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
            return null;
        }
    }

    /**
     * 显示FXML界面
     *
     * @param clazz FxmlView
     * @return controller
     * @throws IOException
     */
    public static <T extends AbstractFxmlView> Initializable showView(Class<T> clazz) {

        try {

            AbstractFxmlView view = CONTEXT.getBean(clazz);
            return view.showView();
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
            return null;
        }
    }

    /**
     * 显示FXML界面
     *
     * @param clazz FxmlView
     * @return controller
     * @throws IOException
     */
    public static <T extends AbstractFxmlView> void showAndWait(Class<T> clazz) {

        try {

            AbstractFxmlView view = CONTEXT.getBean(clazz);
            view.showAndWait();
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
        }
    }

    /**
     * 显示FXML界面
     *
     * @param clazz FxmlView
     * @return controller
     * @throws IOException
     */
    public static <T extends AbstractFxmlView> void showAndWait(Class<T> clazz, Consumer<Initializable> doConsumer, Consumer<Initializable> closeConsumer){

        try {

            AbstractFxmlView view = CONTEXT.getBean(clazz);
            view.showAndWait(doConsumer, closeConsumer);
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
        }
    }

    /**
     * 显示FXML界面
     *
     * @param clazz FxmlView
     * @return controller
     * @throws IOException
     */
    public static <T extends AbstractFxmlView> void showAndWaitOnStart(Class<T> clazz, Consumer<Initializable> doConsumer){

        try {

            AbstractFxmlView view = CONTEXT.getBean(clazz);
            view.showAndWaitOnStart(doConsumer);
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
        }
    }

    /**
     * 显示FXML界面
     *
     * @param clazz FxmlView
     * @return controller
     * @throws IOException
     */
    public static <T extends AbstractFxmlView> void showAndWaitOnClose(Class<T> clazz, Consumer<Initializable> closeConsumer){

        try {

            AbstractFxmlView view = CONTEXT.getBean(clazz);
            view.showAndWaitOnClose(closeConsumer);
        } catch (IOException e) {

            AppNotify.error(e).showAndWait();
        }
    }

    public static <T> T getBean(Class<T> clazz) {

        return CONTEXT.getBean(clazz);
    }
}
