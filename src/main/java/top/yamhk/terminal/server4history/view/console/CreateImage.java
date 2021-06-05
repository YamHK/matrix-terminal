package top.yamhk.terminal.server4history.view.console;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Y_X_Y 2016年12月23日下午2:02:49
 */
public class CreateImage {
    public static void main(String[] args) throws Exception {
        int width = 300, height = 300;
        String s = "宋江,卢俊义,吴用,公孙胜,关胜,林冲,秦明,呼延灼,花荣,柴进";


        // 创建一个画布
        Font font = new Font("Serif", Font.BOLD, 10);
        // 获取画布的画笔
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        // 开始绘图
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(new Color(250, 250, 250));

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        g2.drawString(s, (int) x, (int) baseY);


        File file = new File("D:/image.jpg");
        ImageIO.write(bi, "jpg", file);
    }

    /**
     * 2016年12月23日下午2:10:38
     */
    void test02() {
        // 2.应用二：生成背景透明的图片
        // 首先jpg格式的图片，支持RGB，无法实现背景透明.png与gif支持RGB和alpha属性,可以生成透明图片

        int width = 400;
        int height = 300;

        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();

        // ---------- 增加下面的代码使得背景透明 -----------------
        image = g2d.getDeviceConfiguration().createCompatibleImage(width,
                height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // ---------- 背景透明代码结束 -----------------

        // 画图
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(null);
        // 释放对象
        g2d.dispose();
        // 保存文件
        try {
            ImageIO.write(image, "png", new File("c:/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 测试生成图片
    }

    public void test01() {
        int width = 100;
        int height = 100;
        String s = "你好";

        File file = new File("D:/image.jpg");

        Font font = new Font("Serif", Font.BOLD, 10);
        // 创建一个画布
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取画布的画笔
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        // 开始绘图
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(new Color(0, 0, 255));
        g2.fillRect(0, 0, 100, 10);
        g2.setPaint(new Color(253, 2, 0));
        g2.fillRect(0, 10, 100, 10);
        g2.setPaint(Color.red);

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        // 绘制字符串
        g2.drawString(s, (int) x, (int) baseY);

        try {
            // 将生成的图片保存为jpg格式的文件。ImageIO支持jpg、png、gif等格式
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            System.out.println("生成图片出错........");
            e.printStackTrace();
        }

    }
}
