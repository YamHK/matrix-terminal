package top.yamhk.terminal.server4history.view.console;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Y_X_Y 2016年12月23日上午11:45:41
 */
public class AsciiPic {

    public static void createAsciiPic(final String path) {
        String base = "@#&$%*o!;.", res;// 字符串由复杂到简单
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x, y, pixel, r, g, b, ind;
        float gray;
        for (y = 0; y < image.getHeight(); y += 4) {
            for (x = 0; x < image.getWidth(); x += 2) {
                pixel = image.getRGB(x, y);
                r = (pixel & 0xff0000) >> 16;
                g = (pixel & 0xff00) >> 8;
                b = pixel & 0xff;
                gray = 0.299f * r + 0.578f * g + 0.114f * b;
                ind = Math.round(gray * (base.length() + 1) / 255);
                res = ind >= base.length() ? " " : String.valueOf(base
                        .charAt(ind));
                System.out.println(res);
            }
        }
    }

    public static void main(final String[] args) {
        AsciiPic.createAsciiPic("D:\\TX.jpg");
    }
}
