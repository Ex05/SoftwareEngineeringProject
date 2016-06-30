package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.TexturedQuad;

import static de.janik.softengine.util.ColorARGB.DARK_ORANGE;
import static de.presidente.jungle_king.util.Resources.IMAGE_PREGAME_LOBBY_MAP_PREVIEW;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class PreGameLobby extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private TexturedQuad mapPreview;

    // <- Static ->

    // <- Constructor ->
    public PreGameLobby(final JungleKingGame game) {
        super(game);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        game.setBackgroundColor(DARK_ORANGE);

        mapPreview = new TexturedQuad(IMAGE_PREGAME_LOBBY_MAP_PREVIEW);
        mapPreview.setLocation(engine.getScreenWidth() - mapPreview.getWidth() - 20, engine.getScreenHeight() - mapPreview.getHeight() - 20);

        add(mapPreview);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        super.tick(ticks, engine);
    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }

    // <- Static ->
}
