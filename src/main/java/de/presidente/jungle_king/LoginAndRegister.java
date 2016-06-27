package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.passwd.PasswordService;
import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.Game;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Container;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.PasswordField;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.ui.UI_Component;
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
import de.presidente.net.Packet_009_UserNameAvailable;
import de.presidente.net.Packet_010_RegistrationConfirmation;

import java.util.Arrays;

import static de.janik.softengine.util.ColorARGB.DARK_SLATE_GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.LIME_GREEN;
import static de.janik.softengine.util.ColorARGB.RED;
import static de.janik.softengine.util.ColorARGB.SLATE_BLUE;
import static de.janik.softengine.util.ColorARGB.WHITE;
import static de.presidente.jungle_king.util.Constants.TICKS_PER_SECOND;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static de.presidente.net.Permission.DENIED;
import static de.presidente.net.Permission.GRANTED;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class LoginAndRegister extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final int MOVE_INTERVAL;
    private static final int TIME;

    private final Button buttonLogin;
    private final Button buttonRegister;
    private final Button buttonCreateAccount;

    private final TextField textFieldUserNameLogin;
    private final TextField textFieldUserNameRegister;

    private final PasswordField passwordFieldPasswordLogin;
    private final PasswordField passwordFieldPasswordRegister;
    private final PasswordField passwordFieldPasswordRegisterConfirm;

    private final Label labelNotRegistered;
    private final Label labelCreateNewAccount;
    private final Label labelGoBack;

    private final Rectangle backgroundLogin;
    private final Rectangle backgroundRegister;

    private final Container<DrawableEntity> loginComponents;
    private final Container<DrawableEntity> registerComponents;

    private ConnectionManager server;

    private State state = State.LOGIN;

    private SubState subState = SubState.USER_INPUT;

    private UI_Component focusHolder;

    private long timer;

    private boolean userNameApproved;
    private boolean passwordsMatch;

    // <- Static ->
    static {
        TIME = (int) (0.75f * TICKS_PER_SECOND);

        MOVE_INTERVAL = 900 / TIME;
    }

    // <- Constructor ->
    public LoginAndRegister(final Game game) {
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

        textFieldUserNameLogin = new TextField("Username");
        textFieldUserNameLogin.setSize(400, textFieldHeight);
        textFieldUserNameLogin.setFont(SOURCE_CODE_PRO);
        textFieldUserNameLogin.setBackgroundColor(lightGray);
        textFieldUserNameLogin.setZ(backgroundLogin.getZ() + 1);
        textFieldUserNameLogin.setTextSize(30);
        textFieldUserNameLogin.setLocation(engine.getScreenWidth() / 2 - textFieldUserNameLogin.getWidth() / 2,
                backgroundLogin.getY() + backgroundLogin.getHeight() - offsetTopBottom - textFieldUserNameLogin.getHeight());

        passwordFieldPasswordLogin = new PasswordField("Password");
        passwordFieldPasswordLogin.setSize(400, textFieldHeight);
        passwordFieldPasswordLogin.setFont(SOURCE_CODE_PRO);
        passwordFieldPasswordLogin.setBackgroundColor(lightGray);
        passwordFieldPasswordLogin.setZ(backgroundLogin.getZ() + 1);
        passwordFieldPasswordLogin.setTextSize(30);
        passwordFieldPasswordLogin.setLocation(engine.getScreenWidth() / 2 - passwordFieldPasswordLogin.getWidth() / 2,
                textFieldUserNameLogin.getY() - offset - passwordFieldPasswordLogin.getHeight());

        buttonLogin = new Button("Login.");
        buttonLogin.setFont(SOURCE_CODE_PRO);
        buttonLogin.setFocusAble(false);
        buttonLogin.setZ(passwordFieldPasswordLogin.getZ() + 1);
        buttonLogin.setTextColor(WHITE);
        buttonLogin.setTextSize(28);
        buttonLogin.setSize(400, buttonLogin.getHeight() + (int) (buttonLogin.getHeight() * 0.4f));
        buttonLogin.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonLogin.setLocation(engine.getScreenWidth() / 2 - buttonLogin.getWidth() / 2, passwordFieldPasswordLogin.getY() - (offset + 5) - buttonLogin.getHeight());
        buttonLogin.onMousePress(() -> {
            if (passwordFieldPasswordLogin.getPassword().length != 0 && subState != SubState.AWAITING_SALT) {
                server.send(new Packet_005_ReceiveSalt(textFieldUserNameLogin.getUserInput()));

                subState = SubState.AWAITING_SALT;
            }
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
                switchState(State.TRANSITION_TO_REGISTER);
        });

        loginComponents = new Container<>();

        loginComponents.add(backgroundLogin);
        loginComponents.add(textFieldUserNameLogin);
        loginComponents.add(passwordFieldPasswordLogin);
        loginComponents.add(buttonLogin);
        loginComponents.add(labelNotRegistered);
        loginComponents.add(buttonCreateAccount);

        final int moveOffset = MOVE_INTERVAL * TIME;

        backgroundRegister = new Rectangle(500, 386);
        backgroundRegister.setColor(WHITE);

        backgroundRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - backgroundRegister.getWidth() / 2,
                engine.getScreenHeight() / 2 - backgroundRegister.getHeight() / 2);

        buttonRegister = new Button("Register.");
        buttonRegister.setFocusAble(false);
        buttonRegister.setFont(SOURCE_CODE_PRO);
        buttonRegister.setTextColor(WHITE);
        buttonRegister.setTextSize(28);
        buttonRegister.setSize(400, buttonRegister.getHeight() + (int) (buttonRegister.getHeight() * 0.4f));
        buttonRegister.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - buttonRegister.getWidth() / 2, backgroundRegister.getY() + (offset + 5));

        passwordFieldPasswordRegisterConfirm = new PasswordField("Repeat Password");
        passwordFieldPasswordRegisterConfirm.setSize(400, textFieldHeight);
        passwordFieldPasswordRegisterConfirm.setFont(SOURCE_CODE_PRO);
        passwordFieldPasswordRegisterConfirm.setBackgroundColor(lightGray);
        passwordFieldPasswordRegisterConfirm.setZ(backgroundRegister.getZ() + 1);
        passwordFieldPasswordRegisterConfirm.setTextSize(26);
        passwordFieldPasswordRegisterConfirm.setLocation(moveOffset + engine.getScreenWidth() / 2 - passwordFieldPasswordRegisterConfirm.getWidth() / 2,
                buttonRegister.getY() + offset + passwordFieldPasswordRegisterConfirm.getHeight());

        passwordFieldPasswordRegister = new PasswordField("Password");
        passwordFieldPasswordRegister.setSize(400, textFieldHeight);
        passwordFieldPasswordRegister.setFont(SOURCE_CODE_PRO);
        passwordFieldPasswordRegister.setBackgroundColor(lightGray);
        passwordFieldPasswordRegister.setZ(backgroundRegister.getZ() + 1);
        passwordFieldPasswordRegister.setTextSize(26);
        passwordFieldPasswordRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - passwordFieldPasswordRegister.getWidth() / 2,
                passwordFieldPasswordRegisterConfirm.getY() + offset / 2 + passwordFieldPasswordRegister.getHeight());

        passwordFieldPasswordRegisterConfirm.onInputChange(() -> {
            if (passwordFieldPasswordRegister.getPassword().length != 0)
                if (!Arrays.equals(passwordFieldPasswordRegisterConfirm.getPassword(), passwordFieldPasswordRegister.getPassword())) {
                    passwordFieldPasswordRegisterConfirm.setTextColor(RED);

                    passwordsMatch = false;
                } else {
                    passwordFieldPasswordRegisterConfirm.setTextColor(LIME_GREEN);
                    passwordsMatch = true;
                }
        });

        textFieldUserNameRegister = new TextField("Username");
        textFieldUserNameRegister.setSize(400, textFieldHeight);
        textFieldUserNameRegister.setFont(SOURCE_CODE_PRO);
        textFieldUserNameRegister.setBackgroundColor(lightGray);
        textFieldUserNameRegister.setZ(backgroundRegister.getZ() + 1);
        textFieldUserNameRegister.setTextSize(30);
        textFieldUserNameRegister.setLocation(moveOffset + engine.getScreenWidth() / 2 - textFieldUserNameRegister.getWidth() / 2,
                passwordFieldPasswordRegister.getY() + offset - 5 + textFieldUserNameRegister.getHeight());
        textFieldUserNameRegister.onInputChange(() -> {
            final String userName = textFieldUserNameRegister.getUserInput();

            if (!userName.equals(""))
                server.send(new Packet_008_CheckUsernameAvailability(userName));
        });

        buttonRegister.onMousePress(() -> {
            if (userNameApproved && passwordsMatch && passwordFieldPasswordRegister.getPassword().length > 0) {
                final byte[] salt = PasswordService.GetInstance().generateSalt();

                final Packet_007_Register register = new Packet_007_Register(
                        new LoginCredentials(textFieldUserNameRegister.getUserInput(),
                                PasswordService.GetInstance().pbkdf2Hash(passwordFieldPasswordRegister.getPassword(), salt)), salt);

                server.send(register);

                subState = SubState.AWAIT_REGISTRATION_RESPONSE;
            }
        });

        labelCreateNewAccount = new Label("Create a new Account:");
        labelCreateNewAccount.setTextSize(30);
        labelCreateNewAccount.setZ(backgroundRegister.getZ() + 1);
        labelCreateNewAccount.setTextColor(DARK_SLATE_GRAY);
        labelCreateNewAccount.setLocation(textFieldUserNameRegister.getX() - 15,
                textFieldUserNameRegister.getY() + offset + 6 + labelCreateNewAccount.getHeight());

        labelGoBack = new Label("<<go back");
        labelGoBack.setTextSize(16);
        labelGoBack.setZ(backgroundRegister.getZ() + 1);
        labelGoBack.setTextColor(SLATE_BLUE);
        labelGoBack.setLocation(backgroundRegister.getX() + 1, backgroundRegister.getY() + labelGoBack.getHeight() / 2 - 10);
        labelGoBack.onMousePress(() -> {
            if (state == State.REGISTER)
                switchState(State.TRANSITION_TO_LOGIN);
        });

        registerComponents = new Container<>();

        registerComponents.add(backgroundRegister);
        registerComponents.add(labelCreateNewAccount);
        registerComponents.add(textFieldUserNameRegister);
        registerComponents.add(passwordFieldPasswordRegister);
        registerComponents.add(passwordFieldPasswordRegisterConfirm);
        registerComponents.add(buttonRegister);
        registerComponents.add(labelGoBack);
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        server = getGame().getServerConnection();

        game.setBackgroundColor(new ColorARGB(115, 195, 90));

        loginComponents.forEach(this::add);
        initDefaultFocus();

        switchState(state);
    }

    private void clearLoginForm() {
        textFieldUserNameLogin.clear();

        passwordFieldPasswordLogin.clear();

        initDefaultFocus();
    }

    private void switchState(final State nextState) {
        switch (nextState) {
            case LOGIN: {
                registerComponents.forEach(this::remove);

                initDefaultFocus();

                break;
            }
            case REGISTER: {
                loginComponents.forEach(this::remove);

                if (focusHolder != null)
                    focusHolder.setFocus(true);
                else
                    initDefaultFocus();

                subState = SubState.CHECK_USERNAME_AVAILABILITY;

                break;
            }
            case TRANSITION_TO_LOGIN: {
                focusHolder = getFocusHolder();

                loginComponents.forEach(this::add);

                timer = TIME;

                break;
            }
            case TRANSITION_TO_REGISTER: {
                registerComponents.forEach(this::add);

                timer = TIME;

                break;
            }
        }

        state = nextState;
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        final InputManager inputManager = engine.getInput();

   /*     // TODO:(jan) Remove debug code.
        System.err.println("LoginAndRegister.tick[TODO:(jan) Remove debug code].");
        game.switchState(Lobby.class);

        if (true)
            return;*/

        switch (state) {
            case LOGIN: {
                if (inputManager.isKeyDown(VK_ENTER))
                    buttonLogin.pressMouse();

                break;
            }
            case REGISTER: {
                if (inputManager.isKeyDown(VK_ENTER))
                    buttonRegister.pressMouse();
            }
            break;
            case TRANSITION_TO_LOGIN:
                if (timer > 0) {
                    loginComponents.forEach(e -> e.setLocation(e.getX() + MOVE_INTERVAL, e.getY()));

                    registerComponents.forEach(e -> e.setLocation(e.getX() + MOVE_INTERVAL, e.getY()));

                    timer--;
                } else
                    switchState(State.LOGIN);
                break;
            case TRANSITION_TO_REGISTER:
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

                        if (packet.getPermission() == DENIED)
                            clearLoginForm();

                    } else if (p instanceof Packet_006_Salt) {
                        final Packet_006_Salt packet = (Packet_006_Salt) p;

                        subState = SubState.LOGGING_IN;

                        final byte[] salt = packet.getSalt();

                        final byte[] pbkdf2Hash = PasswordService.GetInstance().pbkdf2Hash(passwordFieldPasswordLogin.getPassword(), salt);

                        server.send(new Packet_001_Login(new LoginCredentials(textFieldUserNameLogin.getUserInput(), pbkdf2Hash)));
                    }
                }

                break;
            }
            case LOGGING_IN: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_003_Permission) {
                        final Packet_003_Permission packet = (Packet_003_Permission) p;

                        if (packet.getPermission() == GRANTED) {
                            game.switchState(Lobby.class);

                            return;
                        } else
                            clearLoginForm();
                    }
                }

                break;
            }

            case CHECK_USERNAME_AVAILABILITY: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_009_UserNameAvailable) {
                        final Packet_009_UserNameAvailable packet = (Packet_009_UserNameAvailable) p;


                        if (packet.getPermission() == GRANTED) {
                            textFieldUserNameRegister.setTextColor(LIME_GREEN);

                            userNameApproved = true;
                        } else {
                            textFieldUserNameRegister.setTextColor(RED);

                            userNameApproved = false;
                        }
                    }
                }

                break;
            }

            case AWAIT_REGISTRATION_RESPONSE: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_010_RegistrationConfirmation) {
                        final Packet_010_RegistrationConfirmation packet = (Packet_010_RegistrationConfirmation) p;

                        if (packet.isGranted())
                            System.out.println("Registered.");
                        else
                            System.err.println("Failed to register");
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
        TRANSITION_TO_LOGIN,
        TRANSITION_TO_REGISTER
    }

    private enum SubState {
        USER_INPUT,
        AWAITING_SALT,
        LOGGING_IN,
        CHECK_USERNAME_AVAILABILITY,
        AWAIT_REGISTRATION_RESPONSE
    }
}
