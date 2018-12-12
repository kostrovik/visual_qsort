package controls;

import com.github.kostrovik.useful.utils.InstanceLocatorUtil;
import icons.SolidIcons;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.text.Text;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: visual-qsort
 * author:  kostrovik
 * date:    2018-12-11
 * github:  https://github.com/kostrovik/visual-qsort
 */
public class NumberItemSkin extends SkinBase<NumberItem> {
    private Logger logger = InstanceLocatorUtil.getLocator().getLogger(NumberItemSkin.class.getName());

    public NumberItemSkin(NumberItem control) {
        super(control);
        createView();
    }

    private void createView() {
        Group valueGroup = new Group();
        Text valueText = new Text();
        valueText.textProperty().bind(getSkinnable().valueProperty().asString());

        SolidIcons removeIcon = SolidIcons.CLOSE;
        Text icon = new Text(removeIcon.getSymbol());
        icon.setFont(removeIcon.getFont());
        icon.getStyleClass().add("icon");

        Button removeBtn = new Button();
        removeBtn.setGraphic(icon);
        removeBtn.setPrefWidth(10);
        removeBtn.setPrefHeight(10);
        removeBtn.setTranslateX(valueText.getBoundsInLocal().getWidth() + 2);
        removeBtn.setTranslateY(-valueText.getBoundsInLocal().getHeight());

        try {
            getSkinnable().getStylesheets().add(Class.forName(getSkinnable().getClass().getName()).getResource("/stylesheets/remove-button.css").toExternalForm());
        } catch (ClassNotFoundException error) {
            logger.log(Level.WARNING, "Ошибка загрузки стилей.", error);
        }

        removeBtn.setOnAction(getSkinnable().getRemoveHandler());

        valueGroup.getChildren().setAll(valueText, removeBtn);

        getSkinnable().getStyleClass().add("number-item");
        getChildren().addAll(valueGroup);
    }
}
