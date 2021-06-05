package top.yamhk.terminal.web;

import lombok.Data;

import java.util.Date;

/**
 * @author YamHK
 * @Date: 2019/5/9 17:45
 */
@Data
public class Login {
    private Long id;
    private Integer userId;
    private String visitUrl;
    private String visitIp;
    private String visitAddress;
    private String os;
    private String browser;
    private Date dateCreate;
    private String requestUri;
    private String queryString;
    /**
     *
     */
    private String remoteAddress;
    private String remoteHost;
    private String remotePort;
    private String remoteUser;

    private String requestMethod;
    private String pathInfo;
    private String localAddress;
    private String localName;
}
