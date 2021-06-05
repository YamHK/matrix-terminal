package top.yamhk.terminal.demo;

import lombok.extern.slf4j.Slf4j;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;

import java.io.File;

/**
 * @Author: YamHK
 * @Date: 2021/5/9 1:35
 */
@Slf4j
public class JavaRunJs {
    public static void main(String[] args) throws Exception {
        File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        String author = directory.getAbsolutePath();
        log.info("{}", courseFile);
        log.info("{}", author);
        String fileName = directory.getAbsolutePath() + "/matrix-terminal-auction/src/main/resources/awsc.js";
        //mock
        Context cx = Context.enter();
        Global scope = new Global(cx);
        cx.setOptimizationLevel(-1);
        cx.setLanguageVersion(Context.VERSION_1_7);
        Main.processFile(cx, scope, fileName);
        System.out.println((String) cx.evaluateString(scope, "return('Its WORKING!')", "js", 1, null));
        //"{\"appkey\":\"FFFF0N0N000000009D7E\",\"scene\":\"nc_login_h5\",\"renderTo\":\"nc\"}"
    }
}
