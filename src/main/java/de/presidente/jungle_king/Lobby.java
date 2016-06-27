package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.entity.DrawableEntity;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Container;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.util.ColorARGB;
import de.presidente.jungle_king.net.ConnectionManager;
import de.presidente.net.Packet;
import de.presidente.net.Packet_004_LobbyEnter;
import de.presidente.net.Packet_011_CheckGameName;
import de.presidente.net.Packet_012_GameNameAvailable;
import de.presidente.net.Packet_013_CreateNewGame;
import de.presidente.net.Packet_014_CreateNewgameConfirmation;

import java.util.Arrays;

import static de.janik.softengine.util.ColorARGB.CYAN;
import static de.janik.softengine.util.ColorARGB.FIREBRICK_RED;
import static de.janik.softengine.util.ColorARGB.GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.RED;
import static de.janik.softengine.util.ColorARGB.WHITE;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Lobby extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final TextField textFieldSearchBar;
    private final TextField textFieldNewGameName;

    private final Rectangle backGroundGames;
    private final Rectangle backGroundUsers;
    private final Rectangle backgroundCreateGame;

    private final Label labelGameName;
    private final Label labelOwner;
    private final Label labelUser;
    private final Label labelPlayer;
    private final Label labelCreateNewGame;

    private final Button buttonCreateGame;
    private final Button buttonOK;
    private final Button buttonCancel;

    private final Container<DrawableEntity> createGameForm;

    private ConnectionManager server;

    private State state = State.ENTERING_LOBBY;
    // <- Static ->

    // <- Constructor ->
    public Lobby(final JungleKingGame jungleKingGame) {
        super(jungleKingGame);

        final ColorARGB lightGray = new ColorARGB(222, 222, 222);

        textFieldSearchBar = new TextField("Search");
        textFieldSearchBar.setSize(340, 60);
        textFieldSearchBar.setFont(SOURCE_CODE_PRO);
        textFieldSearchBar.setBackgroundColor(lightGray);
        textFieldSearchBar.setTextSize(30);
        textFieldSearchBar.setLocation(engine.getScreenWidth() - textFieldSearchBar.getWidth() - 10, engine.getScreenHeight() - textFieldSearchBar.getHeight() - 10);

        backGroundGames = new Rectangle(900, 580);
        backGroundGames.setColor(CYAN);

        labelGameName = new Label("Games:");
        labelGameName.setFont(SOURCE_CODE_PRO);
        labelGameName.setTextSize(26);

        labelOwner = new Label("Owner:");
        labelOwner.setFont(SOURCE_CODE_PRO);
        labelOwner.setTextSize(26);

        labelUser = new Label("User:");
        labelUser.setFont(SOURCE_CODE_PRO);
        labelUser.setTextSize(26);

        backGroundUsers = new Rectangle(textFieldSearchBar.getWidth(), backGroundGames.getHeight() - labelUser.getHeight());
        backGroundUsers.setColor(GRAY);

        labelPlayer = new Label("Player:");
        labelPlayer.setFont(SOURCE_CODE_PRO);
        labelPlayer.setTextSize(26);

        buttonCreateGame = new Button("Create new Game");
        buttonCreateGame.setFocusAble(false);
        buttonCreateGame.setFont(SOURCE_CODE_PRO);
        buttonCreateGame.setTextColor(WHITE);
        buttonCreateGame.setTextSize(28);
        buttonCreateGame.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonCreateGame.setSize(300, 40);

        backGroundGames.setLocation(10, buttonCreateGame.getY() + buttonCreateGame.getHeight() + 20);

        buttonCreateGame.setLocation(backGroundGames.getX() + backGroundGames.getWidth() - buttonCreateGame.getWidth(), 10);

        backGroundUsers.setLocation(textFieldSearchBar.getX(), textFieldSearchBar.getY() - backGroundUsers.getHeight() - labelUser.getHeight());

        labelGameName.setLocation(backGroundGames.getX(), backGroundGames.getY() + backGroundGames.getHeight());
        labelOwner.setLocation(backGroundGames.getX() + backGroundGames.getWidth() / 2, backGroundGames.getY() + backGroundGames.getHeight());
        labelUser.setLocation(backGroundUsers.getX(), backGroundUsers.getY() + backGroundUsers.getHeight());
        labelPlayer.setLocation(backGroundGames.getX() + backGroundGames.getWidth() - labelPlayer.getWidth() - 10, backGroundGames.getY() + backGroundGames.getHeight());

        backgroundCreateGame = new Rectangle(380, 240);
        backgroundCreateGame.setColor(WHITE);
        backgroundCreateGame.setZ(backGroundGames.getZ() + 2);
        backgroundCreateGame.setLocation(engine.getScreenWidth() / 2 - backgroundCreateGame.getWidth() / 2,
                engine.getScreenHeight() / 2 - backgroundCreateGame.getHeight() / 2);

        labelCreateNewGame = new Label("Create new Game:");
        labelCreateNewGame.setFont(SOURCE_CODE_PRO);
        labelCreateNewGame.setTextSize(30);
        labelCreateNewGame.setZ(backgroundCreateGame.getZ() + 1);
        labelCreateNewGame.setLocation(backgroundCreateGame.getX() - 5, backgroundCreateGame.getY() + backgroundCreateGame.getHeight() - labelCreateNewGame.getHeight());

        textFieldNewGameName = new TextField("Name");
        textFieldNewGameName.setSize(backgroundCreateGame.getWidth() - 20, 60);
        textFieldNewGameName.setFont(SOURCE_CODE_PRO);
        textFieldNewGameName.setBackgroundColor(lightGray);
        textFieldNewGameName.setZ(backgroundCreateGame.getZ() + 1);
        textFieldNewGameName.setTextSize(30);
        textFieldNewGameName.setLocation(backgroundCreateGame.getX() + 10, labelCreateNewGame.getY() - textFieldNewGameName.getHeight() - 10);
        textFieldNewGameName.onInputChange(() -> {
            server.send(new Packet_011_CheckGameName(textFieldNewGameName.getUserInput()));
        });

        buttonOK = new Button("OK");
        buttonOK.setFocusAble(false);
        buttonOK.setFont(SOURCE_CODE_PRO);
        buttonOK.setTextColor(WHITE);
        buttonOK.setTextSize(28);
        buttonOK.setBackgroundColor(new ColorARGB(0, 180, 65));
        buttonOK.setZ(backgroundCreateGame.getZ() + 1);
        buttonOK.setSize(100, 40);
        buttonOK.setLocation(backgroundCreateGame.getX() + backgroundCreateGame.getWidth() - buttonOK.getWidth() - 10, backgroundCreateGame.getY() + 10);

        buttonCancel = new Button("Cancel");
        buttonCancel.setFocusAble(false);
        buttonCancel.setFont(SOURCE_CODE_PRO);
        buttonCancel.setTextColor(WHITE);
        buttonCancel.setTextSize(28);
        buttonCancel.setBackgroundColor(FIREBRICK_RED);
        buttonCancel.setZ(backgroundCreateGame.getZ() + 1);
        buttonCancel.setSize(100, 40);
        buttonCancel.setLocation(buttonOK.getX() - 10 - buttonCancel.getWidth(), buttonOK.getY());

        createGameForm = new Container<>();
        createGameForm.add(backgroundCreateGame);
        createGameForm.add(labelCreateNewGame);
        createGameForm.add(textFieldNewGameName);
        createGameForm.add(buttonOK);
        createGameForm.add(buttonCancel);

        createGameForm.forEach(e -> e.setVisible(false));

        buttonCreateGame.onMousePress(() -> {
            if (state != State.CREATE_GAME) {
                state = State.CREATE_GAME;

                createGameForm.forEach(e -> e.setVisible(true));

                textFieldNewGameName.setFocus(true);
            }
        });

        buttonCancel.onMousePress(() -> {
            createGameForm.forEach(e -> e.setVisible(false));

            textFieldNewGameName.clear();

            state = State.IN_LOBBY;
        });

        buttonOK.onMousePress(() -> {
            state = State.AWAIT_GAME_CREATION_CONFIRMATION;

            server.send(new Packet_013_CreateNewGame(textFieldNewGameName.getUserInput()));
        });
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        server = getGame().getServerConnection();

        game.setBackgroundColor(new ColorARGB(115, 195, 90));

        add(textFieldSearchBar);
        add(backGroundGames);
        add(backGroundUsers);
        add(labelGameName);
        add(labelOwner);
        add(labelUser);
        add(labelPlayer);
        add(buttonCreateGame);

        createGameForm.forEach(this::add);
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        final InputManager inputManager = engine.getInput();

        Packet p;

        while ((p = server.retrievePacket()) != null) {
            if (p.getClass().equals(Packet_004_LobbyEnter.class)) {
                if (state == State.ENTERING_LOBBY) {
                    handleLobbyEnter((Packet_004_LobbyEnter) p);

                    state = State.IN_LOBBY;
                }
            } else if (p.getClass().equals(Packet_012_GameNameAvailable.class)) {
                if (state == State.CREATE_GAME)
                    if (!textFieldNewGameName.getUserInput().equals(""))
                        textFieldNewGameName.setTextColor(((Packet_012_GameNameAvailable) p).isGranted() ? GREEN : RED);
            } else if (p.getClass().equals(Packet_014_CreateNewgameConfirmation.class)) {
                if (state == State.AWAIT_GAME_CREATION_CONFIRMATION) {
                    if (((Packet_014_CreateNewgameConfirmation) p).isGranted())
                        // TODO:(jan) Enter pre game lobby.
                        System.err.println("Lobby.tick[TODO:(jan) Enter pre game lobby].");
                    else {
                        textFieldNewGameName.clear();

                        state = State.CREATE_GAME;
                    }
                }
            }
        }
    }

    private void handleLobbyEnter(final Packet_004_LobbyEnter packet) {
        // TODO:(jan) Add all the games to the UI.

        System.out.println(Arrays.toString(packet.getGames()));
        System.out.println(Arrays.toString(packet.getClients()));
    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }

    // <- Static ->
    private enum State {
        ENTERING_LOBBY,
        IN_LOBBY,
        CREATE_GAME,
        AWAIT_GAME_CREATION_CONFIRMATION
    }
}
