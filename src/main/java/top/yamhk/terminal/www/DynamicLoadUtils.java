package top.yamhk.terminal.www;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.yamhk.core.kernel.BeanUtils8Spring;

import java.lang.reflect.Method;

/**
 * @Author: YingX
 * @Date: 2020/10/31 18:11
 */
public class DynamicLoadUtils {

    public static void registerController(String controllerBeanName) throws Exception {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                BeanUtils8Spring.getBean("requestMappingHandlerMapping");

        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = BeanUtils8Spring.getBean(handler);
            if (controller == null) {
                return;
            }
            unregisterController(controllerBeanName);
            //注册Controller
            Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
                    getDeclaredMethod("detectHandlerMethods", Object.class);
            method.setAccessible(true);
            method.invoke(requestMappingHandlerMapping, handler);
        }
    }

    public static void unregisterController(String controllerBeanName) {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                BeanUtils8Spring.getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = BeanUtils8Spring.getBean(handler);
            if (controller == null) {
                return;
            }
            final Class<?> targetClass = controller.getClass();
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                @Override
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    try {
                        Method createMappingMethod = RequestMappingHandlerMapping.class.
                                getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                        createMappingMethod.setAccessible(true);
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                                createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                        if (requestMappingInfo != null) {
                            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }

    public static void addBean(String className, String serviceName, ApplicationContext app) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
        } catch (ClassNotFoundException e) {
            System.out.println(className + ",主动注册失败.");
        }
    }

    public static void addBean(Class clazz, String serviceName, ApplicationContext app) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
    }

    private static void registerBean(String beanName, AbstractBeanDefinition beanDefinition, ApplicationContext context) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}
