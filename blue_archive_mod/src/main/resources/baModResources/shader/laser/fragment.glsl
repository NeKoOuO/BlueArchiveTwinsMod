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
uniform float iTime;


vec3 Strand(in vec2 fragCoord,in vec3 color,in float hoffset,in float hscale,in float vscale,in float timescale){
        float glow=0.07*iResolution;
        float curve=2.0-abs(fragCoord.y-(sin(fragCoord.x*hscale/200.0/iResolution.x*2000.0+iTime*timescale)*iResolution.y*0.35*vscale+iResolution.y/3.0));
        float i=clamp(curve,0.0,2.0);
        i+=clamp((glow+curve)/glow,0.0,2.0)*0.5;
        return i*color;
}

vec3 laser(in vec2 fragCoord){
        float dis=abs(fragCoord.y-0.7);

        float c=smoothstep(0.0,1.0,dis);
        vec3 color=vec3(c,c,c);
        return color;
}

void main() {
        vec2 uv = (gl_FragCoord.xy / iResolution.xy);
        vec4 texture=texture2D(u_texture,uv);
        float timescale=5.0;
        vec3 c=vec3(0,0,0);
        c += Strand(gl_FragCoord, vec3(0.8, 0.8, 0.8), 0.8934 + 2.0 + sin(iTime) * 40.0, 2.0, 0.26, 20.0 * timescale);
        c += Strand(gl_FragCoord, vec3(0.1, 0.1, 2.0), 0.745 + 2.0 + sin(iTime) * 40.0, 2.3, 0.29, 9.0 * timescale);
        c += Strand(gl_FragCoord, vec3(0.1,0.5,2.0),1.0245+2.0+sin(iTime)*40.0,1.2,0.17,12.0*timescale);
        c+=laser(uv);
        gl_FragColor=vec4(c.r,c.g,c.b,abs(sin(iTime/2.0)*2.0));
        gl_FragColor=gl_FragColor+texture;
}