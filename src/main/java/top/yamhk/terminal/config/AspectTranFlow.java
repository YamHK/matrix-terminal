package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.terminal.web.jpa.datamodel.base.TranFlowPo;

import java.lang.reflect.Method;

/**
 * @Author: YamHK
 * @Date: 2020/9/7 16:00
 */
//@Aspect
//@Configuration
@Slf4j
public class AspectTranFlow {
    /**
     * 当前请求
     */
    private final ThreadLocal<TranFlowPo> tranFlowPoThreadLocal = new ThreadLocal<>();

    /**
     * 切面
     */
    @Pointcut("execution(* top.yamhk.terminal.web.api..*.*(..))")
    public void tranFlow() {
        log.warn(">>>>>>>>>>>>>>>LogInit>>>Init");
        //do nothing
    }

    @Before("tranFlow()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TranFlowed tranFlowed = method.getAnnotation(TranFlowed.class);
        TranFlowPo tranFlowPo = new TranFlowPo();
        tranFlowPo.setReqTime(Dates.now());
        tranFlowPo.setRequestBody(JsonUtils.GSON.toJson(joinPoint.getArgs()));
        tranFlowPoThreadLocal.set(tranFlowPo);
    }


    @AfterReturning(pointcut = "tranFlow()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint) {
        TranFlowPo tranFlowPo = tranFlowPoThreadLocal.get();
        long duration = System.currentTimeMillis() - tranFlowPo.getReqTime().getTime();
        tranFlowPoThreadLocal.remove();

    }

    @AfterThrowing(pointcut = "tranFlow()", throwing = "exc")
    public void afterThrowing(JoinPoint joinPoint) {

    }
}
