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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.yamhk.core.base.CommonConstant;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.boot.CacheUtils;
import top.yamhk.terminal.web.Login;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Author:YamHK
 * @Date: 2019/4/14 19:03
 */
@Slf4j
@Aspect
@Configuration
public class AspectLog {

    /**
     * 表达式
     */
    private static final Pattern SYSTEM_PATTERN = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
    /**
     * 当前请求
     */
    private final ThreadLocal<Login> tranFlowPoThreadLocal = new ThreadLocal<>();

    /**
     * construct
     */
    public AspectLog() {
        log.warn("[init]-AspectLog");
    }

    /**
     * log
     */
    @Pointcut("execution(public * top.yamhk.terminal.web.api..*.*(..))")
    public void log() {
        log.warn(CommonConstant.LOG_START + "logInit");
    }

    @Before("log()")
    public void deBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.warn(CommonConstant.LOG_START + "-s-deBefore");
        // 记录下请求内容
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            log.warn(paraName + ":" + request.getParameter(paraName));
        }
        log.warn("URL:{}", request.getRequestURL());
        log.warn("class:{},method:{} "
                , joinPoint.getSignature().getDeclaringTypeName()
                , joinPoint.getSignature().getName());
        log.warn("request method:{}", request.getMethod());
        log.warn("IP:{} ", request.getRemoteAddr());
        log.warn("cookie:{}", request.getHeader("Cookie"));
        log.warn("version:{}", request.getHeader("version"));
        log.warn("param:{}", request.getQueryString());
        log.warn("ARGS:{}", joinPoint.getArgs());

        //记录访问信息
        doVisitLog(request);
        log.warn(CommonConstant.LOG_DOING + "-e-deBefore");
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     *
     * @param proceedingJoinPoint proceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.warn(CommonConstant.LOG_DOING + "-s-doAround");
        Object o = proceedingJoinPoint.proceed();
        log.warn(CommonConstant.LOG_DOING + "-e-doAround");
        return o;
    }

    /**
     * 后置异常通知
     *
     * @param joinPoint joinPoint
     */
    @AfterThrowing("log()")
    public void doAfterThrows(JoinPoint joinPoint) {
        log.warn(CommonConstant.LOG_DOING + "-s-doAfterThrows" + "方法异常时执行.{}", joinPoint);
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     *
     * @param joinPoint joinPoint
     */
    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        log.warn(CommonConstant.LOG_DOING + "-s-doAfter:{}", joinPoint);
    }

    /**
     * doAfterReturning
     *
     * @param ret ret
     */
    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.warn(CommonConstant.LOG_DOING + "-s-doAfterReturning:");
        Login login = tranFlowPoThreadLocal.get();
        //aboutNormalVisitLogJpaRepo.save(login);
        //缓存接口响应结果
        if (ret != null) {
            CacheUtils.RESPONSE_HASH.put(String.valueOf(ret.hashCode()), JsonUtils.GSON.toJson(ret));
            log.warn(CommonConstant.LOG_END + "-e-doAfterReturning:[{}]", ret.hashCode());
        }
        tranFlowPoThreadLocal.remove();
    }

    /**
     * 记录请求信息
     *
     * @param request HttpServletRequest
     */
    private void doVisitLog(HttpServletRequest request) {
        //log.warn(request.get);
        //获得客户机信息
        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
        String requestUri = request.getRequestURI();//得到请求的资源
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        //
        String remoteAddress = request.getRemoteAddr();//得到来访者的IP地址
        String remoteHost = request.getRemoteHost();
        Integer remotePort = request.getRemotePort();
        String remoteUser = request.getRemoteUser();

        String requestMethod = request.getMethod();//得到请求URL地址时使用的方法
        String pathInfo = request.getPathInfo();
        String localAddress = request.getLocalAddr();//获取WEB服务器的IP地址
        String localName = request.getLocalName();//获取WEB服务器的主机名

        //
        String userAgentString = request.getHeader("User-Agent");
        //UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

//         手机机型,操作系统,浏览器
//        Matcher matcher = SYSTEM_PATTERN.matcher(userAgent.toString());
//        String model = null;
//        if (matcher.find()) {
//            model = matcher.group(1).trim();
//        }
//        String os = userAgent.getOperatingSystem() + "(" + model + ")";
//        String browser = userAgent.getBrowser() + "";
        // 获取各项参数②
        String ip = getIpAddress(request);

        Login login = new Login();
        login.setVisitIp(ip);
        login.setVisitUrl(requestUrl);
        login.setRequestUri(requestUri);
        login.setQueryString(queryString);
        login.setRemoteAddress(remoteAddress);
        login.setRemoteHost(remoteHost);
        login.setRemotePort(remotePort.toString());
        login.setRemoteUser(remoteUser);
        login.setRequestMethod(requestMethod);
        login.setPathInfo(pathInfo);
        login.setLocalAddress(localAddress);
        login.setLocalName(localName);
//        login.setOs(os);
//        login.setBrowser(browser);
        login.setDateCreate(new Date());
        //visitLog.setUserId();
        //TODO IP换地址
        //visitLog.setVisitAddress();
        tranFlowPoThreadLocal.set(login);
    }

    /**
     * 从请求头中获取ip地址
     *
     * @param request HttpServletRequest
     * @return IP
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = null == inetAddress ? "ip->>>null" : inetAddress.getHostAddress();
            }
        }
        return ip;
    }
}
