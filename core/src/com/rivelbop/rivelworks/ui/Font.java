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
 * Used to generate a font with ease.
 *
 * @author David Jerzak (RivelBop)
 */
public class Font implements Disposable {
    /**
     * The font generator used to create the {@link BitmapFont}.
     */
    private final FreeTypeFontGenerator GENERATOR;

    /**
     * The parameters/properties of the font that will be generated from the {@link #GENERATOR}.
     */
    private final FreeTypeFontGenerator.FreeTypeFontParameter PARAMETER;

    /**
     * The font generated from {@link #GENERATOR} and the {@link #PARAMETER} provided to it.
     */
    private BitmapFont font;

    /**
     * Generates a {@link BitmapFont} from the provided font file, font size, and color.
     *
     * @param file  The font file used to generate the {@link BitmapFont}.
     * @param size  The size of the font.
     * @param color The color of the font.
     */
    public Font(FileHandle file, int size, Color color) {
        this.GENERATOR = new FreeTypeFontGenerator(file);
        this.PARAMETER = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.PARAMETER.size = size;
        this.PARAMETER.color = color;
        this.font = GENERATOR.generateFont(PARAMETER);
    }

    /**
     * Generates a {@link BitmapFont} from the provided font generator, font size, and color.
     *
     * @param generator The font generator used to generate the {@link BitmapFont}.
     * @param size      The size of the font.
     * @param color     The color of the font.
     */
    public Font(FreeTypeFontGenerator generator, int size, Color color) {
        this.GENERATOR = generator;
        this.PARAMETER = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.PARAMETER.size = size;
        this.PARAMETER.color = color;
        this.font = GENERATOR.generateFont(PARAMETER);
    }

    /**
     * Changes the color of the font, then regenerates a new font based off that color.
     *
     * @param color The new color of the font.
     */
    public void setColor(Color color) {
        PARAMETER.color = color;
        font = GENERATOR.generateFont(PARAMETER);
    }

    /**
     * Changes the size of the font, then regenerates a new font based off that size.
     *
     * @param size The new size of the font.
     */
    public void setSize(int size) {
        PARAMETER.size = size;
        font = GENERATOR.generateFont(PARAMETER);
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
        font.draw(batch, text, x, y);
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
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, text, x - layout.width / 2f, y - layout.height / 2f);
    }

    /**
     * Removes {@link #font} and {@link #GENERATOR} from memory.
     * Should be called if the object is no longer in use and not a part of an {@link AssetManager}.
     */
    @Override
    public void dispose() {
        font.dispose();
        GENERATOR.dispose();
    }
}