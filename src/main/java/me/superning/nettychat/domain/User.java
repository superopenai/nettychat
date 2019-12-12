package me.superning.nettychat.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="me-superning-nettychat-domain-User")
@Data
@Table(name = "`user`")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="null")
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(value="null")
    private String username;

    @Column(name = "`password`")
    @ApiModelProperty(value="null")
    private String password;

    @Column(name = "face_image")
    @ApiModelProperty(value="null")
    private String faceImage;

    @Column(name = "face_image_big")
    @ApiModelProperty(value="null")
    private String faceImageBig;

    @Column(name = "nickname")
    @ApiModelProperty(value="null")
    private String nickname;

    @Column(name = "qrcode")
    @ApiModelProperty(value="null")
    private byte[] qrcode;

    /**
     * client_id
     */
    @Column(name = "cid")
    @ApiModelProperty(value="client_id")
    private String cid;

    private static final long serialVersionUID = 1L;
}