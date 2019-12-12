package me.superning.nettychat.service;

import com.google.zxing.WriterException;
import me.superning.nettychat.domain.User;
import me.superning.nettychat.domain.UserImage;
import me.superning.nettychat.domain.vo.FriendRequestsVo;


import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UserService{
    /**
     *  找有没有这个用户
     * @param username
     *          用户名
     * @return
     */
    boolean findone(String username);

    /**
     * 用户登录
     * @param username
     *          用户名
     * @param password
     *          密码
     * @return
     */
    User getLoginUser (String username, String password);

    /**
     * 注册用户
     * @param username
     *         用户名
     * @param passwd
     *          密码
     * @return
     */
    User registUser(String username, String passwd);

    /**
     * 上传图片到文件服务器
     * @param userImage
     */
    void uploadImage(UserImage userImage);

    /**
     * 根据个人生成个人二维码
     *
     * @param name
     *         用户名
     * @return
     *          返回二维码图片的字节数组
     * @throws IOException
     * @throws WriterException
     */
    byte[] qrcode (String name) throws IOException, WriterException;

    /**
     * 转换图片
     * @param id
     *        主键id
     * @return
     *          一张图片
     * @throws IOException
     * @throws WriterException
     */
    void convertBlob(Long id) throws IOException, WriterException;

    /**
     *
     * @param myid
     *          自己的id
     * @param username
     *          朋友的用户名
     * @return
     */
    Integer findFriendByUsername(long myid, String username);

    /**
     * 根据名字找人
     * @param username
     *         用户名
     * @return
     */
    User findUserByname(String username);

    /**
     * 发送加好友请求
     * @param myid
     * @param username
     */
    void sendFriendRequest(long myid, String username);


    /**
     * 加我好友的请求的人的中间list
     * @param acceptUserId
     *          接收者也就是你
     * @return
     *  +------------+--------------+---------------+--------------+
     * | sendUserId | sendUsername | sendFaceImage | sendNickname |
     * +------------+--------------+---------------+--------------+
     * |          1 | test         |               | test         |
     * +------------+--------------+---------------+--------------+
     */
    List<FriendRequestsVo> listAllToMeRequest(Long acceptUserId);








}
