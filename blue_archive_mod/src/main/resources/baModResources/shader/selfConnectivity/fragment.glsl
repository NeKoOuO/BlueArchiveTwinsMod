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
        uniform vec2 center;
        uniform vec2 len;


void main() {
        vec2 uv=gl_FragCoord.xy/iResolution.xy;
        vec2 block=step(abs(gl_FragCoord.xy-center),len);
        float color=min(block.x,block.y);
        gl_FragColor=texture2D(u_texture,uv)*vec4(color,color,color,color);
}