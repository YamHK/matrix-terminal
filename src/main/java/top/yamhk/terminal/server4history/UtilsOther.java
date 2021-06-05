package top.yamhk.terminal.server4history;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Y_X_Y 2016年12月15日下午4:54:15
 */
public class UtilsOther {
    final static String COLDE = "how I would die.";

    /**
     * 创建文件 写内容
     */
    public static void createFile(String fileName, String fileContent) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(fileContent);
        out.close();
    }

    public static Object getParentValue(Class<? extends Object> cls, Object obj)
            throws Exception {
        String className = cls.getSimpleName();
        Object parentId = cls.newInstance();
        Field[] fields = null;
        try {
            fields = cls.newInstance().getClass().getDeclaredFields();
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (fields == null) {
            return null;
        } else {
            String[] attrName = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                attrName[i] = fields[i].getName();
            }
            for (int j = 0; j < attrName.length; j++) {
                Field field;
                try {
                    field = obj.getClass().getDeclaredField(attrName[j]);
                    field.setAccessible(true);

                    Class<?> typeName = field.getType();
                    Class<?> clsRelt = Class.forName(typeName.getName());
                    String tmpName = clsRelt.getSimpleName();
                    if (tmpName.equals(className)) {
                        Object reltObj = field.get(obj);
                        parentId = reltObj;
                        break;

                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }

        }
        return parentId;
    }

    public static Map<String, Object> beanToMap(Object entity) {
        Map<String, Object> parameter = new HashMap<>(5);
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                Object o = null;
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + fieldName.substring(1);
                Method getMethod;
                try {
                    getMethod = entity.getClass().getMethod(getMethodName);
                    o = getMethod.invoke(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (o != null) {
                    String clsName = o.getClass().getSimpleName();
                    if (clsName.toLowerCase().indexOf("javassist") != -1) {
                        parameter.put(fieldName, null);
                    } else {
                        parameter.put(fieldName, o);
                    }
                } else {
                    parameter.put(fieldName, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameter;
    }

    /**
     * 将经纬度点集合转换为GeneralPath对象
     */
    public static GeneralPath genGeneralPath(ArrayList<Point2D.Double> points) {
        GeneralPath path = new GeneralPath();
        if (points.size() < 3) {
            return null;
        }
        Point2D.Double first = points.get(0);
        path.moveTo(first.x, first.y);

        for (Point2D.Double d : points) {
            path.lineTo(d.x, d.y);
        }

        path.moveTo(first.x, first.y);
        path.closePath();

        return path;
    }

    /**
     * 将json 数组转换为Map 对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean checkPoint(String gprs, String area) throws Exception {
        // boolean bln = false;
        String[] list = area.split(";");
        String[] arr = gprs.split(",");
        ArrayList<Point2D.Double> points = new ArrayList();
        for (String s : list) {
            String[] tmp = s.split(",");
            Point2D.Double point = new Point2D.Double(
                    Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
            points.add(point);
        }
        Point2D.Double curPoint = new Point2D.Double(
                Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
        GeneralPath gp = genGeneralPath(points);
        // bln = checkPointPolygon(curPoint, points);
        return gp.contains(curPoint);
        // return bln;
    }

    public static boolean checkList(List<?> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String createTenPayUrl(String url) {

        String sHtmlText = "<form id=\"ccbsubmit\" userMsg=\"ccbsubmit\" action=\""
                + url
                + "\" method=\"get\"><input type=\"submit\" value=\"财付通确认付款\"></form><script>document.forms['ccbsubmit'].submit();</script>";
        return sHtmlText;
    }

    public static Map<String, Integer> resume(Map<String, Integer> map)
            throws Exception {
        Map<String, Integer> curMap = map;
        Object tmp = curMap.get("children");
        ArrayList<?> obj = (ArrayList<?>) tmp;
        if (obj != null && obj.size() > 0) {
            for (Object tmpObj : obj) {
                @SuppressWarnings("unchecked")
                Map<String, Integer> tmpMap = (Map<String, Integer>) tmpObj;

                if (tmpMap.get("children") != null
                        && !"".equals(tmpMap.get("children"))) {
                    tmpMap = resume(tmpMap);
                }

                Integer numDept = tmpMap.get("numDept");
                Integer numPq = tmpMap.get("numPq");
                Integer numSq = tmpMap.get("numSq");

                numDept += Integer.parseInt(curMap.get("numDept").toString());
                numPq += Integer.parseInt(curMap.get("numPq").toString());
                numSq += Integer.parseInt(curMap.get("numSq").toString());
                curMap.put("numDept", numDept);
                curMap.put("numPq", numPq);
                curMap.put("numSq", numSq);
            }
        }
        return curMap;

    }

    public static boolean copyUpFile(String upfileFileName, File upfile,
                                     String newFileName) {
        boolean bln = false;
        FileInputStream fis;
        try {
            fis = new FileInputStream(upfile);
            FileOutputStream fos = new FileOutputStream(newFileName);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fis.close();
            fos.close();
            bln = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bln;

    }

    public static Integer[] arrStrToInt(String[] strarr) {
        if (strarr == null) {
            return null;
        }
        Integer[] intarr = new Integer[strarr.length];
        for (int i = 0; i < strarr.length; i++) {
            intarr[i] = Integer.parseInt(strarr[i]);
        }
        return intarr;
    }

    public static boolean checkPointPolygon(Point2D.Double point,
                                            List<Point2D.Double> polygon) throws Exception {
        Polygon p = new Polygon();
        // java.awt.geom.GeneralPath
        final int times = 10;
        for (Point2D.Double d : polygon) {
            int x = (int) (d.x * times);
            int y = (int) (d.y * times);
            p.addPoint(x, y);
        }
        int x = (int) point.x * times;
        int y = (int) point.y * times;
        // BufferedImage image = new BufferedImage(600, 600,
        // BufferedImage.TYPE_INT_RGB);
        // Graphics graphics = image.getGraphics();
        // graphics.setColor(Color.WHITE);
        // graphics.fillRect(0, 0, 600, 600);
        // graphics.setColor(Color.BLACK);
        // graphics.drawPolygon(p);
        // graphics.dispose();
        // File file1 = new File("d:\\image1.jpg");
        // ImageIO.write(image, "jpg", file1);
        return p.contains(x, y);
    }

    /**
     * 格式数据化，保留2位小数
     */
    public static String fmtStringToDouble(String num) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Double.parseDouble(num));
    }

    /**
     * 字符串转换成时间格式
     */
    public static Date fmtStringToDate(String time) {
        SimpleDateFormat myFmt;
        Date stime = null;
        if (null == time || "".equals(time.trim())) {
            time = "10010101";
            myFmt = new SimpleDateFormat("yyyyMMdd");
        } else if (time != null && time.length() == 7) {
            myFmt = new SimpleDateFormat("yyyy-MM");
        } else if (time != null && time.length() == 10) {
            myFmt = new SimpleDateFormat("yyyy-MM-dd");
        } else if (time != null && time.length() == 17) {
            myFmt = new SimpleDateFormat("yyyy-MM-dd hhmmss");
        } else if (time != null && time.length() == 19) {
            myFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            time = "10010101";
            myFmt = new SimpleDateFormat("yyyyMMdd");
        }
        try {
            stime = (Date) myFmt.parseObject(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stime;
    }

    public static String backDate(String format) {
        if (null == format || format.length() < 2) {
            format = "yyyyMMdd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 上个月一号
     */
    public static Date backMonthLastBegin(Date date) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        } catch (Exception e) {
            return null;
        }
        return c.getTime();
    }

    /**
     * 当天23:59:59
     */
    public static Date backDateThisEnd(Date stime) {
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = myFmt1.format(stime) + " 23:59:59";
        try {
            stime = (Date) myFmt2.parseObject(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stime;
    }

    /* 判断对象一致 */
    private static boolean isObjEqual(Object obj1, Object obj2) {
        boolean bln = true;
        if (null == obj1 && null == obj2) {
            return bln;
        }
        Class<? extends Object> cls = obj1.getClass();
        Field[] fields = null;
        try {
            fields = cls.newInstance().getClass().getDeclaredFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null != fields && null != obj1 && null != obj2
                && obj1.toString().equals(obj2.toString());
    }

    /**
     * 得到子节点列表
     */
    private static ArrayList<Object> getChildList(ArrayList<?> objs, Object obj)
            throws Exception {
        ArrayList<Object> child = new ArrayList<Object>();
        Class<? extends Object> cls = obj.getClass();
        for (Object dep : objs) {
            Object tmpParent = null;
            tmpParent = null == getParentValue(cls, dep) ? cls.newInstance()
                    : getParentValue(cls, dep);
            if (tmpParent != null && isObjEqual(obj, tmpParent)) {
                child.add(dep);
            }
        }
        return child;
    }

    public static List<Object> getChilds(List<Object> allObjs, Object obj) {
        List<Object> child = null;
        try {
            child = getChildList((ArrayList<?>) allObjs, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Object> result = new ArrayList<Object>();

        result.addAll(child);
        for (Object dep : child) {
            result.addAll(getChilds(allObjs, dep));
        }
        return result;
    }

    /**
     * 递归获取实体信息.check.check.check
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Map<String, Serializable>> recursion(
            ArrayList<?> list, Object pid) {
        /* 获取父节点为PID的所有实体 */
        ArrayList<Object> vList = null;
        try {
            vList = getChildList(list, pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Map<String, Serializable>> rList = new ArrayList<Map<String, Serializable>>(); // 返回值
        @SuppressWarnings("unused")
        Object mp = null;
        if (vList == null || vList.isEmpty() || vList.size() == 0) {
            return new ArrayList<Map<String, Serializable>>(); // 当list下无子节点时返回空数组
        } else {
            for (Object ab : vList) {
                ArrayList<Object> children = null; // 子节点数组
                try {
                    children = getChildList(list, ab);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, Serializable> pDic = new HashMap<>(5); // 字节点信息
                Map<?, ?> tmpMap = beanToMap(ab);
                pDic.putAll((Map<? extends String, ? extends Serializable>) tmpMap);
                mp = pDic.put("uiProvider", "col");
                mp = pDic.put("cls", "master-task");
                mp = pDic.put("iconCls", "task-folder");
                mp = pDic.put("children", children);
                if (children == null || children.isEmpty()
                        || children.size() == 0) {
                    mp = pDic.put("leaf", true);
                    mp = pDic.remove("children");
                }
                if (!rList.contains(pDic)) {
                    rList.add(pDic);
                }
                mp = pDic.put("children", recursion(list, ab));
            }
        }
        return rList;
    }

    /* 判断实体类 */
    public static boolean isClassByException(String clsName) {
        boolean bln = false;
        try {
            Class.forName(clsName);
            bln = true;
        } catch (ClassNotFoundException e) {
            // cui.xLog("非实体类!");
        }
        return bln;
    }

    /**
     * 时间差值X分钟内
     */
    public static boolean isTimeMin(Date stime, Date etime, int xMinute) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(stime);
        ca2.setTime(etime);
        long distanceMin = ca1.getTimeInMillis() - ca2.getTimeInMillis();
        return (Math.abs(distanceMin) / 1000 <= xMinute * 60);
    }

    /**
     * 限制位数截取字符串.汉字
     */
    public static String getSubstring(String s, int length) {
        byte[] bytes = s.getBytes();
        /* i表示当前的字节数,要截取的字节数，从第3个字节开始 */
        int n = 0, i = 2;
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; // 在UCS2第二个字节时n加1
            } else {// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }// 如果i为奇数时，处理成偶数
        if (i % 2 == 1) {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0) {
                i = i - 1;
                // 该UCS2字符是字母或数字，则保留该字符
            } else {
                i = i + 1;
            }
        }
        return new String(bytes, 0, i) + (bytes.length > i ? "..." : "");
    }

    /**
     * 截取字符串获取链接
     */
    public static String getSubUrl(String url) {
        if (null == url || "".equals(url.trim())) {
            return "";
        }
        int beg, com, end;
        beg = url.indexOf("src=\"/");
        com = url.indexOf(".");
        if (beg == -1 || com == -1) {
            return "";
        }
        end = url.substring(com).indexOf("\"");
        url = url.substring(beg, com + end).replaceAll("src=\"", "").trim();
        return url;
    }

    /* md5加密 */
    public static String md5(String Str) {
        MessageDigest mdTemp = null;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mdTemp.update(Str.getBytes());
        byte[] digesta = mdTemp.digest();
        //
        String hs = "", stmp = "";
        for (int n = 0; n < digesta.length; n++) {
            stmp = (Integer.toHexString(digesta[n] & 0XFF));
            hs += (stmp.length() == 1 ? ("0" + stmp) : stmp);
        }
        return hs;
    }

    /* MD5密文 */
    public static String md52(String Str) {
        MessageDigest mdTemp = null;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mdTemp.update(Str.getBytes());// 更新strTemp 数组
        byte[] digesta = mdTemp.digest(); // 重新定义一个数组
        // /* 创建一个16进制数的数组 */
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        int k, i, j = digesta.length; // md数组的长度
        char[] str = new char[j * 2]; // 定义一个字符数组 2倍长度
        for (i = 0, k = 0; i < j; i++) {
            byte byte0 = digesta[i]; // 给byte0变量赋值
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 转换
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str); // 返回转换后的字符串
    }

    /**
     * 数据加密.用0xaa对字节进行异或操作。返回改编后的数组。
     */
    public static byte[] getChangedString(String str) {
        byte[] unchanged = null, result = null;
        int i, temp;
        unchanged = str.getBytes(StandardCharsets.UTF_8);
        if (null != unchanged) {
            result = new byte[unchanged.length];
            for (i = 0; i < unchanged.length; i++) {
                temp = unchanged[i] ^ 0xaa;// 先转换成了整型，再操作的。
                result[i] = (byte) (temp & 0xff);// 把整型，再换成byte型。
            }
        }
        return result;
    }

    /* 字节码 */
    public static String getBytes(String str) {
        str = str.length() < 1 ? COLDE : str;
        String res = "";
        byte[] b = str.getBytes();
        for (byte i : b) {
            res += i + ".";
        }
        return res;
    }

    /**
     * 2016年12月20日下午12:01:01
     */
    public static int getChange2T10(String str2) {
        int val = 1, res = 0, i;
        for (i = str2.length() - 1; i >= 0; i--) {
            res += Integer.parseInt(str2.charAt(i) + "") * val;
            val *= 2;
        }
        return res;
    }

    /**
     * 复制字符串
     */
    @SuppressWarnings("unused")
    private String getMethodName(String fildeName) {
        int len = fildeName.length();
        char[] chars = new char[len];
        fildeName.getChars(0, len, chars, 0);
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * windows文件路径格式转换.左斜杠,右斜杠
     */
    public String backChangedPath(String source) {
        source = source.replace("\\", "/");
        return source;
    }

    /**
     * 2016年12月20日下午4:07:24
     */
    void jmByX(String str) {
        String strBegin = "I'd never given much thought to how I would die.";
        String[] s = strBegin.split(" ");
        // Integer a[]=new Integer[s.length];
        System.out.println(strBegin);
        String strRes;
        for (int i = 0; i < strBegin.length(); i++) {
            strRes = "";
            if (strBegin.charAt(i) < 'A') {
                // strRes += strBegin.charAt(i);
                strRes += strBegin.charAt(i) - 'A';
            } else {
                strRes += "+";
                if (strBegin.charAt(i) - 'A' < 10) {
                    strRes += "0";
                }
                strRes += strBegin.charAt(i) - 'A';
            }
            System.out.print(strRes);
        }
        System.out.println();
        System.out.println(strBegin.toUpperCase());
        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i]);
        }

    }
}
