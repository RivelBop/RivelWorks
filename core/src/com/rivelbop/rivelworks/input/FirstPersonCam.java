package com.rivelbop.rivelworks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rivelbop.rivelworks.util.Utils;
import org.lwjgl.glfw.GLFW;

/**
 * A basic first person camera without movement.
 *
 * @author David Jerzak (RivelBop)
 */
public class FirstPersonCam extends InputAdapter {
    /**
     * Reference to GLFW window to gather cursor position information.
     */
    protected final long WINDOW = ((com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

    /**
     * Camera to alter direction vector.
     */
    protected final Camera CAMERA;

    /**
     * Stores the 'old' cursor position for easy reference.
     */
    protected final Vector2 CURSOR = new Vector2();

    /**
     * Stores the cursor position directly from the GLFW window.
     */
    protected final double[] X_POS = new double[1], Y_POS = new double[1];

    /**
     * Camera sensitivity.
     */
    protected float degreesPerPixel = 0.1f;

    /**
     * Temporary vectors used for direction calculation.
     */
    protected final Vector3
            OLD_PITCH_AXIS = new Vector3(), NEW_DIRECTION = new Vector3(), NEW_PITCH_AXIS = new Vector3(),
            TEMP1 = new Vector3(), TEMP2 = new Vector3(), TEMP3 = new Vector3();

    /**
     * Creates a first person camera from the provided camera.
     *
     * @param camera        The camera to control.
     * @param cursorCatched Whether the cursor should be catched on the screen.
     */
    public FirstPersonCam(Camera camera, boolean cursorCatched) {
        this.CAMERA = camera;
        Gdx.input.setCursorCatched(cursorCatched);
        GLFW.glfwGetCursorPos(WINDOW, X_POS, Y_POS);
        CURSOR.set((float) X_POS[0], (float) Y_POS[0]);
    }

    /**
     * @param degreesPerPixel The sensitivity to set to.
     */
    public void setDegreesPerPixel(float degreesPerPixel) {
        this.degreesPerPixel = degreesPerPixel;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        updateRotation();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        updateRotation();
        return true;
    }

    /**
     * Updates the camera according to the cursor's delta.
     */
    protected void updateRotation() {
        // Get cursor delta
        GLFW.glfwGetCursorPos(WINDOW, X_POS, Y_POS);
        float deltaX = (float) ((CURSOR.x - X_POS[0]) * degreesPerPixel);
        float deltaY = (float) ((CURSOR.y - Y_POS[0]) * degreesPerPixel);

        // Rotate Yaw
        CAMERA.direction.rotate(CAMERA.up, deltaX);

        // Rotate Roll
        OLD_PITCH_AXIS.set(TEMP1.set(CAMERA.direction).crs(CAMERA.up).nor());
        NEW_DIRECTION.set(TEMP2.set(CAMERA.direction).rotate(TEMP1, deltaY));
        NEW_PITCH_AXIS.set(TEMP3.set(TEMP2).crs(CAMERA.up));
        if (!NEW_PITCH_AXIS.hasOppositeDirection(OLD_PITCH_AXIS)) {
            CAMERA.direction.set(NEW_DIRECTION);
        }

        // Set oldX and oldY of cursor for upcoming cursor delta
        CURSOR.set(Gdx.input.getX(), Gdx.input.getY());
    }

    /**
     * @return The camera's YAW in degrees.
     */
    public float getYawDeg() {
        return Utils.getYawDeg(CAMERA);
    }

    /**
     * @return The camera's YAW in radians.
     */
    public float getYawRad() {
        return Utils.getYawRad(CAMERA);
    }

    /**
     * @return The camera's PITCH in degrees.
     */
    public float getPitchDeg() {
        return Utils.getPitchDeg(CAMERA);
    }

    /**
     * @return The camera's PITCH in radians.
     */
    public float getPitchRad() {
        return Utils.getPitchRad(CAMERA);
    }

    /**
     * @return The camera's ROLL in degrees.
     */
    public float getRollDeg() {
        return Utils.getRollDeg(CAMERA);
    }

    /**
     * @return The camera's ROLL in radians.
     */
    public float getRollRad() {
        return Utils.getRollRad(CAMERA);
    }

    /**
     * @return Camera sensitivity in degrees per pixel.
     */
    public float getDegreesPerPixel() {
        return degreesPerPixel;
    }

    /**
     * @return The camera instance.
     */
    public Camera getCamera() {
        return CAMERA;
    }
}