package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.State;
import de.janik.softengine.util.ColorARGB;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class UI_Component extends DrawableEntity {
    // <- Public ->

    // <- Protected ->
    protected final Rectangle background;

    protected boolean hasFocus = false;
    protected boolean focusAble = true;

    // <- Private->
    private final List<UI_Event> focusGainEvents;
    private final List<UI_Event> focusLosEvents;

    private State stateCallBack;

    // <- Static ->

    // <- Constructor ->
    public UI_Component() {
        super(0, 0);

        this.background = new Rectangle(0, 0);

        setSprite(background.getSprite());

        focusGainEvents = new ArrayList<>(1);
        focusLosEvents = new ArrayList<>(1);

        onMousePress(() -> {
            if (isFocusAble() && !hasFocus())
                setFocus(true);
        });
    }

    // <- Abstract ->
    protected abstract void update();

    // <- Object ->
    @Override
    public void pressKey(final KeyEvent e) {
        if (hasFocus())
            super.pressKey(e);
    }

    public void onFocusGain(final UI_Event e) {
        focusGainEvents.add(e);
    }

    public void onFocusLos(final UI_Event e) {
        focusLosEvents.add(e);
    }

    // <- Getter & Setter ->
    @Override
    public void setWidth(final int width) {
        super.setWidth(width);

        if (width != background.getWidth())
            background.setSize(width, background.getHeight());

        update();
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);

        if (height != background.getHeight())
            background.setSize(background.getWidth(), height);

        update();
    }

    public void setBackgroundColor(final ColorARGB color) {
        background.setColor(color);
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public void setFocus(final boolean focus) {
        if (focus != hasFocus) {
            this.hasFocus = focus;

            if (hasFocus)
                stateCallBack.setFocusHolder(this);
            else
                stateCallBack.setFocusHolder(null);
        }
    }

    public boolean isFocusAble() {
        return focusAble;
    }

    public void setFocusAble(final boolean focusAble) {
        this.focusAble = focusAble;
    }

    @Override
    public Bitmap getSprite() {
        return (Bitmap) super.getSprite();
    }

    public void setStateCallBack(final State stateCallBack) {
        this.stateCallBack = stateCallBack;
    }

    public void loseFocus() {
        focusLosEvents.forEach(UI_Event::onUI_Event);
    }

    public void gainFocus() {
        focusGainEvents.forEach(UI_Event::onUI_Event);
    }

    // <- Static ->
}
