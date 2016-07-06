package de.presidente.jungle_king;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.Engine;
import de.janik.softengine.game.State;
import de.janik.softengine.ui.Button;
import de.janik.softengine.ui.Container;
import de.janik.softengine.ui.DropShadow;
import de.janik.softengine.ui.Label;
import de.janik.softengine.ui.Rectangle;
import de.janik.softengine.ui.TextField;
import de.janik.softengine.ui.TexturedQuad;
import de.presidente.jungle_king.net.ConnectionManager;
import de.presidente.net.Packet;
import de.presidente.net.Packet_017_LeavePreGameLobby;
import de.presidente.net.Packet_019_ChatMsgSend;
import de.presidente.net.Packet_020_ChatMsgReceive;

import java.util.List;

import static de.janik.softengine.ui.TextLocation.LEFT;
import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.CADET_BLUE;
import static de.janik.softengine.util.ColorARGB.CYAN;
import static de.janik.softengine.util.ColorARGB.DARK_GREEN;
import static de.janik.softengine.util.ColorARGB.DARK_OLIVE_GREEN;
import static de.janik.softengine.util.ColorARGB.DARK_ORANGE;
import static de.janik.softengine.util.ColorARGB.DARK_SLATE_GRAY;
import static de.janik.softengine.util.ColorARGB.GRAY;
import static de.janik.softengine.util.ColorARGB.GREEN;
import static de.janik.softengine.util.ColorARGB.GREEN_YELLOW;
import static de.janik.softengine.util.ColorARGB.LIGHT_GRAY;
import static de.janik.softengine.util.ColorARGB.RED;
import static de.janik.softengine.util.ColorARGB.SILVER;
import static de.janik.softengine.util.ColorARGB.YELLOW;
import static de.presidente.jungle_king.util.Resources.IMAGE_PREGAME_LOBBY_MAP_PREVIEW;
import static de.presidente.jungle_king.util.Resources.SOURCE_CODE_PRO;
import static java.awt.Font.BOLD;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class PreGameLobby extends State {
    // <- Public ->
    // <- Protected ->

    // <- Private ->
    private TexturedQuad mapPreview;

    private final Button buttonReady;
    private final Button buttonLeave;

    private final Container<Button> buttonLeaveAndReady;
    private final Container<Container<Label>> chat;


    private Rectangle backgroundUser;
    private Rectangle backgroundChat;

    private TextField textFieldChat;

    private ConnectionManager server;

    private volatile boolean poolPackets = true;

    // <- Static ->

    // <- Constructor ->
    public PreGameLobby(final JungleKingGame game) {
        super(game);

        chat = new Container<>();

        buttonReady = new Button("Ready");

        buttonLeave = new Button("Leave");
        buttonLeave.onMousePress(() -> {
            server.send(new Packet_017_LeavePreGameLobby());

            game.switchState(Lobby.class);

            chat.clear();
        });

        buttonLeaveAndReady = new Container<>();
        buttonLeaveAndReady.add(buttonReady);
        buttonLeaveAndReady.add(buttonLeave);

        backgroundUser = new Rectangle(800, 300);
        backgroundChat = new Rectangle(800, 300);

        textFieldChat = new TextField("Enter message.");
        textFieldChat.onKeyPress(e -> {
            if (e.getKeyCode() == VK_ENTER && !textFieldChat.getUserInput().equals("")) {
                server.send(new Packet_019_ChatMsgSend(textFieldChat.getUserInput()));

                textFieldChat.clear();
            }
        });
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public void init() {
        server = getGame().getServerConnection();

        game.setBackgroundColor(DARK_ORANGE);

        mapPreview = new TexturedQuad(IMAGE_PREGAME_LOBBY_MAP_PREVIEW);

        mapPreview.setLocation(engine.getScreenWidth() - mapPreview.getWidth() - 10, engine.getScreenHeight() - mapPreview.getHeight() - 10);

        buttonReady.setFont(SOURCE_CODE_PRO.deriveFont(BOLD));
        buttonReady.setBackgroundColor(CADET_BLUE);
        buttonReady.setTextColor(GREEN);
        buttonReady.getText().setDropShadow(new DropShadow(BLACK, 1, 1));
        buttonReady.setSize(120, 40);
        buttonReady.setTextSize(32);
        buttonReady.setZ(1);
        buttonReady.setLocation(0, 0);

        buttonLeave.setFont(SOURCE_CODE_PRO.deriveFont(BOLD));
        buttonLeave.setBackgroundColor(CADET_BLUE);
        buttonLeave.setTextColor(RED);
        buttonLeave.getText().setDropShadow(new DropShadow(BLACK, 1, 1));
        buttonLeave.setSize(120, 40);
        buttonLeave.setTextSize(32);
        buttonLeave.setZ(1);
        buttonLeave.setLocation(buttonReady.getX() + buttonReady.getWidth() + 20, buttonReady.getY());

        buttonLeaveAndReady.setLocation(engine.getScreenWidth() - (buttonReady.getWidth() + buttonLeave.getWidth() + 20) - 10, 10);

        backgroundUser.setSize(engine.getScreenWidth() - mapPreview.getWidth() - 10 - 10 - 10, 300);
        backgroundUser.setColor(GRAY);
        backgroundUser.setLocation(10, engine.getScreenHeight() - backgroundUser.getHeight() - 10);

        backgroundChat.setSize(engine.getScreenWidth() - mapPreview.getWidth() - 10 - 10 - 10, 340);
        backgroundChat.setColor(LIGHT_GRAY);
        backgroundChat.setLocation(backgroundUser.getX(), backgroundUser.getY() - backgroundChat.getHeight() - 10);

        textFieldChat.setSize(backgroundChat.getWidth(), 50);
        textFieldChat.setTextColor(SILVER);
        textFieldChat.setFont(SOURCE_CODE_PRO);
        textFieldChat.setBackgroundColor(DARK_SLATE_GRAY);
        textFieldChat.setTextSize(30);
        textFieldChat.setLocation(backgroundChat.getX(), backgroundChat.getY() - textFieldChat.getHeight());

        add(mapPreview);

        buttonLeaveAndReady.forEach(this::add);

        add(backgroundUser);
        add(backgroundChat);

        add(textFieldChat);

        poolPackets = true;
    }

    @Override
    public void tick(final long ticks, final Engine engine) {
        super.tick(ticks, engine);

        Packet p;

        while (poolPackets && (p = server.retrievePacket()) != null) {
            if (p.getClass().equals(Packet_020_ChatMsgReceive.class))
                handlePacket_020_ChatMsgReceive((Packet_020_ChatMsgReceive) p);
        }
    }

    private void handlePacket_020_ChatMsgReceive(final Packet_020_ChatMsgReceive packet) {
        final List<Container<Label>> children = chat.getChildren();

        while (children.size() >= 10) {
            Container<Label> container = children.get(0);

            children.remove(container);

            container.forEach(this::remove);
        }

        children.forEach(container -> container.forEach(this::remove));

        final Container<Label> chatMessage = new Container<>();

        final Label sender = new Label(packet.getSender() + ":", LEFT);
        sender.setFont(SOURCE_CODE_PRO.deriveFont(BOLD));
        sender.setTextColor(BLACK);
        sender.setTextSize(26);
        sender.setLocation(2, 0);

        final Label message = new Label(packet.getMsg(), LEFT);
        message.setFont(SOURCE_CODE_PRO.deriveFont(BOLD));
        message.setTextColor(BLACK);
        message.setTextSize(26);
        message.setLocation(sender.getX() + sender.getWidth() - sender.getText().getFont().getSize() * 0.75f, 0);

        chatMessage.add(sender);
        chatMessage.add(message);
        chatMessage.setHeight(message.getHeight());

        chat.add(chatMessage);

        int yOffset = 0;
        for (final Container<Label> container : children) {
            container.setAbsoluteLocation(0, yOffset);

            yOffset -= container.getHeight();
        }

        chat.forEach(container -> container.setLocation(0, backgroundChat.getY() + backgroundChat.getHeight() - 35));

        chat.forEach(c -> c.forEach(this::add));
    }

    // <- Getter & Setter ->
    @Override
    public JungleKingGame getGame() {
        return (JungleKingGame) super.getGame();
    }

    // <- Static ->
}
