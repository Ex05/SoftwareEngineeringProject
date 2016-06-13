package de.janik.imageLoader;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class MissingInputException extends ImageLoaderException {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final String ERROR_STRING;

    // <- Static ->
    static {
        ERROR_STRING = "ERROR: No input source defined.";
    }

    // <- Constructor ->
    public MissingInputException() {
        super(ERROR_STRING);
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
