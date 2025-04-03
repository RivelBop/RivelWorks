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
     * Pushes the current AABB out of the other if they collide.
     *
     * @param other The other AABB body to check collisions.
     * @param precise If false, will simplify corner collisions into the dominant axis.
     */
    public void resolveCollision(AABB other, boolean precise) {
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
                return;
            }

            if (overlapX < overlapY) {
                if (dx > 0) { // Colliding from right
                    x = other.x - other.width; // Push to the left
                } else { // Colliding from left
                    x = other.x + other.width; // Push to the right
                }
                return;
            } else if (!precise || overlapY < overlapX) {
                if (dy > 0) { // Colliding from top
                    y = other.y - other.height; // Push to the bottom
                } else { // Colliding from bottom
                    y = other.y + other.height; // Push to the top
                }
                return;
            }

            if (dy > 0) {
                if (dx > 0) {
                    setPosition(other.x - other.width, other.y - other.height);
                } else {
                    setPosition(other.x + other.width, other.y - other.height);
                }
            } else if (dy < 0) {
                if (dx > 0) {
                    setPosition(other.x - other.width, other.y + other.height);
                } else {
                    setPosition(other.x + other.width, other.y + other.height);
                }
            }
        }
    }

    /**
     * Pushes the current AABB out of the other if they collide using the less precise collision check.
     *
     * @param other The other AABB body to check collisions.
     */
    public void resolveCollision(AABB other) {
        resolveCollision(other, false);
    }

    /**
     * Sets the center x-position.
     *
     * @param x The x-pos value.
     */
    public void setCenterX(float x) {
        this.x = x - width / 2f;
    }

    /**
     * Sets the center y-position.
     *
     * @param y The y-pos value.
     */
    public void setCenterY(float y) {
        this.y = y - height / 2f;
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
