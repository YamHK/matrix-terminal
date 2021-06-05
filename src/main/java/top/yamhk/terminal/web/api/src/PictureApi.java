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
import top.yamhk.core.kernel.BeanCopyUtils;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.AlbumJpaRepo;
import top.yamhk.terminal.irepo.PictureJpaRepo;
import top.yamhk.terminal.web.AgentThreadLocal;
import top.yamhk.terminal.web.Player;
import top.yamhk.terminal.web.api.request.PictureCreateRequest;
import top.yamhk.terminal.web.api.response.PictureResponse;
import top.yamhk.terminal.web.jpa.datamodel.src.AlbumPo;
import top.yamhk.terminal.web.jpa.datamodel.src.PicturePo;

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
@Api(value = "pictures", tags = {"pictures"}, description = "图片维护")
@RestController
@RequestMapping("/v1/pictures")
public class PictureApi {
    /**
     * jpa
     */
    @Autowired(required = false)
    private PictureJpaRepo jpaRepo;
    /**
     * 相册JPA
     */
    @Autowired(required = false)
    private AlbumJpaRepo albumJpaRepo;

    @ApiOperation(value = "新增图片", notes = "单张")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "PictureCreateRequest", paramType = "body")
    @PostMapping
    public MatrixResponse savePicture(@RequestBody @Valid PictureCreateRequest request) {
        PicturePo po = new PicturePo();
        BeanCopyUtils.copyProperties(request, po);
        //
        Optional<AlbumPo> albumPo = albumJpaRepo.findByAlbumId(request.getAlbumId());
        if (!albumPo.isPresent()) {
            return MatrixResponse.ng().withMessage("相册异常");
        }
        //
        Player userPo = AgentThreadLocal.convertToPo();
        //
        PicturePo save = jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("新增成功").withData(save.getId());
    }

    @ApiOperation(value = "修改图片", notes = "单张")
    @ApiImplicitParam(name = "request", value = "请求参数", dataType = "PictureCreateRequest", paramType = "body")
    @PutMapping("/{id}")
    public MatrixResponse updatePicture(@RequestBody @Valid PictureCreateRequest request) {
        //
        Optional<PicturePo> byId = jpaRepo.findById(request.getId());
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        PicturePo po = byId.get();
        //
        Player userPo = AgentThreadLocal.convertToPo();
        //
        BeanCopyUtils.copyProperties(request, po);
        jpaRepo.save(po);
        return MatrixResponse.ok().withMessage("修改成功");
    }

    @ApiOperation(value = "查看单张图片", notes = "单张")
    @GetMapping("/{id}")
    public MatrixResponse<PictureResponse> getPicture(@PathVariable("id") Long id) {
        Optional<PicturePo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.ng().withMessage("图片不存在");
        }
        PicturePo po = byId.get();
        PictureResponse response = new PictureResponse();
        BeanCopyUtils.copyProperties(po, response);
        return MatrixResponse.ok().withData(response);
    }

    /**
     * 单个删除
     *
     * @param id id
     * @return Response
     */
    @ApiOperation(value = "删除", notes = "单个")
    @DeleteMapping("/{id}")
    public MatrixResponse deleteByMsgId(@PathVariable("id") Long id) {
        Optional<PicturePo> byId = jpaRepo.findById(id);
        if (!byId.isPresent()) {
            return MatrixResponse.code(ErrorCodes.DATA_NOT_EXISTS);
        }
        PicturePo save = jpaRepo.save((PicturePo) byId.get().logicDelete());
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
    public MatrixResponse getAllPicture() {
        //登录用户信息
        Player userPo = AgentThreadLocal.convertToPo();
        //查询-所有
        List<PicturePo> all = jpaRepo.findAll();
        List<PicturePo> responses = all.stream()
                .filter(e -> e.visible(userPo))
                .map(e -> (PicturePo) e.convertToResponse(PicturePo.class))
                .sorted(Comparator.comparing(PicturePo::getId).reversed())
                .collect(Collectors.toList());
        log.warn("{}:查询记录{}/{}", userPo.getUsername(), responses.size(), all.size());
        return MatrixResponse.ok().withData(responses);
    }
}
