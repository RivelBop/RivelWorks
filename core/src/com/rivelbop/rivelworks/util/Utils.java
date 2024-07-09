package com.rivelbop.rivelworks.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.lwjgl.glfw.GLFW;

/**
 * Stores a variety of common useful utilities.
 *
 * @author David Jerzak (RivelBop)
 */
public final class Utils {
    private static final Vector2 CURSOR = new Vector2();
    private static final double[] X_POS = new double[1], Y_POS = new double[1];
    public static long window;

    private Utils() {
    }

    /**
     * Clears the screen for 2D games (only clears the color buffer).
     *
     * @param color The color to clear the color buffer to.
     */
    public static void clearScreen2D(Color color) {
        clearScreen2D(color.r, color.g, color.b, color.a);
    }

    /**
     * Clears the screen for 2D games (only clears the color buffer).
     *
     * @param r red
     * @param g green
     * @param b blue
     * @param a alpha
     */
    public static void clearScreen2D(float r, float g, float b, float a) {
        Gdx.gl.glClearColor(r, g, b, a);
        clearScreen2D();
    }

    /**
     * Clears the screen for 2D games (only clears the color buffer).
     */
    public static void clearScreen2D() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Clears the screen for 3D games (clears both the color and depth buffers).
     *
     * @param color The color to clear the color buffer to.
     */
    public static void clearScreen3D(Color color) {
        clearScreen3D(color.r, color.g, color.b, color.a);
    }

    /**
     * Clears the screen for 3D games (clears both the color and depth buffers).
     *
     * @param r red
     * @param g green
     * @param b blue
     * @param a alpha
     */
    public static void clearScreen3D(float r, float g, float b, float a) {
        Gdx.gl.glClearColor(r, g, b, a);
        clearScreen3D();
    }

    /**
     * Clears the screen for 3D games (clears both the color and depth buffers).
     */
    public static void clearScreen3D() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * @return The GLFW cursor's position as a Vector2.
     */
    public static Vector2 getCursorPosition() {
        GLFW.glfwGetCursorPos(window, X_POS, Y_POS);
        CURSOR.set((float) X_POS[0], (float) Y_POS[0]);
        return CURSOR;
    }

    /**
     * Registers the libGDX doc-provided classes to Kryo.
     *
     * @param kryo The Kryo instance to register to.
     */
    public static void registerGdxSerializers(Kryo kryo) {
        // Register libGDX Array class
        kryo.register(Array.class, new Serializer<Array>() {
            private Class genericType;

            {
                super.setAcceptsNull(true);
            }

            public void setGenerics(Kryo kryo, Class[] generics) {
                if (generics != null && kryo.isFinal(generics[0])) {
                    this.genericType = generics[0];
                } else {
                    this.genericType = null;
                }
            }

            public void write(Kryo kryo, Output output, Array array) {
                int length = array.size;
                output.writeInt(length, true);

                if (length == 0) {
                    this.genericType = null;
                    return;
                }

                if (genericType != null) {
                    Serializer serializer = kryo.getSerializer(genericType);
                    this.genericType = null;

                    for (Object element : array) {
                        kryo.writeObjectOrNull(output, element, serializer);
                    }
                } else {
                    for (Object element : array) {
                        kryo.writeClassAndObject(output, element);
                    }
                }
            }

            public Array read(Kryo kryo, Input input, Class<? extends Array> type) {
                Array array = new Array();
                kryo.reference(array);

                int length = input.readInt(true);
                array.ensureCapacity(length);

                if (genericType != null) {
                    Class elementClass = genericType;
                    Serializer serializer = kryo.getSerializer(genericType);
                    this.genericType = null;

                    for (int i = 0; i < length; i++) {
                        array.add(kryo.readObjectOrNull(input, elementClass, serializer));
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        array.add(kryo.readClassAndObject(input));
                    }
                }
                return array;
            }
        });

        // Register libGDX IntArray class
        kryo.register(IntArray.class, new Serializer<IntArray>() {
            {
                super.setAcceptsNull(true);
            }

            public void write(Kryo kryo, Output output, IntArray array) {
                int length = array.size;
                output.writeInt(length, true);

                if (length == 0) {
                    return;
                }

                for (int i = 0, n = array.size; i < n; i++) {
                    output.writeInt(array.get(i), true);
                }
            }

            public IntArray read(Kryo kryo, Input input, Class<? extends IntArray> type) {
                IntArray array = new IntArray();
                kryo.reference(array);

                int length = input.readInt(true);
                array.ensureCapacity(length);

                for (int i = 0; i < length; i++) {
                    array.add(input.readInt(true));
                }
                return array;
            }
        });

        // Register libGDX FloatArray class
        kryo.register(FloatArray.class, new Serializer<FloatArray>() {
            {
                super.setAcceptsNull(true);
            }

            @Override
            public void write(Kryo kryo, Output output, FloatArray array) {
                int length = array.size;
                output.writeInt(length, true);

                if (length == 0) {
                    return;
                }

                for (int i = 0, n = array.size; i < n; i++) {
                    output.writeFloat(array.get(i));
                }
            }

            @Override
            public FloatArray read(Kryo kryo, Input input, Class<? extends FloatArray> type) {
                FloatArray array = new FloatArray();
                kryo.reference(array);

                int length = input.readInt(true);
                array.ensureCapacity(length);

                for (int i = 0; i < length; i++) {
                    array.add(input.readFloat());
                }
                return array;
            }
        });

        // Register libGDX Color class
        kryo.register(Color.class, new Serializer<Color>() {
            @Override
            public Color read(Kryo kryo, Input input, Class<? extends Color> type) {
                Color color = new Color();
                Color.rgba8888ToColor(color, input.readInt());
                return color;
            }

            @Override
            public void write(Kryo kryo, Output output, Color color) {
                output.writeInt(Color.rgba8888(color));
            }
        });
    }

    /**
     * Adds indices to a mesh.
     *
     * @param mesh         The mesh to alter.
     * @param indicesToAdd The indices to add.
     */
    public static void addIndices(Mesh mesh, short[] indicesToAdd) {
        // Retrieve existing indices
        short[] currentIndices = new short[mesh.getNumIndices()];
        mesh.getIndices(currentIndices);

        // Combine current indices and indices to add
        ShortArray allIndices = new ShortArray(currentIndices);
        allIndices.addAll(indicesToAdd);

        // Update the mesh with the combined index buffer
        mesh.setIndices(allIndices.toArray());
    }

    /**
     * Removes indices from a mesh.
     *
     * @param mesh            The mesh to alter.
     * @param indicesToRemove The indices to remove.
     */
    public static void removeIndices(Mesh mesh, short[] indicesToRemove) {
        // Retrieve existing indices
        short[] indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);

        // Update the mesh with the new index buffer
        mesh.setIndices(removeIndices(indices, indicesToRemove));
    }

    /**
     * Removes indices from an array of indices and returns the new array.
     *
     * @param indices         The original indices to alter.
     * @param indicesToRemove The indices to remove.
     * @return The new altered array of indices.
     */
    private static short[] removeIndices(short[] indices, short[] indicesToRemove) {
        ShortArray newIndices = new ShortArray();
        for (int i = 0; i < indices.length; i += 3) {
            boolean removeFace = false;
            for (short j : indicesToRemove) {
                if (indices[i] == j || indices[i + 1] == j || indices[i + 2] == j) {
                    removeFace = true;
                    break;
                }
            }

            if (!removeFace) {
                newIndices.add(indices[i]);
                newIndices.add(indices[i + 1]);
                newIndices.add(indices[i + 2]);
            }
        }
        return newIndices.toArray();
    }

    /**
     * Calculates the yaw from the camera's direction vector.
     *
     * @param camera The camera direction vector to reference.
     * @return YAW in degrees.
     */
    public static float getYawDeg(Camera camera) {
        // Get the camera's direction
        Vector3 direction = camera.direction.cpy().nor();

        // Project the direction onto the XZ plane (y = 0)
        Vector3 directionXZ = new Vector3(direction.x, 0, direction.z).nor();

        // Calculate the yaw using the dot product and arccosine with the reference forward direction (0, 0, -1)
        Vector3 forward = new Vector3(0, 0, -1);
        float yaw = MathUtils.acos(directionXZ.dot(forward)) * MathUtils.radiansToDegrees;

        // Determine the sign of the yaw
        if (direction.x < 0) {
            yaw *= -1;
        }

        return yaw;
    }

    /**
     * Calculates the yaw from the camera's direction vector.
     *
     * @param camera The camera direction vector to reference.
     * @return YAW in radians.
     */
    public static float getYawRad(Camera camera) {
        return MathUtils.degreesToRadians * getYawDeg(camera);
    }

    /**
     * Calculates the pitch from the camera's direction vector.
     *
     * @param camera The camera direction vector to reference.
     * @return PITCH in degrees.
     */
    public static float getPitchDeg(Camera camera) {
        // Get the camera's direction
        Vector3 direction = camera.direction.cpy().nor();

        // Project the direction onto the XZ plane (y = 0)
        Vector3 directionXZ = new Vector3(direction.x, 0, direction.z).nor();

        // Calculate the pitch using the dot product and arccosine
        float pitch = MathUtils.acos(direction.dot(directionXZ)) * MathUtils.radiansToDegrees;

        // Determine the sign of the pitch
        if (direction.y < 0) {
            pitch *= -1;
        }

        return pitch;
    }

    /**
     * Calculates the pitch from the camera's direction vector.
     *
     * @param camera The camera direction vector to reference.
     * @return PITCH in radians.
     */
    public static float getPitchRad(Camera camera) {
        return MathUtils.degreesToRadians * getPitchDeg(camera);
    }

    /**
     * Calculates the roll from the camera's up vector and direction vector.
     *
     * @param camera The camera direction and up vector to reference.
     * @return ROLL in degrees.
     */
    public static float getRollDeg(Camera camera) {
        // Get the camera's direction and up vector.
        Vector3 direction = camera.direction.cpy().nor();
        Vector3 up = camera.up.cpy().nor();

        // Create a right vector from the direction and up vector
        Vector3 right = direction.cpy().crs(up).nor();

        // Project the up vector onto the plane perpendicular to the direction vector
        Vector3 projectedUp = up.cpy().sub(direction.cpy().scl(up.dot(direction))).nor();

        // Calculate the roll using the dot product and arccosine with the reference up vector (0, 1, 0)
        Vector3 referenceUp = new Vector3(0, 1, 0);
        float roll = MathUtils.acos(projectedUp.dot(referenceUp)) * MathUtils.radiansToDegrees;

        // Determine the sign of the roll
        if (right.y < 0) {
            roll *= -1;
        }

        return roll;
    }

    /**
     * Calculates the roll from the camera's up vector and direction vector.
     *
     * @param camera The camera direction and up vector to reference.
     * @return ROLL in radians.
     */
    public static float getRollRad(Camera camera) {
        return MathUtils.degreesToRadians * getRollDeg(camera);
    }
}