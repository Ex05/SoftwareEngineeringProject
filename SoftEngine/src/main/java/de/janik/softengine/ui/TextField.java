package de.janik.softengine.ui;

// <- Import ->

// <- Static_Import ->


import de.janik.softengine.entity.DrawableEntity;

/**
 * @author Gorden.Kappenberg [©2016]
 */
public class TextField extends DrawableEntity {
    // <- Public ->
    // <- Protected ->
    // <- Private->

    private final Text defaultText;

    private Text text;

    // <- Static ->
    // <- Constructor ->
    public TextField() {
        this(null);
    }

    public TextField(final String defaultText) {
        this(0, 0);

        if (defaultText != null)
            this.defaultText.setText(defaultText);

    }

    public TextField(int x, int y) {
        super(x, y);

        defaultText = new Text();
        text = new Text();
    }
    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
