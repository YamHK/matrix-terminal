package top.yamhk.terminal.web;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: YingX
 * @Date: 2021/4/19 21:02
 */
@Data
@Builder
public class PlayerRequest {
    String owner;
    String username;
    String password;
    String nickname;
    List<Integer> restTime;
    private boolean vip;
    private boolean available;
}