package com.Npc1;

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.WriterException;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class QrCodeUtil {
    public static String normal(String text) throws IOException, WriterException {
        return QrCodeGenWrapper.of(text).asString();
    }
    public static String logo(String text, InputStream logoFile) throws IOException, WriterException {
        return QrCodeGenWrapper.of(text)
                .setLogo(logoFile)
                .setLogoRate(7)
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .asString();
    }

    public static String color(String text) throws IOException, WriterException {
        return QrCodeGenWrapper.of(text)
                .setDrawPreColor(Color.RED)
                .asString();
    }

}
