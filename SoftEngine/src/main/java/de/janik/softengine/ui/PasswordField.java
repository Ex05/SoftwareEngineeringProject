package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import java.util.Arrays;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class PasswordField extends TextField {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final char displayCharacter;

    private char[] password;
    // <- Static ->

    // <- Constructor ->
    public PasswordField(final String defaultText) {
        this(defaultText, '*');
    }

    public PasswordField(final String defaultText, final char displayCharacter) {
        super(defaultText);

        this.displayCharacter = displayCharacter;

        password = new char[0];
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    protected void append(final char keyChar) {
        final int length = password.length;

        final char[] tmp = new char[length + 1];

        System.arraycopy(password, 0, tmp, 0, length);

        tmp[password.length] = keyChar;

        Arrays.fill(password, (char) 0);

        password = tmp;

        super.append(displayCharacter);
    }

    @Override
    public void clear() {
        super.clear();

        Arrays.fill(password, (char) 0);

        password = new char[0];
    }

    @Override
    protected void removeLastCharacter() {
        final int length = password.length;

        if (length > 0) {
            final char[] tmp = new char[length - 1];

            System.arraycopy(password, 0, tmp, 0, length - 1);

            Arrays.fill(password, (char) 0);

            password = tmp;
        }

        super.removeLastCharacter();
    }

    // <- Getter & Setter ->
    public char[] getPassword() {
        return password;
    }

    // <- Static ->
}
