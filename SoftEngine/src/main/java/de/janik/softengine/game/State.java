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

    protected final List<State> subStates;

    protected State currentState;

    // <- Private->
    private final List<UI_Component> focusableElements;

    private UI_Component focusHolder = null;

    // <- Static ->

    // <- Constructor ->
    public State(final Game game) {
        engine = game.getEngine();

        this.game = game;

        subStates = new ArrayList<>(1);

        focusableElements = new ArrayList<>();
    }

    // <- Abstract ->
    public abstract void init();

    public void tick(final long ticks, final Engine engine) {
        if (currentState != null)
            currentState.tick(ticks, engine);
    }

    // <- Object ->
    protected void add(final DrawableEntity entity) {
        if (entity instanceof UI_Component) {
            final UI_Component component = (UI_Component) entity;

            if (component.isFocusAble()) {
                component.setStateCallBack(this);

                focusableElements.add(component);

                component.onKeyPress(e -> {
                    if (e.getKeyCode() == VK_TAB && !e.isConsumed()) {
                        if (component.hasFocus()) {
                            final int componentIndex = focusableElements.indexOf(component);

                            if (componentIndex != -1) {
                                int nextIndex = componentIndex;

                                do {
                                    nextIndex = nextIndex < focusableElements.size() - 1 ? componentIndex + 1 : 0;

                                    final UI_Component nextComponent = focusableElements.get(nextIndex);

                                    if (nextComponent.isVisible()) {
                                        nextComponent.setFocus(true);

                                        e.consume();

                                        break;
                                    }

                                } while (nextIndex != componentIndex);
                            }
                        }
                    }
                });

                if (component.hasFocus())
                    setFocusHolder(component);
            }
        }

        game.add(entity);
    }

    protected void add(final Entity entity) {
        game.add(entity);
    }

    public void add(final State state) {
        if (currentState == null)
            currentState = state;

        subStates.add(state);
    }

    public void remove(final DrawableEntity e) {
        if (e instanceof UI_Component) {
            focusableElements.remove(e);

            if (focusHolder == e)
                focusHolder = null;
        }

        game.remove(e);
    }

    public void remove(final Entity e) {
        game.remove(e);
    }

    protected final void initDefaultFocus() {
        if (focusableElements.size() > 0)
            if (focusableElements.stream().filter(UI_Component::hasFocus).findFirst().orElse(null) == null)
                focusableElements.get(0).setFocus(true);
    }

    // <- Getter & Setter ->
    public State getState(final Class<?> state) {
        return subStates.stream().filter(s -> s.getClass().equals(state)).findFirst().orElse(null);
    }

    public Game getGame() {
        return game;
    }

    public UI_Component getFocusHolder() {
        return focusHolder;
    }

    public synchronized void setFocusHolder(final UI_Component focusHolder) {
        if (this.focusHolder != null) {
            this.focusHolder.setFocus(false);

            if (this.focusHolder != null)
                this.focusHolder.loseFocus();
        }

        this.focusHolder = focusHolder;

        if (this.focusHolder != null)
            this.focusHolder.gainFocus();
    }

    // <- Static ->
}
