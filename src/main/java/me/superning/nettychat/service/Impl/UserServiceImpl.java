package me.superning.nettychat.service.Impl;

import com.google.zxing.WriterException;
import me.superning.nettychat.domain.*;
import me.superning.nettychat.domain.vo.FriendRequestsVo;
import me.superning.nettychat.mapper.*;
import me.superning.nettychat.utils.FastDfsClient;
import me.superning.nettychat.utils.FileUtils;
import me.superning.nettychat.utils.SHAUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.superning.nettychat.utils.*;

import me.superning.nettychat.enums.*;

import me.superning.nettychat.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * @author superning
 */
@Service
public class UserServiceImpl implements UserService {

    public qrCodeGener qrCodeGener;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendlistMapper friendlistMapper;
    @Autowired
    private FriendsRequestMapper friendsRequestMapper;



    @Autowired
    FastDfsClient fastDfsClient;



    /**
     * @param username : 用户名
     * @param password : 密码
     * @return 返回你登录的用户
     * @description 根据用户名和密码登录
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User getLoginUser(String username, String password) {
        try {
            String encodePass = SHAUtils.getSHA512(password);
            Example userExample = new Example(User.class);
            userExample.createCriteria().andEqualTo("username", username);
            List<User> userList = userMapper.selectByExample(userExample);
            if (userList.get(0).getPassword().equals(encodePass)) {
                return userList.get(0);
            } else {
                return null;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param id 主键ID
     * @return
     */
    private User selectUserById(Long id) {
        return (User) userMapper.selectByPrimaryKey(id);

    }


    /**
     * @param username 用户名
     * @param passwd   密码
     * @return User
     * @descripton 注册一个新用户
     */

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User registUser(String username, String passwd) {
        User newUser = new User();

        try {
            newUser.setUsername(username);
            newUser.setPassword(SHAUtils.getSHA512(passwd));
            newUser.setFaceImage("");
            newUser.setFaceImageBig("");
            //TODO 二维码
            newUser.setQrcode(qrcode(username));
            // nickname默认和username相同，
            newUser.setNickname(username);

             userMapper.insertSelective(newUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;

    }


    /**
     * @param userImage 上传图片
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void uploadImage(UserImage userImage) {

        try {
            String baseData = userImage.getBaseData();
            String userFacePath = "C:\\" + userImage.getId() + "userface.png";

            MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);


            FileUtils.base64ToFile(userFacePath, baseData);
            String bigurl = fastDfsClient.uploadBase64(multipartFile);


            System.out.println(bigurl);
            String thump = "_80x80";

            String[] split = bigurl.split("\\.");

            String smallurl = split[0] + thump + split[1];
            User user = selectUserById(userImage.getId());
            user.setFaceImage(smallurl);
            user.setFaceImageBig(bigurl);
            userMapper.updateByPrimaryKey(user);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 生成二维码图片字节数组
     * @return 图片的字节数组
     * @throws IOException
     * @throws WriterException
     */
    @Override
    public byte[] qrcode(String username) throws IOException, WriterException {
        qrCodeGener qrCodeGener = new qrCodeGener();

        ByteArrayOutputStream byteArrayOutputStream = qrCodeGener.qdcodeGengerator(username);
        return byteArrayOutputStream.toByteArray();
    }


    /**
     *  把字节数组生成图片到本地
     * @param id
     *        主键id
     * @throws IOException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void convertBlob(Long id) throws IOException {
            User user = (User) userMapper.selectByPrimaryKey(id);
            String tag = user.getUsername();

            File file = new File("F:\\qrcode_" + tag+".jpg");

//        byte[] byteArray = qrCodeGener.qdcodeGengerator(user.getUsername()).toByteArray();
            byte[] qrcode = user.getQrcode();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(qrcode);
            fileOutputStream.flush();
            fileOutputStream.close();


        }


    /**
     *  加好友的枚举情况
      * @param myId
     * @param username
     *          朋友的用户名
     * @return
     */
    @Override
    public Integer findFriendByUsername(long myId,String username) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        User friend = (User) userMapper.selectOneByExample(example);
        if (friend == null)
        {
            return SeachFriendStatusEnums.USER_NOT_EXIST.status;

        }
        if (friend.getId()==myId)
        {
            return  SeachFriendStatusEnums.NOT_YOURSELF.status;

        }

        Example frilendListExample = new Example(Friendlist.class);

        frilendListExample.createCriteria().andEqualTo("myId",myId);
        List<Friendlist> friendlists = friendlistMapper.selectByExample(frilendListExample);
        for (Friendlist friendlist : friendlists) {
            if (friend.getId().equals(friendlist.getId()))
            {
                return SeachFriendStatusEnums.ALREADY_FRIENDS.status;

            }
        }
        return SeachFriendStatusEnums.SUCCESS.status;
    }


    /**
     *
     * @param username
     *         用户名
     * @return
     *
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserByname(String username) {
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("username", username);
        return (User) userMapper.selectOneByExample(userExample);
    }


    /**
     *发送加好友请求
     * @param myid
     *
     * @param username
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
    public void sendFriendRequest(long myid, String username) {
        if (findFriendByUsername(myid, username).equals(SeachFriendStatusEnums.SUCCESS.status))
        {
            Example requestExample = new Example(FriendsRequest.class);
            requestExample.createCriteria().andEqualTo("sendUserId",myid).andEqualTo("acceptUserId",findUserByname(username).getId());
            FriendsRequest friendsRequest = (FriendsRequest) friendsRequestMapper.selectOneByExample(requestExample);
            if (friendsRequest==null)
            {
                FriendsRequest newFriendsRequest = new FriendsRequest();
                newFriendsRequest.setSendUserId(myid);
                newFriendsRequest.setAcceptUserId(findUserByname(username).getId());
                friendsRequestMapper.insertUseGeneratedKeys(newFriendsRequest);
            }





        }



    }

    /**
     * @param acceptUserId
     *         接收方的id
     * @return
     *  +------------+--------------+---------------+--------------+
     * | sendUserId | sendUsername | sendFaceImage | sendNickname |
     * +------------+--------------+---------------+--------------+
     * |          1 | test         |               | test         |
     * +------------+--------------+---------------+--------------+
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FriendRequestsVo> listAllToMeRequest(Long acceptUserId) {

        return userMapper.listAllToMeRequest(acceptUserId);
    }


    /**
     *
     * @param username
     *         通过用户名查询有没有这给用户
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean findone(String username) {

        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("username", username);

        List<User> userList = userMapper.selectByExample(userExample);

        return userList != null;


    }





}
