package com.kais.app;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Background;
import org.springframework.util.StringUtils;
import sun.awt.windows.ThemeReader;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 工具类
 *
 * @author QiuKai
 */
public class AppUtils {

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

    public static <V, P> void backgroundTask(Task<V> task, Consumer<P> consumer, P data) {

        new Thread(task).start();
        task.setOnRunning((WorkerStateEvent e) -> consumer.accept(data));
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
