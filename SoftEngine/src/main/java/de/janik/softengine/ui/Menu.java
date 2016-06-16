package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.entity.Entity;
import de.janik.softengine.game.Game;
import de.janik.softengine.util.ColorARGB;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.LIGHT_GRAY;
import static de.janik.softengine.util.ColorARGB.MAGENTA;
import static de.janik.softengine.util.ColorARGB.ToAWT_Color;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Menu extends DrawableEntity {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final ColorARGB DEFAULT_COLOR_MOUSE_OVER;
    private static final ColorARGB DEFAULT_COLOR_MENU_ITEM_SELECTED;

    private final Game game;

    private final List<DrawableEntity> menuItems;

    private final Label labelSelected;

    private final int[] pixel;

    private final Bitmap bitmap;

    private final Graphics2D background;

    private ColorARGB backgroundColor = MAGENTA;

    private DrawableEntity selectedItem;
    private int selectedIndex;

    private boolean keyDownReleased;
    private boolean keyUpReleased;

    // <- Static ->
    static {
        DEFAULT_COLOR_MOUSE_OVER = ColorARGB.Decode("#E8_A3_17");
        DEFAULT_COLOR_MENU_ITEM_SELECTED = LIGHT_GRAY;
    }

    // <- Constructor ->
    public Menu(final Game game, final int width, final int height) {
        super(0, 0);

        this.width = width;
        this.height = height;

        this.game = game;

        menuItems = new ArrayList<>(2);

        pixel = new int[width * height];

        bitmap = new Bitmap(width, height, pixel);

        background = bitmap.getGraphics();

        setSprite(bitmap);

        labelSelected = new Label(">");
        labelSelected.setTextSize(20);
        labelSelected.setTextColor(BLACK);
        labelSelected.setZ(getZ() + 2);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void tick(final long ticks, final InputManager input) {
        final boolean isKeyDownDown = input.isKeyDownDown();
        final boolean isKeyUpDown = input.isKeyUpDown();

        if (isKeyDownDown && keyDownReleased) {
            keyDownReleased = false;

            if (++selectedIndex >= menuItems.size())
                selectedIndex = 0;
        }

        if (isKeyUpDown && keyUpReleased) {
            keyUpReleased = false;

            if (--selectedIndex < 0)
                selectedIndex = menuItems.size() - 1;
        }

        if (selectedItem instanceof Button)
            if (selectedItem.isMouseOver())
                ((Button) selectedItem).setTextColor(DEFAULT_COLOR_MOUSE_OVER);
            else
                ((Button) selectedItem).setTextColor(BLACK);

        selectedItem = menuItems.get(selectedIndex);

        if (selectedItem instanceof Button)
            ((Button) selectedItem).setTextColor(DEFAULT_COLOR_MENU_ITEM_SELECTED);

        labelSelected.setLocation(selectedItem.getX() - labelSelected.getWidth() - 8, selectedItem.getY() + (selectedItem.getHeight() / 2) - labelSelected.getHeight() / 2);

        if (!isKeyDownDown)
            keyDownReleased = true;

        if (!isKeyUpDown)
            keyUpReleased = true;

        if (input.isKeyDown(VK_ENTER))
            if (selectedItem != null)
                selectedItem.pressKey(VK_ENTER);

        background.setColor(ToAWT_Color(backgroundColor));

        background.fillRect(0, 0, width, height);

        // TODO: Sort for 'Z' without disturbing the item order.
        menuItems.forEach(e -> bitmap.draw(e.getX() - getX(), bitmap.getHeight() - (e.getY() - getY() + e.getHeight()), e.getSprite()));

        bitmap.draw(labelSelected.getX() - getX(), bitmap.getHeight() - (labelSelected.getY() - getY() + labelSelected.getHeight()), labelSelected.getSprite());
    }

    public void init() {
        game.add((Entity) labelSelected);

        menuItems.forEach(e -> game.add((Entity) e));
    }

    public void add(final DrawableEntity entity, final int paddingLeft, final int paddingTop) {
        final int numMenuItems = menuItems.size();

        if (numMenuItems == 0)
            entity.setLocation(getX() + paddingLeft, getY() + getHeight() - entity.getHeight() - paddingTop);
        else
            entity.setLocation(getX() + paddingLeft, menuItems.get(numMenuItems - 1).getY() - entity.getHeight() - paddingTop);

        entity.setZ(getZ() + 1);

        if (entity instanceof Button) {
            entity.onMouseOver(() -> ((Button) entity).setTextColor(DEFAULT_COLOR_MOUSE_OVER));
            entity.onMouseExit(() -> ((Button) entity).setTextColor(BLACK));
        }

        menuItems.add(entity);
    }

    // <- Getter & Setter ->
    @Override
    public Bitmap getSprite() {
        return (Bitmap) super.getSprite();
    }

    @Override
    public void setWidth(final int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);
    }

    // <- Static ->
}
