package controls;

import javafx.geometry.Pos;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class NumericFieldSkin extends SkinBase<NumericField> {
    private String pattern;
    private TextField editor;

    public NumericFieldSkin(NumericField control, String pattern) {
        super(control);
        this.pattern = pattern;

        createSkin();
        initListeners();
    }

    private void createSkin() {
        Text label = new Text();
        label.textProperty().bind(getSkinnable().labelProperty());

        editor = new TextField();
        editor.textProperty().bindBidirectional(getSkinnable().textProperty());

        HBox fieldContainer = new HBox(10);
        fieldContainer.getChildren().addAll(label, editor);
        fieldContainer.setAlignment(Pos.CENTER_LEFT);
        fieldContainer.setFillHeight(true);
        HBox.setHgrow(editor, Priority.ALWAYS);

        getChildren().addAll(fieldContainer);
    }

    private void initListeners() {
        editor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches(pattern)) {
                    editor.setText(oldValue);
                } else {
                    try {
                        editor.setText(String.valueOf(Integer.parseInt(newValue)));
                    } catch (Exception e) {
                        editor.setText(oldValue);
                    }
                }
            }
        });
    }
}