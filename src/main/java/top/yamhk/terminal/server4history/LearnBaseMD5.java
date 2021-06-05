package top.yamhk.terminal.server4history;

import java.security.MessageDigest;

public final class LearnBaseMD5 {
    public static void main(String[] args) {
        String str = new LearnBaseMD5().getMD5("admin");
        System.out.println(str);
    }

    public String getMD5(String s) {
// 创建一个16进制数的数组
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            // 把传入的字符串转化成数组
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // 更新strTemp 数组
            mdTemp.update(strTemp);
            // 重新定义一个数组
            byte[] md = mdTemp.digest();
            // md数组的长度
            int j = md.length;
            // 定义一个字符数组 2倍长度
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                // 给byte0变量赋值
                byte byte0 = md[i];
                // 转换
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 返回转换后的字符串
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}