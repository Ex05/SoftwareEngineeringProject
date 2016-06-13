package de.janik.imageLoader;

// <- Import ->

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static de.janik.imageLoader.ImageType.TYPE_4BYTE_ABGR;
import static de.janik.imageLoader.ScaleType.DEFAULT;
import static java.awt.Color.MAGENTA;
import static java.lang.String.*;

// <- Static_Import ->

/**
 * A utility class to load and manipulate {@link BufferedImage Images}.
 *
 * @author Jan.Marcel.Janik [©2016]
 * @see BufferedImage
 */
public final class ImageLoader {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    /**
     * Singleton instance variable.
     */
    private static final ImageLoader LOADER;

    private BufferedImage img;

    private Integer width;
    private Integer height;

    private Resize resize;

    private ImageType type;

    private ScaleType scaleType;

    private ImageLoaderException e;

    private InputStream is;

    // <- Static ->
    static {
        LOADER = new ImageLoader();
    }

    // <- Constructor ->
    private ImageLoader() {
        reset();
    }

    // <- Abstract ->

    // <- Object ->

    /**
     * @param size The new <b>width</b> &amp; <b>height</b>.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#resize(int, int)
     * @see ImageLoader#Resize(BufferedImage, int, int, ScaleType)
     */
    public ImageLoader resize(final int size) {
        return resize(size, size);
    }

    /**
     * Will resize the internal {@link BufferedImage Image} either at loading time or instantaneous,
     * if {@link ImageLoader#load() load()} already has been called, to the new <b>width</b> &amp; <b>height</b>.
     *
     * @param width  The new <b>width</b>.
     * @param height The new <b>height</b>.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#Resize(BufferedImage, int, int, ScaleType)
     */
    public ImageLoader resize(final int width, final int height) {
        if (img == null) {
            resize = Resize.RESIZE;

            this.width = width;
            this.height = height;
        } else
            img = Resize(img, width, height, scaleType);

        return this;
    }

    /**
     * Will resize the internal {@link BufferedImage Image} either at loading time or instantaneous,
     * if {@link ImageLoader#load() load()} already has been called, to the new <b>width</b>, the <b>height</b> is scaled accordingly.
     *
     * @param width The new <b>width</b>.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#Resize(BufferedImage, int, int, ScaleType)
     */
    public ImageLoader resizeToFitWidth(final int width) {
        if (img == null) {
            resize = Resize.RESIZE_TO_FIT_WIDTH;
            this.width = width;
        } else
            img = Resize(img, width, (int) ((double) img.getHeight() / ((double) img.getWidth() / (double) width)), scaleType);

        return this;
    }

    /**
     * Will resize the internal {@link BufferedImage Image} either at loading time or instantaneous,
     * if {@link ImageLoader#load() load()} already has been called, to the new <b>height</b>, the <b>width</b> is scaled accordingly.
     *
     * @param height The new <b>height</b>.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#Resize(BufferedImage, int, int, ScaleType)
     */
    public ImageLoader resizeToFitHeight(final int height) {
        if (img == null) {
            resize = Resize.RESIZE_TO_FIT_HEIGHT;

            this.height = height;

            return this;
        }

        img = Resize(img, (int) ((double) img.getWidth() / ((double) img.getHeight() / (double) height)), height, scaleType);

        return this;
    }

    /**
     * Changes the internal storing type of the internal {@link BufferedImage Image} either at loading time or instantaneous,
     * if {@link ImageLoader#load() load()} already has been called, to the new {@link ImageType type}.
     *
     * @param type The new <b>imageType</b>.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageType
     */
    public ImageLoader type(final ImageType type) {
        if (img == null)
            this.type = type;
        else
            img = ChangeImageType(img, type);

        return this;
    }

    /**
     * Loads the image from the input specified by {@link ImageLoader#setInputFile(String)},
     * {@link ImageLoader#setInput(String)},{@link ImageLoader#setInputFile(File)},
     * {@link ImageLoader#setInputURL(String)} &amp; {@link ImageLoader#setInputURL(URL)}
     * and then performs resizing &amp; type change operations if needed.
     *
     * @return The singleton instance variable (<b>this</b>).
     * @throws MissingInputException Is thrown, when no input has been specified.
     * @throws ImageLoaderException  Is thrown, when an Exception has in previous function calls occurred, but has not been thrown.
     */
    public ImageLoader load() {
        if (e == null && is == null)
            e = new MissingInputException();

        if (e != null) {
            final ImageLoaderException e = this.e;

            this.e = null;

            e.printStackTrace();

            img = null;
        } else {
            try {
                img = ImageIO.read(is);

                if (resize != null)
                    switch (resize) {
                        case RESIZE: {
                            resize(width, height);

                            break;
                        }
                        case RESIZE_TO_FIT_WIDTH: {
                            resizeToFitWidth(width);

                            break;
                        }
                        case RESIZE_TO_FIT_HEIGHT: {
                            resizeToFitHeight(height);

                            break;
                        }
                    }

                if (type.getType() != img.getType())
                    img = ChangeImageType(img, type);
            } catch (final IOException e) {
                e.printStackTrace();
            }

            reset();
        }

        return this;
    }

    /**
     * Resets all local variables used in the loading process, to there default values.
     */
    private void reset() {
        type = TYPE_4BYTE_ABGR;
        scaleType = DEFAULT;

        resize = null;

        width = null;
        height = null;
    }

    // <- Getter & Setter ->

    /**
     * Sets the input stream to load the image from when {@link ImageLoader#load()} is called.
     *
     * @param url The <b>url</b> to load the image from.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     */
    public ImageLoader setInputURL(final URL url) {
        try {
            is = url.openStream();
        } catch (final IOException e) {
            this.e = new ImageLoaderException(format("ERROR: Failed to get InputStream from url:'%s'.", url.getPath()));
        }

        return this;
    }

    /**
     * Sets the input stream to load the image from when {@link ImageLoader#load()} is called.
     *
     * @param url The <b>url</b> to load the image from.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#setInputURL(URL)
     */
    public ImageLoader setInputURL(final String url) {
        try {
            setInputURL(new URL(url));
        } catch (final MalformedURLException e) {
            this.e = new ImageLoaderException(format("ERROR: Unrecognised url:'%s'.", url));
        }
        return this;
    }

    /**
     * Sets the path of the image that is loaded, when {@link ImageLoader#load()} is called.
     *
     * @param path The <b>path</b> to the image.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     */
    public ImageLoader setInput(final String path) {
        is = this.getClass().getResourceAsStream(path);

        if (is == null)
            e = new ImageLoaderException(String.format("ERROR: Unrecognised path:'%s'.", path));

        return this;
    }

    /**
     * Sets the file to the image that is loaded, when {@link ImageLoader#load()} is called.
     *
     * @param file The <b>file</b> that holds the image.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     * @see ImageLoader#setInputFile(File)
     */
    public ImageLoader setInputFile(final String file) {
        return setInputFile(new File(file));
    }

    /**
     * Sets the file to the image that is loaded, when {@link ImageLoader#load()} is called.
     *
     * @param file The <b>file</b> to the image.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#load()
     */
    public ImageLoader setInputFile(final File file) {
        try {
            is = new FileInputStream(file);
        } catch (final FileNotFoundException e) {
            this.e = new ImageLoaderException(String.format("ERROR: File not found:'%s'.", file.getAbsolutePath()));
        }

        return this;
    }

    /**
     * Sets the scale function, which is used when any of the 'resize' functions are called.
     *
     * @param type The scale function to use when any of the 'resize' functions are called.
     * @return The singleton instance variable (<b>this</b>).
     * @see ImageLoader#resize(int, int)
     * @see ImageLoader#resize(int)
     * @see ImageLoader#resizeToFitHeight(int)
     * @see ImageLoader#resizeToFitWidth(int)
     */
    public ImageLoader setScaleFunction(final ScaleType type) {
        this.scaleType = type;

        return this;
    }

    /**
     * Returns the internal {@link BufferedImage Image} and then clears it.
     *
     * @return The internal {@link BufferedImage Image}.
     */
    private BufferedImage get() {
        final BufferedImage img = this.img;
        this.img = null;

        return img;
    }

    /**
     * Returns an <b>Optional</b> that holds the {@link BufferedImage image} loaded,
     * when {@link ImageLoader#load()} was called.
     *
     * @return The loaded {@link BufferedImage Image}.
     */
    public Optional<BufferedImage> asImage() {
        return Optional.ofNullable(get());
    }

    /**
     * Returns an <b>Optional</b> that holds the {@link ImageIcon icon} loaded,
     * when {@link ImageLoader#load()} was called.
     *
     * @return The loaded {@link ImageIcon icon}.
     */
    public Optional<ImageIcon> asImageIcon() {
        final BufferedImage img = get();
        return Optional.ofNullable(img != null ? new ImageIcon(img) : null);
    }

    // <- Static ->

    /**
     * Returns the singleton instance variable (<b>this</b>).
     *
     * @return The singleton instance variable (<b>this</b>).
     */
    public static ImageLoader GetInstance() {
        return LOADER;
    }

    /**
     * Creates a scaled copy of a given {@link BufferedImage Image}.
     *
     * @param img     The source image
     * @param width   The new width of the image.
     * @param height  The new height of the image.
     * @param scaling The scaling-method used to scale the image.
     * @return A scaled copy of {@link BufferedImage Image} width the size(<b>width</b>, <b>height</b>).
     */
    public static BufferedImage Resize(BufferedImage img, final int width, final int height, final ScaleType scaling) {
        if (img.getWidth() != width || img.getHeight() != height) {
            final Image scaledImage = img.getScaledInstance(width, height, scaling.getType());

            img = new BufferedImage(width, height, img.getType());
            img.getGraphics().drawImage(scaledImage, 0, 0, null);
        }

        return img;
    }

    /**
     * Changes the internal storage type of a given {@link BufferedImage Image}.
     *
     * @param img  The image whose type should be changed.
     * @param type The new type of the image.
     * @return A copy of <code>img</code> with an internal storage type of <code>type</code>.
     */
    public static BufferedImage ChangeImageType(BufferedImage img, final ImageType type) {
        if (img.getType() != type.getType()) {
            final ColorConvertOp cco = new ColorConvertOp(null);
            final BufferedImage dst = new BufferedImage(img.getWidth(), img.getHeight(), type.getType());
            img = cco.filter(img, dst);
        }

        return img;
    }

    public static BufferedImage ErrorImage(final int width, final int height) {
        final BufferedImage img = new BufferedImage(width, height, TYPE_4BYTE_ABGR.getType());

        final Graphics g = img.getGraphics();

        g.setColor(MAGENTA);
        g.fillRect(0, 0, width, height);

        return img;
    }

    private enum Resize {
        RESIZE,
        RESIZE_TO_FIT_WIDTH,
        RESIZE_TO_FIT_HEIGHT
    }
}