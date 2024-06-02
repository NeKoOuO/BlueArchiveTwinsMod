#ifdef GL_ES
    #define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

#define res (iResolution.xy/pw)
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform float pw;

uniform sampler2D u_texture;
uniform vec2 iResolution;

void main() {
        vec2 uv=gl_FragCoord.xy/iResolution.xy;
        vec2 pos=floor(uv*res)/res;
        if(max(abs(pos.x-0.5),abs(pos.y-0.5))>0.5){
            gl_FragColor=vec4(0.0,0.0,0.0,1.0);
        }else{
            gl_FragColor=texture2D(u_texture,pos,-16.0);
        }
}