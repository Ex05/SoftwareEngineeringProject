package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_012_GameNameAvailable extends Packet_003_Permission {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public Packet_012_GameNameAvailable(final boolean permissionGranted) {
        super(permissionGranted);
    }

    public Packet_012_GameNameAvailable(final Permission permission) {
        super(permission);
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
