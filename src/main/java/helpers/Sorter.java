package helpers;

import com.github.kostrovik.useful.interfaces.Listener;
import com.github.kostrovik.useful.utils.InstanceLocatorUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.EventObject;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sorter {
    private Logger logger = InstanceLocatorUtil.getLocator().getLogger(Sorter.class.getName());
    private List<Integer> values;
    private Queue<int[]> animationQueue;
    private ArraySettingsView initForm;
    private ListBlocksView items;

    public Sorter() {
        this.animationQueue = new ConcurrentLinkedQueue<>();
    }

    public Pane getView() {
        VBox page = new VBox();

        initForm = new ArraySettingsView();

        Button animate = new Button("Сортировать");
        animate.setOnAction(event -> animate());

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.getChildren().setAll(animate);

        items = new ListBlocksView();
        initForm.addListener(new Listener<>() {
            @Override
            public void handle(EventObject result) {
                values = initForm.getValues();
                items.setValues(values);
            }

            @Override
            public void error(Throwable error) {
                logger.log(Level.SEVERE, "Ошибка добавления данных", error);
            }
        });
        items.addListener(new Listener<>() {
            @Override
            public void handle(EventObject result) {
                dequeProcess();
            }

            @Override
            public void error(Throwable error) {
                logger.log(Level.SEVERE, "Ошибка анимации", error);
            }
        });

        page.getChildren().setAll(initForm.getView(), buttons, items.getView());
        return page;
    }

    private void animate() {
        if (Objects.nonNull(values) && !values.isEmpty()) {
            QuickSort<Integer> quickSort = new QuickSort<>(values, animationQueue);
            quickSort.sort();
            dequeProcess();
        }
    }

    private void dequeProcess() {
        if (!animationQueue.isEmpty()) {
            int[] params = animationQueue.poll();
            items.animateSwap(params[0], params[1]);
        }
    }
}
