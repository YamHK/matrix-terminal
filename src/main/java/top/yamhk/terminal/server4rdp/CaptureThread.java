package top.yamhk.terminal.server4rdp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author yingx
 */
public class CaptureThread extends Thread {
    private final DataOutputStream dataOutputStream;

    private final Toolkit tk;
    private final Dimension dm;
    private final Rectangle rec;
    private final Robot robot;

    public CaptureThread(DataOutputStream dataOutputStream) throws AWTException {
        this.dataOutputStream = dataOutputStream;
        tk = Toolkit.getDefaultToolkit();
        dm = tk.getScreenSize();
        rec = new Rectangle(0, 0, (int) dm.getWidth(), (int) dm.getHeight());
        robot = new Robot();
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = createCature();
            try {
                dataOutputStream.writeInt(data.length);
                dataOutputStream.write(data);
                dataOutputStream.flush();
                try {
                    Thread.sleep(1000 / 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private byte[] createCature() {
        BufferedImage bimage = robot.createScreenCapture(rec);
        ByteArrayOutputStream byout = new ByteArrayOutputStream();
        try {
            ImageIO.write(bimage, "jpg", byout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byout.toByteArray();
    }

}
