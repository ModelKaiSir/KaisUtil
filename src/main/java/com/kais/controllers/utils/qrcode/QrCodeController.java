package com.kais.controllers.utils.qrcode;

import com.kais.app.AppNotify;
import com.kais.app.AppUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * 识别二维码工具
 *
 * @author QiuKai
 */
public class QrCodeController implements Initializable {

    static final String DEF_RESULT_TEXT = "内容：%s";

    static final String REGION_TEXT = "拖动文件到此";

    /**
     * 读取内容
     */
    @FXML
    Region regionInput;

    /**
     * 输出内容
     */
    @FXML
    Region regionExport;

    /**
     * 提示文本
     */
    @FXML
    Text regionText;

    /**
     * 结果
     */
    @FXML
    Text result;

    private StringProperty regionTextProperty = new SimpleStringProperty(REGION_TEXT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        regionText.textProperty().bind(regionTextProperty);
    }

    @FXML
    public void onClear(ActionEvent e) {

        //清空
        result.setText(String.format(DEF_RESULT_TEXT, ""));
    }

    @FXML
    public void onCopy(ActionEvent e) {

        //复制结果到剪贴板
        System.out.println(result.getText());
        //复制去掉DEF_RESULT_TEXT的内容
        AppUtils.copyText(result.getText().replace(String.format(DEF_RESULT_TEXT, ""), ""));
        AppNotify.info("复制成功！").showAndWait();
    }

    @FXML
    public void onRegionDropOver(DragEvent event) {

        if (event.getGestureSource() != regionInput) {

            if (event.getDragboard().hasFiles()) {

                event.acceptTransferModes(TransferMode.ANY);
            }
        }

        event.consume();
    }

    @FXML
    public void onRegionDropped(DragEvent event) {

        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {

            parseQrCode(db.getFiles()::get, 0);
            success = true;
        }

        /* let the source know whether the string was successfully * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    public void onMouseClick(MouseEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter("图片", "*.png", "*.jpg"));

        File choose = chooser.showOpenDialog(regionText.getScene().getWindow());

        if (null != choose) {

            parseQrCode((i) -> {
                return choose;
            }, -1);
        }
    }

    protected void running(WorkerStateEvent e) {

        regionTextProperty.set("解析中。。。。。");
    }

    protected void succeed(WorkerStateEvent e) {

        regionTextProperty.set(REGION_TEXT);
    }

    protected void parseQrCode(Function<Integer, File> fn, int index) {

        new AppUtils.BackgroundTaskBuilder<Integer>()
                .setRunning(this::running)
                .setSucceed(this::succeed)
                .build(() -> {

                    File source = fn.apply(index);

                    Parses p = Parses.of(source);
                    p.add(QrCodeParse::new);
                    p.parse(r -> result.setText(String.format(DEF_RESULT_TEXT, r)));
                    return 1;
                });
    }
}
