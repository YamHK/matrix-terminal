package top.yamhk.terminal.web.api.src;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.AlbumJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.MajorServerApi;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.AlbumCreateRequest;
import top.yamhk.terminal.web.api.response.AlbumResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.AlbumPo;
import top.yamhk.terminal.web.jpa.domain.Album;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2019/11/3 21:44
 */
@Slf4j
@Api(value = "albums", tags = {"albums"}, description = "相册维护")
@RestController
@RequestMapping("/v1/albums")
public class AlbumApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private AlbumJpaRepo jpaRepo;
    /**
     * userInfo-Jpa
     */
    @Autowired
    private MajorServerApi userInfoJpaRepo;

    @ApiOperation(value = "新增相册", notes = "单个")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "AlbumCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse saveAlbum(@RequestBody @Valid AlbumCreateRequest request) {
        //
        Player userPo = AgentThreadLocal.convertToPo();
        //验重
        if (judgeRepeat(request, userPo)) {
            return MatrixResponse.ng().withMessage("名下存在相同参数");
        }
        //
        AlbumPo po = AlbumPo.builder().build();
        BeanCopyUtils.copyProperties(request, po);
        po.setCreateTime(Dates.now());
        po.setCreateBy(userPo.getNickname());
        po.setDelFlag("0");
        //
        jpaRepo.save(po);
        //
        po.setAlbumId(Album.DEFAULT_ALBUM_CODE + po.getId());
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("创建相册成功");
    }

    @ApiOperation(value = "修改相册", notes = "单个")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "AlbumUpdateRequest", paramType = "body")
    @PutMapping("/{id}")
    public MatrixResponse<AlbumResponse> updateAlbum(@RequestBody @Valid AlbumCreateRequest request, @PathVariable Long id) {
        //
        Player userPo = AgentThreadLocal.convertToPo();
        //验重
        if (judgeRepeat(request, userPo)) {
            return MatrixResponse.ng().withMessage("名下存在相同参数");
        }
        //
        Optional<AlbumPo> alumById = jpaRepo.findById(id);
        if (!alumById.isPresent()) {
            return MatrixResponse.ng().withMessage("相册不存在");
        }
        AlbumPo po = alumById.get();
        //
        BeanCopyUtils.copyProperties(request, po);
        jpaRepo.save(po);
        //
        AlbumResponse response = new AlbumResponse();
        BeanCopyUtils.copyProperties(po, response);
        return MatrixResponse.ok().withData(response);
    }

    /**
     * 验重
     *
     * @param request request
     * @param userPo  userPo
     * @return boolean
     */
    private boolean judgeRepeat(@RequestBody @Valid AlbumCreateRequest request, Player userPo) {
        AlbumPo repeat = jpaRepo.findByPlayerAndAlbumName(userPo, request.getAlbumName());
        return repeat != null;
    }

    /**
     * 单个删除
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByAlbumId(@PathVariable("id") Long id) {
        Optional<AlbumPo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        AlbumPo save = jpaRepo.save((AlbumPo) byId.get().logicDelete());
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
    public MatrixResponse getAllAlbum() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<AlbumPo> all = jpaRepo.findAll();
        List<AlbumResponse> responses = all.stream()
                .map(e -> (AlbumResponse) e.convertToResponse(AlbumResponse.class))
                .sorted(Comparator.comparing(AlbumResponse::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}
