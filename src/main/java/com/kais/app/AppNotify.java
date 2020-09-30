package com.kais.app;

import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 弹窗消息
 *
 * @author QiuKai
 */
public class AppNotify {

    public static final String SYSTEM_ERROR = "系统出错！";
    public static final String SYSTEM_ERROR_CONTENT = "错误信息：";
    public static final String SYSTEM_CALL = "系统提示";
    public static final String SYSTEM_SUCCESS = "操作成功！";

    static final String TOPPING_MESSAGE_UI = "fxml/ToppingMessage.fxml";

    static final Logger LOGGER = Logger.getLogger(AppNotify.class);

    public static javafx.scene.control.Dialog<ButtonType> error(Throwable e) {

        return error(null, null, e);
    }

    public static javafx.scene.control.Dialog<ButtonType> error(String message, StackTraceElement[] elements) {

        return error(message, elements, null);
    }

    private static javafx.scene.control.Dialog<ButtonType> error(String message, StackTraceElement[] elements, Throwable e) {

        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<ButtonType>();

        dialog.setTitle(SYSTEM_ERROR);

        final DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContentText(SYSTEM_ERROR_CONTENT);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialog.initModality(Modality.APPLICATION_MODAL);

        if (null == e) {

            dialogPane.setContentText(message);
            e = new Throwable();
            e.setStackTrace(elements);
        } else {

            dialog.setContentText(e.getMessage());
        }

        Label label = new Label(SYSTEM_ERROR_CONTENT);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane root = new GridPane();
        root.setVisible(false);
        root.setMaxWidth(Double.MAX_VALUE);

        root.add(label, 0, 0);
        root.add(textArea, 0, 1);

        dialogPane.setExpandableContent(root);

        LOGGER.error(e);
        return dialog;
    }

    public static javafx.scene.control.Dialog<ButtonType> info(String title, String message) {

        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<ButtonType>();
        dialog.setTitle(title);

        final DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContentText(message);
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialog.initModality(Modality.APPLICATION_MODAL);
        return dialog;
    }

    public static javafx.scene.control.Dialog<ButtonType> info(String message) {

        return info(SYSTEM_CALL, message);
    }

    public static javafx.scene.control.Dialog<ButtonType> success() {

        return info(SYSTEM_CALL, SYSTEM_SUCCESS);
    }

    public static void toppingMessage(String nv) {

        Stage stage = new Stage();

        Parent parent = AppMain.loadFxml(TOPPING_MESSAGE_UI);

        Text message = (Text) parent.lookup("#message");
        message.setText(nv);

        Button confirm = (Button) parent.lookup("#confirm");
        confirm.setOnAction(e -> {
            stage.close();
        });

        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        stage.show();
    }
}
