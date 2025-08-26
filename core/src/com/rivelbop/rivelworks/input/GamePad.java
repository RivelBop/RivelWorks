package com.rivelbop.rivelworks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.esotericsoftware.minlog.Log;

/**
 * Used as a simplified wrapper around the GDX Controller library.
 *
 * @author David Jerzak (RivelBop)
 */
public class GamePad implements ControllerListener {
    private static final String LOG_TAG = GamePad.class.getSimpleName();

    /**
     * Keeps track of all buttons that have just been pressed to provide functionality for {@link #isButtonJustPressed(int)}.
     */
    private final IntSet JUST_PRESSED_BUTTONS = new IntSet();

    /**
     * The default GDX controller, used as the backbone for a GamePad.
     */
    private final Controller CONTROLLER;

    /**
     * Grabs the current/recently used controller and sets its listener to the default within the class.
     */
    public GamePad() {
        this(Controllers.getCurrent());
    }

    /**
     * Grabs the controller with the provided index and sets its listener to the default within the class.
     *
     * @param index The index of the controller.
     */
    public GamePad(int index) {
        this(getControllers().get(index));
    }

    /**
     * Creates a GamePad with the provided GamePad and sets its listener to the default within the class.
     *
     * @param gamePad The GamePad to pass into the GamePad.
     */
    public GamePad(GamePad gamePad) {
        this(gamePad.getController());
    }

    /**
     * Creates a GamePad with the provided controller and sets its listener to the default within the class.
     *
     * @param controller The controller to pass into the GamePad.
     */
    public GamePad(Controller controller) {
        this.CONTROLLER = controller;

        if (isConnected()) {
            controller.addListener(this);
        } else {
            Log.error(LOG_TAG, "No controller detected!");
        }
    }

    /**
     * Called when controller is connected.
     *
     * @param controller The current GamePad/controller.
     */
    @Override
    public void connected(Controller controller) {
        Log.info(LOG_TAG, "Controller {" + controller.getPlayerIndex() + "} has been connected.");
    }

    /**
     * Called when controlled is disconnected.
     *
     * @param controller The current GamePad/controller.
     */
    @Override
    public void disconnected(Controller controller) {
        Log.info(LOG_TAG, "Controller[" + controller.getPlayerIndex() + "] has been disconnected.");
        JUST_PRESSED_BUTTONS.clear();
    }

    /**
     * Called if a button is held down.
     *
     * @param controller The current GamePad/controller.
     * @param i          The index of the button that has been interacted with.
     * @return USELESS
     */
    @Override
    public boolean buttonDown(Controller controller, int i) {
        JUST_PRESSED_BUTTONS.remove(i);
        Gdx.app.postRunnable(() -> JUST_PRESSED_BUTTONS.add(i));
        return false;
    }

    /**
     * Called if a button is up after being pressed down.
     *
     * @param controller The current GamePad/controller.
     * @param i          The index of the button that has been interacted with.
     * @return USELESS
     */
    @Override
    public boolean buttonUp(Controller controller, int i) {
        return false;
    }

    /**
     * Called when a joystick is moved.
     *
     * @param controller The current GamePad/controller.
     * @param i          The index of the joystick that has been interacted with.
     * @param v          The axis value, -1 to 1.
     * @return USELESS
     */
    @Override
    public boolean axisMoved(Controller controller, int i, float v) {
        return false;
    }

    /**
     * Checks to see if the provided button index is held.
     *
     * @param button The index of the button that is held.
     * @return If the button is held.
     */
    public boolean isButtonPressed(int button) {
        return isConnected() && CONTROLLER.getButton(button);
    }

    /**
     * Checks to see if the provided button index is just pressed (only true once when held down).
     *
     * @param button The index of the button that is just pressed.
     * @return If the button is just pressed.
     */
    public boolean isButtonJustPressed(int button) {
        return !JUST_PRESSED_BUTTONS.contains(button) && isButtonPressed(button);
    }

    /**
     * Gets the axis value of the provided joystick index.
     *
     * @param joystick The index of the joystick that is being checked.
     * @return The axis value of the joystick that is checked.
     */
    public float getJoystick(int joystick) {
        return isConnected() ? CONTROLLER.getAxis(joystick) : 0f;
    }

    /**
     * Gets all the controller button mappings. Should be used when getting the inputs of any button/joystick.
     *
     * @return The controller button/joystick mapping.
     */
    public ControllerMapping buttons() {
        return isConnected() ? CONTROLLER.getMapping() : null;
    }

    /**
     * Vibrates the controller for the provided time and strength.
     *
     * @param millis   The time to vibrate in milliseconds.
     * @param strength The strength of the vibration (0f-1f).
     */
    public void vibrate(int millis, float strength) {
        if (isConnected()) {
            CONTROLLER.startVibration(millis, strength);
        }
    }

    /**
     * Stops controller vibration.
     */
    public void stopVibration() {
        if (isConnected()) {
            CONTROLLER.cancelVibration();
        }
    }

    /**
     * Checks to see if the controller is still connected.
     *
     * @return Controller connection status.
     */
    public boolean isConnected() {
        return CONTROLLER != null && CONTROLLER.isConnected();
    }

    /**
     * Returns the default LibGDX controller stored within the GamePad.
     *
     * @return Default LibGDX controller object.
     */
    public Controller getController() {
        return CONTROLLER;
    }

    /**
     * @return All the connected/available controllers.
     */
    public static Array<Controller> getControllers() {
        return Controllers.getControllers();
    }
}