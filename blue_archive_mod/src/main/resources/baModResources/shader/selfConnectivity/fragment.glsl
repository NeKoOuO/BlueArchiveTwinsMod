#ifdef GL_ES
    #define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec2 iResolution;


void main() {
vec2 uv=gl_FragCoord.xy/iResolution.xy;
gl_FragColor=texture2D(u_texture,uv);
}