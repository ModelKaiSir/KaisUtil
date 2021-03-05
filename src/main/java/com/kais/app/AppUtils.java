package com.kais.app;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Background;
import org.springframework.util.StringUtils;
import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author QiuKai
 */
public class AppUtils {

    static final Pattern PATTERN = Pattern.compile("[A-Z][a-z]+");

    /**
     * 复制文本
     *
     * @param input
     */
    public static void copyText(String input) {

        ClipboardContent content = new ClipboardContent();

        if (!StringUtils.isEmpty(input)) {

            content.putString(input);
            Clipboard.getSystemClipboard().setContent(content);
        }
    }

    /**
     *
     * 格式化为驼峰命名法，首字母小写
     *
     * @param name
     * @return
     */
    public static String formatLower(String name) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }

    /**
     *
     * 格式化为驼峰命名法，首字母大写
     *
     * @param name
     * @return
     */
    public static String formatUpper(String name) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
    }

    public static String keyWords(String...args) {

        return Joiner.on("_").join(args);
    }

    public static String toUnderScore(String str) {

        Matcher m = PATTERN.matcher(str);
        Joiner join = Joiner.on("_");

        ArrayList<String> data = new ArrayList<String>();

        boolean any = false;

        while (m.find()) {
            any = true;
            data.add(m.group());
        }

        if(!any){
            return str;
        }

        return join.join(data);
    }

    public static class BackgroundTaskBuilder<V> {

        EventHandler<WorkerStateEvent> running;
        EventHandler<WorkerStateEvent> succeed;

        public BackgroundTaskBuilder<V> setRunning(EventHandler<WorkerStateEvent> running) {
            this.running = running;
            return this;
        }

        public BackgroundTaskBuilder<V> setSucceed(EventHandler<WorkerStateEvent> succeed) {
            this.succeed = succeed;
            return this;
        }

        public Task<V> build(Supplier<V> supplier) {

            Task<V> task = new Task<V>() {

                @Override
                protected V call() throws Exception {

                    V data = null;
                    try {

                        running();
                        data = supplier.get();
                    } finally {

                        succeeded();
                        return data;
                    }
                }
            };

            Thread thread = new Thread(task);
            thread.start();

            if (null != running) {

                task.setOnRunning(running);
            }

            if (null != succeed) {

                task.setOnSucceeded(succeed);
            }

            return task;
        }
    }
}
