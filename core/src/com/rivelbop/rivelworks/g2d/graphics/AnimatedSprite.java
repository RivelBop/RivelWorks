package com.rivelbop.rivelworks.g2d.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;

import java.util.HashMap;

/**
 * An extension of the LibGDX {@link Sprite} class with a built-in animation system.
 *
 * @author David Jerzak (RivelBop)
 */
public class AnimatedSprite extends Sprite implements Disposable {
    private static final String LOG_TAG = AnimatedSprite.class.getSimpleName();

    /**
     * Stores the selected atlas file for use.
     */
    private final TextureAtlas ATLAS;

    /**
     * Controls the internal animation time used to check the duration of the animation playing and calculate the current frame to display.
     */
    private float animationTime;

    /**
     * Stores the amount of time each frame will be displayed.
     */
    private final float FRAME_DELAY;

    /**
     * Stores the 'loop type' of the animation.
     */
    private final Animation.PlayMode PLAY_MODE;

    /**
     * Stores a set of previously used {@link AtlasAnimation}, each referenced by calling their 'name' as a {@link String}.
     * This is specifically used to avoid having to create a new reference to a file or from an {@link AssetManager}.
     */
    private final HashMap<String, AtlasAnimation> ANIMATIONS;

    /**
     * A reference to the current animation that is playing.
     */
    private AtlasAnimation currentAnimation;

    /**
     * Creates an {@link AnimatedSprite} object by only providing the properties of the animation.
     *
     * @param atlas         The texture atlas file for all animations.
     * @param baseAnimation The name of the 'base' or initial region of animations to play, taken from the provided atlas file.
     * @param frameDelay    The amount of time each frame will be displayed.
     * @param playMode      The 'loop type' of the animation.
     */
    public AnimatedSprite(TextureAtlas atlas, String baseAnimation, float frameDelay, Animation.PlayMode playMode) {
        // Create a sprite with the first frame of the base animation
        super(atlas.findRegion(baseAnimation));

        // Initialize animation variables
        this.ATLAS = atlas;
        this.ANIMATIONS = new HashMap<>();
        this.ANIMATIONS.put(baseAnimation, new AtlasAnimation(atlas, baseAnimation, frameDelay, playMode));
        this.currentAnimation = ANIMATIONS.get(baseAnimation);
        this.FRAME_DELAY = frameDelay;
        this.PLAY_MODE = playMode;
    }

    /**
     * Creates an {@link AnimatedSprite} object by providing the properties of the animation and the position of the sprite.
     *
     * @param atlas         The texture atlas file for all animations.
     * @param baseAnimation The name of the 'base' or initial region of animations to play, taken from the provided atlas file.
     * @param frameDelay    The amount of time each frame will be displayed.
     * @param playMode      The 'loop type' of the animation.
     * @param x             The initial X-position of the sprite.
     * @param y             The initial Y-position of the sprite.
     */
    public AnimatedSprite(TextureAtlas atlas, String baseAnimation, float frameDelay, Animation.PlayMode playMode, float x, float y) {
        this(atlas, baseAnimation, frameDelay, playMode);
        super.setPosition(x, y);
    }

    /**
     * Updates the animation timer, sprite flipping direction, and region of the sprite to the current animation frame.
     */
    public void update() {
        animationTime += Gdx.graphics.getDeltaTime();
        currentAnimation.update(animationTime).flip(currentAnimation.update(animationTime).isFlipX() != isFlipX(), false);
        super.setRegion(currentAnimation.update(animationTime));

        if (currentAnimation.getAnimation().isAnimationFinished(animationTime)) {
            animationTime = 0f;
        }
    }

    /**
     * Updates and renders the sprite to the provided {@link SpriteBatch}.
     *
     * @param spriteBatch The batch to which the sprite will be rendered/drawn to.
     */
    public void render(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }

    /**
     * Updates and renders the sprite to the provided {@link SpriteBatch}.
     *
     * @param spriteBatch The batch to which the sprite will be rendered/drawn to.
     */
    public void updateAndRender(SpriteBatch spriteBatch) {
        update();
        render(spriteBatch);
    }

    /**
     * Adds the provided atlas animation region based on its name to {@link #ANIMATIONS}.
     * The animation created uses {@link #FRAME_DELAY} and {@link #PLAY_MODE}.
     *
     * @param name The name of the atlas region that will supply the current animation.
     */
    public void addAnimation(String name) {
        addAnimation(name, new AtlasAnimation(ATLAS, name, FRAME_DELAY, PLAY_MODE));
    }

    /**
     * Adds the provided atlas animation region based on its name to {@link #ANIMATIONS}.
     * The animation created uses the provided frame delay and play mode.
     *
     * @param name       The name of the atlas region that will supply the current animation.
     * @param frameDelay The amount of time each frame will be displayed.
     * @param playMode   The 'loop type' of the animation.
     */
    public void addAnimation(String name, float frameDelay, Animation.PlayMode playMode) {
        addAnimation(name, new AtlasAnimation(ATLAS, name, frameDelay, playMode));
    }

    /**
     * Adds the provided {@link AtlasAnimation} into {@link #ANIMATIONS}.
     *
     * @param name      The name of the animation that will go into {@link #ANIMATIONS}.
     * @param animation The animation that will be added along with the provided name.
     */
    public void addAnimation(String name, AtlasAnimation animation) {
        ANIMATIONS.put(name, animation);
    }

    /**
     * Changes the {@link #currentAnimation} to the animation with the name provided.
     * Adds the animation to {@link #ANIMATIONS} if the animation was not previously used.
     *
     * @param animation The name of the atlas region that will supply the current animation.
     */
    public void setAnimation(String animation) {
        // If the animation is within the stored animations, it will be utilized as the current animation.
        if (ANIMATIONS.containsKey(animation)) {
            if (currentAnimation != ANIMATIONS.get(animation)) {
                currentAnimation = ANIMATIONS.get(animation);
                animationTime = 0f;
            }
            return;
        }

        // If the animation is not within the stored animations, it will be put into it, then utilized.
        addAnimation(animation);
        currentAnimation = ANIMATIONS.get(animation);
    }

    /**
     * Changes the frame delay of the provided animation.
     *
     * @param animation  The name of the atlas region that will be altered.
     * @param frameDelay The amount of time each frame will be displayed.
     */
    public void setFrameDelay(String animation, float frameDelay) {
        // If the animation is within the stored animations, it will have its frame delay edited.
        if (ANIMATIONS.containsKey(animation)) {
            ANIMATIONS.get(animation).setFrameDelay(frameDelay);
            return;
        }

        Log.error(LOG_TAG, "Animation {" + animation + "} is not a part of this sprites animation list!");
    }

    /**
     * Changes the play mode of the provided animation.
     *
     * @param animation The name of the atlas region that will be altered.
     * @param playMode  The 'loop type' of the animation.
     */
    public void setPlayMode(String animation, Animation.PlayMode playMode) {
        // If the animation is within the stored animations, it will have its play mode edited.
        if (ANIMATIONS.containsKey(animation)) {
            ANIMATIONS.get(animation).setPlayMode(playMode);
            return;
        }

        Log.error(LOG_TAG, "Animation {" + animation + "} is not a part of this sprites animation list!");
    }

    /**
     * Removes all textures provided to the AnimatedSprite from memory.
     * Should be called if the object is no longer in use and textures are not a part of an {@link AssetManager}.
     */
    @Override
    public void dispose() {
        getTexture().dispose();
        ATLAS.dispose();
    }
}