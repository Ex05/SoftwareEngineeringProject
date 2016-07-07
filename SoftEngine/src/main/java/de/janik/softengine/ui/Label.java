package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->


/**
 * @author Jan.Marcel.Janik, Gorden.Kappenberg [©2016]
 */
public final class Label extends TextComponent {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public Label(final String text) {
        super();

        setText(text);

        setFocusAble(false);
    }

    public Label(final String text , final TextLocation textLocation) {
        super();

        setText(text);
        setTextLocation(textLocation);
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
