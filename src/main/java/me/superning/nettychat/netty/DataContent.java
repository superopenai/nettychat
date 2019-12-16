package me.superning.nettychat.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @author superning
 */
@Data
public class DataContent implements Serializable {


    private static final long serialVersionUID = -3849734848171774035L;


    private Integer action; //动作类型
    private ChatMsg chatMsg;//聊天内容
    private String extand; //扩展字段



}
