package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_010_RegistrationConfirmation extends Packet_003_Permission {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    public Packet_010_RegistrationConfirmation(final boolean permissionGranted) {
        super(permissionGranted);
    }

    public Packet_010_RegistrationConfirmation(final Permission permission) {
        super(permission);
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
