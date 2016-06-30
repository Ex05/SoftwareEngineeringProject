package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.imageLoader.ImageLoader;
import de.janik.softengine.Engine;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.DropShadow;
import de.janik.softengine.ui.Label;
import de.janik.softengine.util.ColorARGB;
import de.janik.util.resource_loader.LaunchComponent;
import de.janik.util.resource_loader.Launcher;

import java.awt.Font;

import static de.janik.softengine.ui.Text.Interpolation.BILINEAR;
import static de.janik.softengine.ui.TextLocation.LEFT;
import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.DARK_BLUE;
import static de.janik.softengine.util.ColorARGB.DARK_ORANGE;
import static de.janik.softengine.util.Util.Format;
import static de.presidente.jungle_king.util.Constants.IMAGE_PREGAME_LOBBY_MAP_PREVIEW_PATH;
import static de.presidente.jungle_king.util.Resources.IMAGE_PREGAME_LOBBY_MAP_PREVIEW;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static java.awt.Font.BOLD;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Intro extends State implements LaunchComponent {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final String BUTTON_LOADING_BAR_FORMAT_STRING;

    private final Label textWelcome;
    private final Button buttonPressEnter;

    private final Launcher launcher;

    private final Button loadingBar;

    private float textWelcomePosY;

    private boolean resourcesFinishedLoading = false;
    private boolean launcherStarted = false;
    private boolean dirty = false;

    private int progress;

    private String msg;

    // <- Static ->
    static {
        BUTTON_LOADING_BAR_FORMAT_STRING = "%3d%% %s";
    }

    // <- Constructor ->
    public Intro(final Game game) {
        super(game);

        launcher = new Launcher(this);

        textWelcome = new Label("Welcome");
        textWelcome.setFont(SOURCE_CODE_PRO);
        textWelcome.setAntialiasing(true, BILINEAR);
        textWelcome.setTextColor(DARK_ORANGE);
        textWelcome.setTextSize(256);
        textWelcome.setZ(1);
        textWelcome.getText().setDropShadow(new DropShadow(BLACK, 3, 3));
        textWelcome.setLocation(engine.getScreenWidth() / 2 - textWelcome.getWidth() / 2, -textWelcome.getHeight());

        buttonPressEnter = new Button("<Press Enter>");
        buttonPressEnter.setZ(textWelcome.getZ() + 1);
        buttonPressEnter.setFont(SOURCE_CODE_PRO);
        buttonPressEnter.setFocusAble(false);
        buttonPressEnter.setAntialiasing(true, BILINEAR);
        buttonPressEnter.setTextColor(DARK_ORANGE);
        buttonPressEnter.setTextSize(86);
        buttonPressEnter.setVisible(false);
        buttonPressEnter.getText().setDropShadow(new DropShadow(BLACK, 2, 2));
        buttonPressEnter.onMousePress(() -> game.switchState(LoginAndRegister.class));

        loadingBar = new Button("", LEFT);
        loadingBar.setFocusAble(false);
        loadingBar.setFont(new Font("Consolas", BOLD, 16));
        loadingBar.setTextSize(17);
        loadingBar.setTextColor(DARK_BLUE);
        loadingBar.setVisible(false);

        loadingBar.setLocation(8, 8);

        launcher.add(1, parent -> {
            parent.publish("Loading[Image:" + IMAGE_PREGAME_LOBBY_MAP_PREVIEW_PATH + "].");

            IMAGE_PREGAME_LOBBY_MAP_PREVIEW = ImageLoader.GetInstance().setInputFile(IMAGE_PREGAME_LOBBY_MAP_PREVIEW_PATH).load().asImage().get();
        });
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        game.setBackgroundColor(new ColorARGB(115, 195, 90));

        game.add(textWelcome);
        game.add(buttonPressEnter);

        textWelcomePosY = -textWelcome.getHeight();

        game.add(loadingBar);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        if (textWelcomePosY < engine.getScreenHeight() / 2 - textWelcome.getHeight() / 4) {
            textWelcomePosY += game.getDesiredTicksPerSecond() / 10;
            textWelcome.setY(textWelcomePosY);
        } else if (resourcesFinishedLoading)
            buttonPressEnter.setVisible(true);

        buttonPressEnter.setLocation(engine.getScreenWidth() / 2 - buttonPressEnter.getWidth() / 2, textWelcome.getY() - 40);

        if (!launcherStarted) {
            loadingBar.setVisible(true);

            launcher.launch();

            launcherStarted = true;
        }

        if (dirty) {
            loadingBar.setText(Format(BUTTON_LOADING_BAR_FORMAT_STRING, progress, msg));

            dirty = false;
        }

        if (buttonPressEnter.isVisible())
            if (engine.getInput().isKeyDown(VK_ENTER))
                game.switchState(LoginAndRegister.class);
    }

    @Override
    public void launch() {
        resourcesFinishedLoading = true;

        publish("Loading complete.");
    }

    @Override
    public void publish(final String msg) {
        this.msg = msg;

        dirty = true;
    }

    @Override
    public void progress(final int progress) {
        this.progress = progress;

        dirty = true;
    }

    // <- Getter & Setter ->
    // <- Static ->
}
