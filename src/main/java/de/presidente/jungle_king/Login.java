package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.util.ColorARGB;
import de.presidente.jungle_king.net.ConnectionManager;

import java.util.ArrayList;
import java.util.List;

import static de.janik.softengine.ui.TextLocation.CENTER;
import static de.janik.softengine.util.ColorARGB.DARK_GRAY;
import static de.janik.softengine.util.ColorARGB.DARK_SLATE_GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.WHITE;
import static de.presidente.jungle_king.util.Constants.TICKS_PER_SECOND;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Login extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int MOVE_INTERVAL;
    private static final int TIME;

    private final Button buttonLogin;
    private final Button buttonRegister;
    private final Button buttonCreateAccount;
    private final TextField buttonUserName;
    private final TextField buttonPassword;

    private final Label buttonNotRegistered;

    private final Rectangle backgroundLogin;
    private final Rectangle backgroundRegister;
    private final Rectangle backgroundTextFieldUser;
    private final Rectangle backgroundTextFieldPassword;

    private final List<DrawableEntity> loginComponents;
    private final List<DrawableEntity> registerComponents;

    private ConnectionManager server;

    private State state = State.LOGIN;

    private long timer;

    // <- Static ->
    static {
        TIME = (int) (0.75f * TICKS_PER_SECOND);

        MOVE_INTERVAL = 900 / TIME;
    }

    // <- Constructor ->
    public Login(final Game game) {
        super(game);

        backgroundLogin = new Rectangle(500, 314);
        backgroundLogin.setColor(WHITE);
        backgroundLogin.setLocation(engine.getScreenWidth() / 2 - backgroundLogin.getWidth() / 2,
                engine.getScreenHeight() / 2 - backgroundLogin.getHeight() / 2);

        final int offsetTopBottom = 45;
        final int offset = 25;
        final int offsetCreateAccount = 8;
        final int textFieldHeight = 60;

        final ColorARGB lightGray = new ColorARGB(222, 222, 222);

        backgroundTextFieldUser = new Rectangle(400, textFieldHeight);
        backgroundTextFieldUser.setZ(backgroundLogin.getZ() + 1);
        backgroundTextFieldUser.setColor(lightGray);
        backgroundTextFieldUser.setLocation(engine.getScreenWidth() / 2 - backgroundTextFieldUser.getWidth() / 2,
                backgroundLogin.getY() + backgroundLogin.getHeight() - offsetTopBottom - backgroundTextFieldUser.getHeight());

        backgroundTextFieldPassword = new Rectangle(400, textFieldHeight);
        backgroundTextFieldPassword.setZ(backgroundLogin.getZ() + 1);
        backgroundTextFieldPassword.setColor(lightGray);
        backgroundTextFieldPassword.setLocation(engine.getScreenWidth() / 2 - backgroundTextFieldPassword.getWidth() / 2,
                backgroundTextFieldUser.getY() - offset - backgroundTextFieldPassword.getHeight());

        buttonLogin = new Button("Login.", CENTER);
        buttonLogin.setFont(SOURCE_CODE_PRO);
        buttonLogin.setZ(backgroundTextFieldPassword.getZ() + 1);
        buttonLogin.setTextColor(WHITE);
        buttonLogin.setTextSize(28);
        buttonLogin.setSize(400, buttonLogin.getHeight() + (int) (buttonLogin.getHeight() * 0.4f));
        buttonLogin.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonLogin.setLocation(engine.getScreenWidth() / 2 - buttonLogin.getWidth() / 2, backgroundTextFieldPassword.getY() - (offset + 5) - buttonLogin.getHeight());
        buttonLogin.onMousePress(() -> {
            // TODO:(jan) Login...
        });

        buttonNotRegistered = new Label("Not registered?");
        buttonNotRegistered.setTextSize(16);
        buttonNotRegistered.setZ(backgroundLogin.getZ() + 1);
        buttonNotRegistered.setTextColor(DARK_SLATE_GRAY);
        buttonNotRegistered.setLocation(engine.getScreenWidth() / 2 - buttonNotRegistered.getWidth(), buttonLogin.getY() - offsetCreateAccount - buttonNotRegistered.getHeight());

        buttonCreateAccount = new Button("Create an Account.");
        buttonCreateAccount.setTextSize(16);
        buttonCreateAccount.setZ(backgroundLogin.getZ() + 1);
        buttonCreateAccount.setTextColor(GREEN);
        buttonCreateAccount.setLocation(engine.getScreenWidth() / 2, buttonNotRegistered.getY());
        buttonCreateAccount.onMousePress(() -> {
            if (state == State.LOGIN)
                switchState(State.TRANSITION_REGISTER);
        });

        buttonUserName = new TextField("Username");
        buttonUserName.setFont(SOURCE_CODE_PRO);
        buttonUserName.setZ(backgroundTextFieldUser.getZ() + 1);
        buttonUserName.setTextColor(DARK_GRAY);
        buttonUserName.setTextSize(28);
        buttonUserName.setLocation(backgroundTextFieldUser.getX(), backgroundTextFieldUser.getY() + buttonUserName.getHeight() / 2 - 4);

        buttonPassword = new TextField("Password");
        buttonPassword.setFont(SOURCE_CODE_PRO);
        buttonPassword.setZ(backgroundTextFieldPassword.getZ() + 1);
        buttonPassword.setTextColor(DARK_GRAY);
        buttonPassword.setTextSize(28);
        buttonPassword.setLocation(backgroundTextFieldPassword.getX(), backgroundTextFieldPassword.getY() + buttonPassword.getHeight() / 2 - 4);

        loginComponents = new ArrayList<>(8);

        loginComponents.add(backgroundLogin);
        loginComponents.add(backgroundTextFieldUser);
        loginComponents.add(backgroundTextFieldPassword);
        loginComponents.add(buttonUserName);
        loginComponents.add(buttonPassword);
        loginComponents.add(buttonLogin);
        loginComponents.add(buttonNotRegistered);
        loginComponents.add(buttonCreateAccount);

        final int moveOffset = MOVE_INTERVAL * TIME;

        backgroundRegister = new Rectangle(500, 340);
        backgroundRegister.setColor(WHITE);

        backgroundRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - backgroundRegister.getWidth() / 2,
                engine.getScreenHeight() / 2 - backgroundRegister.getHeight() / 2);

        buttonRegister = new Button("Register.", CENTER);
        buttonRegister.setFont(SOURCE_CODE_PRO);
        buttonRegister.setZ(backgroundTextFieldPassword.getZ() + 1);
        buttonRegister.setTextColor(WHITE);
        buttonRegister.setTextSize(28);
        buttonRegister.setSize(400, buttonRegister.getHeight() + (int) (buttonRegister.getHeight() * 0.4f));
        buttonRegister.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - buttonRegister.getWidth() / 2, backgroundTextFieldPassword.getY() - (offset + 5) - buttonRegister.getHeight());
        buttonRegister.onMousePress(() -> {
            if (state == State.REGISTER)
                switchState(State.TRANSITION_LOGIN);
        });

        registerComponents = new ArrayList<>();

        registerComponents.add(backgroundRegister);
        registerComponents.add(buttonRegister);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        server = getGame().getServerConnection();

        game.setBackgroundColor(new ColorARGB(115, 195, 90));

        loginComponents.forEach(this::add);
        registerComponents.forEach(this::add);

        switchState(state);
    }

    private void switchState(final State nextState) {
        switch (nextState) {
            case LOGIN: {
                registerComponents.forEach(e -> e.setVisible(false));

                break;
            }
            case REGISTER: {
                loginComponents.forEach(e -> e.setVisible(false));

                break;
            }
            case TRANSITION_LOGIN: {
                loginComponents.forEach(e -> e.setVisible(true));

                timer = TIME;

                break;
            }
            case TRANSITION_REGISTER: {
                registerComponents.forEach(e -> e.setVisible(true));

                timer = TIME;

                break;
            }
        }

        state = nextState;
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        switch (state) {
            case LOGIN:
                break;
            case REGISTER:
                break;
            case TRANSITION_LOGIN:
                if (timer > 0) {
                    loginComponents.forEach(e -> e.setLocation(e.getX() + MOVE_INTERVAL, e.getY()));

                    registerComponents.forEach(e -> e.setLocation(e.getX() + MOVE_INTERVAL, e.getY()));

                    timer--;
                } else
                    switchState(State.LOGIN);
                break;
            case TRANSITION_REGISTER:
                if (timer > 0) {
                    loginComponents.forEach(e -> e.setLocation(e.getX() - MOVE_INTERVAL, e.getY()));

                    registerComponents.forEach(e -> e.setLocation(e.getX() - MOVE_INTERVAL, e.getY()));

                    timer--;
                } else
                    switchState(State.REGISTER);

                break;
        }
    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }

    // <- Static ->
    private enum State {
        LOGIN,
        REGISTER,
        TRANSITION_LOGIN,
        TRANSITION_REGISTER
    }
}
