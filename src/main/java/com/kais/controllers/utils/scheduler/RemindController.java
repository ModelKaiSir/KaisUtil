package com.kais.controllers.utils.scheduler;

import com.kais.app.AppMain;
import com.kais.app.AppNotify;
import com.kais.core.task.SchedulerTask;
import com.kais.core.task.SchedulerTaskFactory;
import com.kais.core.task.SimpleSchedulerTask;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务配置
 *
 * @author QiuKai
 */
public class RemindController implements Initializable {

    @FXML
    private TextField title;

    @FXML
    private TextArea comment;

    @FXML
    private RadioButton normal;

    @FXML
    private RadioButton repeat;

    @FXML
    private RadioButton unNotify;

    @FXML
    private RadioButton notify;

    @FXML
    private ComboBox<TimeUnit> timeUnit;

    @FXML
    private Spinner<Integer> timeSpinner;

    private ToggleGroup schedulerGroup;
    private ToggleGroup notifyGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setSchedulerItems();
        setNotifyItems();

        timeUnit.setItems(FXCollections.observableArrayList(TimeUnit.values()));
        timeUnit.valueProperty().addListener(this::timeUnitChange);

        timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24));
        timeSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
    }

    protected void setSchedulerItems() {

        schedulerGroup = new ToggleGroup();
        schedulerGroup.getToggles().addAll(normal, repeat);
    }

    protected void setNotifyItems() {

        notifyGroup = new ToggleGroup();
        notifyGroup.getToggles().addAll(unNotify, notify);
    }

    void timeUnitChange(ObservableValue<? extends TimeUnit> observable, TimeUnit oldValue, TimeUnit newValue) {

        switch (newValue) {

            default:
            case SECONDS:
            case MINUTES:

                timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60));
                break;
            case HOURS:

                timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24));
                break;

            case DAYS:

                timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999));
                break;
        }
    }

    @FXML
    public void confirm(ActionEvent event) {

        try {
            TimeUnit timeUnit = this.timeUnit.getSelectionModel().getSelectedItem();

            Assert.notNull(timeUnit, "时间单位不能为空！");

            //添加Scheduler Job
            SchedulerTask task = SimpleSchedulerTask.of(timeUnit, timeSpinner.getValue(), title.getText(), comment.getText());
            SchedulerTaskFactory.submitTask(task);

            //AppNotify.info(timeUnit.format(timeSpinner.getValue())).showAndWait();
        }
        catch (IllegalArgumentException e){

            AppNotify.info(e.getMessage());
        }catch (Exception e) {

            AppNotify.error(e);
        }
    }
}
