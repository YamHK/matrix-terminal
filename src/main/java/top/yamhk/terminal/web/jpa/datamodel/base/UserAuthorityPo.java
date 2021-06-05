package top.yamhk.terminal.web.jpa.datamodel.base;

import lombok.Data;
import top.yamhk.boot.MatrixTable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author YamHK
 * @Date: 2019/5/9 17:45
 */
@Data
@Entity
@MatrixTable(name = "hk_base_user_authority")
public class UserAuthorityPo {
    @Id
    private int authId;
    private Integer userId;
    private String identityType;
    private String identifier;
    private String credential;
}
