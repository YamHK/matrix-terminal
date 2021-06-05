package top.yamhk.terminal.www;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.kernel.MatrixResponse;

/**
 * @Author: YamHK
 * @Date: 2020/10/8 16:38
 */
@Slf4j
@RestController
@RequestMapping("/v1/we-chat")
public class WeChatApi {

    /**
     * 查看服务器状态
     *
     * @return Response
     */
    @GetMapping
    public MatrixResponse showServerStatus() {
        return MatrixResponse.ok();
    }
}
