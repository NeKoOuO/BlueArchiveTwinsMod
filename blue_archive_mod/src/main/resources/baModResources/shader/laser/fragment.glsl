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


vec3 Strand(in vec2 fragCoord, in vec3 color, in float hoffset, in float hscale, in float vscale, in float timescale)
{
float glow = 0.06 * iResolution.y;
float twopi = 6.28318530718;
float curve = 1.0 - abs(fragCoord.y - (sin(mod(fragCoord.x * hscale / 100.0 / iResolution.x * 1000.0 + iTime * timescale + hoffset, twopi)) * iResolution.y * 0.25 * vscale + iResolution.y / 2.0));
float i = clamp(curve, 0.0, 1.0);
i += clamp((glow + curve) / glow, 0.0, 1.0) * 0.4 ;
return i * color;
}

vec3 Muzzle(in vec2 fragCoord, in float timescale)
{
float theta = atan(iResolution.y / 2.0 - fragCoord.y, iResolution.x - fragCoord.x + 0.13 * iResolution.x);
float len = iResolution.y * (10.0 + sin(theta * 20.0 + float(int(iTime * 20.0)) * -35.0)) / 6.0;
float d = max(-0.6, 1.0 - (sqrt(pow(abs(iResolution.x - fragCoord.x), 2.0) + pow(abs(iResolution.y / 2.0 - ((fragCoord.y - iResolution.y / 2.0) * 4.0 + iResolution.y / 2.0)), 2.0)) / len));
return vec3(d*1.2 , d*1.2, d*1.2 );
}

vec3 Beem(in vec2 uv,in float timescale){
float sim=(((sin(iTime*timescale)+1.0)/8.0)+0.3)*0.2;
float d=1.0-step(sim,abs(uv.y-0.5));
float a=sin(1.0-smoothstep(0.8,1.0,uv.x));
d=d*a;
vec3 color=vec3(0.4,0.7,0.8)*d;
return color;
}

void main() {
        vec2 pos=gl_FragCoord;
                pos.x=iResolution.x-pos.x;
vec2 uv=gl_FragCoord.xy/iResolution.xy;
float timescale=10.0;
vec3 c = vec3(0, 0, 0);
c += Strand(pos, vec3(1.0, 1.0, 1.0), 0.7934 + 1.0 + sin(iTime) * 30.0, 1.0, 0.40, 10.0 * timescale);
c += Strand(pos, vec3(0.0, 0.0, 1.0), 0.645 + 1.0 + sin(iTime) * 30.0, 1.5, 0.4, 10.3 * timescale);
c += Strand(pos, vec3(1.0, 1.0, 1.0), 0.735 + 1.0 + sin(iTime) * 30.0, 1.3, 0.49, 8.0 * timescale);
c += Strand(pos, vec3(1.0, 1.0, 1.0), 0.9245 + 1.0 + sin(iTime) * 30.0, 1.6, 0.44, 12.0 * timescale);
c += Strand(pos, vec3(0.0, 0.0, 1.0), 0.7234 + 1.0 + sin(iTime) * 30.0, 1.9, 0.43, 14.0 * timescale);
c += Strand(pos, vec3(1.0, 1.0, 1.0), 0.84525 + 1.0 + sin(iTime) * 30.0, 1.2, 0.48, 9.0 * timescale);
c += clamp(Muzzle(pos, timescale), 0.0, 1.0);


c+=Beem(pos.xy/iResolution.xy,timescale);
        c+=texture2D(u_texture,uv);
gl_FragColor=vec4(c,0.7);
}