package com.rivelbop.rivelworks.math;

import com.badlogic.gdx.math.Rectangle;

/**
 * A basic directional 2D collision bound system.
 *
 * @author David Jerzak (RivelBop)
 */
public class AABB extends Rectangle {
    /**
     * Stores the direction possibilities.
     */
    public enum CollisionDir {
        NONE,
        TOP, BOTTOM, LEFT, RIGHT,
        TOP_LEFT, TOP_RIGHT,
        BOTTOM_LEFT, BOTTOM_RIGHT,
        ALL
    }

    /**
     * Creates an empty rectangle bound.
     */
    public AABB() {
    }

    /**
     * Creates a rectangle bound at (0,0) with the dimensions.
     *
     * @param width  The width of the bounds.
     * @param height The height of the bounds.
     */
    public AABB(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a rectangle bound with the position and dimension.
     *
     * @param x      The x-position (based on left).
     * @param y      The y-position (based on bottom).
     * @param width  The width of the bounds.
     * @param height The height of the bounds.
     */
    public AABB(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Copies the properties of another AABB object.
     *
     * @param other The position and dimension provider.
     */
    public AABB(AABB other) {
        super(other.x, other.y, other.width, other.height);
    }

    /**
     * Checks the direction from which another AABB object collides the current object.
     *
     * @param other   The other AABB object to check collisions with.
     * @param precise If false, will simplify corner collisions into the dominant axis.
     * @return The direction the collision is happening from.
     */
    public CollisionDir collides(AABB other, boolean precise) {
        // Get the difference of centers
        float dx = other.centerX() - centerX(),
                dy = other.centerY() - centerY();

        // Used to compare range
        float combinedHalfWidths = (width / 2) + (other.width / 2),
                combinedHalfHeights = (height / 2) + (other.height / 2);

        // If overlapping
        if (Math.abs(dx) < combinedHalfWidths && Math.abs(dy) < combinedHalfHeights) {
            // Get overlap amount
            float overlapX = combinedHalfWidths - Math.abs(dx);
            float overlapY = combinedHalfHeights - Math.abs(dy);

            if (dx == 0 && dy == 0) {
                return CollisionDir.ALL;
            }

            if (overlapX < overlapY) {
                return (dx > 0) ? CollisionDir.RIGHT : CollisionDir.LEFT;
            } else if (!precise || overlapY < overlapX) {
                return (dy > 0) ? CollisionDir.TOP : CollisionDir.BOTTOM;
            }

            if (dy > 0) {
                return (dx > 0) ? CollisionDir.TOP_RIGHT : CollisionDir.TOP_LEFT;
            } else if (dy < 0) {
                return (dx > 0) ? CollisionDir.BOTTOM_RIGHT : CollisionDir.BOTTOM_LEFT;
            }
        }

        // Not colliding
        return CollisionDir.NONE;
    }

    /**
     * Calls the less precise directional AABB collision.
     *
     * @param other The other AABB body to check where collision is coming from.
     * @return The direction the collision is happening from.
     */
    public CollisionDir collides(AABB other) {
        return collides(other, false);
    }

    /**
     * @return The center x-position.
     */
    public float centerX() {
        return x + width / 2f;
    }

    /**
     * @return The center y-position.
     */
    public float centerY() {
        return y + height / 2f;
    }
}
