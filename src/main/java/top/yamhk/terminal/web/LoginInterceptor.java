package top.yamhk.terminal.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.base.utils.JwtUtils;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.UserPo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @Author: YamHK
 * @Date: 2019/11/4 21:29
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 用户信息查询
     */
    @Autowired
    private MajorServerApi userInfoJpaRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ExecutionException {
        //设置编码
        //设置将字符以"UTF-8"编码输出到客户端浏览器
        response.setCharacterEncoding("UTF-8");
        //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
        response.setHeader("content-type", "text/html;charset=UTF-8");
        //跨域问题-同源策略
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        //获取token+校验
        String token = request.getHeader("accessToken");
        if (token == null) {
            log.warn("拦截到未登录用户 with no token@{},{}", request.getRequestURI(), request.getRequestURL());
            //没有登录用户信息，请先登录！
            response.getWriter().write(JsonUtils.toJson(MatrixResponse.code(ErrorCodes.USER_NO_LOGIN)));
            return false;
        }
        //判断是否白名单
        boolean containsWhiteList = JwtUtils.isContainsWhiteList(token);
        //用户信息
        Optional<UserInfo> userInfo = Optional.empty();
        //白名单
        if (containsWhiteList) {
            userInfo = getAndCacheUserInfo(token);
            //cache into threadLocal
            AgentThreadLocal.set(userInfo.get());
            return true;
        }
        //判断token是否有效
        boolean verify = JwtUtils.verify(token);
        //双非
        if (!verify) {
            log.warn("login interceptor NO");
            response.getWriter().write(JsonUtils.toJson(MatrixResponse.code(ErrorCodes.USER_LOGIN_EXPIRED)));
            return false;
        }
        //正常登录用户
        log.warn("login interceptor YES");
        String username = JwtUtils.getUsername(token);
        userInfo = UserInfoPool.USER_CASH.get(username);
        //服务重启自动登录
        if (!userInfo.isPresent()) {
            //cache
            log.warn("token有效-1-自动登录-加入当前线程");
            userInfo = getAndCacheUserInfo(username);
        }
        //cache into threadLocal
        AgentThreadLocal.set(userInfo.get());
        return true;
    }

    /**
     * 从数据库查询出用户信息，放入当前线程以及用户池
     *
     * @param username username
     */
    private Optional<UserInfo> getAndCacheUserInfo(String username) {
        UserInfo userInfo;
        userInfo = getUserInfoByUsername(username);
        UserInfoPool.USER_CASH.put(username, Optional.of(userInfo));
        return Optional.of(userInfo);
    }

    /**
     * 获取用户信息实体
     *
     * @param username username
     * @return UserInfo
     */
    private UserInfo getUserInfoByUsername(String username) {
        //校验用户名密码
        String byUsername = userInfoJpaRepo.findByUsername(username);
        UserPo po = JsonUtils.GSON.fromJson(byUsername, UserPo.class);
        //深拷贝还是浅拷贝-failed to lazily initialize a collection of
        UserInfo userInfo = new UserInfo();
        BeanCopyUtils.copyProperties(po, userInfo);
        return userInfo;
    }
}
