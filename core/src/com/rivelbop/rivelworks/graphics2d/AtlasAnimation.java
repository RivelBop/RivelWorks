package com.rivelbop.rivelworks.graphics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Creates and stores an {@link Animation} based on a {@link TextureAtlas}.
 *
 * @author David Jerzak (RivelBop)
 */
public class AtlasAnimation {
    /**
     * Internal clock for an animation.
     */
    private float animationTime;

    /**
     * Default LibGDX Animation object.
     */
    private final Animation<TextureAtlas.AtlasRegion> ANIMATION;

    /**
     * Creates an {@link AtlasAnimation} object by providing each aspect of an animation.
     *
     * @param atlas      The texture atlas file used by the animation.
     * @param name       The name of the region from the provided atlas file.
     * @param frameDelay The amount of time each frame will be displayed.
     * @param playMode   The 'loop type' of the animation.
     */
    public AtlasAnimation(TextureAtlas atlas, String name, float frameDelay, Animation.PlayMode playMode) {
        this.ANIMATION = new Animation<>(frameDelay, atlas.findRegions(name), playMode);
    }

    /**
     * Updates and returns the frames of the animation based on an external animation timer.
     *
     * @param animationTime An external timer to use for calculating animation frames.
     * @return The animation frame based on the provided timer.
     */
    public TextureAtlas.AtlasRegion update(float animationTime) {
        return ANIMATION.getKeyFrame(animationTime);
    }

    /**
     * Updates and returns the frame of the animation based on the internal animation timer.
     *
     * @return The animation frame based on the internal timer.
     */
    public TextureAtlas.AtlasRegion update() {
        animationTime += Gdx.graphics.getDeltaTime();
        return ANIMATION.getKeyFrame(animationTime);
    }

    /**
     * @return The default LibGDX {@link Animation} stored in the {@link AtlasAnimation} object.
     */
    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return ANIMATION;
    }

    /**
     * Sets the frame delay of the animation to the one provided.
     *
     * @param frameDelay The amount of time each frame will be displayed.
     */
    public void setFrameDelay(float frameDelay) {
        ANIMATION.setFrameDuration(frameDelay);
    }

    /**
     * @return The frame delay of the animation.
     */
    public float getFrameDelay() {
        return ANIMATION.getFrameDuration();
    }

    /**
     * Sets the play mode of the animation to the one provided.
     *
     * @param playMode The 'loop type' of the animation.
     */
    public void setPlayMode(Animation.PlayMode playMode) {
        ANIMATION.setPlayMode(playMode);
    }

    /**
     * @return The play mode of the animation.
     */
    public Animation.PlayMode getPlayMode() {
        return ANIMATION.getPlayMode();
    }
}