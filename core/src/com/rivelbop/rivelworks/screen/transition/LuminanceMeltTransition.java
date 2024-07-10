package com.rivelbop.rivelworks.screen.transition;

import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition;

/**
 * A {@link de.eskalon.commons.screen.ManagedScreen} transition that 'melts' the screen.
 *
 * @author David Jerzak (RivelBop)
 * @author 0gust1
 */
public class LuminanceMeltTransition extends GLTransitionsShaderTransition {
    /**
     * Transition code
     */
    public static final String SHADER =
            "// Author: 0gust1\n" +
                    "// License: MIT\n" +
                    "//My own first transition — based on crosshatch code (from pthrasher), using  simplex noise formula (copied and pasted)\n" +
                    "//-> cooler with high contrasted images (isolated dark subject on light background f.e.)\n" +
                    "//TODO : try to rebase it on DoomTransition (from zeh)?\n" +
                    "//optimizations :\n" +
                    "//luminance (see http://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color#answer-596241)\n" +
                    "// Y = (R+R+B+G+G+G)/6\n" +
                    "//or Y = (R+R+R+B+G+G+G+G)>>3 \n" +
                    "\n" +
                    "\n" +
                    "//direction of movement :  0 : up, 1, down\n" +
                    "uniform bool direction; // = 1 \n" +
                    "//luminance threshold\n" +
                    "uniform float l_threshold; // = 0.8 \n" +
                    "//does the movement takes effect above or below luminance threshold ?\n" +
                    "uniform bool above; // = false \n" +
                    "\n" +
                    "\n" +
                    "//Random function borrowed from everywhere\n" +
                    "float rand(vec2 co){\n" +
                    "  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\n" +
                    "}\n" +
                    "\n" +
                    "\n" +
                    "// Simplex noise :\n" +
                    "// Description : Array and textureless GLSL 2D simplex noise function.\n" +
                    "//      Author : Ian McEwan, Ashima Arts.\n" +
                    "//  Maintainer : ijm\n" +
                    "//     Lastmod : 20110822 (ijm)\n" +
                    "//     License : MIT  \n" +
                    "//               2011 Ashima Arts. All rights reserved.\n" +
                    "//               Distributed under the MIT License. See LICENSE file.\n" +
                    "//               https://github.com/ashima/webgl-noise\n" +
                    "// \n" +
                    "\n" +
                    "vec3 mod289(vec3 x) {\n" +
                    "  return x - floor(x * (1.0 / 289.0)) * 289.0;\n" +
                    "}\n" +
                    "\n" +
                    "vec2 mod289(vec2 x) {\n" +
                    "  return x - floor(x * (1.0 / 289.0)) * 289.0;\n" +
                    "}\n" +
                    "\n" +
                    "vec3 permute(vec3 x) {\n" +
                    "  return mod289(((x*34.0)+1.0)*x);\n" +
                    "}\n" +
                    "\n" +
                    "float snoise(vec2 v)\n" +
                    "  {\n" +
                    "  const vec4 C = vec4(0.211324865405187,  // (3.0-sqrt(3.0))/6.0\n" +
                    "                      0.366025403784439,  // 0.5*(sqrt(3.0)-1.0)\n" +
                    "                     -0.577350269189626,  // -1.0 + 2.0 * C.x\n" +
                    "                      0.024390243902439); // 1.0 / 41.0\n" +
                    "// First corner\n" +
                    "  vec2 i  = floor(v + dot(v, C.yy) );\n" +
                    "  vec2 x0 = v -   i + dot(i, C.xx);\n" +
                    "\n" +
                    "// Other corners\n" +
                    "  vec2 i1;\n" +
                    "  //i1.x = step( x0.y, x0.x ); // x0.x > x0.y ? 1.0 : 0.0\n" +
                    "  //i1.y = 1.0 - i1.x;\n" +
                    "  i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);\n" +
                    "  // x0 = x0 - 0.0 + 0.0 * C.xx ;\n" +
                    "  // x1 = x0 - i1 + 1.0 * C.xx ;\n" +
                    "  // x2 = x0 - 1.0 + 2.0 * C.xx ;\n" +
                    "  vec4 x12 = x0.xyxy + C.xxzz;\n" +
                    "  x12.xy -= i1;\n" +
                    "\n" +
                    "// Permutations\n" +
                    "  i = mod289(i); // Avoid truncation effects in permutation\n" +
                    "  vec3 p = permute( permute( i.y + vec3(0.0, i1.y, 1.0 ))\n" +
                    "\t\t+ i.x + vec3(0.0, i1.x, 1.0 ));\n" +
                    "\n" +
                    "  vec3 m = max(0.5 - vec3(dot(x0,x0), dot(x12.xy,x12.xy), dot(x12.zw,x12.zw)), 0.0);\n" +
                    "  m = m*m ;\n" +
                    "  m = m*m ;\n" +
                    "\n" +
                    "// Gradients: 41 points uniformly over a line, mapped onto a diamond.\n" +
                    "// The ring size 17*17 = 289 is close to a multiple of 41 (41*7 = 287)\n" +
                    "\n" +
                    "  vec3 x = 2.0 * fract(p * C.www) - 1.0;\n" +
                    "  vec3 h = abs(x) - 0.5;\n" +
                    "  vec3 ox = floor(x + 0.5);\n" +
                    "  vec3 a0 = x - ox;\n" +
                    "\n" +
                    "// Normalise gradients implicitly by scaling m\n" +
                    "// Approximation of: m *= inversesqrt( a0*a0 + h*h );\n" +
                    "  m *= 1.79284291400159 - 0.85373472095314 * ( a0*a0 + h*h );\n" +
                    "\n" +
                    "// Compute final noise value at P\n" +
                    "  vec3 g;\n" +
                    "  g.x  = a0.x  * x0.x  + h.x  * x0.y;\n" +
                    "  g.yz = a0.yz * x12.xz + h.yz * x12.yw;\n" +
                    "  return 130.0 * dot(m, g);\n" +
                    "}\n" +
                    "\n" +
                    "// Simplex noise -- end\n" +
                    "\n" +
                    "float luminance(vec4 color){\n" +
                    "  //(0.299*R + 0.587*G + 0.114*B)\n" +
                    "  return color.r*0.299+color.g*0.587+color.b*0.114;\n" +
                    "}\n" +
                    "\n" +
                    "vec2 center = vec2(1.0, direction);\n" +
                    "\n" +
                    "vec4 transition(vec2 uv) {\n" +
                    "  vec2 p = uv.xy / vec2(1.0).xy;\n" +
                    "  if (progress == 0.0) {\n" +
                    "    return getFromColor(p);\n" +
                    "  } else if (progress == 1.0) {\n" +
                    "    return getToColor(p);\n" +
                    "  } else {\n" +
                    "    float x = progress;\n" +
                    "    float dist = distance(center, p)- progress*exp(snoise(vec2(p.x, 0.0)));\n" +
                    "    float r = x - rand(vec2(p.x, 0.1));\n" +
                    "    float m;\n" +
                    "    if(above){\n" +
                    "     m = dist <= r && luminance(getFromColor(p))>l_threshold ? 1.0 : (progress*progress*progress);\n" +
                    "    }\n" +
                    "    else{\n" +
                    "     m = dist <= r && luminance(getFromColor(p))<l_threshold ? 1.0 : (progress*progress*progress);  \n" +
                    "    }\n" +
                    "    return mix(getFromColor(p), getToColor(p), m);    \n" +
                    "  }\n" +
                    "}\n";

    /**
     * Creates the transition with the specified duration.
     *
     * @param duration Seconds
     */
    public LuminanceMeltTransition(float duration) {
        this(duration, null);
    }

    /**
     * Creates the transition with the specified duration and interpolation.
     *
     * @param duration      Seconds
     * @param interpolation Interpolator to transition with.
     */
    public LuminanceMeltTransition(float duration, Interpolation interpolation) {
        super(SHADER, duration, interpolation);
        this.getProgram().bind();
        set(true, 0.8f, false);
    }

    public void set(boolean direction, float l_threshold, boolean above) {
        this.getProgram().setUniformf("direction", direction ? 1 : 0);
        this.getProgram().setUniformf("l_threshold", l_threshold);
        this.getProgram().setUniformf("above", above ? 1 : 0);
    }
}