package de.presidente.net;
// <- Import ->

// <- Static_Import ->

import static de.presidente.net.Permission.GUARANTED;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Packet_003_Permission extends Packet {
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
    @Override
    public String toString() {
        return String.format("%s[Permission:%s]", getClass().getSimpleName(), permission);
    }


    // <- Getter & Setter ->
    public boolean isPermissionGuranted() {
        return permission == GUARANTED;
    }

    public Permission getPermission() {
        return permission;
    }

    // <- Static ->
}
