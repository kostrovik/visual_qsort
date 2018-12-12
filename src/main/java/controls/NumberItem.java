package controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.Objects;

/**
 * project: visual-qsort
 * author:  kostrovik
 * date:    2018-12-11
 * github:  https://github.com/kostrovik/visual-qsort
 */
public class NumberItem extends Control {
    private IntegerProperty value;
    private EventHandler<ActionEvent> removeHandler = event -> {
        // по умолчанию при удалении не делать ничего
    };

    public NumberItem() {
        this.value = new SimpleIntegerProperty(0);
    }

    public NumberItem(int value, EventHandler<ActionEvent> removeHandler) {
        this.value = new SimpleIntegerProperty(value);

        if (Objects.nonNull(removeHandler)) {
            this.removeHandler = removeHandler;
        }
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public EventHandler<ActionEvent> getRemoveHandler() {
        return removeHandler;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new NumberItemSkin(this);
    }
}
