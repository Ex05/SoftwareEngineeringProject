package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
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

import static de.janik.softengine.util.ColorARGB.CYAN;
import static de.janik.softengine.util.ColorARGB.FIREBRICK_RED;
import static de.janik.softengine.util.ColorARGB.GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.LIGHT_GRAY;
import static de.janik.softengine.util.ColorARGB.RED;
import static de.janik.softengine.util.ColorARGB.SILVER_GRAY;
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
        labelOwner.setLocation(backGroundGames.getX() + backGroundGames.getWidth() / 2 - labelOwner.getWidth(), backGroundGames.getY() + backGroundGames.getHeight());
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

                if (!textFieldNewGameName.hasFocus())
                    textFieldNewGameName.setFocus(true);
            }
        });

        buttonCancel.onMousePress(() -> {
            textFieldNewGameName.clear();
            textFieldNewGameName.setFocus(false);

            createGameForm.forEach(e -> e.setVisible(false));

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
        Packet p;

        while ((p = server.retrievePacket()) != null) {
            if (p.getClass().equals(Packet_004_LobbyEnter.class))
                handlePacket_004_LobbyEnter((Packet_004_LobbyEnter) p);
            else if (p.getClass().equals(Packet_012_GameNameAvailable.class))
                handlePacket_012_GameNameAvailable((Packet_012_GameNameAvailable) p);
            else if (p.getClass().equals(Packet_014_CreateNewgameConfirmation.class))
                handlePacket_014_CreateNewGameConfirmation((Packet_014_CreateNewgameConfirmation) p);
        }
    }

    private void handlePacket_014_CreateNewGameConfirmation(final Packet_014_CreateNewgameConfirmation packet) {
        if (state == State.AWAIT_GAME_CREATION_CONFIRMATION) {
            if (packet.isGranted())
                // TODO:(jan) Enter pre game lobby.
                System.err.println("Lobby.tick[TODO:(jan) Enter pre game lobby].");
            else {
                textFieldNewGameName.clear();

                state = State.CREATE_GAME;
            }
        }
    }

    private void handlePacket_012_GameNameAvailable(final Packet_012_GameNameAvailable packet) {
        if (state == State.CREATE_GAME)
            if (!textFieldNewGameName.getUserInput().equals(""))
                textFieldNewGameName.setTextColor(packet.isGranted() ? GREEN : RED);
    }

    private void handlePacket_004_LobbyEnter(final Packet_004_LobbyEnter packet) {
        if (state == State.ENTERING_LOBBY) {
            final String[] games = packet.getGames();
            final String[] owner = packet.getOwners();
            final Byte[] player = packet.getPlayerCounts();

            int i = 0;
            for (final String game : games) {
                final Container<DrawableEntity> gameContainer = new Container<>();

                final Rectangle background = new Rectangle(backGroundGames.getWidth(), 44);
                background.setColor(i % 2 == 0 ? SILVER_GRAY : LIGHT_GRAY);
                background.setZ(backGroundGames.getZ() + 1);

                final Label labelGame = new Label(game);
                labelGame.setFont(SOURCE_CODE_PRO);
                labelGame.setTextSize(30);
                labelGame.setZ(backgroundCreateGame.getZ() + 1);

                final Label labelOwner = new Label(owner[i]);
                labelOwner.setFont(SOURCE_CODE_PRO);
                labelOwner.setTextSize(30);
                labelOwner.setZ(backgroundCreateGame.getZ() + 1);
                labelOwner.setLocation(this.labelOwner.getX(), 0);

                final Label labelPLayerCount = new Label(player[i] + "/5");
                labelPLayerCount.setFont(SOURCE_CODE_PRO);
                labelPLayerCount.setTextSize(30);
                labelPLayerCount.setZ(backgroundCreateGame.getZ() + 1);
                labelPLayerCount.setLocation(this.labelPlayer.getX(), 0);

                gameContainer.add(background);
                gameContainer.add(labelGame);
                gameContainer.add(labelOwner);
                gameContainer.add(labelPLayerCount);

                gameContainer.forEach(this::add);

                gameContainer.setLocation(backGroundGames.getX(), backGroundGames.getY() + backGroundGames.getHeight() - background.getHeight() - (background.getHeight() * i++));
            }

            int j = 0;
            for (final String user : packet.getClients()) {
                final Container<DrawableEntity> gameContainer = new Container<>();

                final Rectangle background = new Rectangle(backGroundGames.getWidth(), 44);
                background.setColor(j % 2 == 0 ? SILVER_GRAY : LIGHT_GRAY);
                background.setZ(backGroundGames.getZ() + 1);

                final Label labelUser = new Label(user);
                labelUser.setFont(SOURCE_CODE_PRO);
                labelUser.setTextSize(30);
                labelUser.setZ(backGroundUsers.getZ() + 1);

                gameContainer.add(background);
                gameContainer.add(labelUser);

                gameContainer.forEach(this::add);

                gameContainer.setLocation(backGroundUsers.getX(), backGroundUsers.getY() + backGroundUsers.getHeight() - background.getHeight() - (background.getHeight() * j++));
            }

            state = State.IN_LOBBY;
        }
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
