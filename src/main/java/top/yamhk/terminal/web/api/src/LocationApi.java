package top.yamhk.terminal.web.api.src;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.JsonUtils;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.LocationJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.jpa.datamodel.src.LocationPo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2019/4/14 19:03
 */
@Slf4j
@Api(value = "locations", tags = {"locations"}, description = "记录地理坐标")
@RestController
@RequestMapping(value = "/v1/locations")
public class LocationApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private LocationJpaRepo jpaRepo;


    @ApiOperation(value = "查看同位置的记录", notes = "正在处理经纬度范围计算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", paramType = "path"),
            @ApiImplicitParam(name = "latitude", paramType = "path")
    })
    @GetMapping("/this-place")
    public MatrixResponse getThisLocation(String longitude, String latitude) {
        log.warn("addLocation-user:" + longitude + ">request:" + latitude);
        MatrixResponse matrixResponse = MatrixResponse.ok();
        matrixResponse.setData("实现中");
        return matrixResponse;
    }

    @ApiOperation(value = "查看我的位置", notes = "查看我的位置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", paramType = "path"),
            @ApiImplicitParam(name = "latitude", paramType = "path")
    })
    @GetMapping("/{username}")
    public MatrixResponse getMyLocation() {
        MatrixResponse matrixResponse = MatrixResponse.ok();
        matrixResponse.setData(jpaRepo.findByPlayer(AgentThreadLocal.convertToPo()));
        return matrixResponse;
    }

    @ApiOperation(value = "记录位置以及附带信息", notes = "暂只是支持文字录入")
    @PostMapping
    public MatrixResponse addLocation(@RequestBody @Valid LocationCreateRequest request) {
        String userName = AgentThreadLocal.get().getUsername();
        log.warn("addLocation-user:" + userName + ">request:" + request);
        /*取参*/
        /*赋值*/
        /*实例赋值*/
        LocationPo po = new LocationPo();
        BeanCopyUtils.copyProperties(request, po);
        //
        po.setItemType("位置信息");
        po.setCreateTime(new Date(Calendar.getInstance().getTimeInMillis()));
        //
        MatrixResponse matrixResponse = MatrixResponse.ok();
        matrixResponse.setData(JsonUtils.toJson(jpaRepo.save(po)));
        return matrixResponse;
    }

    /**
     * 单个删除
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByLocationId(@PathVariable("id") Long id) {
        Optional<LocationPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        LocationPo save = jpaRepo.save((LocationPo) byId.get().logicDelete());
        log.warn("删除记录>id:" + save.getId());
        return MatrixResponse.ok().withMessage("删除成功");
    }

    /**
     * 查询-所有
     *
     * @return List
     */
    @ApiOperation(value = "获取所有记录", notes = "just notes")
    @GetMapping
    public MatrixResponse getAllLocaltion() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<LocationPo> all = jpaRepo.findAll();
        List<LocationPo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (LocationPo) e.convertToResponse(LocationPo.class))
                .sorted(Comparator.comparing(LocationPo::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }

    @Data
    private static class LocationCreateRequest {
        /**
         * 经度
         */
        @NotEmpty
        @ApiModelProperty("经度")
        private String longitude;
        /**
         * 纬度
         */
        @NotEmpty
        @ApiModelProperty("经度")
        private String latitude;
        /**
         * 海拔
         */
        @ApiModelProperty("海拔")
        private String altitude;
        /**
         * 内容
         */
        @ApiModelProperty("内容")
        private String words;
        /**
         * 图片
         */
        @ApiModelProperty("图片")
        private String picture;
    }
}

