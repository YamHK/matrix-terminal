package top.yamhk.terminal.web.prepare;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.kernel.MatrixResponse;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
@Api(value = "Demo 【value】", tags = {"Demo 【tags】"}, description = "Demo 【description】")
@RestController
@RequestMapping(value = "/v1/demo")
public class DemoYamHKApi {
    @GetMapping
    public String index() {
        return "Hello World!";
    }

    @ApiOperation(value = "test 【value】", notes = "test 【notes】")
    @GetMapping(value = "/hello")
    public MatrixResponse home() {
        String hello = "hello world!";
        MatrixResponse matrixResponse = MatrixResponse.ok();
        String data = "hello spring boot,get username from mysql >>>" + hello + "<<<";
        matrixResponse.setData(data);
        return matrixResponse;
    }
}
