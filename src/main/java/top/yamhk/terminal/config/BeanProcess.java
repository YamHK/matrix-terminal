package top.yamhk.terminal.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;

/**
 * 打印所有的bean
 *
 * @Author: YamHK
 * @Date: 2020/9/22 21:58
 */
//@Component
public class BeanProcess implements BeanPostProcessor, BeanFactoryAware {
    private BeanFactory factory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("myAnnoService".equals(beanName)) {
            Annotation[] declaredAnnotations = bean.getClass().getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                System.out.println("hello world " + annotation.annotationType().getName());
            }
        }
        System.err.println("after init: [" + beanName + "]" + bean.getClass());
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // TODO Auto-generated method stub
        factory = beanFactory;
    }
}
