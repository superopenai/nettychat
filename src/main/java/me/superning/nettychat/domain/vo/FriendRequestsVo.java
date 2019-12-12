package me.superning.nettychat.domain.vo;

import lombok.Data;

/**
 * 好友请求发送方的信息
 * @author superning
 *  +------------+--------------+---------------+--------------+
 * | sendUserId | sendUsername | sendFaceImage | sendNickname |
 * +------------+--------------+---------------+--------------+
 * |          1 | test         |               | test         |
 * +------------+--------------+---------------+--------------+
 */

@Data
public class FriendRequestsVo {

    private Long sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;





}
