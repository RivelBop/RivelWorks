package com.rivelbop.rivelworks.g3d.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.esotericsoftware.minlog.Log;

/**
 * An extension of DecalBatch that provides a similar functionality to the other batches.
 *
 * @author David Jerzak (RivelBop)
 */
public class BillBoardBatch extends DecalBatch {
    private static final String LOG_TAG = BillBoardBatch.class.getSimpleName();

    /**
     * Ensures that rendering on the batch has begun, used to optimize rendering by removing decals from batch.
     */
    private boolean hasBegun;

    /**
     * Creates a bill board batch using a {@link CameraGroupStrategy}.
     *
     * @param camera The camera to use.
     */
    public BillBoardBatch(Camera camera) {
        this(new CameraGroupStrategy(camera));
    }

    /**
     * Creates a bill board batch using any {@link GroupStrategy}.
     *
     * @param groupStrategy The group strategy to use.
     */
    public BillBoardBatch(GroupStrategy groupStrategy) {
        super(groupStrategy);
    }

    /**
     * Creates a bill board batch using a {@link CameraGroupStrategy} and batch pool size.
     *
     * @param camera The camera to use.
     * @param size   The batch pool size.
     */
    public BillBoardBatch(Camera camera, int size) {
        this(new CameraGroupStrategy(camera), size);
    }

    /**
     * Creates a bill board batch using a {@link GroupStrategy} and batch pool size.
     *
     * @param groupStrategy The group strategy to use.
     * @param size          The batch pool size.
     */
    public BillBoardBatch(GroupStrategy groupStrategy, int size) {
        super(size, groupStrategy);
    }

    /**
     * Begin rendering to batch.
     */
    public void begin() {
        hasBegun = true;
        super.clear();
    }

    /**
     * Add and render a decal/billboard to the batch.
     *
     * @param decal The decal/billboard to render.
     */
    public void render(Decal decal) {
        super.add(decal);
    }

    /**
     * Finish rendering to batch (must call begin first).
     */
    public void end() {
        if (hasBegun) {
            super.render();
            hasBegun = false;
            return;
        }
        Log.error(LOG_TAG, "Batch has not been started!");
    }
}