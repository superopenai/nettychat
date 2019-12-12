package me.superning.nettychat.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="me-superning-nettychat-domain-FriendsRequest")
@Data
@Table(name = "friends_request")
public class FriendsRequest implements Serializable {
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

    @Column(name = "request_date_time")
    @ApiModelProperty(value="null")
    private Date requestDateTime;

    private static final long serialVersionUID = 1L;
}