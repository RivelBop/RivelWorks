package com.rivelbop.rivelworks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntSet;

/**
 * An improved replacement for {@link com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController}.
 *
 * @author David Jerzak (RivelBop)
 */
public class FirstPersonFlyCam extends FirstPersonCam {
    /**
     * Stores key interactions to be used for camera movement.
     */
    protected final IntSet KEYS = new IntSet();

    /**
     * Temporary vector used to apply angular movement to the camera.
     */
    protected final Vector3 TEMP = new Vector3();

    /**
     * Whether the camera should be automatically updated when {@link #update()} is called.
     */
    public boolean autoUpdate = true;

    /**
     * The velocity applied to move the camera.
     */
    public float velocity = 15f;

    /**
     * The input keys to move the camera's position.
     */
    public int
            forwardKey = Input.Keys.W,
            strafeLeftKey = Input.Keys.A,
            backwardKey = Input.Keys.S,
            strafeRightKey = Input.Keys.D;

    /**
     * Creates a first person fly camera from the provided camera.
     *
     * @param camera        The camera to control.
     * @param cursorCatched Whether the cursor should be catched on the screen.
     */
    public FirstPersonFlyCam(Camera camera, boolean cursorCatched) {
        super(camera, cursorCatched);
    }

    /**
     * Updates the camera's position.
     */
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (KEYS.contains(forwardKey)) {
            TEMP.set(CAMERA.direction).nor().scl(deltaTime * velocity);
            CAMERA.position.add(TEMP);
        }
        if (KEYS.contains(strafeLeftKey)) {
            TEMP.set(CAMERA.direction).crs(CAMERA.up).nor().scl(-deltaTime * velocity);
            CAMERA.position.add(TEMP);
        }
        if (KEYS.contains(backwardKey)) {
            TEMP.set(CAMERA.direction).nor().scl(-deltaTime * velocity);
            CAMERA.position.add(TEMP);
        }
        if (KEYS.contains(strafeRightKey)) {
            TEMP.set(CAMERA.direction).crs(CAMERA.up).nor().scl(deltaTime * velocity);
            CAMERA.position.add(TEMP);
        }

        if (autoUpdate) {
            CAMERA.update();
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        KEYS.remove(keycode);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        KEYS.add(keycode);
        return true;
    }
}