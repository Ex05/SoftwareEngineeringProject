package de.janik.softengine.ui;
// <- Import ->

// <- Static_Import ->

import de.janik.softengine.util.ColorARGB;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import static de.janik.softengine.ui.Text.Interpolation.NEAREST_NEIGHBOR;
import static de.janik.softengine.util.ColorARGB.BLACK;
import static de.janik.softengine.util.ColorARGB.ToAWT_Color;
import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Text extends A_Sprite {
    // <- Public ->
    public static final Font DEFAULT_FONT;

    // <- Protected ->

    // <- Private->
    private BufferedImage img;

    private Graphics2D g;

    private int[] pixel;

    private String text;

    private Font font;

    private ColorARGB color;

    private int width;
    private int height;

    private boolean antialiasing = false;
    private boolean dropShadowEnabled = false;
    private boolean outlineOnly = false;

    private Interpolation interpolation = NEAREST_NEIGHBOR;

    private DropShadow dropShadow = new DropShadow(BLACK, 1, 1);

    // <- Static ->
    static {
        DEFAULT_FONT = new Font(MONOSPACED, BOLD, 12);
    }

    // <- Constructor ->
    public Text(final String text, final ColorARGB color) {
        this();

        this.text = text;
        this.color = color;

        updateText();
    }

    public Text() {
        text = " ";
        font = DEFAULT_FONT;
        color = BLACK;
    }

    // <- Abstract ->

    // <- Object ->
    private void updateText() {
        final FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
        final Rectangle2D r = font.getStringBounds(text, frc);

        // FIXME:(jan) 10.06.2016 We add some "constant" space to the text to be able to fit it into the image.
        width = (int) r.getWidth() + font.getSize();
        height = (int) r.getHeight();

        if (width > 0 && height > 0) {
            img = new BufferedImage(width, height, TYPE_INT_ARGB);
            pixel = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

            g = (Graphics2D) img.getGraphics();

            g.setRenderingHint(KEY_ANTIALIASING, antialiasing ? VALUE_ANTIALIAS_ON : VALUE_ANTIALIAS_OFF);
            g.setRenderingHint(KEY_INTERPOLATION, interpolation.getValue());

            g.setFont(font);

            final FontMetrics fm = g.getFontMetrics();

            float posX = (width - fm.stringWidth(text)) / 2.0f;
            posX = posX < 0 ? 0 : posX;

            final float posY = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2.0f);

            if (dropShadowEnabled) {
                if (text.length() != 0) {
                    final TextLayout textLayout = new TextLayout(text, font, g.getFontRenderContext());
                    g.setPaint(ColorARGB.ToAWT_Color(dropShadow.getColor()));
                    textLayout.draw(g, posX + dropShadow.getxOffset(), posY + dropShadow.getyOffset());

                    g.setPaint(ToAWT_Color(color));
                    textLayout.draw(g, posX, posY);
                }
            } else if (outlineOnly) {
                final FontRenderContext renderContext = g.getFontRenderContext();

                final TextLayout textTl = new TextLayout(text, font, renderContext);

                final Shape outline = textTl.getOutline(null);

                final java.awt.Rectangle outlineBounds = outline.getBounds();

                final AffineTransform transform = g.getTransform();
                transform.translate(width / 2 - (outlineBounds.width / 2), height / 2 + (outlineBounds.height / 2));

                g.transform(transform);
                g.setColor(Color.blue);
                g.draw(outline);
                g.setClip(outline);
            } else {
                g.setColor(ToAWT_Color(color));
                g.drawString(text, posX, posY);
            }
        }
    }

    // <- Getter & Setter ->
    public void setTextSize(final int textSize) {
        font = font.deriveFont((float) textSize);

        updateText();
    }

    public void setText(final String text) {
        this.text = text;

        updateText();
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(final Font font) {
        this.font = font;

        updateText();
    }

    public ColorARGB getColor() {
        return color;
    }

    public void setColor(final ColorARGB color) {
        this.color = color;

        updateText();
    }

    public int[] getPixel() {
        return pixel;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setAntialiasing(final boolean antialiasing) {
        this.antialiasing = antialiasing;

        updateText();
    }

    public void setAntialiasing(final boolean antialiasing, final Interpolation interpolation) {
        this.interpolation = interpolation;

        setAntialiasing(antialiasing);
    }

    public Graphics2D getGraphics() {
        return g;
    }

    public boolean isDropShadowEnabled() {
        return dropShadowEnabled;
    }

    public boolean isOutlineOnly() {
        return outlineOnly;
    }

    public void setOutlineOnly(final boolean outlineOnly) {
        this.outlineOnly = outlineOnly;

        updateText();
    }
    public void setDropShadowEnabled(final boolean dropShadowEnabled) {
        this.dropShadowEnabled = dropShadowEnabled;

        updateText();
    }

    public void setDropShadow(final DropShadow dropShadow) {
        this.dropShadow = dropShadow;

        setDropShadowEnabled(true);
    }

    // <- Static ->
    public enum Interpolation {
        BILINEAR(VALUE_INTERPOLATION_BILINEAR),
        BICUBIC(VALUE_INTERPOLATION_BICUBIC),
        NEAREST_NEIGHBOR(VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        private final Object value;

        Interpolation(final Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }
}
