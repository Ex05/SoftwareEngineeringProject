package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_012_GameNameAvailable extends Packet_003_Permission {
    private String gameName;
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public Packet_012_GameNameAvailable(final String gameName, final boolean permissionGranted) {
        super(permissionGranted);

        this.gameName = gameName;
    }

    public Packet_012_GameNameAvailable(final String gameName, final Permission permission) {
        super(permission);

        this.gameName = gameName;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getGameName() {
        return gameName;
    }

    // <- Static ->
}
