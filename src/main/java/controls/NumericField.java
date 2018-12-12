package controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.Objects;

public class NumericField extends Control {
    private StringProperty label;
    private String pattern = "^\\d+$";
    private StringProperty text;

    public NumericField(String label) {
        this.label = new SimpleStringProperty(label);
        this.text = new SimpleStringProperty("");
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(Objects.nonNull(text) ? text : "");
    }

    public void clear() {
        setText("");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new NumericFieldSkin(this, pattern);
    }
}
