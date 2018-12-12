package core;

import com.github.kostrovik.useful.utils.InstanceLocatorUtil;
import helpers.Sorter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Visualizer extends Application {
    private static Logger logger = InstanceLocatorUtil.getLocator().getLogger(Visualizer.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) {
        logger.info("Запуск приложения.");

        Pane page = new Sorter().getView();
        Scene scene = new Scene(page, 800, 500);
        mainWindow.setScene(scene);
        mainWindow.setTitle("Визуализация алгоритма быстрой сортировки");

        mainWindow.show();
    }
}