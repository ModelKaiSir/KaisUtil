package com.kais.controllers.utils.scheduler;

import com.kais.app.AppNotify;
import com.kais.core.task.quartz.AppNotifySchedulerTask;
import com.kais.core.task.quartz.QuartzJobFactory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.Optional;
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

        timeUnit.setItems(FXCollections.observableArrayList(new TimeUnit[]{ TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS }));
        timeUnit.valueProperty().addListener(this::timeUnitChange);

        timeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24));
        timeSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
    }

    protected void setSchedulerItems() {

        schedulerGroup = new ToggleGroup();
        schedulerGroup.getToggles().addAll(normal, repeat);
        schedulerGroup.selectToggle(normal);
    }

    protected void setNotifyItems() {

        notifyGroup = new ToggleGroup();
        notifyGroup.getToggles().addAll(unNotify, notify);
        notifyGroup.selectToggle(notify);
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

    private Optional<String> translateTimeUnit(TimeUnit unit) {

        if (unit == TimeUnit.SECONDS) {
            return Optional.of("秒");
        } else {
            return Optional.empty();
        }
    }

    @FXML
    public void confirm(ActionEvent event) {

        try {
            TimeUnit timeUnit = this.timeUnit.getSelectionModel().getSelectedItem();

            Assert.notNull(timeUnit, "时间单位不能为空！");

            //添加Quartz Scheduler Job
            QuartzJobFactory.submitTask(AppNotifySchedulerTask
                    .of(timeSpinner.getValue(), title.getText(), comment.getText())
                    .withUnit(timeUnit)
                    .withRepeat(() -> schedulerGroup.getSelectedToggle().equals(repeat))
                    .popup(() -> notifyGroup.getSelectedToggle().equals(notify)));

            long minutes;
            Optional<String> unit;

            if (timeUnit == TimeUnit.SECONDS) {

                minutes = timeSpinner.getValue();
                unit = translateTimeUnit(timeUnit);
            } else {

                minutes = TimeUnit.MINUTES.convert(timeSpinner.getValue(), timeUnit);
                unit = translateTimeUnit(timeUnit);
            }

            AppNotify.info(String.format("%d%s后执行任务", minutes, unit.orElse("分钟"))).showAndWait();
        } catch (IllegalArgumentException e) {

            AppNotify.info(e.getMessage());
        } catch (Exception e) {

            AppNotify.error(e);
        }
    }
}