package top.yamhk.terminal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: YamHK
 * @Date: 2019/12/10 16:58
 */
@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /**
     * 默认异常处理
     *
     * @param request request
     * @param e       e
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public MatrixResponse defaultExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("前端请求参数-{}", request.toString());
        log.error("系统未知异常-->", e);
        return MatrixResponse.code(ErrorCodes.UN_KNOW_ERROR).withMessage(e.getMessage());
    }
}
