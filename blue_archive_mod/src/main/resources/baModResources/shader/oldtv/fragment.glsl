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

float luma(vec3 color){
        return dot(color,vec3(0.2126, 0.5152, 0.0722));
}

float filmNoise(vec2 uv){
        vec2 uv2=uv;
        uv*=0.8;
        vec2 st=uv;
        uv.x*=iResolution.x/iResolution.y;
        uv*=0.6+cos(iTime*22);
        uv.y+=sin(iTime*22);
        uv.x-=cos(iTime*22);
        st*=0.5+sin(iTime)/5;
        st.y-=sin(iTime*23);
        st.x+=cos(iTime*23);

//        float tex1=luma(texture2D(u_text))
        float texMask=1.-pow(distance(uv2,vec2(0.5)),2.2);
        float finalTex=clamp(1.-texMask,0.,1.);
        return finalTex;
}
float flicker(in vec2 uv, float amount)
{
uv *= 0.0001;
return mix(pow(cos(uv.y * 100.2 + iTime * 80.), 0.4), 1., 1. - amount);
}
float filmStripes(in vec2 uv, float amount)
{
    float stripes;
    float mask = cos(uv.x - cos(uv.y + iTime) + sin(uv.x * 10.2) - cos(uv.x * 60. + iTime)) + sin(uv.y * 2.);
    mask += flicker(uv, 1.);

    if(fract(uv.x + iTime) >= 0.928 && fract(uv.x + iTime) <= 0.929)
    {
    stripes = sin(uv.x * 4300. * sin(uv.x * 102.)) * mask;
    }
    if(fract(uv.x + fract(1. - iTime)) >= 0.98 + fract(iTime) && fract(uv.x + fract(iTime / 2. + sin(iTime / 2.))) <= 0.97 + fract(iTime + 0.2))
    {
    stripes = sin(uv.x * 4300. * sin(uv.x * 102.)) * mask;
    }
    if(fract(uv.x + fract(- iTime * 1. + sin(iTime / 2.))) >= 0.96 + fract(iTime) && fract(uv.x + fract(iTime / 2. + sin(iTime / 2.))) <= 0.95 + fract(iTime + 0.2))
    {
    stripes = sin(uv.x * 4300. * sin(uv.x * 102.)) * mask;
    }
    if(fract(uv.x + fract(- iTime * 1. + sin(iTime / 2.))) >= 0.99 + fract(iTime) && fract(uv.x + fract(iTime / 2. + sin(iTime / 2.))) <= 0.98 + fract(iTime + 0.2))
    {
    stripes = sin(uv.x * 4300. * sin(uv.x * 102.)) * mask;
    }

    stripes = 1. - stripes;

    return mix(1., stripes, amount);
}

float filmGrain(in vec2 uv, float amount)
{
    float grain = fract(sin(uv.x * 100. * uv.y * 524. + fract(iTime)) * 5000.);
    float w = 1.;
    return mix(w, grain, amount);
}
void main() {
        vec2 uv=gl_FragCoord.xy/iResolution.xy;
        vec3 col=texture2D(u_texture,uv).rgb;

        col*=luma(col);
        col*=1.9;
        col=col/1.5+0.12;

        col+=1.-filmStripes(uv,0.07);
        col+=1.-filmStripes(uv+uv,0.05);

        col-=filmNoise(uv)/4;

        col *= filmGrain(uv, 0.16);

        gl_FragColor=vec4(col,1);
}