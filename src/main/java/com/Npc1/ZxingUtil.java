package com.Npc1;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ZxingUtil {

    public  static String createImage(String text,InputStream imgPath) throws Exception{
        HashMap map = new HashMap();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//纠错程度
        map.put(EncodeHintType.CHARACTER_SET,"utf-8");//字符编码,可存中文
        map.put(EncodeHintType.MARGIN,1);//边距

        //前端无法识别，需要转换成BufferedImage，
        //bitMatrix存储二进制点信息
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,300,300,map);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int x = 0;x<width;x++){
            for (int y = 0;y<height;y++){
                image.setRGB(x,y,bitMatrix.get(x,y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null){
            //转String返回
            return imageToString(image);
        }
        image = insertLogo(image,imgPath);
        return imageToString(image);

    }

    private static BufferedImage insertLogo(BufferedImage image, InputStream imgPath) throws Exception {

        Image logo = ImageIO.read(imgPath);
        int w = logo.getWidth(null);
        int h = logo.getHeight(null);
        if(w > 100){
            w = 100;
        }
        if (h > 100){
            h = 100;
        }

        Image logo1 = logo.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        BufferedImage bfImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics g = bfImage.getGraphics();
        g.drawImage(logo1,0, 0,null);
        g.dispose();

        //二维码和logo合成,重绘
        Graphics2D grah = image.createGraphics();
        int x = (300 - w)/2;
        int y = (300 - h)/2;
        grah.drawImage(logo1,x,y,null);
        Shape shape = new RoundRectangle2D.Float(x,y,w,h,6,6);//圆角矩形
        grah.setStroke(new BasicStroke(3f));//画笔粗细
        grah.draw(shape);
        grah.dispose();//释放资源
        return image;
    }

    //转Base64
    public  static String imageToString(BufferedImage image) throws IOException {
        //先变成流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image,"png",os);
        return Base64.encodeBase64String(os.toByteArray());
    }
}
