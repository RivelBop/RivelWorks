package com.rivelbop.rivelworks;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.esotericsoftware.minlog.Log;
import com.rivelbop.rivelworks.io.ConsoleFileOutputStream;
import com.rivelbop.rivelworks.util.Utils;
import de.pottgames.tuningfork.Audio;
import de.pottgames.tuningfork.AudioConfig;
import de.pottgames.tuningfork.logger.TuningForkLogger;

import java.io.FileNotFoundException;

/**
 * Used to initialize and somewhat 'handle' the engine.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public final class RivelWorks {
    private static final String LOG_TAG = RivelWorks.class.getSimpleName();

    /**
     * TuningFork Audio Instance
     */
    private static Audio audio;

    private RivelWorks() {
    }

    /**
     * Used to initialize all necessary libraries for RivelWorks to function (Logger, Audio, Box2D, Bullet).
     *
     * @param logLevel      The level of logging to display.
     * @param logOutputFile The file to log to.
     * @param box2D         Should Box2D be initialized?
     * @param bullet        Should JBullet be initialized?
     */
    public static void init(int logLevel, String logOutputFile, boolean box2D, boolean bullet) {
        Utils.window = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        // Creates a basic logging system
        Log.set(logLevel);
        if (logOutputFile != null) {
            ConsoleFileOutputStream outputStream;
            try {
                outputStream = new ConsoleFileOutputStream(logOutputFile);
            } catch (FileNotFoundException e) {
                Log.error(LOG_TAG, "Trouble logging to provided file!");
                throw new RuntimeException(e);
            }
            System.setOut(outputStream);
            System.setErr(outputStream);
            Log.debug(LOG_TAG, "Logging to file {" + logOutputFile + "} and the console!");
        }

        // Configures libGDX logger to the 'minlog' library
        Gdx.app.setApplicationLogger(new ApplicationLogger() {
            @Override
            public void log(String tag, String message) {
                Log.info(tag, message);
            }

            @Override
            public void log(String tag, String message, Throwable exception) {
                Log.info(tag, message, exception);
            }

            @Override
            public void error(String tag, String message) {
                Log.error(tag, message);
            }

            @Override
            public void error(String tag, String message, Throwable exception) {
                Log.error(tag, message, exception);
            }

            @Override
            public void debug(String tag, String message) {
                Log.debug(tag, message);
            }

            @Override
            public void debug(String tag, String message, Throwable exception) {
                Log.debug(tag, message, exception);
            }
        });
        Log.info(LOG_TAG, "Logger has initialized!");

        // Creates an instance of TuningFork Audio integrated with the logging configuration
        AudioConfig audioConfig = new AudioConfig();
        audioConfig.setLogger(new TuningForkLogger() {
            @Override
            public void error(Class<?> aClass, String s) {
                Log.error(aClass.getSimpleName(), s);
            }

            @Override
            public void warn(Class<?> aClass, String s) {
                Log.warn(aClass.getSimpleName(), s);
            }

            @Override
            public void info(Class<?> aClass, String s) {
                Log.info(aClass.getSimpleName(), s);
            }

            @Override
            public void debug(Class<?> aClass, String s) {
                Log.debug(aClass.getSimpleName(), s);
            }

            @Override
            public void trace(Class<?> aClass, String s) {
                Log.trace(aClass.getSimpleName(), s);
            }
        });
        audio = Audio.init(audioConfig);

        // Initialize physics libraries
        if (box2D) {
            Box2D.init();
            Log.info(LOG_TAG, "Box2D has initialized!");
        }

        if (bullet) {
            Bullet.init();
            Log.info(LOG_TAG, "Bullet has initialized!");
        }

        Log.info(LOG_TAG, "RivelWorks has initialized!");
    }

    /**
     * @return The TuningFork Audio instance.
     */
    public static Audio getAudio() {
        return audio;
    }

    /**
     * Disposes of all necessary libraries (currently only Audio).
     */
    public static void dispose() {
        audio.dispose();
    }
}