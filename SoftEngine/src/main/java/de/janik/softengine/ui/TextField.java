package de.janik.softengine.ui;

// <- Import ->

// <- Static_Import ->

import de.janik.softengine.util.ColorARGB;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static de.janik.softengine.ui.TextLocation.LEFT;
import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.SILVER_GRAY;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;

/**
 * @author Gorden.Kappenberg, Jan.Marcel.Janik [©2016]
 */
public class TextField extends Button {
    // <- Public ->

    // <- Protected ->
    protected String text = "";

    // <- Private->
    private final List<UI_Event> inputChangeEvents;

    private String defaultText = "";

    private ColorARGB textColor = BLACK;
    private ColorARGB textColorDefaultText = SILVER_GRAY;

    // <- Static ->

    // <- Constructor ->
    public TextField() {
        this("");
    }

    public TextField(final String defaultText) {
        this.defaultText = defaultText;

        inputChangeEvents = new ArrayList<>(1);

        displayDefaultText();

        setTextLocation(LEFT);

        onKeyPress(this::handleKeyInput);

        onFocusGain(() -> {
            if (text.equals(""))
                setText(text);
        });

        onFocusLos(() -> {
            if (text.equals(""))
                displayDefaultText();
        });
    }

    // <- Abstract ->

    // <- Object ->
    private void onInputChange() {
        inputChangeEvents.forEach(UI_Event::onUI_Event);
    }

    public void onInputChange(final UI_Event event) {
        inputChangeEvents.add(event);
    }

    private void handleKeyInput(final KeyEvent e) {
        if (hasFocus()) {
            final char keyChar = e.getKeyChar();

            if (keyChar >= 32 && keyChar < 127)
                append(keyChar);
            else if (e.getKeyCode() == VK_BACK_SPACE)
                removeLastCharacter();
        }
    }

    private void removeLastCharacter() {
        if (text.length() > 0)
            text = text.substring(0, text.length() - 1);

        setText(text);
    }

    protected void append(final char keyChar) {
        text += keyChar;

        super.text.setColor(textColor);

        setText(text);
    }

    private void displayDefaultText() {
        super.text.setColor(textColorDefaultText);

        super.setText(defaultText);
    }

    // <- Getter & Setter ->
    public String getUserInput(){
        return text;
    }

    @Override
    public void setText(final String text) {
        this.text = text;

        super.setText(this.text);
    }

    @Override
    public void setTextColor(final ColorARGB color) {
        textColor = color;

        super.setTextColor(textColor);
    }

    public void setDefaultTextColor(final ColorARGB color) {
        textColorDefaultText = color;

        super.setTextColor(textColorDefaultText);
    }
    // <- Static ->
}
