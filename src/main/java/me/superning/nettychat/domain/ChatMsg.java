package me.superning.nettychat.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="me-superning-nettychat-domain-ChatMsg")
@Data
@Table(name = "chat_msg")
public class ChatMsg implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="null")
    private Long id;

    @Column(name = "send_user_id")
    @ApiModelProperty(value="null")
    private Long sendUserId;

    @Column(name = "accept_user_id")
    @ApiModelProperty(value="null")
    private Long acceptUserId;

    @Column(name = "message")
    @ApiModelProperty(value="null")
    private String message;

    @Column(name = "sign_flag")
    @ApiModelProperty(value="null")
    private Boolean signFlag;

    @Column(name = "create_time")
    @ApiModelProperty(value="null")
    @JSONField(format = "yyyy-mm-dd hh:mm:ss")
    private Date createTime;

    private static final long serialVersionUID = 1L;

}