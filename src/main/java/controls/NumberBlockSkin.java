package controls;

import javafx.scene.Group;
import javafx.scene.control.SkinBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * project: visual-qsort
 * author:  kostrovik
 * date:    2018-12-11
 * github:  https://github.com/kostrovik/visual-qsort
 */
public class NumberBlockSkin extends SkinBase<NumberBlock> {
    public NumberBlockSkin(NumberBlock control) {
        super(control);
        createView();
    }

    private void createView() {
        Group valueItem = new Group();
        Text valueText = new Text();
        valueText.textProperty().bind(getSkinnable().valueProperty().asString());

        Rectangle border = new Rectangle(0, 0, Color.TRANSPARENT);
        border.setArcWidth(3);
        border.setArcHeight(3);
        border.setStroke(Color.INDIANRED);
        border.setLayoutX(valueText.getBoundsInParent().getMinX() - 10);
        border.setLayoutY(valueText.getBoundsInParent().getMinY() - 5);
        border.setWidth(valueText.getBoundsInParent().getWidth() + 20);
        border.setHeight(valueText.getBoundsInParent().getHeight() + 10);

        valueItem.getChildren().addAll(border, valueText);
        border.setWidth(Math.ceil(valueItem.getBoundsInLocal().getWidth()));

        getChildren().add(valueItem);
    }
}
