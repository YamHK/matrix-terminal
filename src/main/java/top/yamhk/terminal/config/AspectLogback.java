package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.boot.CacheUtils;
import top.yamhk.terminal.common.CommonConstant;

/**
 * @Author:YamHK
 * @Date: 2019/4/14 19:03
 */
@Aspect
@Configuration
@Slf4j
public class AspectLogback {

    public AspectLogback() {
        log.debug("AspectLogback 初始化");
    }

    /**
     * 切面
     */
//    @Pointcut("execution(public * ch.qos.logback.classic..*.*(..))")
    @Pointcut("execution(public * org.slf4j..*.*(..))")
//    @Pointcut("execution(public * top.yamhk.terminal.web.job..*.*(..))")
    public void logback() {
        log.warn(CommonConstant.LOG_START + "logInit");
        //do nothing
    }

    @Before("logback()")
    public void doBefore(JoinPoint joinPoint) {
        log.warn("{}-s-doBefore-,{}", CommonConstant.LOG_DOING, joinPoint);
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     *
     * @param proceedingJoinPoint proceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around("logback()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.warn("{}-s-doAround-", CommonConstant.LOG_DOING);
        Object o = proceedingJoinPoint.proceed();
        log.warn("{}-e-doAround-", CommonConstant.LOG_DOING);
        return o;
    }

    /**
     * 后置异常通知
     *
     * @param joinPoint joinPoint
     */
    @AfterThrowing("logback()")
    public void doAfterThrows(JoinPoint joinPoint) {
        log.warn("{}-s-doAfterThrows-{}", CommonConstant.LOG_DOING, joinPoint);
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     *
     * @param joinPoint joinPoint
     */
    @After("logback()")
    public void doAfter(JoinPoint joinPoint) {
        log.warn("{}-s-doAfter-{}", CommonConstant.LOG_DOING, joinPoint);
    }

    /**
     * doAfterReturning
     *
     * @param ret ret
     */
    @AfterReturning(returning = "ret", pointcut = "logback()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.warn("{}-s-doAfterReturning-", CommonConstant.LOG_DOING);
        //缓存接口响应结果
        if (ret != null) {
            CacheUtils.RESPONSE_HASH.put(String.valueOf(ret.hashCode()), JsonUtils.GSON.toJson(ret));
            log.warn(CommonConstant.LOG_END + "-m-doAfterReturning:[{}]", ret.hashCode());
        }
        log.warn("{}-e-doAfterReturning-", CommonConstant.LOG_END);
    }
}
