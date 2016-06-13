package de.presidente.net;
// <- Import ->

// <- Static_Import ->

import static de.presidente.net.Permission.GUARANTED;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_003_Permission extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final Permission permission;

    // <- Static ->

    // <- Constructor ->
    public Packet_003_Permission(final Permission permission) {
        this.permission = permission;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public boolean isPermissionGuranted() {
        return permission == GUARANTED;
    }

    public Permission getPermission() {
        return permission;
    }

    // <- Static ->
}
