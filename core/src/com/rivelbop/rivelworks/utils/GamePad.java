package com.rivelbop.rivelworks.utils;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.minlog.Log;

import java.util.HashMap;

/**
 * Used as a simplified wrapper around the GDX Controller library.
 *
 * @author David Jerzak (RivelBop)
 */
public class GamePad implements ControllerListener {
    /**
     * Keeps track of all buttons that have just been pressed to provide functionality for {@link #isButtonJustPressed(int)}.
     */
    private final HashMap<Integer, Boolean> JUST_PRESSED_MAPPINGS = new HashMap<>();

    /**
     * The default GDX controller, used as the backbone for a GamePad.
     */
    private final Controller controller;

    /**
     * Grabs the current/recently used controller and sets its listener to the default within the class.
     */
    public GamePad() {
        controller = Controllers.getCurrent();

        if (controller != null) {
            controller.addListener(this);
        } else {
            Log.error("No Controller Detected!");
        }
    }

    /**
     * Grabs the controller with the provided index and sets its listener to the default within the class.
     *
     * @param index The index of the controller.
     */
    public GamePad(int index) {
        controller = getControllers().get(index);

        if (controller != null) {
            controller.addListener(this);
        } else {
            Log.error("No Controller Detected!");
        }
    }

    /**
     * Creates a GamePad with the provided controller and sets its listener to the default within the class.
     *
     * @param controller The controller to pass into the GamePad.
     */
    public GamePad(Controller controller) {
        this.controller = controller;

        if (controller != null) {
            controller.addListener(this);
        } else {
            Log.error("No Controller Detected!");
        }
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
     * Called when controller is connected.
     *
     * @param controller The current GamePad/controller.
     */
    @Override
    public void connected(Controller controller) {
        Log.debug("Controller has been connected!");
    }

    /**
     * Called when controlled is disconnected.
     *
     * @param controller The current GamePad/controller.
     */
    @Override
    public void disconnected(Controller controller) {
        Log.debug("Controller has been disconnected!");
        JUST_PRESSED_MAPPINGS.clear();
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
        JUST_PRESSED_MAPPINGS.put(i, false);
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
        JUST_PRESSED_MAPPINGS.put(button, true);
        return controller.getButton(button);
    }

    /**
     * Checks to see if the provided button index is just pressed (only true once when held down).
     *
     * @param button The index of the button that is just pressed.
     * @return If the button is just pressed.
     */
    public boolean isButtonJustPressed(int button) {
        Boolean pressed = JUST_PRESSED_MAPPINGS.get(button);
        if (pressed == null) {
            pressed = false;
        }

        if (!pressed) {
            JUST_PRESSED_MAPPINGS.put(button, true);
            return controller.getButton(button);
        }
        return false;
    }

    /**
     * Gets the axis value of the provided joystick index.
     *
     * @param joystick The index of the joystick that is being checked.
     * @return The axis value of the joystick that is checked.
     */
    public float getJoystick(int joystick) {
        return controller.getAxis(joystick);
    }

    /**
     * Gets all the controller button mappings. Should be used when getting the inputs of any button/joystick.
     *
     * @return The controller button/joystick mapping.
     */
    public ControllerMapping mapping() {
        return controller.getMapping();
    }

    /**
     * Vibrates the controller for the provided time and strength.
     *
     * @param millis   The time to vibrate in milliseconds.
     * @param strength The strength of the vibration (0f-1f).
     */
    public void vibrate(int millis, float strength) {
        if (controller.canVibrate() && !controller.isVibrating()) {
            controller.startVibration(millis, strength);
        }
    }

    /**
     * Stops controller vibration.
     */
    public void stopVibration() {
        controller.cancelVibration();
    }

    /**
     * Checks to see if the controller is still connected.
     *
     * @return Controller connection status.
     */
    public boolean isConnected() {
        return controller.isConnected();
    }

    /**
     * Returns the default LibGDX controller stored within the GamePad.
     *
     * @return Default LibGDX controller object.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @return All the connected/available controllers.
     */
    public static Array<Controller> getControllers() {
        return Controllers.getControllers();
    }
}