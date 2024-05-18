package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

/**
 * A bitmap font with the ability to draw to center.
 *
 * @author David Jerzak (RivelBop)
 */
public final class Font implements Disposable {
    /**
     * The font generated from {@link FontBuilder}.
     */
    private final BitmapFont bitmapFont;

    /**
     * Creates a font from the provided {@link BitmapFont}.
     *
     * @param font The BitmapFont to utilize.
     */
    private Font(BitmapFont font) {
        this.bitmapFont = font;
    }

    /**
     * Renders the provided {@link String} into the provided {@link SpriteBatch}.
     *
     * @param batch The sprite batch to render the font to.
     * @param text  The text to render.
     * @param x     The x-position of the text.
     * @param y     The y-position of the text.
     */
    public void draw(SpriteBatch batch, String text, float x, float y) {
        bitmapFont.draw(batch, text, x, y);
    }

    /**
     * Renders the provided {@link String} into the provided {@link SpriteBatch} based on its center.
     *
     * @param batch The sprite batch to render the font to.
     * @param text  The text to render.
     * @param x     The center x-position of the text.
     * @param y     The center y-position of the text.
     */
    public void drawCenter(SpriteBatch batch, String text, float x, float y) {
        GlyphLayout layout = new GlyphLayout(bitmapFont, text);
        bitmapFont.draw(batch, text, x - layout.width / 2f, y + layout.height / 2f);
    }

    /**
     * @return The {@link BitmapFont} generated from the {@link FontBuilder}.
     */
    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    /**
     * Removes {@link #bitmapFont} from memory.
     * Should be called if the object is no longer in use and not a part of an {@link AssetManager}.
     */
    @Override
    public void dispose() {
        bitmapFont.dispose();
    }

    /**
     * Used to create a Font object.
     */
    public final static class FontBuilder implements Disposable {
        /**
         * The parameter data used to generate a new font from the {@link #generator}.
         */
        private final FreeTypeFontGenerator.FreeTypeFontParameter PARAMETER = new FreeTypeFontGenerator.FreeTypeFontParameter();

        /**
         * The generator that creates the font using the {@link #PARAMETER}.
         */
        private FreeTypeFontGenerator generator;

        /**
         * Creates a new font generator from the provided font file.
         *
         * @param file The font file used in the generator.
         * @return The FontBuilder, used to set more properties before building.
         */
        public FontBuilder setFont(FileHandle file) {
            this.generator = new FreeTypeFontGenerator(file);
            return this;
        }

        /**
         * Sets the font builder's generator to the provided generator (used with an {@link AssetManager}).
         *
         * @param generator The generator that will be stored within the FontBuilder.
         * @return The FontBuilder, used to set more properties before building.
         */
        public FontBuilder setFont(FreeTypeFontGenerator generator) {
            this.generator = generator;
            return this;
        }

        /**
         * Sets the {@link #PARAMETER}'s font size to the provided size.
         *
         * @param size The size of the font.
         * @return The FontBuilder, used to set more properties before building.
         */
        public FontBuilder setSize(int size) {
            this.PARAMETER.size = size;
            return this;
        }

        /**
         * Sets the {@link #PARAMETER}'s font color to the provided {@link Color}.
         *
         * @param color The color of the font.
         * @return The FontBuilder, used to set more properties before building.
         */
        public FontBuilder setColor(Color color) {
            this.PARAMETER.color = color;
            return this;
        }

        /**
         * @return The font generated from the {@link #PARAMETER} provided to the {@link #generator}.
         */
        public Font build() {
            if (generator == null) {
                return null;
            }
            return new Font(generator.generateFont(PARAMETER));
        }

        /**
         * Disposes of the font generator stored within the FontBuilder.
         */
        @Override
        public void dispose() {
            generator.dispose();
        }
    }
}