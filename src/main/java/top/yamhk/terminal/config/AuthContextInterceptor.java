package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.yamhk.terminal.web.AuthThreadLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YingX
 * @Date: 2021/4/22 22:38
 */
@Slf4j
public class AuthContextInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String uid = request.getHeader("uid");
        AuthThreadLocal.set(uid);
        return super.preHandle(request, response, handler);
    }
}
