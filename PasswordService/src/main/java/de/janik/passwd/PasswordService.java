package de.janik.passwd;
// <- Import ->

// <- Static_Import ->

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class PasswordService {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final PasswordService SERVICE;

    private static final int ITERATIONS;
    private static final int HASH_LENGTH;

    private static final String PBKDF2_WITH_HMAC_SHA1;

    private SecretKeyFactory keyFactory;

    private SecureRandom secureRandomGenerator;

    // <- Static ->
    static {
        SERVICE = new PasswordService();

        // TODO:(jan) Test what is reasonable here, I used 10_000 for Android-Phones.
        ITERATIONS = 10_000;
        HASH_LENGTH = 256;

        PBKDF2_WITH_HMAC_SHA1 = "PBKDF2WithHmacSHA1";
    }

    // <- Constructor ->
    private PasswordService() {
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // <- Abstract ->

    // <- Object ->
    public boolean validate(final char[] password, final byte[] salt, final byte[] hash) {
        final byte[] pbkdf2Hash = pbkdf2Hash(password, salt);

        return slowEquals(pbkdf2Hash, hash, pbkdf2Hash.length);
    }
    public boolean validate( final byte[] hash1, final byte[] hash2) {
          return slowEquals(hash2, hash2, hash1.length);
    }

    public byte[] pbkdf2Hash(final char[] password, final byte[] salt) {
        final PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH);

        byte[] hash = null;
        try {
            hash = keyFactory.generateSecret(spec).getEncoded();
        } catch (final InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return hash;
    }

    private boolean slowEquals(final byte[] a, final byte[] b, final int length) {
        // If the length of a != b, diff wil not equal 0.
        int diff = a.length ^ b.length;

        // Iterate over the whole length of the arrays, to prevent timing-attacks.
        for (int i = 0; i < length; i++)
            // OR 'diff' with the XOR product of a and b, this way if dif will ever be != 0, it will always be != 0.
            diff |= a[i] ^ b[i];

        return diff == 0;
    }

    public byte[] generateSalt(final int length) {
        final byte[] salt = new byte[length];

        secureRandomGenerator.nextBytes(salt);

        return salt;
    }

    // <- Getter & Setter ->
    public int getHashLength() {
        return HASH_LENGTH;
    }

    // <- Static ->
    public static PasswordService GetInstance() {
        return SERVICE;
    }
}
