package com.tuotiansudai.util;

import com.google.common.collect.Lists;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

import java.awt.*;

/**
 * Created by Administrator on 2015/8/13.
 */
public class CaptchaGenerator {

    public static Captcha generate(int captchaWidth, int captchaHeight) {
        Captcha.Builder captchaBuilder = new Captcha.Builder(captchaWidth, captchaHeight);
        DefaultWordRenderer wordRenderer = new DefaultWordRenderer(Lists.newArrayList(Color.BLACK), Lists.newArrayList(new Font("Geneva", Font.BOLD, 24)));
        CurvedLineNoiseProducer noiseProducer = new CurvedLineNoiseProducer(Color.BLACK, 1.0f);
        return captchaBuilder.addText(wordRenderer).addNoise(noiseProducer).addBackground(new GradiatedBackgroundProducer()).build();
    }

}
