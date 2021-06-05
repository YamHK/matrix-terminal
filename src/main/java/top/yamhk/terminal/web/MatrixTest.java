package top.yamhk.terminal.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.LocalFile;
import top.yamhk.core.base.utils.CipherUtil;
import top.yamhk.core.base.utils.XcodeUtils;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.BeanUtils8Spring;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.web.infrastructure.RemoteFeign;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: YamHK
 * @Date: 2020/10/20 16:59
 */
@Slf4j
@RestController
@RequestMapping("/v1/test")
public class MatrixTest {


    /**
     * feign
     */
    @Autowired
    private RemoteFeign remoteFeign;

    private MatrixTest() {
    }

    private static MatrixTest getInstance() {
        return BeanUtils8Spring.getBean(MatrixTest.class);
    }

    /**
     * main
     *
     * @param args args
     */
    public static void main(String[] args) throws IOException {
//        CopyTest copyTest = new CopyTest();
//        copyTest.work();
//        new WebTest().testWeb();
//        work();
//        ################
//        XcodeUtils.getDefaultExecutor().execute(SubBioServer.getInstance());
    }

    private static void work() throws IOException {
        MatrixTest matrixTest = new MatrixTest();
        //file
        File file = new File("D:/App/~workspace/matrix/demo/src/main/java/top/yamhk/Demo.java");
        String defaultEncrypt = CipherUtil.defaultEncrypt(XcodeUtils.readFile(file));
        MatrixRequest request = MatrixRequest.buildWithClazz("").withName("Demo").withContent(defaultEncrypt);
        matrixTest.uploadWithMakeFile(request);
        // class
        Map<String, String> localFile = matrixTest.scan();
        String fileName = localFile.get("MatrixTest" + ".class");
        log.info("class path:" + fileName);
        file = new File(fileName);
        log.warn("createFile>{}>>>r:{},w:{},e:{}", fileName, file.canRead(), file.canWrite(), file.canExecute());
        defaultEncrypt = XcodeUtils.readFile(file);
        request = MatrixRequest.buildWithClazz(fileName).withName("MatrixTest").withContent(defaultEncrypt);
        matrixTest.upload(request);
    }

    @PostMapping
    private void doTest(@RequestBody DemoRequest request) {
        RemoteFeign.FeignRequest feignRequest =
                BeanCopyUtils.copyBySpring(request, RemoteFeign.FeignRequest.class);
        log.warn("1-request:{}", request);
        log.warn("2-request:{}", feignRequest);
        String result = remoteFeign.doPostTest(feignRequest);
        log.warn("result:{}", result);
    }

    @GetMapping("/clazzPath")
    public String getCustomerClassPath() throws IOException {
        return XcodeUtils.getCustomerClassPath();
    }

    @PostMapping("/scan")
    private Map<String, String> scan() throws IOException {
        String customerClassPath = XcodeUtils.getCustomerClassPath();
        String pathname = customerClassPath.substring(0, customerClassPath.length() - 5);
        LocalFile localFile = new LocalFile(pathname);
        log.warn("scan localFile directory:{}", localFile);
        List<LocalFile> javaFileList = XcodeUtils.dirScanFilesIntoList(localFile, true, Lists.newArrayList());
        //
        String pre = XcodeUtils.getPrefix(javaFileList);
        log.info("pre:{}", pre);
        //
        Map<String, String> result = Maps.newHashMap();
        AtomicInteger count = new AtomicInteger(1);
        javaFileList.forEach(e -> result.put(e.getName(), e.getAbsolutePath()));
        //
        return result;
    }

    @GetMapping("/clear")
    public String clear() throws IOException {
        LocalFile genFile = new LocalFile(XcodeUtils.getCustomerClassPath());
        List<LocalFile> javaFileList = XcodeUtils.dirScanFilesIntoList(genFile, false, Lists.newArrayList());
        javaFileList.forEach(e -> {
            if (e.isJavaFile()) {
                log.info(e.getAbsolutePath());
                e.delete();
            }
        });
        return "OK";
    }

    /**
     * test
     */
    @PostMapping("/uploadAndMake")
    public MatrixResponse uploadWithMakeFile(@RequestBody MatrixRequest request) throws IOException {
        //
        String fileName = (getCustomerClassPath() + request.getName() + ".java");
        //
        String defaultDecrypt = CipherUtil.defaultDecrypt(request.getContent());
        boolean createFile = createFile(fileName, defaultDecrypt);
        Boolean makeJavaFile = makeFile(fileName);
        log.warn("create:{},make:{}", createFile, makeJavaFile);
        return MatrixResponse.ok().withData(defaultDecrypt);
    }

    /**
     * test
     */
    @PostMapping("/upload")
    public MatrixResponse upload(@RequestBody MatrixRequest request) throws IOException {
        //
        String fileName = (getCustomerClassPath() + request.getName() + ".class");
        //
        Files.copy(new File(request.getClazz()).toPath(), new File(fileName).toPath());
        return MatrixResponse.ok();
    }

    @PostMapping("/analysis")
    private void analysis(@RequestBody MatrixRequest request) {
        Class<?> clazzFind = XcodeUtils.clazzFind(request.getClazz());
        if (clazzFind == null) {
            return;
        }
        Method[] declaredMethods = clazzFind.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            log.warn("methods1:dec-{}/{}", i, declaredMethods[i]);
        }
    }

    /**
     * run
     */
    @PostMapping("/run")
    public void run() {
        Class<?> clazzFind = XcodeUtils.clazzFind("top.yamhk.Demo");
        Method[] declaredMethods = clazzFind.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            log.warn("methods1:dec-{}/{}", i, declaredMethods[i]);
        }
//        log.warn("invoke...invokeMain");
//        invokeMain(clazzFind);
//        log.warn("invoke...invokeWork");
//        invokeWork(clazzFind);
    }


    private void invokeObject(Object genFile) throws IllegalAccessException, InvocationTargetException {
        Class<?> fileClass = genFile.getClass();
        Method[] declaredMethods = fileClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            if (declaredMethods[i].getParameterCount() < 1 && Modifier.isPublic(declaredMethods[i].getModifiers())) {
                log.warn("method:{}/invoke:{}", declaredMethods[i], declaredMethods[i].invoke(genFile));
            }
        }
    }


    private void invokeMain(Class<?> clazzFind) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method method;
        //main
        Class<?>[] param = new Class[]{String[].class};
        method = clazzFind.getDeclaredMethod("main", param);
        //args-invoke
        Object[] args = new Object[1];
        args[0] = new String[]{"Hello", "World", "!"};
        method.invoke(null, args);
    }

    private void invokeWork(Class<?> clazzFind) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method method;
        method = clazzFind.getDeclaredMethod("work");
        method.setAccessible(true);
        //args-invoke
        method.invoke(clazzFind.getDeclaredConstructor().newInstance());
    }

    /**
     * createFile
     *
     * @return boolean
     */
    public boolean createFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        //
        log.warn("createFile>{}>>>r:{},w:{},e:{}", fileName, file.canRead(), file.canWrite(), file.canExecute());

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));
        dos.writeBytes(content);
        dos.close();
        return true;
    }

    /**
     * makeFile
     *
     * @param fileName fileName
     * @return Boolean
     */
    public Boolean makeFile(String fileName) {
        try {
            Runtime rt = Runtime.getRuntime();
            log.warn("javac>{}", fileName);
            String command = "javac " + fileName;
            log.warn("command:{}", command);
            Process ps = rt.exec(command);
            ps.waitFor();
            byte[] out = new byte[1024];
            DataInputStream dos = new DataInputStream(ps.getInputStream());
            dos.read(out);
            String s = new String(out);
            log.error("make result:>>{}<<", s);
            return s.indexOf("Exception") <= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Data
    private static class MatrixRequest {
        private String clazz;
        private String name;
        private String content;
        private String[] args;
        private String parameter;

        private MatrixRequest() {
        }

        public static MatrixRequest buildWithClazz(String defaultEncrypt) {
            MatrixRequest request = new MatrixRequest();
            request.setClazz(defaultEncrypt);
            return request;
        }

        public MatrixRequest withContent(String content) {
            this.setContent(content);
            return this;
        }

        public MatrixRequest withName(String name) {
            this.setName(name);
            return this;
        }
    }
}
