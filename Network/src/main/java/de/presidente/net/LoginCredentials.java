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

    private final byte[] saltedPasswordHash;

    // <- Static ->

    // <- Constructor ->
    public LoginCredentials(final String userName, final byte[] saltedPasswordHash) {
        this.userName = userName;
        this.saltedPasswordHash = saltedPasswordHash;
    }

    // <- Abstract ->

    // <- Object ->
    public void erase() {
        Arrays.fill(saltedPasswordHash, (byte) 0x00);
    }

    // <- Getter & Setter ->
    public byte[] getSaltedPasswordHash() {
        return saltedPasswordHash;
    }

    public String getUserName() {
        return userName;
    }

    // <- Static ->
}
