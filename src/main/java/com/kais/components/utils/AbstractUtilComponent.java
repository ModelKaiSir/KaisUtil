package com.kais.components.utils;

import com.kais.app.AppMain;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * @author QiuKai
 */
@Component
public abstract class AbstractUtilComponent implements UtilComponent {

    static final String UTIL_REGION = "fxml/UtilRegion.fxml";
    static final String UTIL_CAPTION = "#caption";

    protected Parent parent = null;

    public AbstractUtilComponent() {

        parent = AppMain.loadFxml(UTIL_REGION);
        Text caption = (Text) parent.lookup(UTIL_CAPTION);
        caption.setText(getCaption());
    }

    @PostConstruct
    public void init(){

        //添加点击事件
        parent.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> run());
    }

    @Override
    public Parent getParent() {
        return parent;
    }

    /**
     *
     * 小工具标题
     * @return
     */
    protected abstract String getCaption();
}
