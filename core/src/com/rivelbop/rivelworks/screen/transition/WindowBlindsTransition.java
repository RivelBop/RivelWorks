package com.rivelbop.rivelworks.screen.transition;

import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition;

/**
 * A {@link de.eskalon.commons.screen.ManagedScreen} transition that emulates window blinds on the screen.
 *
 * @author David Jerzak (RivelBop)
 * @author Fabien Benetou
 */
public class WindowBlindsTransition extends GLTransitionsShaderTransition {
    /**
     * Transition code
     */
    private static final String SHADER =
            "// Author: Fabien Benetou\n" +
                    "// License: MIT\n" +
                    "\n" +
                    "vec4 transition (vec2 uv) {\n" +
                    "  float t = progress;\n" +
                    "  \n" +
                    "  if (mod(floor(uv.y*100.*progress),2.)==0.)\n" +
                    "    t*=2.-.5;\n" +
                    "  \n" +
                    "  return mix(\n" +
                    "    getFromColor(uv),\n" +
                    "    getToColor(uv),\n" +
                    "    mix(t, progress, smoothstep(0.8, 1.0, progress))\n" +
                    "  );\n" +
                    "}\n";

    /**
     * Creates the transition with the specified duration.
     *
     * @param duration Seconds
     */
    public WindowBlindsTransition(float duration) {
        this(duration, null);
    }

    /**
     * Creates the transition with the specified duration and interpolation.
     *
     * @param duration      Seconds
     * @param interpolation Interpolator to transition with.
     */
    public WindowBlindsTransition(float duration, Interpolation interpolation) {
        super(SHADER, duration, interpolation);
    }
}