package top.yamhk.terminal.server4test;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: YamHK
 * @Date: 2020/12/23 19:22
 */
public class WebTest {

    private static void testWeb() throws IOException {
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 65535; i++) {
            String command = "telnet 122.228.128.250 " + i;
            Process exec = Runtime.getRuntime().exec(command);
            InputStreamReader isr = new InputStreamReader(exec.getInputStream(), "GBK");
            BufferedReader br = new BufferedReader(isr);
            /*UTF-8,GBK */
            String strTem;
            StringBuilder strRes = new StringBuilder();
            while ((strTem = br.readLine()) != null) {
                strRes.append(strTem).append("\n");
            }
            if (StringUtils.isNotBlank(strRes.toString())) {
                System.out.println(command + ":" + strRes);
            }
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("cost:" + (t2 - t1));
    }
}
