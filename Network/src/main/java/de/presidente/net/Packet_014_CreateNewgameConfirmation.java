package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_014_CreateNewgameConfirmation extends Packet_003_Permission {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public Packet_014_CreateNewgameConfirmation(final boolean permissionGranted) {
        super(permissionGranted);
    }

    public Packet_014_CreateNewgameConfirmation(final Permission permission) {
        super(permission);
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
