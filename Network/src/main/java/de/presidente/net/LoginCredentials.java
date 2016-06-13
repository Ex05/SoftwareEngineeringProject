package de.presidente.net;
// <- Import ->

// <- Static_Import ->

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class LoginCredentials implements Serializable {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final String userName;

    private final byte[] passwordHash;

    // <- Static ->

    // <- Constructor ->
    public LoginCredentials(final String userName, final byte[] passwordHash) {
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    // <- Abstract ->

    // <- Object ->
    public void errase() {
        Arrays.fill(passwordHash, (byte) 0x00);
    }

    // <- Getter & Setter ->
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    // <- Static ->
}
