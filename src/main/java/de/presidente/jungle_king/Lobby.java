package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.InputManager;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.util.ColorARGB;
import de.presidente.jungle_king.net.ConnectionManager;
import de.presidente.net.Packet;
import de.presidente.net.Packet_004_LobbyEnter;

import java.util.Arrays;

import static de.janik.softengine.util.ColorARGB.CYAN;
import static de.janik.softengine.util.ColorARGB.GRAY;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Lobby extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final TextField textFieldSearchBar;

    private final Rectangle backGroundGames;
    private final Rectangle backGroundUsers;

    private final Label labelGameName;
    private final Label labelOwner;
    private final Label labelUser;
    private final Label labelPlayer;

    private ConnectionManager server;

    private State state = State.ENTERING;
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
        textFieldSearchBar.setLocation(engine.getScreenWidth() - textFieldSearchBar.getWidth() - 20, engine.getScreenHeight() - textFieldSearchBar.getHeight() - 20);

        backGroundGames = new Rectangle(900, 620);
        backGroundGames.setColor(CYAN);
        backGroundGames.setLocation(20, 20);

        labelGameName = new Label("Games:");
        labelGameName.setFont(SOURCE_CODE_PRO);
        labelGameName.setTextSize(26);
        labelGameName.setLocation(backGroundGames.getX(), backGroundGames.getY() + backGroundGames.getHeight());

        labelOwner = new Label("Owner:");
        labelOwner.setFont(SOURCE_CODE_PRO);
        labelOwner.setTextSize(26);
        labelOwner.setLocation(backGroundGames.getX() + backGroundGames.getWidth() / 2, backGroundGames.getY() + backGroundGames.getHeight());

        labelUser = new Label("User:");
        labelUser.setFont(SOURCE_CODE_PRO);
        labelUser.setTextSize(26);

        backGroundUsers = new Rectangle(textFieldSearchBar.getWidth(), backGroundGames.getHeight() - labelUser.getHeight());
        backGroundUsers.setColor(GRAY);
        backGroundUsers.setLocation(textFieldSearchBar.getX(), textFieldSearchBar.getY() - backGroundUsers.getHeight() - labelUser.getHeight());

        labelUser.setLocation(backGroundUsers.getX(), backGroundUsers.getY() + backGroundUsers.getHeight());

        labelPlayer= new Label("Player:");
        labelPlayer.setFont(SOURCE_CODE_PRO);
        labelPlayer.setTextSize(26);
        labelPlayer.setLocation(backGroundGames.getX() + backGroundGames.getWidth() - labelPlayer.getWidth() - 20, backGroundGames.getY() + backGroundGames.getHeight());
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

        // initDefaultFocus();
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        final InputManager inputManager = engine.getInput();

        switch (state) {
            case ENTERING: {
                Packet p;

                while ((p = server.retrievePacket()) != null) {
                    if (p instanceof Packet_004_LobbyEnter) {
                        handleLobbyEnter((Packet_004_LobbyEnter) p);

                        state = State.IN_LOBBY;
                    }
                }

                break;
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
        ENTERING,
        IN_LOBBY
    }
}
