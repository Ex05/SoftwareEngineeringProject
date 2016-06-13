package de.janik.softengine.entity;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.ui.A_Sprite;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
@FunctionalInterface
public interface Drawable {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->
    // <- Constructor ->

    // <- Abstract ->
    A_Sprite getSprite();

    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
