package com.rivelbop.rivelworks.input;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.esotericsoftware.minlog.Log;

/**
 * Handles multi-input support, allowing the user to easily customize in-game controls.
 *
 * @author David Jerzak (RivelBop)
 */
public class InputManager extends InputMultiplexer {
    private static final String LOG_TAG = InputManager.class.getSimpleName();

    /**
     * Stores the game pads provided to the input manager along with their provided index (ID).
     */
    private final IntMap<GamePad> GAMEPADS = new IntMap<>();

    /**
     * Stores the input map for each game pad and player.
     */
    private final IntMap<ObjectMap<String, InputMap>> INDEXED_INPUT_MAPS = new IntMap<>();

    /**
     * Controls both the input for desktop and controllers.
     */
    public interface InputMap {
        boolean keyboardAndMouse();

        boolean gamePad(GamePad gamePad);
    }

    /**
     * Creates the default input map for the first player.
     */
    public InputManager() {
        this(new ObjectMap<>());
    }

    /**
     * Creates the first player and grabs the provided input map.
     *
     * @param inputMap The initial/base input map.
     */
    public InputManager(ObjectMap<String, InputMap> inputMap) {
        setGamePad(new GamePad(), inputMap);
    }

    /**
     * Checks to see if the provided input name is pressed for the first player.
     *
     * @param inputName The name of the input to check.
     * @return Whether the input is pressed.
     */
    public boolean isPressed(String inputName) {
        return isPressed(0, inputName);
    }

    /**
     * Checks to see if the provided input name is pressed for the indexed player.
     *
     * @param index     The index of the player to check.
     * @param inputName The name of the input to check.
     * @return Whether the input is pressed.
     */
    public boolean isPressed(int index, String inputName) {
        ObjectMap<String, InputMap> indexedInputMap = INDEXED_INPUT_MAPS.get(index);
        InputMap inputMap = (indexedInputMap != null) ? indexedInputMap.get(inputName) : null;
        if (inputMap != null) {
            GamePad gamePad = GAMEPADS.get(index);
            return inputMap.keyboardAndMouse() || (gamePad != null && gamePad.isConnected() && inputMap.gamePad(gamePad));
        }
        return false;
    }

    /**
     * Sets the game pad of the first player.
     *
     * @param gamePad The game pad to set.
     */
    public void setGamePad(GamePad gamePad) {
        setGamePad(0, gamePad);
    }

    /**
     * Sets the game pad and input map of the first player.
     *
     * @param gamePad  The game pad to set.
     * @param inputMap The input map to set.
     */
    public void setGamePad(GamePad gamePad, ObjectMap<String, InputMap> inputMap) {
        setGamePad(0, gamePad, inputMap);
    }

    /**
     * Sets the game pad of the indexed player.
     *
     * @param index   The index to set the game pad to.
     * @param gamePad The game pad to set.
     */
    public void setGamePad(int index, GamePad gamePad) {
        setGamePad(index, gamePad, INDEXED_INPUT_MAPS.get(0));
    }

    /**
     * Sets the game pad and input map of the indexed player.
     *
     * @param index    The index to set the game pad and input map.
     * @param gamePad  The game pad to set.
     * @param inputMap The input map to set.
     */
    public void setGamePad(int index, GamePad gamePad, ObjectMap<String, InputMap> inputMap) {
        GAMEPADS.put(index, gamePad);
        setInputMap(index, inputMap);
        Log.info(LOG_TAG, "New game pad set at index: " + index);
    }

    /**
     * Sets the input map of the first player.
     *
     * @param inputMap The input map to set.
     */
    public void setInputMap(ObjectMap<String, InputMap> inputMap) {
        setInputMap(0, inputMap);
    }

    /**
     * Sets the input map of the indexed player.
     *
     * @param index    The index to set the input map.
     * @param inputMap The input map to set.
     */
    public void setInputMap(int index, ObjectMap<String, InputMap> inputMap) {
        if (GAMEPADS.get(index) != null) {
            INDEXED_INPUT_MAPS.put(index, inputMap);
            Log.debug(LOG_TAG, "New input map set at index: " + index);
        }
    }

    /**
     * Adds the specified input into the input map to the first player.
     *
     * @param name     The name of the input map.
     * @param inputMap The input map itself.
     */
    public void setInput(String name, InputMap inputMap) {
        setInput(0, name, inputMap);
    }

    /**
     * Adds the specified input into the input map to the indexed player.
     *
     * @param index    The index to set the input map.
     * @param name     The name of the input map.
     * @param inputMap The input map itself.
     */
    public void setInput(int index, String name, InputMap inputMap) {
        ObjectMap<String, InputMap> mapper = INDEXED_INPUT_MAPS.get(index);
        if (mapper != null) {
            mapper.put(name, inputMap);
            Log.debug(LOG_TAG, "New input set at index: " + index);
        }
    }

    /**
     * @return The first player's game pad.
     */
    public GamePad getGamePad() {
        return getGamePad(0);
    }

    /**
     * @param index The index of the player/game pad.
     * @return The indexed game pad.
     */
    public GamePad getGamePad(int index) {
        return GAMEPADS.get(index);
    }

    /**
     * @return The first players overall input map.
     */
    public ObjectMap<String, InputMap> getInputMap() {
        return getInputMap(0);
    }

    /**
     * @param index The index of the specific player's overall input map.
     * @return The overall input map of the specified player.
     */
    public ObjectMap<String, InputMap> getInputMap(int index) {
        return INDEXED_INPUT_MAPS.get(index);
    }

    /**
     * Removes the first player's game pad.
     */
    public void removeGamePad() {
        removeGamePad(0);
    }

    /**
     * Removes the game pad at the provided index.
     *
     * @param index The index of the game pad.
     */
    public void removeGamePad(int index) {
        GAMEPADS.remove(index);
        if (index != 0) {
            INDEXED_INPUT_MAPS.remove(index);
            Log.info(LOG_TAG, "Removed game pad at index: " + index);
        }
    }

    /**
     * Removes all disconnected controllers from the list of provided game pads.
     */
    public void removeAllDisconnectedGamePads() {
        GAMEPADS.forEach(e -> {
            GamePad gamePad = e.value;
            if (gamePad == null || !gamePad.isConnected()) {
                removeGamePad(e.key);
            }
        });
        INDEXED_INPUT_MAPS.forEach(e -> {
            int key = e.key;
            if (!GAMEPADS.containsKey(key)) {
                INDEXED_INPUT_MAPS.remove(key);
            }
        });
        Log.debug(LOG_TAG, "All disconnected controllers have been removed.");
    }
}