package de.janik.softengine.ui;

// <- Import ->

// <- Static_Import ->


import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.math.Vector;

import static de.janik.softengine.ui.TextLocation.*;

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
        this(null);
    }

    public TextField(final String defaultText) {
        this(0, 0);

        if (defaultText != null)
        {
            this.defaultText = defaultText;
            this.text.setText(defaultText);
        }


    }

    public TextField(int x, int y) {
        super(x, y);

        text = new Text();

        textPosition = new Vector();

        background = new Rectangle(0, 0);

        setSprite(background.getSprite());
    }


    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(long ticks, InputManager input) {


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

    }

    // <- Getter & Setter ->

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
