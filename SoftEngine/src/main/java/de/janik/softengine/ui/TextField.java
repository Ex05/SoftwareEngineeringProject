package de.janik.softengine.ui;

// <- Import ->

// <- Static_Import ->


import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.math.Vector;
import de.janik.softengine.util.ColorARGB;
import de.janik.softengine.game.State;

import java.awt.*;
import java.awt.event.KeyEvent;

import static de.janik.softengine.ui.TextLocation.*;
import static java.awt.event.KeyEvent.*;

/**
 * @author Gorden.Kappenberg [©2016]
 */
public class TextField extends DrawableEntity {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    private final Vector textPosition;

    private final Rectangle background;

    private Text text;

    private TextLocation textLocation = RIGHT;

    private boolean showdefaultText = true;

    private String defaultText;
    private String inputText = "";

    // <- Static ->
    // <- Constructor ->
    public TextField() {
        this(null, null);
    }

    public TextField(final String defaultText, State state) {
        this(0, 0, state);

        this.defaultText = defaultText;
        this.text.setText(defaultText);
    }

    public TextField(int x, int y, State state) {
        super(x, y);

        text = new Text();

        textPosition = new Vector();

        background = new Rectangle(0, 0);

        setSprite(background.getSprite());

        if(state!=null)
            setState(state);

        this.onKeyPress(e -> {
            if (this.isFocus())
                handleKeyEvent(e);
        });
    }

    // <- Abstract ->
    // <- Object ->
    @Override
    public void tick(long ticks, InputManager input) {

        if (!this.isFocus() && inputText.equals("") && !text.getText().equals(defaultText))
        {
            text.setColor(ColorARGB.DARK_GRAY);
            this.setText(defaultText);
        }


        if (this.isFocus() && !text.getText().equals(inputText))
        {
            text.setColor(ColorARGB.GRAY);
            this.setText(inputText);
        }


        if (!this.isFocus())
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

    @Override
    public void pressMouse() {
        if (!this.isFocus())
            this.getState().getFocus(this);
    }

    private void handleKeyEvent(KeyEvent e) {

        if (Character.isDigit(e.getKeyChar()) || Character.isLetter(e.getKeyChar()))
            inputText = inputText + e.getKeyChar();
        else {
            if (e.getExtendedKeyCode() == VK_TAB)
                this.getState().getFocus(this);

            if (e.getExtendedKeyCode() == VK_BACK_SPACE)
                if (!inputText.equals(""))
                    inputText = inputText.substring(0, inputText.length() - 1);

            if (e.getExtendedKeyCode() == VK_MINUS || e.getExtendedKeyCode() == VK_SPACE) inputText = inputText + e.getKeyChar();
        }
    }

    private void update() {
        if (background.getWidth() < text.getWidth())
            background.resize(text.getWidth(), background.getHeight());

        if (background.getHeight() < text.getHeight())
            background.resize(background.getWidth(), text.getHeight());

        background.fill();

        setSprite(background.getSprite());
    }

    // <- Getter & Setter ->

    @Override
    public void setWidth(final int width) {
        super.setWidth(width);

        if (width != background.getWidth())
            background.resize(width, background.getHeight());

        update();
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);

        if (height != background.getHeight())
            background.resize(background.getWidth(), height);

        update();
    }

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

    public void setBackgroundColor(final ColorARGB color) {
        background.setColor(color);
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

    @Override
    public Bitmap getSprite() {
        return (Bitmap) super.getSprite();
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
