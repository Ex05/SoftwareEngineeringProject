package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.passwd.PasswordService;
import de.janik.softengine.Engine;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.PasswordField;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.util.ColorARGB;
import de.presidente.jungle_king.net.ConnectionManager;
import de.presidente.net.LoginCredentials;
import de.presidente.net.Packet;
import de.presidente.net.Packet_001_Login;
import de.presidente.net.Packet_003_Permission;
import de.presidente.net.Packet_005_ReceiveSalt;
import de.presidente.net.Packet_006_Salt;
import de.presidente.net.Packet_007_Register;
import de.presidente.net.Packet_008_CheckUsernameAvailability;
import de.presidente.net.Packet_009_UsernameAvailable;

import java.util.ArrayList;
import java.util.List;

import static de.janik.softengine.util.ColorARGB.DARK_SLATE_GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.RED;
import static de.janik.softengine.util.ColorARGB.WHITE;
import static de.presidente.jungle_king.util.Constants.TICKS_PER_SECOND;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static de.presidente.net.Permission.DENIED;
import static de.presidente.net.Permission.GUARANTED;

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
    private final TextField textFieldUserName;
    private final PasswordField passwordFieldPassword;

    private final Label labelNotRegistered;

    private final Rectangle backgroundLogin;
    private final Rectangle backgroundRegister;

    private final List<DrawableEntity> loginComponents;
    private final List<DrawableEntity> registerComponents;

    private ConnectionManager server;

    private State state = State.LOGIN;

    private SubState subState = SubState.USER_INPUT;

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

        textFieldUserName = new TextField("Username");
        textFieldUserName.setSize(400, textFieldHeight);
        textFieldUserName.setFont(SOURCE_CODE_PRO);
        textFieldUserName.setBackgroundColor(lightGray);
        textFieldUserName.setZ(backgroundLogin.getZ() + 1);
        textFieldUserName.setTextSize(28);
        textFieldUserName.setLocation(engine.getScreenWidth() / 2 - textFieldUserName.getWidth() / 2,
                backgroundLogin.getY() + backgroundLogin.getHeight() - offsetTopBottom - textFieldUserName.getHeight());
        textFieldUserName.onInputChange(() -> {
            final String userName = textFieldUserName.getUserInput();

            if (!userName.equals(""))
                server.send(new Packet_008_CheckUsernameAvailability(userName));
        });

        passwordFieldPassword = new PasswordField("Password");
        passwordFieldPassword.setSize(400, textFieldHeight);
        passwordFieldPassword.setFont(SOURCE_CODE_PRO);
        passwordFieldPassword.setBackgroundColor(lightGray);
        passwordFieldPassword.setZ(backgroundLogin.getZ() + 1);
        passwordFieldPassword.setTextSize(28);
        passwordFieldPassword.setLocation(engine.getScreenWidth() / 2 - passwordFieldPassword.getWidth() / 2,
                textFieldUserName.getY() - offset - passwordFieldPassword.getHeight());

        buttonLogin = new Button("Login.");
        buttonLogin.setFont(SOURCE_CODE_PRO);
        buttonLogin.setZ(passwordFieldPassword.getZ() + 1);
        buttonLogin.setTextColor(WHITE);
        buttonLogin.setTextSize(28);
        buttonLogin.setSize(400, buttonLogin.getHeight() + (int) (buttonLogin.getHeight() * 0.4f));
        buttonLogin.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonLogin.setLocation(engine.getScreenWidth() / 2 - buttonLogin.getWidth() / 2, passwordFieldPassword.getY() - (offset + 5) - buttonLogin.getHeight());
        buttonLogin.onMousePress(() -> {
            // TODO:(jan) Check that a password was entered.

            server.send(new Packet_005_ReceiveSalt(textFieldUserName.getUserInput()));

            subState = SubState.AWAITING_SALT;
        });

        labelNotRegistered = new Label("Not registered?");
        labelNotRegistered.setTextSize(16);
        labelNotRegistered.setZ(backgroundLogin.getZ() + 1);
        labelNotRegistered.setTextColor(DARK_SLATE_GRAY);
        labelNotRegistered.setLocation(engine.getScreenWidth() / 2 - labelNotRegistered.getWidth(), buttonLogin.getY() - offsetCreateAccount - labelNotRegistered.getHeight());

        buttonCreateAccount = new Button("Create an Account.");
        buttonCreateAccount.setFocusAble(false);
        buttonCreateAccount.setTextSize(16);
        buttonCreateAccount.setZ(backgroundLogin.getZ() + 1);
        buttonCreateAccount.setTextColor(GREEN);
        buttonCreateAccount.setLocation(engine.getScreenWidth() / 2, labelNotRegistered.getY());
        buttonCreateAccount.onMousePress(() -> {
            if (state == State.LOGIN)
                switchState(State.TRANSITION_REGISTER);
        });

        loginComponents = new ArrayList<>(8);

        loginComponents.add(backgroundLogin);
        loginComponents.add(textFieldUserName);
        loginComponents.add(passwordFieldPassword);
        loginComponents.add(buttonLogin);
        loginComponents.add(labelNotRegistered);
        loginComponents.add(buttonCreateAccount);

        final int moveOffset = MOVE_INTERVAL * TIME;

        backgroundRegister = new Rectangle(500, 340);
        backgroundRegister.setColor(WHITE);

        backgroundRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - backgroundRegister.getWidth() / 2,
                engine.getScreenHeight() / 2 - backgroundRegister.getHeight() / 2);

        buttonRegister = new Button("Register.");
        buttonRegister.setFont(SOURCE_CODE_PRO);
        buttonRegister.setZ(backgroundRegister.getZ() + 1);
        buttonRegister.setTextColor(WHITE);
        buttonRegister.setTextSize(28);
        buttonRegister.setSize(400, buttonRegister.getHeight() + (int) (buttonRegister.getHeight() * 0.4f));
        buttonRegister.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - buttonRegister.getWidth() / 2, backgroundRegister.getY() + (offset + 5));
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

        switch (subState) {
            case USER_INPUT: {
                break;
            }
            case AWAITING_SALT: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_003_Permission) {
                        final Packet_003_Permission packet = (Packet_003_Permission) p;

                        if (packet.getPermission() == DENIED) {
                            // TODO:(jan) Handle wrong username.
                            System.err.println("Login.tick[TODO:(jan) Handle wrong username.]");

                            textFieldUserName.setTextColor(RED);
                        }

                    } else if (p instanceof Packet_006_Salt) {
                        final Packet_006_Salt packet = (Packet_006_Salt) p;

                        subState = SubState.LOGGING_IN;

                        final byte[] salt = packet.getSalt();

                        final byte[] pbkdf2Hash = PasswordService.GetInstance().pbkdf2Hash(passwordFieldPassword.getPassword(), salt);

                        server.send(new Packet_001_Login(new LoginCredentials(textFieldUserName.getUserInput(), pbkdf2Hash)));
                    }
                }

                break;
            }
            case LOGGING_IN: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_003_Permission) {
                        final Packet_003_Permission packet = (Packet_003_Permission) p;

                        if (packet.getPermission() == GUARANTED) {
                            // TODO:(jan) Enter lobby.
                            System.err.println("Login.tick[TODO:(jan) Enter lobby.]");

                            System.out.println("Logged in.");
                        }
                        else {
                            // TODO:(jan) Handle wrong password.

                            System.err.println("Login.tick[TODO:(jan) Handle wrong password.]");

                            passwordFieldPassword.setTextColor(RED);
                        }
                    }
                }

                break;
            }
            case CHECK_USERNAME: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_009_UsernameAvailable) {
                        final Packet_009_UsernameAvailable packet = (Packet_009_UsernameAvailable) p;

                        textFieldUserName.setTextColor(packet.getPermission() == GUARANTED ? GREEN : RED);
                    }
                }

                break;
            }

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

    private enum SubState {
        USER_INPUT,
        AWAITING_SALT,
        LOGGING_IN,
        CHECK_USERNAME
    }
}
