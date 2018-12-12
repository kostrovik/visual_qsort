package helpers;

import com.github.kostrovik.useful.models.AbstractObservable;
import controls.NumberBlock;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * project: visual-qsort
 * author:  kostrovik
 * date:    2018-12-11
 * github:  https://github.com/kostrovik/visual-qsort
 */
public class ListBlocksView extends AbstractObservable {
    private Timeline animation;
    private HBox arrayItems;
    private static final int ANIMATION_DURATION = 500;
    private Node value1;
    private Node value2;
    private List<Integer> values;
    private double blockH;
    private int left;
    private int right;

    public ListBlocksView() {
        this.animation = new Timeline();
    }

    public Pane getView() {
        arrayItems = new HBox();
        arrayItems.setAlignment(Pos.CENTER);
        arrayItems.setPadding(new Insets(50, 10, 50, 10));
        return arrayItems;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
        render();
    }

    private void render() {
        arrayItems.getChildren().clear();
        for (Integer value : values) {
            NumberBlock block = new NumberBlock(value);
            arrayItems.getChildren().add(block);
        }
    }

    public void animateSwap(int left, int right) {
        value1 = arrayItems.getChildren().get(left);
        value2 = arrayItems.getChildren().get(right);

        blockH = value1.getLayoutBounds().getHeight();

        this.left = left;
        this.right = right;

        frameOne();
    }

    private void frameOne() {
        KeyValue kd1 = new KeyValue(value1.translateYProperty(), blockH * 1.5);
        KeyValue kd2 = new KeyValue(value2.translateYProperty(), -blockH * 1.5);

        KeyFrame kfd1 = new KeyFrame(Duration.millis(ANIMATION_DURATION), kd1, kd2);
        animation.getKeyFrames().clear();
        animation.getKeyFrames().add(kfd1);
        animation.setOnFinished(event -> frameTwo());
        animation.play();
    }

    private void frameTwo() {
        double diff = value1.getLayoutBounds().getWidth() - value2.getLayoutBounds().getWidth();

        animation.getKeyFrames().clear();
        for (int i = left + 1; i < right; i++) {
            animation.getKeyFrames().add(new KeyFrame(
                    Duration.millis(ANIMATION_DURATION),
//                    new KeyValue(arrayItems.getChildren().get(i).translateXProperty(), -diff)
                    new KeyValue(arrayItems.getChildren().get(i).translateXProperty(), arrayItems.getChildren().get(i).getTranslateX() - diff)
            ));
        }

        KeyFrame frame = getSwapKeyFrames();
        animation.getKeyFrames().add(frame);

        animation.setOnFinished(event -> frameThree());
        animation.play();
    }

    private KeyFrame getSwapKeyFrames() {
        double middleItemsWidth = 0;
        for (int i = left + 1; i < right; i++) {
            middleItemsWidth += arrayItems.getChildren().get(i).getLayoutBounds().getWidth();
        }

        double valuePosition1 = 0 - (value1.getLayoutBounds().getWidth() + middleItemsWidth);
        double valuePosition2 = value2.getLayoutBounds().getWidth() + middleItemsWidth;

        KeyValue kp1 = new KeyValue(value1.translateXProperty(), valuePosition2);
        KeyValue kp2 = new KeyValue(value2.translateXProperty(), valuePosition1);

        return new KeyFrame(Duration.millis(ANIMATION_DURATION), kp1, kp2);
    }

    /**
     * По итогу анимации приходится делать переустановку узлов для дальнейших анимаций.
     * Иначе анимируются не верные узлы и если не сбрасывать им TranslateX в 0 то плывут координаты.
     */
    private void frameThree() {
        KeyValue ku1 = new KeyValue(value1.translateYProperty(), 0);
        KeyValue ku2 = new KeyValue(value2.translateYProperty(), 0);

        KeyFrame kfu1 = new KeyFrame(Duration.millis(ANIMATION_DURATION), ku1, ku2);

        animation.getKeyFrames().clear();
        animation.getKeyFrames().add(kfu1);

        animation.setOnFinished(event -> {
            ArrayList<Node> items = new ArrayList<>(arrayItems.getChildren());
            Collections.swap(items, left, right);
            Collections.swap(values, left, right);
            items.forEach(item -> item.setTranslateX(0));
            arrayItems.getChildren().setAll(items);

            notifyLlisteners(new Object());
        });
        animation.play();
    }
}
