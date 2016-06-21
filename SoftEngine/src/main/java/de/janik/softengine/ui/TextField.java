package de.janik.softengine.ui;

// <- Import ->

// <- Static_Import ->


import de.janik.softengine.InputManager;
import de.janik.softengine.math.Vector;
import de.janik.softengine.util.ColorARGB;

import java.awt.Font;
import java.awt.event.KeyEvent;

import static de.janik.softengine.ui.TextLocation.ABSOLUTE;
import static de.janik.softengine.ui.TextLocation.RIGHT;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_SPACE;

/**
 * @author Gorden.Kappenberg [©2016]
 */
public class TextField extends UI_Component {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    private final Vector textPosition;

    private Text text;

    private TextLocation textLocation = RIGHT;

    private boolean showdefaultText = true;

    private String defaultText;
    private String inputText = "";

    // <- Static ->
    // <- Constructor ->
    public TextField(final String defaultText) {
        this();

        this.defaultText = defaultText;

        this.text.setText(defaultText);
    }

    public TextField() {
        text = new Text();

        textPosition = new Vector();

        onKeyPress(this::handleKeyEvent);
        onMousePress(() -> setFocus(true));
    }

    // <- Abstract ->
    // <- Object ->
    @Override
    public void tick(long ticks, InputManager input) {
        if (!hasFocus() && inputText.equals("") && !text.getText().equals(defaultText)) {
            text.setColor(ColorARGB.DARK_GRAY);
            this.setText(defaultText);
        }

        if (hasFocus() && !text.getText().equals(inputText)) {
            text.setColor(ColorARGB.GRAY);
            this.setText(inputText);
        }

        if (!hasFocus())
            showdefaultText = true;

        switch (textLocation) {
            case LEFT:
                textPosition.setX(background.getWidth() - text.getWidth()).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case RIGHT:
                textPosition.setX(0).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case CENTER:
                textPosition.setX(background.getWidth() / 2 - text.getWidth() / 2).setY(background.getHeight() / 2 - text.getHeight() / 2);
                break;
            case ABSOLUTE:
                break;
        }

        getSprite().draw((int) textPosition.getX(), (int) textPosition.getY(), text);
    }

    private void handleKeyEvent(final KeyEvent e) {
        if (Character.isDigit(e.getKeyChar()) || Character.isLetter(e.getKeyChar()))
            inputText = inputText + e.getKeyChar();
        else {
            if (e.getExtendedKeyCode() == VK_BACK_SPACE)
                if (!inputText.equals(""))
                    inputText = inputText.substring(0, inputText.length() - 1);

            if (e.getExtendedKeyCode() == VK_MINUS || e.getExtendedKeyCode() == VK_SPACE)
                inputText = inputText + e.getKeyChar();
        }
    }

    @Override
    protected void update() {
        if (background.getWidth() < text.getWidth())
            background.resize(text.getWidth(), background.getHeight());

        if (background.getHeight() < text.getHeight())
            background.resize(background.getWidth(), text.getHeight());

        background.fill();

        setSprite(background.getSprite());
    }

    // <- Getter & Setter ->
    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;

        if (width != background.getWidth() || height != background.getHeight())
            background.resize(width, height);

        update();
    }

    public void setFont(final Font font) {
        text.setFont(font);

        update();
    }

    public void setTextSize(final int textSize) {
        text.setTextSize(textSize);

        update();
    }

    public void setText(final String text) {
        this.text.setText(text);

        update();
    }

    public void setTextColor(final ColorARGB color) {
        text.setColor(color);

        update();
    }

    public void setAntialiasing(final boolean antialiasing) {
        text.setAntialiasing(antialiasing);

        update();
    }

    public void setAntialiasing(final boolean antialiasing, final Text.Interpolation interpolation) {
        text.setAntialiasing(antialiasing, interpolation);

        update();
    }

    public Text getText() {
        return text;
    }

    public void setTextLocation(final int x, final int y) {
        textPosition.setX(x).setY(y);

        setTextLocation(ABSOLUTE);
    }

    public void setTextLocation(final TextLocation textLocation) {
        this.textLocation = textLocation;
    }

    // <- Static ->
}
