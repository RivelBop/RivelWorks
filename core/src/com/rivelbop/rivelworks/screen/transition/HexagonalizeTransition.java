package com.rivelbop.rivelworks.screen.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition;

/**
 * A {@link de.eskalon.commons.screen.ManagedScreen} transition that hexagonalizes the screen.
 *
 * @author David Jerzak (RivelBop)
 * @author Fernando Kuteken
 */
public class HexagonalizeTransition extends GLTransitionsShaderTransition {
    /**
     * Transition code
     */
    private static final String SHADER =
            "uniform float ratio;\n" +
                    "// Author: Fernando Kuteken\n" +
                    "// License: MIT\n" +
                    "// Hexagonal math from: http://www.redblobgames.com/grids/hexagons/\n" +
                    "\n" +
                    "uniform int steps; // = 50;\n" +
                    "uniform float horizontalHexagons; //= 20;\n" +
                    "\n" +
                    "struct Hexagon {\n" +
                    "  float q;\n" +
                    "  float r;\n" +
                    "  float s;\n" +
                    "};\n" +
                    "\n" +
                    "Hexagon createHexagon(float q, float r){\n" +
                    "  Hexagon hex;\n" +
                    "  hex.q = q;\n" +
                    "  hex.r = r;\n" +
                    "  hex.s = -q - r;\n" +
                    "  return hex;\n" +
                    "}\n" +
                    "\n" +
                    "Hexagon roundHexagon(Hexagon hex){\n" +
                    "  \n" +
                    "  float q = floor(hex.q + 0.5);\n" +
                    "  float r = floor(hex.r + 0.5);\n" +
                    "  float s = floor(hex.s + 0.5);\n" +
                    "\n" +
                    "  float deltaQ = abs(q - hex.q);\n" +
                    "  float deltaR = abs(r - hex.r);\n" +
                    "  float deltaS = abs(s - hex.s);\n" +
                    "\n" +
                    "  if (deltaQ > deltaR && deltaQ > deltaS)\n" +
                    "    q = -r - s;\n" +
                    "  else if (deltaR > deltaS)\n" +
                    "    r = -q - s;\n" +
                    "  else\n" +
                    "    s = -q - r;\n" +
                    "\n" +
                    "  return createHexagon(q, r);\n" +
                    "}\n" +
                    "\n" +
                    "Hexagon hexagonFromPoint(vec2 point, float size) {\n" +
                    "  \n" +
                    "  point.y /= ratio;\n" +
                    "  point = (point - 0.5) / size;\n" +
                    "  \n" +
                    "  float q = (sqrt(3.0) / 3.0) * point.x + (-1.0 / 3.0) * point.y;\n" +
                    "  float r = 0.0 * point.x + 2.0 / 3.0 * point.y;\n" +
                    "\n" +
                    "  Hexagon hex = createHexagon(q, r);\n" +
                    "  return roundHexagon(hex);\n" +
                    "  \n" +
                    "}\n" +
                    "\n" +
                    "vec2 pointFromHexagon(Hexagon hex, float size) {\n" +
                    "  \n" +
                    "  float x = (sqrt(3.0) * hex.q + (sqrt(3.0) / 2.0) * hex.r) * size + 0.5;\n" +
                    "  float y = (0.0 * hex.q + (3.0 / 2.0) * hex.r) * size + 0.5;\n" +
                    "  \n" +
                    "  return vec2(x, y * ratio);\n" +
                    "}\n" +
                    "\n" +
                    "vec4 transition (vec2 uv) {\n" +
                    "  \n" +
                    "  float dist = 2.0 * min(progress, 1.0 - progress);\n" +
                    "  dist = steps > 0 ? ceil(dist * float(steps)) / float(steps) : dist;\n" +
                    "  \n" +
                    "  float size = (sqrt(3.0) / 3.0) * dist / horizontalHexagons;\n" +
                    "  \n" +
                    "  vec2 point = dist > 0.0 ? pointFromHexagon(hexagonFromPoint(uv, size), size) : uv;\n" +
                    "\n" +
                    "  return mix(getFromColor(point), getToColor(point), progress);\n" +
                    "  \n" +
                    "}\n";

    /**
     * Creates the transition with the specified duration.
     *
     * @param duration Seconds
     */
    public HexagonalizeTransition(float duration) {
        this(duration, null);
    }

    /**
     * Creates the transition with the specified duration and interpolation.
     *
     * @param duration      Seconds
     * @param interpolation Interpolator to transition with.
     */
    public HexagonalizeTransition(float duration, Interpolation interpolation) {
        super(SHADER, duration, interpolation);
        super.getProgram().bind();
        set((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 50, 20f);
    }

    public void set(float ratio, int steps, float horizontalHexagons) {
        super.getProgram().setUniformf("ratio", ratio);
        super.getProgram().setUniformf("steps", steps);
        super.getProgram().setUniformf("horizontalHexagons", horizontalHexagons);
    }
}