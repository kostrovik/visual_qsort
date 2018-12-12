package helpers;

import com.github.kostrovik.useful.models.AbstractObservable;
import com.github.kostrovik.useful.utils.InstanceLocatorUtil;
import controls.NumberItem;
import controls.NumericField;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArraySettingsView extends AbstractObservable {
    private Logger logger = InstanceLocatorUtil.getLocator().getLogger(ArraySettingsView.class.getName());

    private List<Integer> values;

    private Random randomize;
    private HBox listValues;
    private Button addValueButton;

    public ArraySettingsView() {
        this.randomize = new Random();
        this.values = new ArrayList<>();
    }

    public Pane getView() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10, 10, 10, 10));

        listValues = new HBox(10);

        HBox listValuesForm = new HBox(10);
        Pane initArrayForm = renderValueForm();
        Button initRandomArray = new Button("Создать случайный массив");

        Separator vSeparator = new Separator();
        vSeparator.setOrientation(Orientation.VERTICAL);
        vSeparator.setValignment(VPos.CENTER);
        try {
            vSeparator.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("/stylesheets/separator.css").toExternalForm());
        } catch (ClassNotFoundException error) {
            logger.log(Level.WARNING, "Ошибка загрузки стилей.", error);
        }

        Text message = new Text("Приложение является ознакомительной визуализацией. Максимальное количество элементов массива ограничено 10.");

        initArrayForm.prefWidthProperty().bind(listValuesForm.widthProperty().divide(2));
        message.wrappingWidthProperty().bind(listValuesForm.widthProperty().divide(2));

        initArrayForm.minWidth(300);
        message.minWidth(300);

        listValuesForm.getChildren().setAll(initArrayForm, vSeparator, message);

        listValues.setAlignment(Pos.CENTER);

        initRandomArray.setOnAction(event -> initRandomArray());

        form.getChildren().setAll(listValuesForm, initRandomArray, listValues);

        return form;
    }

    public List<Integer> getValues() {
        return values;
    }

    private Pane renderValueForm() {
        HBox form = new HBox(10);
        form.setAlignment(Pos.CENTER_LEFT);

        NumericField field = new NumericField("Значение");
        addValueButton = new Button("Добавить");
        addValueButton.setOnAction(event -> {
            if (!addValueButton.isDisabled()) {
                String newValue = field.getText().trim();
                if (newValue.length() > 0) {
                    try {
                        values.add(Integer.parseInt(newValue));
                        notifyLlisteners(values);
                        renderValues(listValues);
                        field.clear();
                        checkButtonState();
                    } catch (RuntimeException error) {
                        logger.log(Level.SEVERE, String.format("Ошибка добавления числа в массив %s.", newValue), error);
                    }
                }
            }
        });

        addValueButton.setMinWidth(Region.USE_PREF_SIZE);
        HBox.setHgrow(addValueButton, Priority.NEVER);
        HBox.setHgrow(field, Priority.ALWAYS);

        form.getChildren().setAll(field, addValueButton);

        return form;
    }

    private void checkButtonState() {
        addValueButton.setDisable(values.size() > 9);
    }

    private void renderValues(Pane parent) {
        parent.getChildren().clear();
        for (int i = 0; i < values.size(); i++) {
            parent.getChildren().add(renderListItem(values.get(i), i));
        }
    }

    private void removeItem(int index) {
        values.remove(index);
        notifyLlisteners(values);
        renderValues(listValues);
        checkButtonState();
    }

    private void initRandomArray() {
        values.clear();
        for (int i = 0; i < 10; i++) {
            values.add(randomize.nextInt(999));
        }
        notifyLlisteners(values);
        checkButtonState();
        renderValues(listValues);
    }

    private NumberItem renderListItem(int value, int index) {
        return new NumberItem(value, event -> removeItem(index));
    }
}
