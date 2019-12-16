package me.superning.nettychat.enums;

/**
 * @author superning
 */

public enum MsgActionEnum {
    CONNECT(1,"初始化(重连)连接"),
    CHAT(2,"聊天消息"),
    SIGNED(3,"消息已经接收"),
    KEEPALIVE(4,"心跳检测"),
    PULL_FRIEND(5, "拉取好友");



    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }
    public Integer getType() {
        return type;
    }
}
