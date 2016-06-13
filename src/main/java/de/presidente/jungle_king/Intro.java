package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.TextField;
import de.janik.util.resource_loader.LaunchComponent;
import de.janik.util.resource_loader.Launcher;

import static de.janik.softengine.ui.Text.Interpolation.BILINEAR;
import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.DARK_ORANGE;
import static de.janik.softengine.util.ColorARGB.LIGHT_GRAY;
import static de.janik.softengine.util.ColorARGB.ORANGE;
import static de.janik.softengine.util.Util.Format;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Intro extends State implements LaunchComponent {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final String BUTTON_LOADING_BAR_FORMAT_STRING;

    private final TextField textWelcome;
    private final Button buttonPressEnter;

    private final Launcher launcher;

    private final Button loadingBar;

    private float textWelcomePosY;

    private boolean enterPressed = false;
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

        textWelcome = new TextField("Welcome");
        textWelcome.setFont(SOURCE_CODE_PRO);
        textWelcome.setAntialiasing(true, BILINEAR);
        textWelcome.setTextColor(ORANGE);
        textWelcome.setTextSize(256);
        textWelcome.setZ(1);
        textWelcome.setLocation(engine.getScreenWidth() / 2 - textWelcome.getWidth() / 2, -textWelcome.getHeight());

        buttonPressEnter = new Button("<Press Enter>");
        buttonPressEnter.setFont(SOURCE_CODE_PRO);
        buttonPressEnter.setAntialiasing(true, BILINEAR);
        buttonPressEnter.setTextColor(DARK_ORANGE);
        buttonPressEnter.setTextSize(86);
        buttonPressEnter.setVisible(false);
        buttonPressEnter.onKeyPress(keyCode -> {
            if (keyCode == VK_ENTER) {
                // TODO:(jan) Switch state to 'playing".
            }
        });

        loadingBar = new Button();
        loadingBar.setFont(SOURCE_CODE_PRO);
        loadingBar.setTextSize(16);
        loadingBar.setTextColor(LIGHT_GRAY);
        loadingBar.setVisible(false);

        loadingBar.setLocation(8, 8);

        // TODO:(jan) Add everything that needs to be loaded here.
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        game.setBackgroundColor(BLACK);

        game.add(textWelcome);
        game.add(buttonPressEnter);

        textWelcomePosY = -textWelcome.getHeight();

        game.add(loadingBar);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        if (textWelcomePosY < engine.getScreenHeight() / 2 - textWelcome.getHeight() / 4) {
            textWelcomePosY += 10;
            textWelcome.setY(textWelcomePosY);
        } else if (resourcesFinishedLoading)
            buttonPressEnter.setVisible(true);

        buttonPressEnter.setLocation(engine.getScreenWidth() / 2 - buttonPressEnter.getWidth() / 2, textWelcome.getY() - 40);

        if (!enterPressed)
            if (engine.getInput().isKeyDown(VK_ENTER)) {
                buttonPressEnter.pressKey(VK_ENTER);

                enterPressed = true;
            }

        if (!launcherStarted) {
            loadingBar.setVisible(true);

            launcher.launch();

            launcherStarted = true;
        }

        if (dirty) {
            loadingBar.setText(Format(BUTTON_LOADING_BAR_FORMAT_STRING, progress, msg));

            dirty = false;
        }
    }

    @Override
    public void launch() {
        // TODO:(jan) Switch state to 'playing'.

        resourcesFinishedLoading = true;

        loadingBar.setVisible(false);
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
