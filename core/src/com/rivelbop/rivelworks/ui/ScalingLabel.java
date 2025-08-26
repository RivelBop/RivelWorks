package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class ScalingLabel extends ScalingElement {
    private final Matrix4
            ORIGINAL_MATRIX = new Matrix4(),
            SCALED_MATRIX = new Matrix4();
    private final Vector2 OFFSET = new Vector2();

    protected final boolean USE_INT_POS;
    protected BitmapFont font;
    protected GlyphLayout layout;
    protected Color color;
    protected String text;
    protected float fontScale = 1f;

    public ScalingLabel(BitmapFont font, Color color, String text, float x, float y, int alignment, int screenWidth, int screenHeight) {
        super(x, y, 1f, 1f, alignment, screenWidth, screenHeight);

        this.font = font;
        this.USE_INT_POS = font.usesIntegerPositions();
        this.layout = new GlyphLayout();
        setText(text, color);
    }

    public ScalingLabel(ScalingScene scene, BitmapFont font, Color color, String text, float x, float y, int alignment) {
        this(font, color, text, x, y, alignment, scene.getNativeWidth(), scene.getNativeHeight());
        scene.add(this);
    }

    public ScalingLabel(BitmapFont font, String text, float x, float y, int alignment, int screenWidth, int screenHeight) {
        this(font, font.getColor(), text, x, y, alignment, screenWidth, screenHeight);
    }

    public ScalingLabel(ScalingScene scene, BitmapFont font, String text, float x, float y, int alignment) {
        this(scene, font, font.getColor(), text, x, y, alignment);
    }

    @Override
    protected void render(SpriteBatch batch) {
        font.setUseIntegerPositions(false);

        ORIGINAL_MATRIX.set(batch.getTransformMatrix());
        SCALED_MATRIX.set(ORIGINAL_MATRIX).scale(scale * fontScale, scale * fontScale, 1);
        batch.setTransformMatrix(SCALED_MATRIX);

        font.draw(batch, layout, currX / (scale * fontScale) + OFFSET.x, currY / (scale * fontScale) + layout.height + OFFSET.y);

        batch.setTransformMatrix(ORIGINAL_MATRIX);

        font.setUseIntegerPositions(USE_INT_POS);
    }

    @Override
    protected void updateFromBounds(float x, float y, float width, float height) {
        offset();
    }

    public void setText(String text) {
        layout.setText(font, text, 0, text.length(), color, 0, Align.left, false, null);
        this.text = text;
        this.setNativeWidth(layout.width * fontScale, false);
        this.setNativeHeight(layout.height * fontScale, true);
    }

    public void setText(String text, Color color) {
        this.text = text;
        setColor(color);
        this.setNativeWidth(layout.width * fontScale, false);
        this.setNativeHeight(layout.height * fontScale, true);
    }

    public void setColor(Color color) {
        this.color = color;
        layout.setText(font, text, 0, text.length(), color, 0, Align.left, false, null);
    }

    public void setScale(float scale) {
        fontScale = scale;
        this.setNativeWidth(layout.width * fontScale, false);
        this.setNativeHeight(layout.height * fontScale, true);
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    private void offset() {
        switch (alignment) {
            case Align.center:
                OFFSET.set(-layout.width / 2f, -layout.height / 2f);
                break;
            case Align.right:
                OFFSET.set(-layout.width, -layout.height / 2f);
                break;
            case Align.topRight:
                OFFSET.set(-layout.width, -layout.height);
                break;
            case Align.top:
                OFFSET.set(-layout.width / 2f, -layout.height);
                break;
            case Align.topLeft:
                OFFSET.set(0f, -layout.height);
                break;
            case Align.left:
                OFFSET.set(0f, -layout.height / 2f);
                break;
            case Align.bottomLeft:
                OFFSET.set(0f, 0f);
                break;
            case Align.bottom:
                OFFSET.set(-layout.width / 2f, 0f);
                break;
            case Align.bottomRight:
                OFFSET.set(-layout.width, 0f);
                break;
        }
    }
}
