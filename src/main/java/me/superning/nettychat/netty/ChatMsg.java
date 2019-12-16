package me.superning.nettychat.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @author superning
 */
@Data
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = -2696947097790719337L;

    private Long senderId;
    private Long receieverId;
    private String msg;
    private Long msgId;




}
