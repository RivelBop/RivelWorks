package com.rivelbop.rivelworks.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import de.pottgames.tuningfork.SoundBuffer;
import de.pottgames.tuningfork.SoundBufferLoader;

import java.io.File;
import java.util.Objects;

/**
 * A simpler and secured {@link AssetManager}.
 *
 * @author David Jerzak (RivelBop)
 */
public class Assets implements Disposable {
    private static final String LOG_TAG = Assets.class.getSimpleName();

    /**
     * The asset manager that stores all the assets that will be loaded and unloaded.
     */
    private final AssetManager ASSET_MANAGER;

    /**
     * Initializes the {@link #ASSET_MANAGER} with all the necessary loaders.
     */
    public Assets() {
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        this.ASSET_MANAGER = new AssetManager(resolver, true);
        ASSET_MANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        ASSET_MANAGER.setLoader(SoundBuffer.class, new SoundBufferLoader(resolver));
    }

    /**
     * @return Whether all files have loaded.
     */
    public boolean update() {
        return ASSET_MANAGER.update();
    }

    /**
     * Loads the provided file and type into the {@link #ASSET_MANAGER}.
     *
     * @param fileName The name of the file to load.
     * @param type     The file type to load.
     * @param <T>      TYPE
     */
    public <T> void load(String fileName, Class<T> type) {
        load(fileName, type, null);
    }

    /**
     * Loads the provided file and type into the {@link #ASSET_MANAGER}.
     *
     * @param fileName  The name of the file to load.
     * @param type      The file type to load.
     * @param parameter The loading parameter.
     * @param <T>       TYPE
     */
    public <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        // Check and load the asset file
        File file = Gdx.files.internal(fileName).file();
        if (!file.exists()) {
            if (parameter != null) {
                ASSET_MANAGER.load(fileName, type, parameter);
            } else {
                ASSET_MANAGER.load(fileName, type);
            }
            Log.info(LOG_TAG, "LOADED: " + fileName);
            return;
        }
        Log.error(LOG_TAG, "ERROR LOADING: " + fileName);

        // If failed, create a new asset directory to store the external assets
        File externalAssetsFolder = new File(ASSET_MANAGER.hashCode() + "_assets");
        externalAssetsFolder.mkdir();

        // Move the external asset to the newly created asset directory
        if (file.renameTo(new File(externalAssetsFolder.getPath() + File.separator + fileName))) {
            Log.debug(LOG_TAG, "FILE MOVED: " + fileName);
            load(fileName, type, parameter);
            return;
        }
        Log.error(LOG_TAG, "ERROR MOVING FILE: " + fileName);
    }

    /**
     * Retrieves the provided file by name and type from the {@link #ASSET_MANAGER}.
     *
     * @param fileName The name of the file to retrieve.
     * @param type     The type of file to retrieve.
     * @param <T>      TYPE
     * @return The asset file from the {@link #ASSET_MANAGER}.
     */
    public <T> T get(String fileName, Class<T> type) {
        return ASSET_MANAGER.get(fileName, type);
    }

    /**
     * Unloads the provided file from the {@link #ASSET_MANAGER}.
     *
     * @param fileName The file to unload from the assets.
     */
    public void unload(String fileName) {
        ASSET_MANAGER.unload(fileName);
        Log.debug(LOG_TAG, "UNLOADED: " + fileName);
    }

    /**
     * Dispose of the {@link #ASSET_MANAGER}.
     */
    @Override
    public void dispose() {
        File directory = new File(ASSET_MANAGER.hashCode() + "_assets");
        if (directory.isDirectory()) {
            for (File f : Objects.requireNonNull(directory.listFiles())) {
                moveBack(f);
            }
            directory.delete();
        }
        ASSET_MANAGER.dispose();
        Log.debug(LOG_TAG, "DISPOSED");
    }

    /**
     * @return The asset manager stored all loaded files.
     */
    public AssetManager getAssetManager() {
        return ASSET_MANAGER;
    }

    /**
     * Moves the provided file back one directory.
     *
     * @param file The file to move.
     */
    private void moveBack(File file) {
        file.renameTo(new File("." + File.separator + file.getName()));
    }
}