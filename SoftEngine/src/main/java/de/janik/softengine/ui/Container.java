package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Container<T extends DrawableEntity> extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final List<T> children;

    // <- Static ->

    // <- Constructor ->
    public Container() {
        super(0, 0);

        children = new ArrayList<>();
    }

    // <- Abstract ->

    // <- Object ->
    public void add(final T t) {
        children.add(t);
    }

    public void remove(final T t) {
        children.remove(t);
    }

    @Override
    public void tick(final long ticks, final InputManager input) {

    }

    // <- Getter & Setter ->
    @Override
    public void setX(final float x) {
        super.setX(x);

        children.forEach(e -> e.setX(e.getX() + x));
    }

    @Override
    public void setY(final float y) {
        super.setY(y);

        children.forEach(e -> e.setY(e.getY() + y));
    }

    public List<T> getChildren() {
        return children;
    }

    public void forEach(final Consumer<? super T> action) {
        children.forEach(action);
    }

    // <- Static ->
}
