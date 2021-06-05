package top.yamhk.terminal.server4history.view.console;

public class HeartCreate {
    static Integer wide = 18;
    String flag_details1 = "*";
    String flag_details2 = " ";
    String res = "";

    public static void main(String[] args) {
        new HeartCreate().getHeart();
    }

    /*
     * others ←EVOL: y=1/x(x>0); x^2=y^2=r^2; y=|2X|; x=-3|sin(y)|(-PI<y<PI);
     */
    /* 上半部分 */
    String HighLevel(int wide) {
        String res_all = "";
        int i, j, k = 0, t = 0, m = 0, n = 0, count = 1;// i控制循环内高度，j控制每行前面空格，k控制输出的*数
        // t控制高层星号中间空格，m记录高层最底行的星数，以下逐行增4
        // n记录顶行空个数，以下逐行减4；count记录高层高度，与high无关

        m = (wide - 4) / 2;
        do {
            count++;
            m -= 4;
        } while (m > 4);
        // 区别对待奇偶宽度，奇数中间最小空1个，偶数最小空两个
        if ((wide - 4) % 2 == 0) {
            n = 2 + 4 * (count - 1);
            m--;
        } else {
            n = 1 + 4 * (count - 1);
        }

        for (i = 0; i < count; i++) {
            for (j = (count - i) * 2; j > 0; j--) {
                res_all += (flag_details2);
            }
            for (k = 0; k < m; k++) {
                res_all += (flag_details1);
            }
            for (t = 0; t < n; t++) {
                res_all += (flag_details2);
            }
            for (k = 0; k < m; k++) {
                res_all += (flag_details1);
            }
            m += 4;
            n -= 4;
            res_all += ("\n");
        }

        return res_all;
    }

    /* 下半部分 */
    String LowLevel(int wide) {
        String res_all = "";
        int i = 0, j = 0, k = 0;// i控制输出行，j控制输出每行前的空格，k控制输出*
        int high = 0, tmp = wide;
        do// 计算所需高度
        {
            high++;
            tmp -= 4;
        } while (tmp > 4);
        high += 1;
        for (i = 0; i < high; i++) {
            for (j = 0; j < 2 * i; j++) {
                res_all += (flag_details2);
            }
            for (k = wide - 4 * i; k > 0; k--) {
                res_all += (flag_details1);
            }
            res_all += ("\n");
        }
        return res_all;
    }

    private void getHeart() {
        this.res += this.HighLevel(wide);
        this.res += this.LowLevel(wide);
        System.out.println(res);
    }
}
