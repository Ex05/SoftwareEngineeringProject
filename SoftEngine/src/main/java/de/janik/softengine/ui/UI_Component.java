package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.UI_Event;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.State;
import de.janik.softengine.util.ColorARGB;

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
    private final List<UI_Event> onFocusGain;
    private final List<UI_Event> onFocusLos;

    private State stateCallBack;

    // <- Static ->

    // <- Constructor ->
    public UI_Component() {
        super(0, 0);

        this.background = new Rectangle(0, 0);

        setSprite(background.getSprite());

        onFocusGain = new ArrayList<>(1);
        onFocusLos = new ArrayList<>(1);
    }

    // <- Abstract ->
    protected abstract void update();

    // <- Object ->
    public void onFocusGain(final UI_Event e) {
        onFocusGain.add(e);
    }

    public void onFocusLos(final UI_Event e) {
        onFocusLos.add(e);
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

    public void setBackgroundColor(final ColorARGB color) {
        background.setColor(color);
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public void setFocus(final boolean focus) {
        this.hasFocus = focus;

        if (focus)
            stateCallBack.setFocusHolder(this);
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
        onFocusLos.forEach(UI_Event::onUI_Event);
    }

    public void gainFocus() {
        onFocusGain.forEach(UI_Event::onUI_Event);
    }

    // <- Static ->
}
