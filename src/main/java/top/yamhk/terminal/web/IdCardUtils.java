package top.yamhk.terminal.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import top.yamhk.core.base.CommonUtils;
import top.yamhk.core.base.utils.CommonUtilsImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 身份证号码信息分析
 */
@Slf4j
@Component
public class IdCardUtils {
    /**
     * 星座起始日期对应月份
     * 金牛起始/牧羊截至-星座/百科-c_d[4]=20/+21
     */
    final Integer[] c_d = {22, 20, 19, 21, +21, 21, 22, 23, 23, 23, 24, 23};
    /**
     * 星座
     */
    final String[] c_n = "摩羯,水瓶,双鱼,白羊,金牛,双子,巨蟹,狮子,处女,天秤,天蝎,射手".split(",");
    /**
     * 属相
     */
    final String[] v_animal = "鼠,牛,虎,兔,龙,蛇,马,羊,猴,鸡,狗,猪".split(",");
    /**
     * 性别.女♀男♂ ~"♀", "♂"
     */
    final String[] v_sex = {"F", "M"};
    final String sep = ".";
    /* 其他 */

    CommonUtils ui = CommonUtilsImpl.getInstance();
    String str_temp;
    Set<String> Message_Unknow = new HashSet<String>();

    @Autowired(required = false)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /*
     * 甲、乙、丙、丁、戊、己、庚、辛、壬、癸 . 子、丑、寅、卯、辰、巳、午、未、申、酉、戌、亥 .
     * 正、二、三、四、五、六、七、八、九、十、十一月、腊月
     */

    /**
     * tools-数字.左补位.前置零
     *
     * @param sourceData   sourceData
     * @param formatLength formatLength
     * @return String
     */
    static String frontCompZero(int sourceData, int formatLength) {
        // 0 代表前面补充0,4 代表长度为4,d 代表参数为正数型
        return String.format("%0" + formatLength + "d", sourceData);
    }

    /* Main函数 */
    public static void main(String[] args) throws Exception {
        /* 实例分析器 */
        IdCardUtils utils = new IdCardUtils();
        String sfz_zqj, res, birth;
        //utils.awork("wz");
        System.out.println(utils.analyse("332601196903144326"));

        for (int i = 1; i < 13; i++) {
            for (int j = 1; j < 32; j++) {
                birth = frontCompZero(i, 2) + frontCompZero(j, 2);
                sfz_zqj = "3302261995" + birth + "6124";
                res = utils.analyse(sfz_zqj);
                System.out.println(res + "," + birth);
            }
        }
        utils.findUnknowMessage();
    }

    /**
     * 十二星座 -百度百科
     *
     * @param year  year
     * @param month month
     * @param day   day
     * @return String
     */
    String constellationAnalyse(int year, int month, int day) {
        // 日期归属月份
        int cMonth = (day > c_d[month % 12]) ? month : (month - 1);
        cMonth = (cMonth == 0) ? cMonth + 12 : cMonth;
        return day > c_d[month % 12] ? c_n[month % 12] : c_n[(month - 1) % 12];
    }

    /**
     * 属相-公元1000年.鼠年
     *
     * @param year year
     * @return String
     */
    String animalAnalyse(Integer year) {
        return v_animal[(Math.abs(year - 1000)) % 12];
    }

    /**
     * 年龄
     *
     * @param year
     * @param m
     * @param d
     * @return
     */
    String ageAnalyse(int year, int m, int d) {
        int ny = Integer.valueOf(ui.backDate("yyyy"));
        int nm = Integer.valueOf(ui.backDate("MM"));
        int nd = Integer.valueOf(ui.backDate("dd"));
        return (ny - year) + "." + ((nm < m || nm == m && nd < d) ? "-" : "+");
    }

    /* 身份证验证 */
    @SuppressWarnings("unused")
    private boolean endAnalyse(String IC) {
        /* 身份证校验相关参数 */
        int[] xishu = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        String[] verifies = "1,0,X,9,8,7,6,5,4,3,2".split(",");
        boolean judgeResult = true;
        String msg = "";
        /* 位数校验 */
        if (IC.length() != 18 && IC.length() != 15) {
            judgeResult = false;
            msg += "无效位数";
        }
        /* 老身份证转换 */
        /* 尾号验证 */
        Integer sum = 0;
        for (int i = 0; i < 17; i++) {
            if (IC.charAt(i) > '9' || IC.charAt(i) < '0') {
                judgeResult = false;
                msg += "含非法字符";
                break;
            }
            sum += Integer.parseInt(IC.charAt(i) + "") * xishu[i];
        }
        judgeResult = verifies[sum % 11].equals(IC.charAt(17) + "") && judgeResult;
        return judgeResult;
    }

    /* IC_CARD分析入口 */
    public String analyse(String IC) throws Exception {
        if (null == IC || IC.length() < 15) {
            return "sfz wuxiao";
        }
        Integer y, m, d;
        String s1 = IC.substring(0, 6);
        String res = "";
        String sql = "select ic_name from cm_lib_code_address where 1=1 and ic_no=:icNo";
        Map<String, String> param = new HashMap<>();
        param.put("icNo", s1);
        List<IcCardResponse> resutlt = namedParameterJdbcTemplate.query(sql, param, (x, row) -> {
            IcCardResponse response = new IcCardResponse();
            response.setIcName(x.getString(1));
            return response;
        });
        if (resutlt.size() != 1) {
            log.error("---sfz[{}]号码前六位无效或者数据库数据缺失---完成后统一检索检索-->", IC);
            Message_Unknow.add(IC.substring(0, 6));
            return "end with error";
        }
        // 性别
        if (this.endAnalyse(IC)) {
            y = Integer.valueOf(IC.substring(6, 10));
            m = Integer.valueOf(IC.substring(10, 12));
            d = Integer.valueOf(IC.substring(12, 14));
            Integer sex = Integer.valueOf(IC.charAt(16) + "");
            // 女偶男奇
            res += v_sex[sex % 2] + sep;
            // 生肖
            res += this.animalAnalyse(y) + sep;
            // 年龄
            res += this.ageAnalyse(y, m, d) + sep;
            // 星座
            res += this.constellationAnalyse(y, m, d) + sep;
        } else {
            res += "无效";
        }
        // 区域编码
        res += resutlt.get(0).getIcName();
        return res;
    }

    boolean validityIC() {
        return true;
    }

    /* 检索未知IC前缀的信息 */
    void findUnknowMessage() throws Exception {
        Iterator<String> it = Message_Unknow.iterator();
        int count = 1;
        while (it.hasNext()) {
            String IC = it.next();
            // ?表示参数，#表示锚点
            String url = "http://www.baidu.com/#wd=" + IC.substring(0, 6);
            java.net.URI uri = new java.net.URI(url);
            System.out.println(IC.substring(0, 6));
            System.out.println("searching...:...");
            java.awt.Desktop.getDesktop().browse(uri);

            Thread.sleep(1000 * 125 * count);
            Runtime.getRuntime().exec(
                    "rundll32 url.dll,FileProtocolHandler" + url);
            // Runtime.getRuntime().exec("cmd /c start "+url);
        }
    }

    /* 星座清单 */
    void constellationAnalyseList() {
        str_temp = "";
        for (int i = 1; i <= 12; i++) {
            str_temp += c_n[i % 12] + ",";
            // 起始时间
            str_temp += frontCompZero(i, 2) + "/";
            str_temp += c_d[i % 12] + "-";
            // 结束时间
            str_temp += frontCompZero(i + 1 == 12 ? 12 : (i + 1) % 12, 2) + "/";
            str_temp += c_d[(i + 1) % 12] - 1 + ".\n";
        }
        System.out.println(str_temp);
    }

    void awork(String msg) throws Exception {
        IdCardUtils a = new IdCardUtils();
        String sql = "select a.phone,a.ic,a.user_name,b.v_long from cm_add_rela_short a,cm_add_rela_long b where a.id=b.id";
        sql += " and b.v_short like '%:short%'";
        sql += " ORDER BY a.user_name,a.ic;";
        Map<String, String> param = new HashMap<>();
        param.put("short", msg);
        AtomicReference<Integer> count = new AtomicReference<>(1);

        List<FriendsResponse> resutlt = namedParameterJdbcTemplate.query(sql, param, (x, row) -> {
            FriendsResponse response = new FriendsResponse();
            response.setId(Long.valueOf(frontCompZero(count.getAndSet(count.get() + 1), 9)));
            response.setPhone(x.getString(1));
            response.setIc(x.getString(2));
            response.setUsername(x.getString(3));
            response.setVLong(x.getString(4));
            return response;
        });
        resutlt.forEach(e -> {
            log.warn("查到对象->", e);
        });
        /* 星座分布数组 */
        a.constellationAnalyseList();
        /* 查询未录入编码 */
        a.findUnknowMessage();

    }
}
