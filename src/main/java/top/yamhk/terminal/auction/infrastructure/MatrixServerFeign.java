package top.yamhk.terminal.auction.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.web.PlayerResponse;

/**
 * http://120.79.21.103:8085
 * http://127.0.0.1:8085
 *
 * @Author: YingX
 * @Date: 2021/4/21 13:16
 */
@FeignClient(value = "matrix", url = "http://127.0.0.1:8085")
public interface MatrixServerFeign {
    /**
     * 客户端登陆
     *
     * @param owner owner
     * @return List
     */
    @GetMapping("/v1/auction/players")
    MatrixResponse<PlayerResponse> getPlayers(@RequestParam String owner);
}
