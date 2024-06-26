#ifdef GL_ES
    #define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

#define res (iResolution.xy/pw)
varying vec2 v_texCoords;
uniform float pw;

uniform sampler2D u_texture;
uniform vec2 iResolution;
uniform vec2 iMouse;
void main() {
        vec2 center=iMouse.xy/iResolution.xy;
        vec2 uv=gl_FragCoord.xy/iResolution.xy;
        vec2 aspectRatio=vec2(iResolution.x/iResolution.y,1.0);
        vec2 offset=(uv-center)*aspectRatio;

        float dist=length(offset);
        float radius = pow(dist, 1.3);

        float theta = atan(offset.y, offset.x);
        uv=center+radius*vec2(cos(theta),sin(theta))/aspectRatio;
        vec4 color=texture2D(u_texture,uv);
        gl_FragColor=color;
}