package top.yamhk.terminal.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yamhk.core.base.utils.Dates;
import top.yamhk.core.kernel.ErrorCodes;
import top.yamhk.core.kernel.MatrixResponse;
import top.yamhk.terminal.irepo.ParticipationJpaRepo;
import top.yamhk.terminal.web.jpa.datamodel.src.ParticipationPo;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: YamHK
 * @Date: 2020/9/24 18:36
 */
@Slf4j
@RestController
@RequestMapping("/v1/sign-in")
public class SignInApi {

    /**
     * signIn
     */
    public final static Cache<String, Set<String>> signInHistory = CacheBuilder.newBuilder()
            //初始大小
            .initialCapacity(30)
            .concurrencyLevel(5)
            .expireAfterWrite(365, TimeUnit.DAYS)
            .build();
    /**
     * jpa
     */
    @Autowired
    private ParticipationJpaRepo jpaRepo;

    /**
     * 清空记录
     *
     * @return Response
     */
    @DeleteMapping
    public MatrixResponse delete() {
        String username = AgentThreadLocal.convertToPo().getUsername();
        HashSet<String> set = Sets.newHashSet();
        signInHistory.put(username, set);
        return MatrixResponse.ok().withData(set).withMessage("清除成功");
    }

    /**
     * 记录
     *
     * @return Response
     */
    @GetMapping
    public MatrixResponse record() {
        String username = AgentThreadLocal.convertToPo().getUsername();
        Set<String> signed = getSignHistory(username);//record
        //UnsupportedOperationException
        return MatrixResponse.ok().withData(signed).withMessage("查询成功");
    }

    /**
     * 签到
     *
     * @return String
     */
    @PostMapping
    public MatrixResponse signIn(@RequestBody SignInRequest request) {
        Player userPo = AgentThreadLocal.convertToPo();

        Set<String> signed = getSignHistory(userPo.getUsername());//signIn

        //签到日期-入库
        String signDate = request.isResign() ? Dates.formatDate(Dates.parseDate(request.getSignDate())) : Dates.formatDate(Dates.now());
        //判重
        if (signed.contains(signDate)) {
            return MatrixResponse.ng().withMessage("重复签到");
        }
        //数据库
        ParticipationPo participationPo = new ParticipationPo();
        participationPo.setDate(signDate);
        participationPo.setCreateTime(request.signTime);
        participationPo.setCreateBy(userPo.getUsername());
        ParticipationPo save = jpaRepo.save(participationPo);
        log.warn("保存签到记录{}", save.getId());
        //缓存
        signed.add(signDate);
        signInHistory.put(userPo.getUsername(), signed);
        //响应
        return MatrixResponse.ok().withData(signed).withMessage(request.isResign() ? "补签成功" : "签到成功");
    }

    /**
     * 获得签到记录
     *
     * @param username username
     * @return Set
     */
    private Set<String> getSignHistory(String username) {
        try {
            Set<String> signed = signInHistory.get(username, () -> Collections.singleton(""));
            //构建新集合
            Set<String> response = Sets.newHashSet(signed);
            response.remove("");
            if (response.isEmpty()) {
                List<ParticipationPo> byCreateBy = jpaRepo.findByCreateBy(username);
                log.warn("{}参与记录个数{}", username, byCreateBy.size());
                return byCreateBy.stream().map(ParticipationPo::getDate).collect(Collectors.toSet());
            }
            return response;
        } catch (ExecutionException e) {
            log.error("", e);
            throw new RuntimeException(ErrorCodes.DATA_NOT_EXISTS.getMessage());
        }
    }

    /**
     *
     */
    @Data
    private static class SignInRequest {
        /**
         * 补签日期
         */
        @ApiModelProperty("补签日期")
        private String signDate;
        /**
         * 签到日期
         */
        @JsonIgnore
        private Date signTime = Dates.now();

        /**
         * 判断是否补签
         *
         * @return boolean
         */
        public boolean isResign() {
            return StringUtils.isNotBlank(this.signDate) && Dates.parseDate(this.signDate).before(Dates.today());
        }
    }
}
