package me.superning.nettychat.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="me-superning-nettychat-domain-Friendlist")
@Data
@Table(name = "Friendlist")
public class Friendlist implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="null")
    private Long id;

    @Column(name = "my_id")
    @ApiModelProperty(value="null")
    private Long myId;

    @Column(name = "my_friend_id")
    @ApiModelProperty(value="null")
    private Long myFriendId;

    private static final long serialVersionUID = 1L;
}