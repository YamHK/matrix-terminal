package top.yamhk.terminal.www;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.kernel.MatrixResponse;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: YamHK
 * @Date: 2020/9/24 22:00
 */
@RestController
@RequestMapping("/v1/draw")
public class DrawApi {
    /**
     * 最大号码
     */
    final long MAX_LUCK_NUMBER = 99990000L;

    /**
     * 抽奖
     *
     * @return Response
     */
    @PostMapping
    public MatrixResponse draw() {
        long anyThing = ThreadLocalRandom.current().nextLong(2);
        return MatrixResponse.ok().withData(anyThing).withMessage(Long.toString(Long.MAX_VALUE));
    }
}
