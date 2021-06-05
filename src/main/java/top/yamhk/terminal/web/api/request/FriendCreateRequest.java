package top.yamhk.terminal.web.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class FriendCreateRequest extends BaseSrcCreateRequest {
    /**
     * 好友id
     */
    @NotEmpty
    @ApiModelProperty("好友id-username")
    private String friendId;
}
