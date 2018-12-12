package controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * project: visual-qsort
 * author:  kostrovik
 * date:    2018-12-11
 * github:  https://github.com/kostrovik/visual-qsort
 */
public class NumberBlock extends Control {
    private IntegerProperty value;

    public NumberBlock(int value) {
        this.value = new SimpleIntegerProperty(value);
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

    @Override
    protected Skin<?> createDefaultSkin() {
        return new NumberBlockSkin(this);
    }
}
