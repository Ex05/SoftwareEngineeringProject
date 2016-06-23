package de.janik.softengine.game;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.entity.Entity;
import de.janik.softengine.ui.UI_Component;

import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.VK_TAB;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class State {
    // <- Public ->

    // <- Protected ->
    protected Engine engine;

    protected final Game game;

    // <- Private->
    private final List<UI_Component> focusableElements;

    private UI_Component focusHolder = null;

    // <- Static ->

    // <- Constructor ->
    public State(final Game game) {
        engine = game.getEngine();

        this.game = game;

        focusableElements = new ArrayList<>();
    }

    // <- Abstract ->
    public abstract void init();

    public abstract void tick(final long ticks, final Engine engine);

    // <- Object ->
    protected void add(final DrawableEntity entity) {
        if (entity instanceof UI_Component) {
            final UI_Component component = (UI_Component) entity;

            if (component.isFocusAble()) {
                component.setStateCallBack(this);

                if (focusableElements.size() == 0)
                    component.setFocus(true);

                focusableElements.add(component);

                component.onKeyPress(e -> {
                    if (e.getKeyCode() == VK_TAB && !e.isConsumed()) {
                        if (component.hasFocus()) {
                            final int componentIndex = focusableElements.indexOf(component);

                            if (componentIndex != -1) {
                                final int nextIndex = componentIndex + 1 < focusableElements.size() - 1 ? componentIndex + 1 : 0;

                                final UI_Component nextComponent = focusableElements.get(nextIndex);
                                nextComponent.setFocus(true);

                                System.out.println(componentIndex + "|" + nextIndex);

                                e.consume();
                            }
                        }
                    }
                });
            }
        }

        game.add(entity);
    }

    protected void add(final Entity entity) {
        game.add(entity);
    }

    // <- Getter & Setter ->
    public Game getGame() {
        return game;
    }

    public UI_Component getFocusHolder() {
        return focusHolder;
    }

    public void setFocusHolder(final UI_Component focusHolder) {
        if (this.focusHolder != null) {
            this.focusHolder.setFocus(false);
            this.focusHolder.loseFocus();
        }

        this.focusHolder = focusHolder;

        this.focusHolder.gainFocus();
    }

    // <- Static ->
}
