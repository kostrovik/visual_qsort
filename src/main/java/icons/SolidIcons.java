package icons;

import javafx.scene.text.Font;

public enum SolidIcons {
    CLOSE("\uf410");

    private final String character;

    private SolidIcons(String character) {
        this.character = character;
    }

    public String getSymbol() {
        return character;
    }

    public Font getFont() {
        return Font.loadFont(this.getClass().getResource("/stylesheets/icons/fa-solid-900.ttf").toExternalForm(), 10.0);
    }
}
