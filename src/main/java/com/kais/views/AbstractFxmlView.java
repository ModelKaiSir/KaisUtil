package com.kais.views;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author QiuKai
 */
@Component
public abstract class AbstractFxmlView implements ApplicationContextAware {

    private String fxml;
    private String title;

    @Value("#{properties['language']}")
    public String language;

    protected ApplicationContext context;

    public AbstractFxmlView() {

        Assert.isTrue(getClass().isAnnotationPresent(FxmlView.class), "view load fail.");
        FxmlView view = getClass().getAnnotation(FxmlView.class);

        title = view.title();
        fxml = view.value();

        System.out.println(language);
    }

    public Initializable showView() throws IOException {
        System.out.println(language);
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        Resource resource = new ClassPathResource(fxml);
        loader.setLocation(resource.getURL());
        loader.setResources(ResourceBundle.getBundle("zh_CN"));

        Stage stage = new Stage();
        stage.setTitle(title);

        try (InputStream in = resource.getInputStream()) {

            // 对象方法的参数是InputStream，返回值是Object
            Parent parent = (Parent) loader.load(in);
            stage.setScene(new Scene(parent));
            //返回Controller
            stage.show();
            return (Initializable) loader.getController();
        }
    }

    public void showAndWait() throws IOException {

        this.showAndWait(null, null);
    }

    public void showAndWaitOnStart(Consumer<Initializable> doConsumer) throws IOException {

        this.showAndWait(doConsumer, null);
    }

    public void showAndWaitOnClose(Consumer<Initializable> closeConsumer) throws IOException {

        this.showAndWait(null, closeConsumer);
    }

    public void showAndWait(Consumer<Initializable> doConsumer, Consumer<Initializable> closeConsumer) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        Resource resource = new ClassPathResource(fxml);
        loader.setLocation(resource.getURL());

        System.out.println(language);
        loader.setResources(ResourceBundle.getBundle("zh_CN"));

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        try (InputStream in = resource.getInputStream()) {

            // 对象方法的参数是InputStream，返回值是Object
            Parent parent = (Parent) loader.load(in);
            stage.setScene(new Scene(parent));

            registerStart(doConsumer, stage, loader::getController);
            registerClose(closeConsumer, stage, loader::getController);
            //返回Controller
            stage.showAndWait();
        }
    }

    private void registerClose(Consumer<Initializable> consumer, Stage stage, Supplier<Initializable> s) {

        if (null == consumer) {
            return;
        }
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (WindowEvent e) -> consumer.accept(s.get()));
    }

    private void registerStart(Consumer<Initializable> consumer, Stage stage, Supplier<Initializable> s) {

        if (null == consumer) {
            return;
        }
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, (WindowEvent e) -> consumer.accept(s.get()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = context;
    }
}
