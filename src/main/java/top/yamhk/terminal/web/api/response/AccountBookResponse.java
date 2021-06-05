package top.yamhk.terminal.web.api.response;

import lombok.Data;
import top.yamhk.terminal.web.jpa.datamodel.src.AccountBookPo;

import java.util.List;

@Data
public class AccountBookResponse {
    /**
     * 账本清单
     */
    List<AccountBookPo> accountBooks;
    /**
     * 开支总和
     */
    private Double sum;
    /**
     * 月份
     */
    private String month;
}
