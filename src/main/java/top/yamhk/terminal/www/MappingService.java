package top.yamhk.terminal.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.yamhk.core.base.utils.XcodeUtils;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author: YingX
 * @Date: 2020/10/31 17:59
 */
@Component
public class MappingService {

    RequestMappingInfo requestMappingInfo;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public void addMapping() throws Exception {
        // 这里的path和请求方式都和我们在jar包中那个类定义的是一样的，不然照样会出现404,还有其他参数，这里就先不设置了
        requestMappingInfo = RequestMappingInfo
                .paths("/clazzPath")
                .methods(RequestMethod.GET)
                .build();

        String spec = MappingService.class.getResource("/") + "1.jar";
        spec = XcodeUtils.getCustomerClassPath();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(spec)});

        Class<?> myController = classLoader.loadClass("top.yamhk.terminal.web.MatrixTest.java");
        Object obj = myController.newInstance();
        // 这里就注册了我们的handlermapping，但是这里只能一个一个方法进行注册（而且不限制你重复注册，但是如果重复注册的话，请求的时候会报错）
        requestMappingHandlerMapping.registerMapping(requestMappingInfo, obj, myController.getDeclaredMethod("getOneName", String.class));
    }

    public void removeMapping() {
        requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
    }
}
