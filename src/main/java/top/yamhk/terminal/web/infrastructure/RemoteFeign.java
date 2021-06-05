package top.yamhk.terminal.web.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/8/12 9:02
 */
@FeignClient(name = "server", url = "http://120.79.21.103:8085/")
public interface RemoteFeign {

    /**
     * 获取公众号关注用户
     *
     * @return String
     */
    @GetMapping("/v1/menu")
    String doPostTest(FeignRequest request);

    @Data
    class FeignRequest {
        /**
         * 上级菜单编号
         */
        @NotNull
        private String originId = "-1";
        /**
         * 菜单名称
         */
        @NotBlank
        private String menuName = "test";
        /**
         * 菜单路径
         */
        @NotBlank
        private String menuUrl = "www.baidu.com";
        /**
         * 编号
         */
        private String itemId;
        /**
         * 名称
         */
        private String itemName;
        /**
         * 描述
         */
        private String itemDesc;
        /**
         * 备注
         */
        private String itemRemark;
        /**
         * list
         */
        @JsonProperty("Rmk")
        private List<Remarks> remarks;
    }

    @Data
    class Remarks {
        @JsonProperty("USERNAME")
        String username = "a";
        @JsonProperty("hw")
        String helloWorld = "aa";
    }
}
