package de.presidente.net;
// <- Import ->

// <- Static_Import ->

import java.io.Serializable;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class GameInfo implements Serializable {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final String gameName;
    private final String owner;

    private final byte playerCount;

    // <- Static ->

    // <- Constructor ->
    public GameInfo(final String gameName, final String owner, final byte playerCount) {
        this.gameName = gameName;
        this.owner = owner;
        this.playerCount = playerCount;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public byte getPlayerCount() {
        return playerCount;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return gameName;
    }

    // <- Static ->
}
