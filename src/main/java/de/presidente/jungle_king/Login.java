package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.util.ColorARGB;

import static de.janik.softengine.util.ColorARGB.DARK_GRAY;
import static de.janik.softengine.util.ColorARGB.DARK_SLATE_GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.WHITE;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Login extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Button buttonLogin;
    private final Button buttonNotRegistered;
    private final Button buttonCreateAccount;
    private final Button buttonUserName;
    private final Button buttonPassword;

    private final Rectangle background;
    private final Rectangle backgroundButtonLogin;
    private final Rectangle backgroundTextFieldUser;
    private final Rectangle backgroundTextFieldPassword;

    // <- Static ->

    // <- Constructor ->
    public Login(final Game game) {
        super(game);

        background = new Rectangle(500, 300);
        background.setColor(WHITE);
        background.setLocation(engine.getScreenWidth() / 2 - background.getWidth() / 2,
                engine.getScreenHeight() / 2 - background.getHeight() / 2);

        final int offsetTopBottom = 45;
        final int offset = 25;
        final int offsetCreateAccount = 8;
        final int textFieldHeight = 60;

        final ColorARGB lightGray = new ColorARGB(222, 222, 222);

        backgroundTextFieldUser = new Rectangle(400, textFieldHeight);
        backgroundTextFieldUser.setZ(background.getZ() + 1);
        backgroundTextFieldUser.setColor(lightGray);
        backgroundTextFieldUser.setLocation(engine.getScreenWidth() / 2 - backgroundTextFieldUser.getWidth() / 2,
                background.getY() + background.getHeight() - offsetTopBottom - backgroundTextFieldUser.getHeight());

        backgroundTextFieldPassword = new Rectangle(400, textFieldHeight);
        backgroundTextFieldPassword.setZ(background.getZ() + 1);
        backgroundTextFieldPassword.setColor(lightGray);
        backgroundTextFieldPassword.setLocation(engine.getScreenWidth() / 2 - backgroundTextFieldPassword.getWidth() / 2,
                backgroundTextFieldUser.getY() - offset - backgroundTextFieldPassword.getHeight());

        buttonLogin = new Button("Login.");
        buttonLogin.setFont(SOURCE_CODE_PRO);
        buttonLogin.setZ(backgroundTextFieldPassword.getZ() + 1);
        buttonLogin.setTextColor(WHITE);
        buttonLogin.setTextSize(28);
        buttonLogin.setLocation(engine.getScreenWidth() / 2 - buttonLogin.getWidth() / 2, backgroundTextFieldPassword.getY() - (offset + 5) - buttonLogin.getHeight());

        backgroundButtonLogin = new Rectangle(400, buttonLogin.getHeight() + (int) (buttonLogin.getHeight() * 0.4f));
        backgroundButtonLogin.setZ(background.getZ() + 1);
        backgroundButtonLogin.setColor(new ColorARGB(0, 180, 65));
        backgroundButtonLogin.setZ(buttonLogin.getZ() - 1);
        backgroundButtonLogin.setLocation(buttonLogin.getX() - (backgroundButtonLogin.getWidth() / 2 - buttonLogin.getWidth() / 2),
                buttonLogin.getY() - (backgroundButtonLogin.getHeight() / 2 - buttonLogin.getHeight() / 2));

        buttonNotRegistered = new Button("Not registered?");
        buttonNotRegistered.setTextSize(16);
        buttonNotRegistered.setZ(background.getZ() + 1);
        buttonNotRegistered.setTextColor(DARK_SLATE_GRAY);
        buttonNotRegistered.setLocation(engine.getScreenWidth() / 2 - buttonNotRegistered.getWidth(), backgroundButtonLogin.getY() - offsetCreateAccount - buttonNotRegistered.getHeight());

        buttonCreateAccount = new Button("Create an Account.");
        buttonCreateAccount.setTextSize(16);
        buttonCreateAccount.setZ(background.getZ() + 1);
        buttonCreateAccount.setTextColor(GREEN);
        buttonCreateAccount.setLocation(engine.getScreenWidth() / 2, buttonNotRegistered.getY());

        buttonUserName = new Button("Username");
        buttonUserName.setFont(SOURCE_CODE_PRO);
        buttonUserName.setZ(backgroundTextFieldUser.getZ() + 1);
        buttonUserName.setTextColor(DARK_GRAY);
        buttonUserName.setTextSize(28);
        buttonUserName.setLocation(backgroundTextFieldUser.getX(), backgroundTextFieldUser.getY() + buttonUserName.getHeight() / 2 - 4);

        buttonPassword = new Button("Password");
        buttonPassword.setFont(SOURCE_CODE_PRO);
        buttonPassword.setZ(backgroundTextFieldPassword.getZ() + 1);
        buttonPassword.setTextColor(DARK_GRAY);
        buttonPassword.setTextSize(28);
        buttonPassword.setLocation(backgroundTextFieldPassword.getX(), backgroundTextFieldPassword.getY() + buttonPassword.getHeight() / 2 - 4);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        game.setBackgroundColor(new ColorARGB(115, 195, 90));

        game.add(background);
        game.add(backgroundTextFieldUser);
        game.add(buttonUserName);
        game.add(backgroundTextFieldPassword);
        game.add(buttonPassword);
        game.add(backgroundButtonLogin);
        game.add(buttonLogin);
        game.add(buttonNotRegistered);
        game.add(buttonCreateAccount);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
    }

    // <- Getter & Setter ->
    // <- Static ->
}
