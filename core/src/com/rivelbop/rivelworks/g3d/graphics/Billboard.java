package com.rivelbop.rivelworks.g3d.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * An extension of {@link Decal} that is meant to simplify Billboard creation.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class Billboard extends Decal implements Disposable {
    /**
     * Stores temporary camera.direction vector data for orthographic use.
     */
    protected static final Vector3 DIR = new Vector3();

    /**
     * Create a billboard by providing the texture and whether it has transparency.
     *
     * @param textureRegion The region of a texture applied to the billboard.
     * @param isTransparent Whether the texture is transparent or not.
     */
    public Billboard(TextureRegion textureRegion, boolean isTransparent) {
        // Same procedure as Decal.newDecal(TextureRegion, isTransparent);
        super.setTextureRegion(textureRegion);
        super.setBlending(isTransparent ? 770 : -1, isTransparent ? 771 : -1);
        super.dimensions.x = (float) textureRegion.getRegionWidth();
        super.dimensions.y = (float) textureRegion.getRegionHeight();
        super.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Create a billboard by providing the texture and whether it has transparency.
     *
     * @param texture       The texture applied to the billboard.
     * @param isTransparent Whether the texture is transparent or not.
     */
    public Billboard(Texture texture, boolean isTransparent) {
        this(new TextureRegion(texture), isTransparent);
    }

    /**
     * Makes the Billboard face the provided {@link Camera}.
     *
     * @param camera The camera the Billboard will face.
     */
    public void lookAt(Camera camera) {
        if (camera instanceof PerspectiveCamera) {
            lookAt((PerspectiveCamera) camera);
            return;
        }
        DIR.set(-camera.direction.x, -camera.direction.y, -camera.direction.z);
        super.setRotation(DIR, Vector3.Y);
    }

    /**
     * Makes the Billboard face the provided {@link PerspectiveCamera}.
     *
     * @param camera The camera the Billboard will face.
     */
    public void lookAt(PerspectiveCamera camera) {
        super.lookAt(camera.position, camera.up);
    }

    /**
     * Removes the texture used by the Billboard from memory.
     */
    @Override
    public void dispose() {
        super.getTextureRegion().getTexture().dispose();
    }
}